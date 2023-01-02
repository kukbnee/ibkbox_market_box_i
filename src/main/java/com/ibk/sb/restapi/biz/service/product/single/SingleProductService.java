package com.ibk.sb.restapi.biz.service.product.single;

import com.ibk.sb.restapi.app.common.constant.ComCode;
import com.ibk.sb.restapi.app.common.util.FileUtil;
import com.ibk.sb.restapi.app.common.vo.CustomUser;
import com.ibk.sb.restapi.app.common.vo.PagingVO;
import com.ibk.sb.restapi.biz.service.delivery.repo.DeliveryRepo;
import com.ibk.sb.restapi.biz.service.delivery.vo.DeliveryinfoVO;
import com.ibk.sb.restapi.biz.service.mainbox.MainBoxService;
import com.ibk.sb.restapi.biz.service.mainbox.vo.MainCompanyVO;
import com.ibk.sb.restapi.biz.service.order.repo.OrderSearchRepo;
import com.ibk.sb.restapi.biz.service.order.vo.common.OrderPdfDtlVO;
import com.ibk.sb.restapi.biz.service.patent.PatentService;
import com.ibk.sb.restapi.biz.service.product.common.repo.ProductCommonRepo;
import com.ibk.sb.restapi.biz.service.product.common.vo.*;
import com.ibk.sb.restapi.biz.service.product.single.repo.SingleProductRepo;
import com.ibk.sb.restapi.biz.service.product.single.vo.SummarySingleProductVO;
import com.ibk.sb.restapi.biz.service.seller.SellerStoreService;
import com.ibk.sb.restapi.biz.service.seller.repo.SellerStoreRepo;
import com.ibk.sb.restapi.biz.service.seller.vo.SellerInfoVO;
import com.ibk.sb.restapi.biz.service.user.vo.MyPageUserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SingleProductService {

    private final SingleProductRepo singleProductRepo;

    private final ProductCommonRepo productCommonRepo;

    private final SellerStoreService sellerStoreService;

    private final DeliveryRepo deliveryRepo;

    private final OrderSearchRepo orderSearchRepo;

    private final SellerStoreRepo sellerStoreRepo;

    private final PatentService patentService;

    private final FileUtil fileUtil;

    private final MainBoxService mainBoxService;

    /**
     * 제품 리스트 조회
     * @param requestProductInfoSearchVO
     * @return
     * @throws Exception
     */
    public PagingVO<SummarySingleProductVO> searchSingleProductList(RequestSearchProductVO requestProductInfoSearchVO) throws Exception {

        /*
         * 로그인 체크
         *      로그인 정보가 CustomUser.class 라면 jwt 로그인 조회
         *      로그인 상태가 아니라면 String 타입으로 떨이짐
         */
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof CustomUser) {
            CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            requestProductInfoSearchVO.setLoginUserId(user.getLoginUserId());
            requestProductInfoSearchVO.setLoginUtlinsttId(user.getUtlinsttId());
        }

        // 카테고리 검색용 조건 셋팅
        requestProductInfoSearchVO.setCategoryCondition(requestProductInfoSearchVO);

        // 판매중인 상품만 검색하도록 수정_ 20221109 seonggil
        requestProductInfoSearchVO.setAdminSearchStatus(false);
        requestProductInfoSearchVO.setPdfSttsId(ComCode.SELLING_OK.getCode());

        // 상품 리스트 조회
        List<SummarySingleProductVO> singleProductVOList = singleProductRepo.selectSingleProductList(requestProductInfoSearchVO);

        // 상품 이미지 셋팅
        if (singleProductVOList.size() > 0) {
            singleProductVOList.forEach(x -> {
                x.setImgUrl(fileUtil.setImageUrl(x.getImgFileId()));
            });

        }

        return new PagingVO<>(requestProductInfoSearchVO, singleProductVOList);

     }

    /**
     * 제품 상세 조회(상품 상세화면)
     * @param requestSearchProductVO
     * @return
     * @throws Exception
     */
    public ProductDetailVO searchSingleProduct(RequestSearchProductVO requestSearchProductVO) throws Exception {

        ProductDetailVO resultVO = new ProductDetailVO();

        // 로그인 정보 조회
        // 로그인 정보가 CustomUser.class 라면 jwt 로그인 조회
        // 로그인 상태가 아니라면 String 타입으로 떨이짐
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof CustomUser) {
            CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            requestSearchProductVO.setLoginUserId(user.getLoginUserId());
            requestSearchProductVO.setLoginUtlinsttId(user.getUtlinsttId());
        }

        // 상품정보 조회
        SummarySingleProductVO productInfo = singleProductRepo.selectSingleProduct(requestSearchProductVO);
        resultVO.setProductInfo(productInfo);

        // 상품 상세 판매자 헤더 정보 조회
        resultVO.setSellerStoreHeader(sellerStoreService.searchSellerHeaderInfo(productInfo.getSelrUsisId()));

        // 상품 파일 정보
        List<ProductFileVO> productFileVOList = productCommonRepo.selectProductFileList(productInfo.getPdfInfoId(), ComCode.GDS05001.getCode(), null);
        productFileVOList.forEach(x -> { x.setImgUrl(fileUtil.setImageUrl(x.getFileId())); });
        resultVO.setProductFileList(productFileVOList);

        // 상품 영상 정보
        List<ProductVideoVO> productVideoVOList = productCommonRepo.selectProductVideoList(productInfo.getPdfInfoId());
        productVideoVOList.forEach(x -> { x.setImgUrl(fileUtil.setImageUrl(x.getImgFileId())); });
        resultVO.setProductVideoList(productVideoVOList);

        // 상품 링크 정보
        List<ProductLinkVO> productLinkList = productCommonRepo.selectProductLinkList(productInfo.getPdfInfoId());
        resultVO.setProductLinkList(productLinkList);

        /*
         * 배송정보 셋팅
         */
        // 배송 기본 정보 조회
        DeliveryinfoVO deliveryinfoVO = deliveryRepo.selectDeliveryInfo(productInfo.getPdfInfoId());
        resultVO.setDeliveryinfo(deliveryinfoVO);

        // 무료 배송이 아닐 경우,
        if (deliveryinfoVO != null) {
            if (!deliveryinfoVO.getDvryTypeId().equals(ComCode.GDS02003.getCode())) {

                // 배송 유형이 화물 서비스 일 경우,
                if (deliveryinfoVO.getDvryTypeId().equals(ComCode.GDS02001.getCode())) {
                    // 화물서비스 견적 정보 리스트 조회
                    resultVO.setDeliveryProductServiceInfoList(deliveryRepo.selectDeliveryProductInfo(productInfo.getPdfInfoId()));
                }

                // 배송비 유형이 지역별 배송비 혹은 지역/수량별 배송비일 경우
                String dvrynonePtrnId = deliveryinfoVO.getDvrynonePtrnId();
                // 배송비 유형 ID 존재 체크
                if(StringUtils.hasLength(dvrynonePtrnId)) {
                    if (dvrynonePtrnId.equals(ComCode.GDS04002.getCode()) || dvrynonePtrnId.equals(ComCode.GDS04004.getCode())) {
                        // 상품 지역별 배송 정보 리스트 조회
                        resultVO.setDeliveryLocalInfoList(deliveryRepo.selectDeliveryLocalInfo(productInfo.getPdfInfoId()));
                    }

                    // 배송비 유형이 수량별 배송비 혹은 지역/수량별 배송비일 경우
                    if (dvrynonePtrnId.equals(ComCode.GDS04003.getCode()) || dvrynonePtrnId.equals(ComCode.GDS04004.getCode())) {
                        // 상품 수량별 배송 정보 리스트 조화
                        resultVO.setDeliveryCntInfoList(deliveryRepo.selectDeliveryCntInfo(productInfo.getPdfInfoId()));
                    }
                }
            }
        }

        /*
         * 특허 정보 셋팅
         */
        resultVO.setPatentList(patentService.searchPatentList(productInfo.getPdfInfoId()));


        /*
         * 판매자의 다른 상품 조회
         */
        // 검색조건 셋팅
        //      페이지 1
        //      검색 결과 최대 3건
        //      등록 최신순
        RequestSearchProductVO searchVO01 = new RequestSearchProductVO();
        searchVO01.setPage(1);
        searchVO01.setRecord(3);
        searchVO01.setOrderByDate("Y");
        searchVO01.setSelrUsisId(productInfo.getSelrUsisId());
        searchVO01.setLoginUserId(requestSearchProductVO.getLoginUserId());
        searchVO01.setLoginUtlinsttId(requestSearchProductVO.getLoginUtlinsttId());
        searchVO01.setRelatedProductYn("Y");
        searchVO01.setPdfInfoId(requestSearchProductVO.getPdfInfoId());
