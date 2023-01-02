package com.ibk.sb.restapi.biz.service.product.bundle;

import com.ibk.sb.restapi.app.common.constant.ComCode;
import com.ibk.sb.restapi.app.common.util.FileUtil;
import com.ibk.sb.restapi.app.common.vo.CustomUser;
import com.ibk.sb.restapi.app.common.vo.PagingVO;
import com.ibk.sb.restapi.biz.service.product.bundle.repo.BundleInfoRepo;
import com.ibk.sb.restapi.biz.service.product.bundle.repo.BundleProductRepo;
import com.ibk.sb.restapi.biz.service.product.bundle.vo.BundleProductInfoVO;
import com.ibk.sb.restapi.biz.service.product.bundle.vo.SummaryBundleInfoVO;
import com.ibk.sb.restapi.biz.service.product.bundle.vo.SummaryBundleProductVO;
import com.ibk.sb.restapi.biz.service.product.common.vo.RequestSearchProductVO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BundleProductService {

    private final BundleInfoRepo bundleInfoRepo;

    private final BundleProductRepo bundleProductRepo;

    private final FileUtil fileUtil;

    /**
     * 묶음 상품 정보 리스트 조회
     * @param requestProductInfoSearchVO
     * @return
     * @throws Exception
     */
    public PagingVO<SummaryBundleInfoVO> searchBundleProductInfoList(RequestSearchProductVO requestProductInfoSearchVO) throws Exception {

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

        // 묶음 상품 정보 리스트 조회
        List<SummaryBundleInfoVO> bundleInfoVOList = bundleInfoRepo.selectBundleInfoList(requestProductInfoSearchVO);

        /*
         * 묶음상품 리스트 조회
         */
        bundleInfoVOList.forEach(x -> {
            // 묶음 상품 정보 이미지 URL 셋팅
            x.setImgUrl(fileUtil.setImageUrl(x.getImgFileId()));

            // 묶음 상품 검색 조건 셋팅
            RequestSearchProductVO requestBundleProductVO = new RequestSearchProductVO();
            // 로그인 정보 셋팅
            requestBundleProductVO.setLoginUserId(requestProductInfoSearchVO.getLoginUserId());
            requestBundleProductVO.setLoginUtlinsttId(requestProductInfoSearchVO.getLoginUtlinsttId());
            // 페이지 정보 셋팅
            if (requestProductInfoSearchVO.getItemPage() != null) {
                requestBundleProductVO.setPage(requestProductInfoSearchVO.getItemPage().getPage());
                requestBundleProductVO.setRecord(requestProductInfoSearchVO.getItemPage().getRecord());
            }
            // 상품 공통 파일 정보 구분 코드 ID 셋팅 - 상품 이미지
            requestBundleProductVO.setFilePtrnId(ComCode.GDS05001.getCode());
            // 묶음 상품 정보 ID 셋팅
            requestBundleProductVO.setBunInfId(x.getBunInfId());

            // 묶음 상품 리스트 조회 및 셋팅
            List<SummaryBundleProductVO> resultItemList = bundleProductRepo.selectBundleProductList(requestBundleProductVO);

            // 묶음 상품 리스트 이미지 URL 셋팅
            if(resultItemList.size() > 0) {
                resultItemList.forEach(y -> {
                    y.setImgUrl(fileUtil.setImageUrl(y.getImgFileId()));
                });
            }
            x.setItems(resultItemList);
        });

        return new PagingVO<>(requestProductInfoSearchVO, bundleInfoVOList);
    }

    /**
     * 묶음 제품 정보 상세 조회
     * @param requestSearchProductVO
     * @return
     * @throws Exception
     */
    public BundleProductInfoVO searchBundleProductInfo(RequestSearchProductVO requestSearchProductVO) throws Exception {

        // 묶음 제품 정보 상세 조회
        BundleProductInfoVO bundleProductInfoVO = bundleInfoRepo.selectBundleProductInfo(requestSearchProductVO.getBunInfId());

        /*
         * 로그인 체크
         *      로그인 정보가 CustomUser.class 라면 jwt 로그인 조회
         *      로그인 상태가 아니라면 String 타입으로 떨이짐
         */
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof CustomUser) {
            CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            // 묶음 제품 정보 상세 이력추가
            bundleInfoRepo.insertBundleProductViewHistory(requestSearchProductVO.getBunInfId(), user.getUtlinsttId(), user.getLoginUserId());
        }

        // 묶음 제품 정보 이미지 URL 셋팅
        bundleProductInfoVO.setImgUrl(fileUtil.setImageUrl(bundleProductInfoVO.getFileId()));

        return bundleProductInfoVO;
    }

    /**
     * 묶음 상품 리스트 조회
     * @param requestSearchProductVO
     * @return
     * @throws Exception
     */
    public List<SummaryBundleProductVO> searchBundleProductList(RequestSearchProductVO requestSearchProductVO) throws Exception {

        /*
         * 로그인 체크
         *      로그인 정보가 CustomUser.class 라면 jwt 로그인 조회
         *      로그인 상태가 아니라면 String 타입으로 떨이짐
         */
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof CustomUser) {
            CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            requestSearchProductVO.setLoginUserId(user.getLoginUserId());
            requestSearchProductVO.setLoginUtlinsttId(user.getUtlinsttId());
        }

        List<SummaryBundleProductVO> summaryBundleProductVOList = bundleProductRepo.selectBundleProductList(requestSearchProductVO);
        // 묶음 상품 리스트 이미지 URL 셋팅
        if(summaryBundleProductVOList.size() > 0) {
            summaryBundleProductVOList.forEach(x -> {
                x.setImgUrl(fileUtil.setImageUrl(x.getImgFileId()));
            });
        }

        return summaryBundleProductVOList;

    }

    /**
     * 운영자포털 > 묶음상품 > 메인 유무
     * @param searchParams
     * @return
     * @throws Exception
     */
    public void updateBundleMainUsed(List<RequestSearchProductVO> searchParams) throws Exception {

        if(searchParams.size() > 0) {
            searchParams.forEach(x -> {
                RequestSearchProductVO data = new RequestSearchProductVO();
                data.setBunInfId(x.getBunInfId());
                data.setMainPageFlg(x.getMainPageFlg());
                bundleProductRepo.updateBundleMainUsed(data); //문의 등록
            });
        }

    }


