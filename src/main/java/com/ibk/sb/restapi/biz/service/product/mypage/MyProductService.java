package com.ibk.sb.restapi.biz.service.product.mypage;

import com.ibk.sb.restapi.app.common.constant.AlarmCode;
import com.ibk.sb.restapi.app.common.constant.ComCode;
import com.ibk.sb.restapi.app.common.util.BizException;
import com.ibk.sb.restapi.app.common.util.FileUtil;
import com.ibk.sb.restapi.app.common.vo.CustomUser;
import com.ibk.sb.restapi.app.common.vo.PagingVO;
import com.ibk.sb.restapi.app.common.vo.ResponseData;
import com.ibk.sb.restapi.biz.service.agency.repo.AgencyRepo;
import com.ibk.sb.restapi.biz.service.agency.vo.SummaryAgencyVO;
import com.ibk.sb.restapi.biz.service.alarm.AlarmService;
import com.ibk.sb.restapi.biz.service.alarm.vo.RequestAlarmVO;
import com.ibk.sb.restapi.biz.service.delivery.repo.DeliveryRepo;
import com.ibk.sb.restapi.biz.service.delivery.vo.*;
import com.ibk.sb.restapi.biz.service.estimation.EstimationService;
import com.ibk.sb.restapi.biz.service.estimation.vo.EstimationProductVO;
import com.ibk.sb.restapi.biz.service.estimation.vo.RequestSearchEstimationVO;
import com.ibk.sb.restapi.biz.service.file.repo.FileRepo;
import com.ibk.sb.restapi.biz.service.file.vo.FileInfoVO;
import com.ibk.sb.restapi.biz.service.patent.kipris.*;
import com.ibk.sb.restapi.biz.service.mainbox.MainBoxService;
import com.ibk.sb.restapi.biz.service.mainbox.vo.MainCompanyVO;
import com.ibk.sb.restapi.biz.service.patent.kipris.vo.RequestSearchKiprisVO;
import com.ibk.sb.restapi.biz.service.patent.repo.PatentRepo;
import com.ibk.sb.restapi.biz.service.patent.vo.PatentVO;
import com.ibk.sb.restapi.biz.service.product.bundle.repo.BundleInfoRepo;
import com.ibk.sb.restapi.biz.service.product.bundle.repo.BundleProductRepo;
import com.ibk.sb.restapi.biz.service.product.bundle.vo.BundleProductInfoVO;
import com.ibk.sb.restapi.biz.service.product.bundle.vo.BundleProductVO;
import com.ibk.sb.restapi.biz.service.product.buyer.repo.BuyerProductRepo;
import com.ibk.sb.restapi.biz.service.product.buyer.vo.BuyerProductInfoVO;
import com.ibk.sb.restapi.biz.service.product.buyer.vo.BuyerProductVO;
import com.ibk.sb.restapi.biz.service.product.buyer.vo.RequestBuyerProductInfoSearchVO;
import com.ibk.sb.restapi.biz.service.product.common.repo.ProductCommonRepo;
import com.ibk.sb.restapi.biz.service.product.common.vo.*;
import com.ibk.sb.restapi.biz.service.product.mypage.repo.MyProductRepo;
import com.ibk.sb.restapi.biz.service.product.mypage.vo.*;
import com.ibk.sb.restapi.biz.service.product.mypage.vo.esti.RequestSearchEstiProductVO;
import com.ibk.sb.restapi.biz.service.product.single.repo.SingleProductRepo;
import com.ibk.sb.restapi.biz.service.product.single.vo.SingleProductVO;
import com.ibk.sb.restapi.biz.service.seller.repo.SellerStoreRepo;
import com.ibk.sb.restapi.biz.service.seller.vo.SellerInfoVO;
import com.ibk.sb.restapi.biz.service.user.vo.MyPageUserVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MyProductService {

    private final MyProductRepo myProductRepo;

    private final SingleProductRepo singleProductRepo;

    private final BundleInfoRepo bundleInfoRepo;

    private final BundleProductRepo bundleProductRepo;

    private final ProductCommonRepo productCommonRepo;

    private final DeliveryRepo deliveryRepo;

    private final PatentRepo patentRepo;

    private final BuyerProductRepo buyerProductRepo;

    private final MainBoxService mainBoxService;

    private final FileRepo fileRepo;

    private final FileUtil fileUtil;

    private final KiprisService kiprisService;

    private final EstimationService estimationService;

    private final SellerStoreRepo sellerStoreRepo;

    private final AlarmService alarmService;

    private final AgencyRepo agencyRepo;

    /*********************************************
     * 공통
     *********************************************/

    /**
     * 마이페이지 상품관리 헤더 정보 조회
     * @return
     * @throws Exception
     */
    public MyProductHeaderVO searchMyProductHeaderInfo() throws Exception {

        MyProductHeaderVO resultVO = new MyProductHeaderVO();

        // 상품 검색 조건 셋팅
        RequestSearchMyProductVO requestSearchMyProductVO = new RequestSearchMyProductVO();

        /*
         * 로그인 체크
         *      로그인 정보가 CustomUser.class 라면 jwt 로그인 조회
         *      로그인 상태가 아니라면 String 타입으로 떨이짐
         */
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof CustomUser) {
            CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            requestSearchMyProductVO.setLoginUtlinsttId(user.getUtlinsttId());
            requestSearchMyProductVO.setLoginUserId(user.getLoginUserId());
        }

        // 개별 상품 개수 조회
        List<SummaryMySingleProductVO> singleProductList = myProductRepo.selectMySingleProductList(requestSearchMyProductVO);
        int singleProductCnt = singleProductList.size() > 0 ? singleProductList.get(0).getTotalCnt() : 0;
        resultVO.setSingleProductCnt(singleProductCnt);

        // 묶음 상품 개수 조회
        List<SummaryMyBundleProductInfoVO> bundleProductList  = myProductRepo.selectMyBundleProductInfoList(requestSearchMyProductVO);
        int bundleProductCnt = bundleProductList.size() > 0 ? bundleProductList.get(0).getTotalCnt() : 0;
        resultVO.setBundleProductCnt(bundleProductCnt);

        // 바이어 상품 개수 조회
        List<SummaryMyBuyerProductInfoVO> buyerProductList = myProductRepo.selectMyBuyerProductInfoList(requestSearchMyProductVO);
        int buyerProductCnt = buyerProductList.size() > 0 ? buyerProductList.get(0).getTotalCnt() : 0;
        resultVO.setBuyerProductCnt(buyerProductCnt);

        return resultVO;
    }

    /*********************************************
     * 개별상품
     *********************************************/

    /**
     * 마이페이지 상품관리 개별상품 리스트 조회
     * @param requestSearchMyProductVO
     * @return
     * @throws Exception
     */
    public PagingVO<SummaryMySingleProductVO> searchMySingleProductList(RequestSearchMyProductVO requestSearchMyProductVO) throws Exception {

        /*
         * 로그인 체크
         *      로그인 정보가 CustomUser.class 라면 jwt 로그인 조회
         *      로그인 상태가 아니라면 String 타입으로 떨이짐
         */
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof CustomUser) {
            CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            requestSearchMyProductVO.setLoginUtlinsttId(user.getUtlinsttId());
            requestSearchMyProductVO.setLoginUserId(user.getLoginUserId());
        }

        // 개별상품 리스트 조회
        List<SummaryMySingleProductVO> singleProductList = myProductRepo.selectMySingleProductList(requestSearchMyProductVO);
        // 개별상품 대표 이미지 조회
        singleProductList.forEach(x -> {
            x.setImgUrl(fileUtil.setImageUrl(x.getImgFileId()));
        });

        return new PagingVO<>(requestSearchMyProductVO, singleProductList);
    }

    /**
     * 마이페이지 상품관리 개별상품 등록 / 수정
     * @param detailMySingleProductVO
     * @return
     */
    public String saveMySingleProduct(DetailMySingleProductVO detailMySingleProductVO) throws Exception {

        // 로그인 체크
        String loginUserId = null;
        String utlinsttId = null;

        // 로그인 정보가 CustomUser.class 라면 jwt 로그인 조회
        // 로그인 상태가 아니라면 String 타입으로 떨이짐
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof CustomUser) {
            CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            loginUserId = user.getLoginUserId();
            utlinsttId = user.getUtlinsttId();
        }


        // 디폴트 로직 : 수정(update)
        // update flg = true
        boolean updateFlg = true;

        // 개별상품 ID 셋팅
        String pdfInfoId = StringUtils.hasLength(detailMySingleProductVO.getPdfInfoId()) ? detailMySingleProductVO.getPdfInfoId() : null;

        // ID 가 존재하지 않을 경우, 새로 생성하고 등록 로직
        if(!StringUtils.hasLength(pdfInfoId)) {
            pdfInfoId = UUID.randomUUID().toString();
            updateFlg = false;
        }

        // 상품 ID, 로그인한 사용자의 이용기관 ID, 사용자 ID map 셋팅
        Map<String, String> baseInfoMap = new HashMap<>();
        baseInfoMap.put("pdfInfoId", pdfInfoId);
        baseInfoMap.put("utlinsttId", utlinsttId);
        baseInfoMap.put("loginUserId", loginUserId);

        // 상품 기본 정보 등록 / 수정
        this.saveSingleProduct(baseInfoMap, detailMySingleProductVO.getSingleProductVO(), detailMySingleProductVO.getPatentList(), updateFlg);
        // 상품 키워드 정보 등록 / 수정
        this.saveProductKeyword(baseInfoMap, detailMySingleProductVO.getPdfKwrList());
        // 상품 판매 정보 등록 / 수정
        this.saveProductSale(baseInfoMap, detailMySingleProductVO.getProductSaleVO(), updateFlg);
        // 상품 배송 정보 등록 / 수정
        this.saveDeliveryinfo(baseInfoMap, detailMySingleProductVO.getDeliveryinfoVO(), updateFlg);
        // 상품 화물서비스 기본 정보 등록 / 수정
        this.saveDeliveryBaseInfo(baseInfoMap, detailMySingleProductVO.getDeliveryBaseInfoVO(), updateFlg);
        // 상품 화물서비스 견적 정보 등록 / 수정
        this.saveDeliveryProductServiceInfo(baseInfoMap, detailMySingleProductVO.getDeliveryProductServiceInfoList());
        // 지역별 배송비 리스트 등록 / 수정
        this.saveDeliveryLocalInfoList(baseInfoMap, detailMySingleProductVO.getDeliveryLocalInfoList());
        // 수량별 배송비 리스트 등록 / 수정
        this.saveDeliveryCntInfoList(baseInfoMap, detailMySingleProductVO.getDeliveryCntInfoList());
        // 상품 이미지 파일 정보 / 수정
        this.saveProductFileList(baseInfoMap, detailMySingleProductVO.getProductFileList(), updateFlg);
        // 상품 제품 영상 정보 등록 / 수정
        this.saveProductVideoList(baseInfoMap, detailMySingleProductVO.getProductVideoList(), updateFlg);
        // 상품 제품 링크 정보 등록 / 수정
        this.saveProductLinkList(baseInfoMap, detailMySingleProductVO.getProductLinkList(), updateFlg);
        // 상품 특허 정보 등록 / 수정
        this.savePatentList(baseInfoMap, detailMySingleProductVO.getPatentList());
        // 상품 반품/교환 정보 등록 / 수정
        this.saveProductReturn(baseInfoMap, detailMySingleProductVO.getProductReturnVO(), updateFlg);

        /*
         * 상품 수정일 경우, 상품에 에이전시 ID 가 있다면, 에이전시 상품을 판매하고 있는 판매자에게 알림 송신
         */
        if(updateFlg) {
            SingleProductVO singleProductVO = singleProductRepo.selectMySingleProduct(pdfInfoId);
            // 에이전시 ID 가 존재 한다면, 알림 송신
            if(singleProductVO != null && !StringUtils.hasLength(singleProductVO.getAgenInfId())) {

                // 에이전시 등록중인 상품 리스트
                List<SummaryAgencyVO> agencyVOList = agencyRepo.selectAgencyInfoListByPdfId(singleProductVO.getPdfInfoId());

                agencyVOList.forEach(x -> {
                    try {
                        /*
                         * 알림 서비스
                         */
                        // 알림 요청 인스턴스 생성
                        RequestAlarmVO requestAlarmVO = alarmService.getAlarmRequest(AlarmCode.AlarmCodeEnum.AGENCY_PRODUCT_CHANGE, singleProductVO.getPdfInfoId(), new Object[]{singleProductVO.getPdfNm()});
                        // 에이전시 접수 정보 조회
                        SummaryAgencyVO summaryAgencyVO = agencyRepo.selectAgencyInfo(singleProductVO.getAgenInfId());
                        // 알림 발송
                        alarmService.sendMarketAlarm(requestAlarmVO, x.getSenUsisId());

                    } catch (BizException bx) {
                        log.error("Business Exception ({}) : {}", bx.getErrorCode(), bx.getErrorMsg());
                    } catch (Exception e) {
                        log.error(e.getMessage());
                    }
                });
            }
        }

        return pdfInfoId;
    }

    /**
     * 상품 기본 정보 등록 / 수정
     */
    private void saveSingleProduct(Map<String, String> baseInfoMap, SingleProductVO singleProductVO, List<PatentVO> patentList, boolean updateFlg) {
        if(singleProductVO != null) {
            singleProductVO.setPdfInfoId(baseInfoMap.get("pdfInfoId"));
            singleProductVO.setSelrUsisId(baseInfoMap.get("utlinsttId"));
            singleProductVO.setRgsnUserId(baseInfoMap.get("loginUserId"));
            singleProductVO.setAmnnUserId(baseInfoMap.get("loginUserId"));
            if(patentList != null && patentList.size() > 0) {
                singleProductVO.setPtntInfoYn("Y");
            }else {
                singleProductVO.setPtntInfoYn("N");
            }
            if(updateFlg) {
                singleProductRepo.updateSingleProduct(singleProductVO);
            } else {
                singleProductRepo.insertSingleProduct(singleProductVO);
            }
        }
    }

    /**
     * 상품 키워드 정보 등록 / 수정
     */
    private void saveProductKeyword(Map<String, String> baseInfoMap, List<ProductKeywordVO> pdfKwrList) {

        // 기존의 상품 키워드 정보 삭제
        productCommonRepo.deleteProductKeyWord(baseInfoMap.get("pdfInfoId"));

        if(pdfKwrList != null && pdfKwrList.size() > 0) {
            pdfKwrList.forEach(x -> {
                x.setPdfInfoId(baseInfoMap.get("pdfInfoId"));
                x.setRgsnUserId(baseInfoMap.get("loginUserId"));
                x.setAmnnUserId(baseInfoMap.get("loginUserId"));
                // 상품 키워드 정보 작성
                productCommonRepo.insertProductKeyWord(x);
            });
        }
    }

    /**
     * 상품 판매 정보 등록 / 수정
     */
    private void saveProductSale(Map<String, String> baseInfoMap, ProductSaleVO productSale, boolean updateFlg) {
        if(productSale != null) {
            productSale.setPdfInfoId(baseInfoMap.get("pdfInfoId"));
            productSale.setRgsnUserId(baseInfoMap.get("loginUserId"));
            productSale.setAmnnUserId(baseInfoMap.get("loginUserId"));
            if(updateFlg) {
                productCommonRepo.updateProductSale(productSale);
            } else {
                productCommonRepo.insertProductSale(productSale);
            }
        }
    }

    /**
     * 상품 배송 정보 등록 / 수정
     */
    private void saveDeliveryinfo(Map<String, String> baseInfoMap, DeliveryinfoVO deliveryinfo, boolean updateFlg) {
        if(deliveryinfo != null) {
            deliveryinfo.setPdfInfoId(baseInfoMap.get("pdfInfoId"));
            deliveryinfo.setRgsnUserId(baseInfoMap.get("loginUserId"));
            deliveryinfo.setAmnnUserId(baseInfoMap.get("loginUserId"));
            if(updateFlg) {
                deliveryRepo.updateDeliveryinfo(deliveryinfo);
            } else {
                deliveryRepo.insertDeliveryinfo(deliveryinfo);
            }
        }
    }

    /**
     * 상품 화물서비스 기본 정보 등록 / 수정
     */
    private void saveDeliveryBaseInfo(Map<String, String> baseInfoMap, DeliveryBaseInfoVO deliveryBaseInfoVO, boolean updateFlg) {
        if(deliveryBaseInfoVO != null) {
            deliveryBaseInfoVO.setPdfInfoId(baseInfoMap.get("pdfInfoId"));
            deliveryBaseInfoVO.setRgsnUserId(baseInfoMap.get("loginUserId"));
            deliveryBaseInfoVO.setAmnnUserId(baseInfoMap.get("loginUserId"));
            if(updateFlg) {
                deliveryRepo.updateDeliveryBaseInfo(deliveryBaseInfoVO);
            } else {
                deliveryRepo.insertDeliveryBaseInfo(deliveryBaseInfoVO);
            }
        }
    }

    /**
     * 상품 화물서비스 견적 정보 등록 / 수정
     */
    private void saveDeliveryProductServiceInfo(Map<String, String> baseInfoMap, List<DeliveryProductServiceInfoVO> deliveryProductServiceInfoList) {

        // 기존의 화물서비스 견적 정보 리스트 삭제
        deliveryRepo.deleteDeliveryProductServiceInfo(baseInfoMap.get("pdfInfoId"));

        if(deliveryProductServiceInfoList != null && deliveryProductServiceInfoList.size() > 0) {
            deliveryProductServiceInfoList.forEach(x -> {
                x.setPdfInfoId(baseInfoMap.get("pdfInfoId"));
                deliveryRepo.insertDeliveryProductServiceInfo(x);
            });

            // 상품 배송 정보 수정(화물서비스 업체 ID)
            DeliveryinfoVO deliveryinfoVO = new DeliveryinfoVO();
            deliveryinfoVO.setPdfInfoId(baseInfoMap.get("pdfInfoId"));
            deliveryinfoVO.setEntpInfoId(deliveryProductServiceInfoList.get(0).getEntpInfoId());
            deliveryRepo.updateDeliveryinfoForEntpInfoId(deliveryinfoVO);
        }

    }

    /**
     * 지역별 배송비 리스트 등록 / 수정
     */
    private void saveDeliveryLocalInfoList(Map<String, String> baseInfoMap, List<DeliveryLocalInfoVO> deliveryLocalInfoList) {

        // 기존의 지역별 배송비 리스트 삭제
        deliveryRepo.deleteDeliveryLocalInfo(baseInfoMap.get("pdfInfoId"));

        if(deliveryLocalInfoList != null && deliveryLocalInfoList.size() > 0) {
            deliveryLocalInfoList.forEach(x -> {
                x.setPdfInfoId(baseInfoMap.get("pdfInfoId"));
                x.setRgsnUserId(baseInfoMap.get("loginUserId"));
                x.setAmnnUserId(baseInfoMap.get("loginUserId"));
                // 지역별 배송비 리스트 등록
                deliveryRepo.insertDeliveryLocalInfo(x);
            });
        }
    }

    /**
     * 수량별 배송비 리스트 등록 / 수정
     */
    private void saveDeliveryCntInfoList(Map<String, String> baseInfoMap, List<DeliveryCntInfoVO> deliveryCntInfoList) {

        // 기존의 수량별 배송비 리스트 삭제
        deliveryRepo.deleteDeliveryCntInfo(baseInfoMap.get("pdfInfoId"));

        if(deliveryCntInfoList != null && deliveryCntInfoList.size() > 0) {
            deliveryCntInfoList.forEach(x -> {
                x.setPdfInfoId(baseInfoMap.get("pdfInfoId"));
                x.setRgsnUserId(baseInfoMap.get("loginUserId"));
                x.setAmnnUserId(baseInfoMap.get("loginUserId"));
                // 수량별 배송비 리스트 등록
                deliveryRepo.insertDeliveryCntInfo(x);
            });
        }
    }

    /**
     * 상품 이미지 파일 정보 / 수정
     */
    private void saveProductFileList(Map<String, String> baseInfoMap, List<ProductFileVO> productFileList, boolean updateFlg) {

        // 수정일 경우 기존의 상품 파일 정보 삭제, 공통 파일 테이블 삭제
        if(updateFlg) {
            List<ProductFileVO> oldFileVOList = productCommonRepo.selectProductFileList(baseInfoMap.get("pdfInfoId"), null, null);
            oldFileVOList.forEach(x -> {
                fileRepo.deleteFileInfo(x.getFileId());
            });
            productCommonRepo.deleteProductFile(baseInfoMap.get("pdfInfoId"));
        }

        if(productFileList != null && productFileList.size() > 0) {
            productFileList.forEach(x -> {
                x.setPdfInfoId(baseInfoMap.get("pdfInfoId"));
                x.setRgsnUserId(baseInfoMap.get("loginUserId"));
                x.setAmnnUserId(baseInfoMap.get("loginUserId"));
                // 파일 유형 ID - 상품 이미지
                x.setFilePtrnId(ComCode.GDS05001.getCode());
                // 상품 파일 정보 등록
                productCommonRepo.insertProductFile(x);
                // 수정인 경우 삭제했던 공통 테이블의 데이터를 'N' 으로 셋팅
                if(updateFlg) {
                    fileRepo.updateFileInfo(x.getFileId());
                }
            });
        }
    }

    /**
     * 상품 제품 영상 정보 등록 / 수정
     */
    private void saveProductVideoList(Map<String, String> baseInfoMap, List<ProductVideoVO> productVideoList, boolean updateFlg) {

        // 수정일 경우 기존의 상품 제품 영상 정보 삭제, 공통 파일 테이블 삭제
        if(updateFlg) {
            List<ProductVideoVO> oldVideoVOList = productCommonRepo.selectProductVideoList(baseInfoMap.get("pdfInfoId"));
            oldVideoVOList.forEach(x -> {
                fileRepo.deleteFileInfo(x.getFileId());
            });
            productCommonRepo.deleteProductVideo(baseInfoMap.get("pdfInfoId"));
        }

        if(productVideoList != null && productVideoList.size() > 0) {
            productVideoList.forEach(x -> {
                x.setPdfInfoId(baseInfoMap.get("pdfInfoId"));
                x.setRgsnUserId(baseInfoMap.get("loginUserId"));
                x.setAmnnUserId(baseInfoMap.get("loginUserId"));
                // 상품 제품 영상 정보 등록
                productCommonRepo.insertProductVideo(x);
                // 수정인 경우 삭제했던 공통 테이블의 데이터를 'N' 으로 셋팅
                if(updateFlg) {
                    fileRepo.updateFileInfo(x.getFileId());
                }
            });
        }
    }

    /**
     * 상품 제품 링크 정보 등록 / 수정
     */
    private void saveProductLinkList(Map<String, String> baseInfoMap, List<ProductLinkVO> productLinkList, boolean updateFlg) {

        // 수정일 경우 기존의 상품 제품 영상 정보 삭제, 공통 파일 테이블 삭제
        if(updateFlg) {
            productCommonRepo.deleteProductLink(baseInfoMap.get("pdfInfoId"));
        }

        if(productLinkList != null && productLinkList.size() > 0) {
            productLinkList.forEach(x -> {
                x.setPdfInfoId(baseInfoMap.get("pdfInfoId"));
                x.setRgsnUserId(baseInfoMap.get("loginUserId"));
                x.setAmnnUserId(baseInfoMap.get("loginUserId"));
                // 상품 제품 영상 정보 등록
                productCommonRepo.insertProductLink(x);
            });
        }
    }

    /**
     * 상품 특허 정보 등록 / 수정
     */
    private void savePatentList(Map<String, String> baseInfoMap, List<PatentVO> patentList) {

        // 기존의 특허 정보 삭제
        patentRepo.deletePatent(baseInfoMap.get("pdfInfoId"));

        if(patentList != null && patentList.size() > 0) {
            patentList.forEach(x -> {
                x.setPdfInfoId(baseInfoMap.get("pdfInfoId"));
                x.setRgsnUserId(baseInfoMap.get("loginUserId"));
                x.setAmnnUserId(baseInfoMap.get("loginUserId"));
                patentRepo.insertPatent(x);
            });
        }
    }

    /**
     * 상품 반품/교환 정보 등록 / 수정
     */
    private void saveProductReturn(Map<String, String> baseInfoMap, ProductReturnVO productReturnVO, boolean updateFlg) {

        if(productReturnVO != null) {
            productReturnVO.setPdfInfoId(baseInfoMap.get("pdfInfoId"));
            productReturnVO.setRgsnUserId(baseInfoMap.get("loginUserId"));
            productReturnVO.setAmnnUserId(baseInfoMap.get("loginUserId"));
            if(updateFlg) {
                productCommonRepo.updateProductReturn(productReturnVO);
            } else {
                productCommonRepo.insertProductReturn(productReturnVO);
            }

        }
    }

    /**
     * 마이페이지 상품관리 개별상품 등록 판매자 정보 조회
     * @return
     */
    public MyPageUserVO searchMyProductSellerInfo() throws Exception {

        // 로그인 체크
        String utlinsttId = null;

        // 로그인 정보가 CustomUser.class 라면 jwt 로그인 조회
        // 로그인 상태가 아니라면 String 타입으로 떨이짐
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof CustomUser) {
            CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            utlinsttId = user.getUtlinsttId();
        }

        MyPageUserVO myPageUserVO = new MyPageUserVO();

        // 메인BOX 기업정보 조회(이용기관 원장 조회)
        MainCompanyVO mainCompanyVO = mainBoxService.searchMainCompany(utlinsttId);

        // mapping
        myPageUserVO.setBplcNm(mainCompanyVO.getBplcNm());
        myPageUserVO.setRprsntvNm(mainCompanyVO.getRprsntvNm());
        myPageUserVO.setBizrno(mainCompanyVO.getBizrno());
        myPageUserVO.setAdres(mainCompanyVO.getAdres());
        myPageUserVO.setDetailAdres(mainCompanyVO.getDetailAdres());
        myPageUserVO.setNwAdres(mainCompanyVO.getNwAdres());
        myPageUserVO.setNwAdresDetail(mainCompanyVO.getNwAdresDetail());
        myPageUserVO.setReprsntTelno(mainCompanyVO.getReprsntTelno());

        // 통신판매업 신고번호
        SellerInfoVO sellerInfoVO = sellerStoreRepo.selectSellerInfo(utlinsttId);
        myPageUserVO.setCsbStmtno(sellerInfoVO.getCsbStmtno());

        return myPageUserVO;
    }

    /**
     * 마이페이지 상품관리 개별상품 등록 반품/교환 정보 조회
     * @return
     * @throws Exception
     */
    public ProductReturnVO searchMyProductSellerLastRtin() throws Exception {

        // 로그인 체크
        String utlinsttId = null;

        // 로그인 정보가 CustomUser.class 라면 jwt 로그인 조회
        // 로그인 상태가 아니라면 String 타입으로 떨이짐
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof CustomUser) {
            CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            utlinsttId = user.getUtlinsttId();
        }

        ProductReturnVO productReturnVO = new ProductReturnVO();
        productReturnVO = singleProductRepo.selectMyProductSellerLastRtin(utlinsttId);

        return productReturnVO;
    }

    /**
     * 마이페이지 상품관리 개별상품 삭제
     * @param singleProductVO
     * @throws Exception
     */
    public void deleteSingleProduct(List<SingleProductVO> singleProductVO) throws Exception {

        // 로그인 체크
        String loginUserId = "";

        // 로그인 정보가 CustomUser.class 라면 jwt 로그인 조회
        // 로그인 상태가 아니라면 String 타입으로 떨이짐
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof CustomUser) {
            CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            loginUserId = user.getLoginUserId();
        }

        if(singleProductVO.size() > 0) {
            String finalLoginUserId = loginUserId;
            singleProductVO.forEach(x -> {
                if(!StringUtils.hasLength(x.getPdfInfoId())) {
                    return;
                }

                // 삭제할 개별상품 조회
                SingleProductVO oldSingleProductVO = singleProductRepo.selectMySingleProduct(x.getPdfInfoId());
                // 삭제할 개별상품이 존재하지 않는 경우, 처리 안함
                if(oldSingleProductVO == null) {
                    return;
                }

                // 수정한 유저 ID 셋팅
                x.setAmnnUserId(finalLoginUserId);

                // 마이페이지 상품관리 개별상품 삭제
                singleProductRepo.deleteSingleProduct(x);

                // 개별상품 파일 정보 리스트 조회
                List<ProductFileVO> productFileList = productCommonRepo.selectProductFileList(x.getPdfInfoId(), null, null);
                // 첨부된 파일 정보 리스트가 존재하면, 삭제 처리
                if(productFileList != null && productFileList.size() > 0) {
                    productFileList.forEach(y -> {
                        fileRepo.deleteFileInfo(y.getFileId());
                    });
                }
            });
        }
    }

    /**
     * 마이페이지 상품관리 개별상품 화물서비스 선택
     * @param requestSearchEstiProductVO
     * @return
     * @throws Exception
     */
    public ResponseData searchMySingleProductDeliveryList(RequestSearchEstiProductVO requestSearchEstiProductVO) throws Exception {

        // 로그인 정보

        // validation(입력값)

        // 공통 처리

        RequestSearchEstimationVO requestSearchEstimationVO = new RequestSearchEstimationVO();
        requestSearchEstimationVO.setRlontfZpcd(requestSearchEstiProductVO.getRlontfZpcd());
        requestSearchEstimationVO.setRlontfAdr(requestSearchEstiProductVO.getRlontfAdr());
        requestSearchEstimationVO.setRlontfDtad(requestSearchEstiProductVO.getRlontfDtad());
        List<EstimationProductVO> items = new ArrayList<>();
        EstimationProductVO item = new EstimationProductVO();
        item.setOrdnQty(requestSearchEstiProductVO.getEstiQty());
        item.setPdfNm(requestSearchEstiProductVO.getPdfNm());
//        item.setGearPdfInfoId(requestSearchEstiProductVO.getPdfInfoId());
        item.setPrdtBrdh(requestSearchEstiProductVO.getPrdtBrdh());
        item.setPrdtVrtc(requestSearchEstiProductVO.getPrdtVrtc());
        item.setPrdtAhgd(requestSearchEstiProductVO.getPrdtAhgd());
        item.setPrdtWgt(requestSearchEstiProductVO.getPrdtWgt());

        item.setDchGdsPrc(requestSearchEstiProductVO.getDchGdsPrc());
        item.setMxmmGdsCnt(requestSearchEstiProductVO.getMxmmGdsCnt());   // 최대상품수
        item.setPrdtpcknUtId(requestSearchEstiProductVO.getPrdtpcknUtId());
        item.setOrdnQty(requestSearchEstiProductVO.getEstiQty());

        List<FileInfoVO> pdfFileList = requestSearchEstiProductVO.getProductFileList();
        for (FileInfoVO vo : pdfFileList) {
            FileInfoVO pdfFileVo = fileRepo.selectFileInfo(vo.getFileId());
            if (pdfFileVo != null) {
                vo.setFileNm(pdfFileVo.getFileNm()); // 파일명 set
            }
        }

        item.setProductFileList(requestSearchEstiProductVO.getProductFileList());
        items.add(item);
        requestSearchEstimationVO.setItems(items);

        return estimationService.searchDeliveryList(requestSearchEstimationVO, EstimationService.DeliveryListServiceKind.MyProductService_SingleProductDeliveryList);
    }

    /**
     * 마이페이지 상품관리 개별상품 상세 조회
     * @param requestSearchMyProductVO
     * @return
     */
    public DetailMySingleProductVO searchMySingleProduct(RequestSearchMyProductVO requestSearchMyProductVO) throws Exception {

        DetailMySingleProductVO resultVO = new DetailMySingleProductVO();

        String pdfInfoId = requestSearchMyProductVO.getPdfInfoId();

        // 상품 아이디가 존재하지 않는 경우, 검색 처리 안함
        if(!StringUtils.hasLength(pdfInfoId)) {
            return null;
        }

        // 마이페이지 상품관리 개별상품 조회
        SingleProductVO singleProductVO = singleProductRepo.selectMySingleProduct(pdfInfoId);

        // 마이페이지 상품관리 개별상품 셋팅
        resultVO.setSingleProduct(singleProductVO);
        // 상품 키워드 리스트 조회
        resultVO.setPdfKwrList(productCommonRepo.selectProductKeyWordList(pdfInfoId));
        // 상품 판매 정보 조회
        resultVO.setProductSale(productCommonRepo.selectProductSale(pdfInfoId));
        // 상품 배송 정보 조회
        resultVO.setDeliveryinfo(deliveryRepo.selectDeliveryInfo(pdfInfoId));

        // 상품 화물 서비스 기본 정보 조회
        DeliveryBaseInfoVO deliveryBaseInfoVO = deliveryRepo.selectDeliveryBaseInfo(pdfInfoId);
        if (deliveryBaseInfoVO != null) {
            deliveryBaseInfoVO.setImgUrl(fileUtil.setImageUrl(deliveryBaseInfoVO.getFileId()));
        }
        resultVO.setDeliveryBaseInfo(deliveryBaseInfoVO);

        // 상품 화물 서비스 견적 정보 조회
        resultVO.setDeliveryProductServiceInfoList(deliveryRepo.selectDeliveryProductInfo(pdfInfoId));

        // 지역별 배송비 리스트 조회
        resultVO.setDeliveryLocalInfoList(deliveryRepo.selectDeliveryLocalInfo(pdfInfoId));
        // 수량별 배송비 리스트 조회
        resultVO.setDeliveryCntInfoList(deliveryRepo.selectDeliveryCntInfo(pdfInfoId));

        // 상품 이미지 파일 정보 리스트 조회
        List<ProductFileVO> fileImgList = productCommonRepo.selectProductFileList(pdfInfoId, ComCode.GDS05001.getCode(), null);
        // 상품 이미지 파일 URL 셋팅
        if(fileImgList.size() > 0) {
            fileImgList.forEach(x -> {
                x.setImgUrl(fileUtil.setImageUrl(x.getFileId()));
            });
        }
        // 상품 이미지 파일 정보 리스트 셋팅
        resultVO.setProductFileList(fileImgList);

        // 상품 제품 영상 정보 리스트 조회
        List<ProductVideoVO> productVideoVOList = productCommonRepo.selectProductVideoList(pdfInfoId);
        // 상품 제품 영상 정보 섬네일 이미지 URL 셋팅
        if(productVideoVOList.size() > 0) {
            productVideoVOList.forEach(x -> {
                x.setImgUrl(fileUtil.setImageUrl(x.getFileId()));
            });
        }
        // 상품 제품 영상 정보 리스트 조회
        resultVO.setProductVideoList(productVideoVOList);

        // 상품 특허 정보 리스트
        List<PatentVO> productPatentList = patentRepo.searchPatentList(pdfInfoId);
        // 특허 정보 리스트 중 특허 출원 번호만 리스트 화
        List<String> ptntAlfrNoList = productPatentList.stream().map(x -> x.getPtntAlfrNo()).collect(Collectors.toList());

        // 상품 기본 정보에 에이전시ID 가 없는 경우, 판매자 상품
        if(!StringUtils.hasLength(singleProductVO.getAgenInfId())) {
            // 토탈 상품 특허 정보 리스트 조회
//            List<PatentVO> totalPatentList = new ArrayList<>();
            // TODO 키프리스 api 신청 후 처리
             List<PatentVO> totalPatentList = kiprisService.searchKiprisPagingList(new RequestSearchKiprisVO());

            // 토탈 상품 특허 정보 중에서 특허 정보 테이블에 등록되어있는 특허는 checked 를 "Y"
            totalPatentList.forEach(x -> {
                if(ptntAlfrNoList.contains(x.getPtntAlfrNo())) {
                    x.setChecked("Y");
                }
            });
            resultVO.setPatentList(totalPatentList);
        }

        // 에이전시 상품
        // 특허 정보 테이블에 등록되어있는 특허만 조회
        else {
            resultVO.setPatentList(productPatentList);
        }

        // 상품 반품/교환 정보
        resultVO.setProductReturn(productCommonRepo.selectProductReturn(pdfInfoId));

        // 상품 링크 정보
        List<ProductLinkVO> productLinkList = productCommonRepo.selectProductLinkList(pdfInfoId);
        resultVO.setProductLinkList(productLinkList);

        return resultVO;
    }


    /*********************************************
     * 묶음 상품
     *********************************************/

    /**
     * 마이페이지 상품관리 번들상품 정보 리스트 조회
     * @param requestSearchMyProductVO
     * @return
     * @throws Exception
     */
    public PagingVO<SummaryMyBundleProductInfoVO> searchMyBundleProductInfoList(RequestSearchMyProductVO requestSearchMyProductVO) throws Exception {

        /*
         * 로그인 체크
         *      로그인 정보가 CustomUser.class 라면 jwt 로그인 조회
         *      로그인 상태가 아니라면 String 타입으로 떨이짐
         */
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof CustomUser) {
            CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            requestSearchMyProductVO.setLoginUtlinsttId(user.getUtlinsttId());
            requestSearchMyProductVO.setLoginUserId(user.getLoginUserId());
        }

        // 번들상품 정보 리스트 조회
        List<SummaryMyBundleProductInfoVO> bundleProductList = myProductRepo.selectMyBundleProductInfoList(requestSearchMyProductVO);

        // 번들상품 정보 이미지 URL 셋팅
        if(bundleProductList.size() > 0) {
            bundleProductList.forEach(x -> {
                x.setImgUrl(fileUtil.setImageUrl(x.getImgFileId()));
            });
        }

        return new PagingVO<>(requestSearchMyProductVO, bundleProductList);
    }

    /**
     * 마이페이지 상품관리 묶음상품 등록 / 수정
     * @param detailMyBundleProductVO
     * @return
     */
    public String saveMyBundleProduct(DetailMyBundleProductVO detailMyBundleProductVO) throws Exception{

        // 로그인 체크
        String loginUserId = null;
        String utlinsttId = null;

        // 로그인 정보가 CustomUser.class 라면 jwt 로그인 조회
        // 로그인 상태가 아니라면 String 타입으로 떨이짐
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof CustomUser) {
            CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            loginUserId = user.getLoginUserId();
            utlinsttId = user.getUtlinsttId();
        }


        // 디폴트 로직 : 수정(update)
        // update flg = true
        boolean updateFlg = true;

        // 개별상품 ID 셋팅
        String bunInfId = StringUtils.hasLength(detailMyBundleProductVO.getBunInfId()) ? detailMyBundleProductVO.getBunInfId() : null;
        // ID 가 존재하지 않을 경우, 새로 생성하고 등록 로직
        if(!StringUtils.hasLength(bunInfId)) {
            bunInfId = UUID.randomUUID().toString();
            updateFlg = false;
        }

        String finalLoginUserId = loginUserId;
        String finalUtlinsttId = utlinsttId;
        String finalBunInfId = bunInfId;

        /*
         * 묶음 상품 정보
         */
        BundleProductInfoVO bundleProductInfoVO = detailMyBundleProductVO.getBundleProductInfoVO();
        if(bundleProductInfoVO != null){
            bundleProductInfoVO.setBunInfId(finalBunInfId);
            bundleProductInfoVO.setSelrUsisId(finalUtlinsttId);
            bundleProductInfoVO.setRgsnUserId(finalLoginUserId);
            bundleProductInfoVO.setAmnnUserId(finalLoginUserId);
            if(updateFlg) {
                // 수정
                bundleProductRepo.updateBundleProductInfo(bundleProductInfoVO);
            } else {
                // 등록
                bundleProductRepo.insertBundleProductInfo(bundleProductInfoVO);
            }
        }

        /*
         * 묶음 상품 목록
         */
        List<SummaryMyProductVO> bundleProductVOList = detailMyBundleProductVO.getBundleProductList();

        // 기존의 묶음 상품 목록 삭제
        bundleProductRepo.deleteBundleProductList(finalBunInfId);

        if(bundleProductVOList != null && bundleProductVOList.size() > 0) {
            bundleProductVOList.forEach(x -> {
                // 상품 아이디 존재 체크
                if(StringUtils.hasLength(x.getPdfInfoId())) {
                    BundleProductVO bundleProductVO = new BundleProductVO();
                    bundleProductVO.setBunInfId(finalBunInfId);
                    bundleProductVO.setPdfInfoId(x.getPdfInfoId());
                    bundleProductVO.setMainYn(x.getMainYn());
                    bundleProductVO.setRgsnUserId(finalLoginUserId);
                    bundleProductVO.setAmnnUserId(finalLoginUserId);
                    // 등록
                    bundleProductRepo.insertBundleProduct(bundleProductVO);
                }
            });
        }

        return bunInfId;
    }

    /**
     * 마이페이지 상품관리 상품 리스트 조회
     * @param requestSearchMyProductVO
     * @return
     */
    public List<SummaryMyProductVO> searchMyProductList(RequestSearchMyProductVO requestSearchMyProductVO) throws Exception {

        // 로그인 체크
        String utlinsttId = null;

        // 로그인 정보가 CustomUser.class 라면 jwt 로그인 조회
        // 로그인 상태가 아니라면 String 타입으로 떨이짐
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof CustomUser) {
            CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            utlinsttId = user.getUtlinsttId();
        }
        requestSearchMyProductVO.setLoginUtlinsttId(utlinsttId);

        requestSearchMyProductVO.setPdfPgrsYnFlg("use"); // 묶음에 추가할 상품을 고르는 것이기 때문에, '진열함' 조건을 사용한다.
        List<SummaryMyProductVO> summaryMyProductVOList = myProductRepo.selectMyProductList(requestSearchMyProductVO);
        // 상품 이미지 URL 셋팅
        if(summaryMyProductVOList.size() > 0) {
            summaryMyProductVOList.forEach(x ->{
                x.setImgUrl(fileUtil.setImageUrl(x.getImgFileId()));
            });
        }

        return summaryMyProductVOList;
    }

    /**
     * 마이페이지 상품관리 묶음상품 삭제
     * @param bundleProductInfoVO
     * @throws Exception
     */
    public void deleteMyBundleProductInfo(List<BundleProductInfoVO> bundleProductInfoVO) throws Exception {

        // 로그인 체크
        String loginUserId = "";

        // 로그인 정보가 CustomUser.class 라면 jwt 로그인 조회
        // 로그인 상태가 아니라면 String 타입으로 떨이짐
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof CustomUser) {
            CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            loginUserId = user.getLoginUserId();
        }

        if(bundleProductInfoVO.size() > 0) {
            String finalLoginUserId = loginUserId;
            bundleProductInfoVO.forEach(x -> {
                if(!StringUtils.hasLength(x.getBunInfId())) {
                    return;
                }

                BundleProductInfoVO oldBundleProductInfoVO = bundleInfoRepo.selectBundleProductInfo(x.getBunInfId());
                // 삭제할 묶음상품이 존재하지 않으면 처리 안함
                if (oldBundleProductInfoVO == null) {
                    return;
                }

                // 수정한 유저 ID 셋팅
                x.setAmnnUserId(finalLoginUserId);

                // 마이페이지 상품관리 묶음상품 삭제
                bundleInfoRepo.deleteBundleProductInfo(x);

                // 상품 묶음상품 정보 파일 삭제
                fileRepo.deleteFileInfo(oldBundleProductInfoVO.getFileId());
            });
        }
    }

    /**
     * 마이페이지 상품관리 묶음상품 상세 조회
     * @param requestSearchMyProductVO
     * @return
     */
    public DetailMyBundleProductVO searchMyBundleProduct(RequestSearchMyProductVO requestSearchMyProductVO) {

        DetailMyBundleProductVO resultVO = new DetailMyBundleProductVO();

        String bunInfId = requestSearchMyProductVO.getBunInfId();

        // 상품 아이디가 존재하지 않는 경우, 검색 처리 안함
        if(!StringUtils.hasLength(bunInfId)) {
            return null;
        }

        // 마이페이지 상품관리 묶음상품 정보 조회
        BundleProductInfoVO bundleProductInfoVO = bundleInfoRepo.selectBundleProductInfo(bunInfId);
        bundleProductInfoVO.setImgUrl(fileUtil.setImageUrl(bundleProductInfoVO.getFileId()));
        resultVO.setBundleProductInfo(bundleProductInfoVO);

        // 마이페이지 상품관리 묶음상품 목록 조회
        List<SummaryMyProductVO> summaryMyProductList = myProductRepo.selectMyProductList(requestSearchMyProductVO);
        // 묶음 상품 목록 이미지 URL 셋팅
        if(summaryMyProductList.size() > 0) { summaryMyProductList.forEach(x -> { x.setImgUrl(fileUtil.setImageUrl(x.getImgFileId()));}); }
        resultVO.setBundleProductList(summaryMyProductList);

        return resultVO;
    }


    /*********************************************
     * 바이어 상품
     *********************************************/

    /**
     * 마이페이지 상품관리 바이어 상품 리스트 조회
     * @param requestSearchMyProductVO
     * @return
     * @throws Exception
     */
    public PagingVO<SummaryMyBuyerProductInfoVO> searchMyBuyerProductInfoList(RequestSearchMyProductVO requestSearchMyProductVO) throws Exception {

        /*
         * 로그인 체크
         *      로그인 정보가 CustomUser.class 라면 jwt 로그인 조회
         *      로그인 상태가 아니라면 String 타입으로 떨이짐
         */
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof CustomUser) {
            CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            requestSearchMyProductVO.setLoginUtlinsttId(user.getUtlinsttId());
            requestSearchMyProductVO.setLoginUserId(user.getLoginUserId());
        }

        // 바이어 상품 정보 리스트 조회
        List<SummaryMyBuyerProductInfoVO> buyerProductList = myProductRepo.selectMyBuyerProductInfoList(requestSearchMyProductVO);
        // 바이어 상품 정보 리스트 이미지 URL 셋팅
        if(buyerProductList.size() > 0) {
            buyerProductList.forEach(x -> {
                x.setImgUrl(fileUtil.setImageUrl(x.getFileId()));
            });
        }

        return new PagingVO<>(requestSearchMyProductVO, buyerProductList);
    }

    /**
     * 마이페이지 상품관리 바이어 상품 등록 / 수정
     * @param detailMyBuyerProductVO
     * @return
     * @throws Exception
     */
    public String saveMyBuyerProduct(DetailMyBuyerProductVO detailMyBuyerProductVO) throws Exception {

        // 로그인 체크
        String loginUserId = null;
        String utlinsttId = null;

        // 로그인 정보가 CustomUser.class 라면 jwt 로그인 조회
        // 로그인 상태가 아니라면 String 타입으로 떨이짐
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof CustomUser) {
            CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            loginUserId = user.getLoginUserId();
            utlinsttId = user.getUtlinsttId();
        }

        // 디폴트 로직 : 수정(update)
        // update flg = true
        boolean updateFlg = true;

        // 바이어 상품 ID 셋팅
        String buyerInfId = detailMyBuyerProductVO.getBuyerProductInfoVO() != null ? detailMyBuyerProductVO.getBuyerProductInfoVO().getBuyerInfId() : null;
        // ID 가 존재하지 않을 경우, 새로 생성하고 등록 로직
        if(!StringUtils.hasLength(buyerInfId)) {
            buyerInfId = UUID.randomUUID().toString();
            updateFlg = false;
        }

        String finalLoginUserId = loginUserId;
        String finalUtlinsttId = utlinsttId;
        String finalBuyerInfId = buyerInfId;

        /*
         * 바이어 상품 정보 등록
         */
        BuyerProductInfoVO buyerProductInfoVO = detailMyBuyerProductVO.getBuyerProductInfoVO();
        if (buyerProductInfoVO != null) {
            buyerProductInfoVO.setBuyerInfId(finalBuyerInfId);
            buyerProductInfoVO.setSelrUsisId(finalUtlinsttId);
            buyerProductInfoVO.setRgsnUserId(finalLoginUserId);
            buyerProductInfoVO.setAmnnUserId(finalLoginUserId);
            if(updateFlg) {
                buyerProductRepo.updateBuyerProductInfo(buyerProductInfoVO);
            } else {
                buyerProductRepo.insertBuyerProductInfo(buyerProductInfoVO);
            }
        }

        /*
         * 묶음 상품 목록 등록
         */
        List<SummaryMyProductVO> buyerProductList = detailMyBuyerProductVO.getBuyerProductList();

        // 기존의 바이어 상품 목록 삭제
        buyerProductRepo.deleteBuyerProduct(finalBuyerInfId);

        if (buyerProductList != null && buyerProductList.size() > 0) {
            buyerProductList.forEach(x -> {
                // 바이어 상품 아이디 존재 체크
                if (!StringUtils.hasLength(x.getBuyerInfId())) {
                    BuyerProductVO buyerProductVO = new BuyerProductVO();
                    buyerProductVO.setBuyerInfId(finalBuyerInfId);
                    buyerProductVO.setPdfInfoId(x.getPdfInfoId());
                    buyerProductVO.setRgsnUserId(finalLoginUserId);
                    buyerProductVO.setAmnnUserId(finalLoginUserId);
                    buyerProductRepo.insertBuyerProduct(buyerProductVO);
                }
            });
        }

        return buyerInfId;
    }

    /**
     * 마이페이지 상품관리 바이어상품 삭제
     * @param buyerProductInfoVO
     * @throws Exception
     */
    public void deleteMyBuyerProductInfo(List<BuyerProductInfoVO> buyerProductInfoVO) throws Exception {

        String loginUserId = null;
        String utlinsttId = null;

        // 로그인 정보가 CustomUser.class 라면 jwt 로그인 조회
        // 로그인 상태가 아니라면 String 타입으로 떨이짐
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof CustomUser) {
            CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            loginUserId = user.getLoginUserId();
            utlinsttId = user.getUtlinsttId();
        }

        if(buyerProductInfoVO.size() > 0) {
            String finalLoginUserId = loginUserId;
            String finalUtlinsttId = utlinsttId;
            buyerProductInfoVO.forEach(x -> {
                if(!StringUtils.hasLength(x.getBuyerInfId())) {
                    return;
                }

                RequestBuyerProductInfoSearchVO requestBuyerProductInfoSearchVO = new RequestBuyerProductInfoSearchVO();
                requestBuyerProductInfoSearchVO.setBuyerInfId(x.getBuyerInfId());
//                requestBuyerProductInfoSearchVO.setRgsnUserId(finalLoginUserId);
                requestBuyerProductInfoSearchVO.setSelrUsisId(finalUtlinsttId);
                BuyerProductInfoVO oldBuyerproductInfo = buyerProductRepo.selectBuyerProductInfo(requestBuyerProductInfoSearchVO);

                // 바이어 상품 조회 결과가 존재하지 않는 경우, 처리 안함
                if (oldBuyerproductInfo == null) {
                    return;
                }

                // 수정자 ID 셋팅
                oldBuyerproductInfo.setAmnnUserId(finalLoginUserId);
                // 바이어 상품 삭제 처리
                buyerProductRepo.deleteBuyerProductInfo(oldBuyerproductInfo);
                // 이미지 파일 삭제 처리
                fileRepo.deleteFileInfo(oldBuyerproductInfo.getFileId());
            });
        }
    }

    /**
     * 마이페이지 상품관리 바이어상품 상세 조회
     * @param requestSearchMyProductVO
     * @return
     */
    public DetailMyBuyerProductVO searchMyBuyerProduct(RequestSearchMyProductVO requestSearchMyProductVO) {

        String loginUserId = null;
        String utlinsttId = null;

        // 로그인 정보가 CustomUser.class 라면 jwt 로그인 조회
        // 로그인 상태가 아니라면 String 타입으로 떨이짐
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof CustomUser) {
            CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            loginUserId = user.getLoginUserId();
            utlinsttId = user.getUtlinsttId();
        }

        DetailMyBuyerProductVO resultVO = new DetailMyBuyerProductVO();

        String buyerInfId = requestSearchMyProductVO.getBuyerInfId();

        // 바이어 상품 아이디가 존재하지 않는 경우, 검색 처리 안함
        if(!StringUtils.hasLength(buyerInfId)) {
            return null;
        }

        RequestBuyerProductInfoSearchVO searchVO = new RequestBuyerProductInfoSearchVO();
        searchVO.setBuyerInfId(buyerInfId);
        if (requestSearchMyProductVO.getBuyerFlg() != null && requestSearchMyProductVO.getBuyerFlg().equals("selr")) {
            searchVO.setSelrUsisId(utlinsttId); // 바이어상품 판매자(곧, 로그인사용자)인 경우에 set 한다. 비로그인사용자인 경우에는 set 하지 않는다.
        }
        // 마이페이지 상품관리 묶음상품 정보 조회
        BuyerProductInfoVO buyerProductInfoVO = buyerProductRepo.selectBuyerProductInfo(searchVO);
        if (buyerProductInfoVO != null) {
            buyerProductInfoVO.setImgUrl(fileUtil.setImageUrl(buyerProductInfoVO.getFileId()));
        }
        // 마이페이지 상품관리 묶음상품 정보 셋팅
        resultVO.setBuyerProductInfo(buyerProductInfoVO);

        // 마이페이지 상품관리 묶음상품 목록 조회
        requestSearchMyProductVO.setPdfPgrsYnFlg("use"); // '진열함' 조건을 사용한다.
        List<SummaryMyProductVO> summaryMyProductList = myProductRepo.selectMyProductList(requestSearchMyProductVO);
        // 마이페이지 상품관리 묶음상품 목록 이미지 URL 셋팅
        if(summaryMyProductList.size() > 0) {
            summaryMyProductList.forEach(x -> {
                x.setImgUrl(fileUtil.setImageUrl(x.getImgFileId()));
            });
        }
//        resultVO.setBuyerProductList(myProductRepo.selectMyProductList(requestSearchMyProductVO));
        resultVO.setBuyerProductList(summaryMyProductList);
        return resultVO;
    }
}