//        searchVO01.setCelebrityFlg(requestSearchProductVO.getCelebrityFlg());
//        searchVO01.setPopularFlg(requestSearchProductVO.getPopularFlg());
//        searchVO01.setOrderByDate(requestSearchProductVO.getOrderByDate());
//        searchVO01.setInquFlg(requestSearchProductVO.getInquFlg());
        searchVO01.setAdminSearchStatus(requestSearchProductVO.isAdminSearchStatus());
//        searchVO01.setPdfInfoCon(requestSearchProductVO.getPdfInfoCon());
//        searchVO01.setCategoryId(requestSearchProductVO.getCategoryId());
        searchVO01.setSelrUsisId(productInfo.getSelrUsisId());
        searchVO01.setAgencyFlg(requestSearchProductVO.getAgencyFlg());
        List<SummarySingleProductVO> sellerProductList = singleProductRepo.selectSingleProductList(searchVO01);
        sellerProductList.forEach(x -> { x.setImgUrl(fileUtil.setImageUrl(x.getImgFileId())); });
        resultVO.setSellerProductList(sellerProductList);

        /*
         * 관련상품 리스트 조회
         */
        // 검색조건 셋팅
        //      페이지 1
        //      검색 결과 최대 3건
        //      검색상품과 카테고리가 같은 상품
        //      1개월 이내 조회 많은 순
        RequestSearchProductVO searchVO02 = new RequestSearchProductVO();
        searchVO02.setPage(1);
        searchVO02.setRecord(3);