//    /**
//     * 묶음 상품 리스트 조회
//     * @param requestProductInfoSearchVO
//     * @return
//     * @throws Exception
//     */
//    public List<BundleProductVO> searchBundleProductList(RequestSearchProductVO requestProductInfoSearchVO) throws Exception {
//
//        List<BundleProductVO> bundleProductList = bundleProductRepo.selectBundleProductList(requestProductInfoSearchVO);
//
//        return bundleProductList;
//    }

//    /**
//     * 번들 제품 정보 입력/수정
//     * @param bundleProductInfoVO
//     * @return
//     * @throws Exception
//     */
//    public void saveBundleProductInfo(BundleProductInfoVO bundleProductInfoVO) throws Exception {
//
//        // 로그인 정보 조회
////        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//        // check if the product exists
//        BundleProductInfoVO product = searchBundleProductInfo(bundleProductInfoVO.getBunInfId());
//
//        // save the product
//        if(product == null) {
//            String productId = UUID.randomUUID().toString();
//
////            productInfoVO.setRgsnUserId(user.getUsername());
//
//            bundleProductInfoVO.setBunInfId(productId);
//
//            bundleProductInfoRepo.insertBundleProductInfo(bundleProductInfoVO);
//
//            bundleProductInfoVO.getBundleProducts().stream().map(data -> bundleProductRepo.insertBundleProduct(data));
//
//        } else {
//            // update the product
////            productInfoVO.setAmnnUserId(user.getUsername());
//
//            bundleProductInfoRepo.updateBundleProductInfo(bundleProductInfoVO);
//
//            bundleProductInfoVO.getBundleProducts().stream().map(data -> bundleProductRepo.updateBundleProduct(data));
//        }
//    }
//
//    /**
//     * 번들 제품 정보 삭제
//     * @param bunInfId
//     * @return
//     * @throws Exception
//     */
//    public void deleteBundleProductInfo(String bunInfId) throws Exception {
//
//        // 로그인 정보 조회
//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//        // delete bundle product info
//        bundleProductInfoRepo.deleteBundleProductInfo(bunInfId);
//
//        // delete bundle products associated with the info
//        bundleProductRepo.deleteBundleProducts(bunInfId);
//
//    }
//
//    /**
//     * 번들 제품 삭제
//     * @param bunInfId
//     * @return
//     * @throws Exception
//     */
//    public void deleteBundleProducts(String bunInfId) throws Exception {
//
//        // 로그인 정보 조회
//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//        bundleProductRepo.deleteBundleProducts(bunInfId);
//
//    }
//
//    /**
//     * 번들 제품 삭제
//     * @param gdsId
//     * @return
//     * @throws Exception
//     */
//    public void deleteBundleProduct(String gdsId) throws Exception {
//
//        // 로그인 정보 조회
//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//        bundleProductRepo.deleteBundleProduct(gdsId);
//
//    }
//
//
//
//
//    /**
//     * 상품 스와이퍼 조회
//     */
//
//    /**
//     * 상단 인기상품 조회
//     */
//
//    /**
//     * 하단 인기상품 조회
//     */
//
//    /**
//     * 셀럽쵸이스 조회
//     */
}