//        searchVO02.setOrderByDate("Y");
        searchVO02.setPopularFlg("Y");
        searchVO02.setLoginUserId(requestSearchProductVO.getLoginUserId());
        searchVO02.setLoginUtlinsttId(requestSearchProductVO.getLoginUtlinsttId());
        searchVO02.setRelatedProductYn("Y");
        searchVO02.setPdfInfoId(requestSearchProductVO.getPdfInfoId());
//        searchVO02.setCelebrityFlg(requestSearchProductVO.getCelebrityFlg());
//        searchVO02.setPopularFlg(requestSearchProductVO.getPopularFlg());
//        searchVO02.setOrderByDate(requestSearchProductVO.getOrderByDate());
//        searchVO02.setInquFlg(requestSearchProductVO.getInquFlg());
        searchVO02.setAdminSearchStatus(requestSearchProductVO.isAdminSearchStatus());
//        searchVO02.setPdfInfoCon(requestSearchProductVO.getPdfInfoCon());
        Integer levelRelated = 3; // 현재 카테고리 2단계까지를 관련상품으로 처리. 3단계면 levelRelated = 4 이다.
        searchVO02.setCategoryId(productInfo.getPdfCtgyId().substring(0, levelRelated * 2));
        searchVO02.setCategoryDepth(String.valueOf(levelRelated));
        searchVO02.setSelrUsisId(requestSearchProductVO.getSelrUsisId());
        searchVO02.setAgencyFlg(requestSearchProductVO.getAgencyFlg());;
        List<SummarySingleProductVO> relatedProductList = singleProductRepo.selectSingleProductList(searchVO02);
        relatedProductList.forEach(x -> { x.setImgUrl(fileUtil.setImageUrl(x.getImgFileId())); });
        resultVO.setRelatedProductList(relatedProductList);

        /*
         * 판매자 정보
         */
        // 판매자 정보 조회 by MainBox
        MainCompanyVO mainCompanyVO = mainBoxService.searchMainCompany(productInfo.getSelrUsisId());

        // 판매자 총 상품 판매 갯수 조회
        RequestSearchProductVO searchVO03 = new RequestSearchProductVO();
        searchVO03.setSelrUsisId(productInfo.getSelrUsisId());
        List<SummarySingleProductVO> totalSellerProductList = singleProductRepo.selectSingleProductList(searchVO03);
        int totalSize = 0;
        if(totalSellerProductList.size() > 0) {
            totalSize = totalSellerProductList.get(0).getTotalCnt();
        }
        mainCompanyVO.setSellerProductTotalCnt(totalSize);
        resultVO.setSellerInfo(mainCompanyVO);

        /*
         * 판매자 정보(회원타입, 회원상태, 통신판매업 신고번호)
         */
        MyPageUserVO sellerPageUserVO = new MyPageUserVO();
        SellerInfoVO sellerInfoVO = sellerStoreRepo.selectSellerInfo(productInfo.getSelrUsisId());
        sellerPageUserVO.setMmbrtypeId(sellerInfoVO.getMmbrtypeId());
        sellerPageUserVO.setMmbrtypeName(sellerInfoVO.getMmbrtypeName());
        sellerPageUserVO.setMmbrsttsId(sellerInfoVO.getMmbrsttsId());
        sellerPageUserVO.setMmbrsttsName(sellerInfoVO.getMmbrsttsName());
        sellerPageUserVO.setCsbStmtno(sellerInfoVO.getCsbStmtno());
        resultVO.setSellerPageUserVO(sellerPageUserVO);

        /*
         * 반품 / 교환 정보
         */
        ProductReturnVO productReturnVO =  productCommonRepo.selectProductReturn(requestSearchProductVO.getPdfInfoId());
        resultVO.setProductReturnInfo(productReturnVO);

        /*
         * 주문 상품 정보
         */
        OrderPdfDtlVO orderPdfDtlVO = orderSearchRepo.searchOrdnPdfDtl(requestSearchProductVO);
        if (orderPdfDtlVO == null) {
            resultVO.setOrderPdfDtlVO(new OrderPdfDtlVO());
        } else {
            resultVO.setOrderPdfDtlVO(orderPdfDtlVO);
        }

        return resultVO;
    }

    /**
     * 상품 상세 핀메자 정보 문의번호 클릭
     * 판매자의 문의번호를 클릭한 이용자의 기록을 남기는 용도
     * @throws Exception
     */
    public void clickProductDetailSeller(ProductSearchHistoryVO productSearchHistoryVO) throws Exception {

        // 로그인 정보 조회
        // 로그인 정보가 CustomUser.class 라면 jwt 로그인 조회
        // 로그인 상태가 아니라면 String 타입으로 떨이짐
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof CustomUser) {
            CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            productSearchHistoryVO.setInqUsisId(user.getUtlinsttId());
            productSearchHistoryVO.setInqUserId(user.getLoginUserId());
            productSearchHistoryVO.setRgsnUserId(user.getLoginUserId());
        }

        // 이력 유형 ID - 상품 조회 이력
        productSearchHistoryVO.setPrhsPtrnId(ComCode.HTS00001.getCode());

        // 상품 조회 이력 등록
        productCommonRepo.insertProductSearchHistory(productSearchHistoryVO);
    }
}
