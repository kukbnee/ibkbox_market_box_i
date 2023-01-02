package com.ibk.sb.restapi.biz.api.product;

import com.ibk.sb.restapi.app.common.util.BizException;
import com.ibk.sb.restapi.app.common.vo.PagingVO;
import com.ibk.sb.restapi.app.common.vo.ResponseData;
import com.ibk.sb.restapi.biz.service.agency.AgencyService;
import com.ibk.sb.restapi.biz.service.agency.vo.RequestSearchAgencyVO;
import com.ibk.sb.restapi.biz.service.product.bundle.BundleProductService;
import com.ibk.sb.restapi.biz.service.product.bundle.vo.BundleProductInfoVO;
import com.ibk.sb.restapi.biz.service.product.bundle.vo.SummaryBundleProductVO;
import com.ibk.sb.restapi.biz.service.product.common.vo.ProductSearchHistoryVO;
import com.ibk.sb.restapi.biz.service.product.single.SingleProductService;
import com.ibk.sb.restapi.biz.service.product.common.vo.ProductDetailVO;
import com.ibk.sb.restapi.biz.service.product.common.vo.RequestSearchProductVO;
import com.ibk.sb.restapi.biz.service.qna.QnaService;
import com.ibk.sb.restapi.biz.service.qna.vo.QnaInfoVO;
import com.ibk.sb.restapi.biz.service.review.ReviewService;
import com.ibk.sb.restapi.biz.service.review.vo.RequestSearchReviewVO;
import com.ibk.sb.restapi.biz.service.review.vo.ReviewHeaderVO;
import com.ibk.sb.restapi.biz.service.review.vo.ReviewVO;
import com.ibk.sb.restapi.biz.service.seller.SellerStoreService;
import com.ibk.sb.restapi.biz.service.seller.vo.SellerStoreHeaderVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"커머스 박스 - 상품 상세 API"})
@RestController
@Slf4j
@RequestMapping(path = {"/api/product/detail", "/api/mk/v1/product/detail"}, produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
public class ProductDetailController {

    private final BundleProductService bundleProductService;

    private final ReviewService reviewService;

    private final SellerStoreService sellerStoreService;

    private final SingleProductService singleProductService;

    private final AgencyService agencyService;

    private final QnaService qnaService;

    /**
     * 상품 상세 판매자 헤더 정보 조회
     * @param selrUsisId : 판매자 이용기관 ID
     * @return
     */
    @ApiOperation(value = "상품 상세 판매자 헤더 정보 조회")
    @ApiImplicitParam(name = "selrUsisId", value = "판매자 이용기관 ID")
    @GetMapping("/seller/header")
    public ResponseData searchSellerHeaderInfo(@RequestParam(name = "selrUsisId")String selrUsisId) {

        try {
            // 상품 상세 판매자 헤더 정보 조회
            SellerStoreHeaderVO sellerStoreHeaderVO = sellerStoreService.searchSellerHeaderInfo(selrUsisId);

            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(sellerStoreHeaderVO)
                    .build();

        } catch (BizException ex) {
            log.error(ex.getMessage());
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(ex.getMessage())
                    .build();
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(ex.getMessage())
                    .build();
        }
    }

    /**
     * 상품 상세 조회
     * @return
     */
    @ApiOperation(value = "상품 상세 조회")
    @GetMapping()
    public ResponseData searchProductDetail(RequestSearchProductVO requestSearchProductVO) {

        try {
            // 상품 상세 정보 조회
            ProductDetailVO productDetailVO = singleProductService.searchSingleProduct(requestSearchProductVO);

            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(productDetailVO)
                    .build();

        } catch (BizException ex) {
            log.error(ex.getMessage());
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(ex.getMessage())
                    .build();
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(ex.getMessage())
                    .build();
        }
    }

//    /**
//     * 상품 상세 판매자 정보 조회
//     * @return
//     */
//    @GetMapping("/seller")
//    public ResponseData searchProductSeller() {
//
//        try {
//            // List<BannerVO> bannerVOList = bannerService.searchMainBanner();
//
//            return ResponseData.builder()
//                    .code(HttpStatus.OK.value())
//                    .message(HttpStatus.OK.getReasonPhrase())
//                    .data(null)
//                    .build();
//
//        } catch (Exception ex) {
//            log.error(ex.getMessage());
//            return ResponseData.builder()
//                    .code(HttpStatus.BAD_REQUEST.value())
//                    .message(ex.getMessage())
//                    .build();
//        }
//    }
//
//
//    /**
//     * 상품 판매자 연관상품 목록 조회
//     * @return
//     */
//    @GetMapping("/related/list")
//    public ResponseData searchProductDetailRelatedList() {
//
//        try {
//            // List<BannerVO> bannerVOList = bannerService.searchMainBanner();
//
//            return ResponseData.builder()
//                    .code(HttpStatus.OK.value())
//                    .message(HttpStatus.OK.getReasonPhrase())
//                    .data(null)
//                    .build();
//
//        } catch (Exception ex) {
//            log.error(ex.getMessage());
//            return ResponseData.builder()
//                    .code(HttpStatus.BAD_REQUEST.value())
//                    .message(ex.getMessage())
//                    .build();
//        }
//    }

    /*********************************************
     * 구매후기
     *********************************************/

    /**
     * 구매후기 헤더정보 조회
     * 상품 상세 구매후기 헤더정보 조회
     * @return
     */
    @ApiOperation(value = "구매후기 헤더정보 조회")
    @GetMapping("/review/header")
    public ResponseData searchProductDetailReviewHeader(RequestSearchReviewVO requestSearchReviewVO) {

        try {
            ReviewHeaderVO reviewHeaderVO = reviewService.searchProductDetailReviewHeader(requestSearchReviewVO);

            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(reviewHeaderVO)
                    .build();

        } catch (BizException ex) {
            log.error(ex.getMessage());
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(ex.getMessage())
                    .build();
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(ex.getMessage())
                    .build();
        }
    }

    /**
     * 구매후기 이미지 목록 조회
     * 상품 상세 구매후기 이미지 목록
     * @return
     */
    @ApiOperation(value = "구매후기 이미지 목록 조회")
    @GetMapping("/review/img/list")
    public ResponseData searchProductDetailReviewImageList(RequestSearchReviewVO requestSearchReviewVO) {

        try {
            List<ReviewVO> reviewVOList = reviewService.searchProductDetailReviewImageList(requestSearchReviewVO);

            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(reviewVOList)
                    .build();

        } catch (BizException ex) {
            log.error(ex.getMessage());
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(ex.getMessage())
                    .build();
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(ex.getMessage())
                    .build();
        }
    }

    /**
     * 구매후기 목록 조회
     * 상품 상세 구매후기 리스트 조회
     * @return
     */
    @ApiOperation(value = "구매후기 목록 조회")
    @GetMapping("/review/list")
    public ResponseData searchProductDetailReviewList(RequestSearchReviewVO requestSearchReviewVO) {

        try {
            PagingVO<ReviewVO> reviewVOList = reviewService.searchProductDetailReviewList(requestSearchReviewVO);

            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(reviewVOList)
                    .build();

        } catch (BizException ex) {
            log.error(ex.getMessage());
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(ex.getMessage())
                    .build();
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(ex.getMessage())
                    .build();
        }
    }

    /**
     * 구매후기 등록
     * 상품 상세 구매후기 등록
     * @return
     */
    @ApiOperation(value = "구매후기 등록")
    @ApiImplicitParam(name = "reviewVO", value = "ReviewVO")
    @PostMapping("/review/save")
    public ResponseData saveProductDetailReview(@RequestBody ReviewVO reviewVO) {

        try {
            reviewService.saveProductDetailReview(reviewVO);

            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .build();

        } catch (BizException ex) {
            log.error(ex.getMessage());
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(ex.getMessage())
                    .build();
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(ex.getMessage())
                    .build();
        }
    }

    /**
     * 구매후기 수정
     * 상품 상세 구매후기 수정
     * @return
     */
    @ApiOperation(value = "구매후기 수정")
    @ApiImplicitParam(name = "reviewVO", value = "ReviewVO")
    @PostMapping("/review/update")
    public ResponseData updateProductDetailReview(@RequestBody ReviewVO reviewVO) {

        try {
            reviewService.updateProductDetailReview(reviewVO);

            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .build();

        } catch (BizException ex) {
            log.error(ex.getMessage());
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(ex.getMessage())
                    .build();
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(ex.getMessage())
                    .build();
        }
    }

    /**
     * 구매후기 삭제
     * 상품 상세 구매후기 삭제
     * @param reviewVO
     * @return
     */
    @ApiOperation(value = "구매후기 삭제")
    @ApiImplicitParam(name = "reviewVO", value = "ReviewVO")
    @PostMapping("/review/delete")
    public ResponseData deleteProductDetailReview(@RequestBody ReviewVO reviewVO) {

        try {
            reviewService.deleteProductDetailReview(reviewVO);

            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .build();

        } catch (BizException ex) {
            log.error(ex.getMessage());
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(ex.getMessage())
                    .build();
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(ex.getMessage())
                    .build();
        }
    }

    /*********************************************
     * 에이전시
     *********************************************/

    /**
     * 상품상세 에이전시 요청
     * @return
     */
    @ApiOperation(value = "상품상세 에이전시 요청")
    @GetMapping("/agency/request")
    public ResponseData requestProductDetailAgency(RequestSearchAgencyVO requestSearchAgencyVO) {
        try {

            return agencyService.requestProductDetailAgency(requestSearchAgencyVO);

        } catch (BizException ex) {
            log.error(ex.getMessage());
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(ex.getMessage())
                    .build();
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(ex.getMessage())
                    .build();
        }
    }

    /*********************************************
     * etc
     *********************************************/

    /**
     * 문의번호 클릭
     * 상품 상세 핀메자 정보 문의번호 클릭
     * 판매자의 문의번호를 클릭한 이용자의 기록을 남기는 용도
     * @return
     */
    @ApiOperation(value = "문의번호 클릭")
    @ApiImplicitParam(name = "productSearchHistoryVO", value = "ProductSearchHistoryVO")
    @PostMapping("/seller/click")
    public ResponseData clickProductDetailSeller(@RequestBody ProductSearchHistoryVO productSearchHistoryVO) {

        try {

            singleProductService.clickProductDetailSeller(productSearchHistoryVO);

            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .build();

        } catch (BizException ex) {
            log.error(ex.getMessage());
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(ex.getMessage())
                    .build();
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(ex.getMessage())
                    .build();
        }
    }

    /**
     * 문의하기 등록
     * 상품 상세 문의하기 등록
     *      문의 정보 데이터가 없을 경우, 데이터를 등록하고 문의 정보 ID return
     *      문의 정보 데이터가 존재할 경우, 해당 문의 정보 ID return
     * @param qnaInfoVO
     * @return
     */
    @ApiOperation(value = "문의하기 등록")
    @ApiImplicitParam(name = "qnaInfoVO", value = "QnaInfoVO")
    @PostMapping("/qna/save")
    public ResponseData saveProductDetailQnaInfo(@RequestBody QnaInfoVO qnaInfoVO) {

        try {

            String qnaInfoId = qnaService.saveQnaInfo(qnaInfoVO);

            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(qnaInfoId)
                    .build();

        } catch (BizException ex) {
            log.error(ex.getMessage());
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(ex.getMessage())
                    .build();
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(ex.getMessage())
                    .build();
        }
    }









//    /**
//     * 상품 상세 장바구니 추가 / 삭제
//     * @return
//     */
//    @PostMapping("/basket/save")
//    public ResponseData saveProductDetailBasket(@RequestBody HashMap<String, Object> requestObject) {
//
//        try {
//            // List<BannerVO> bannerVOList = bannerService.searchMainBanner();
//
//            return ResponseData.builder()
//                    .code(HttpStatus.OK.value())
//                    .message(HttpStatus.OK.getReasonPhrase())
//                    .data(null)
//                    .build();
//
//        } catch (Exception ex) {
//            log.error(ex.getMessage());
//            return ResponseData.builder()
//                    .code(HttpStatus.BAD_REQUEST.value())
//                    .message(ex.getMessage())
//                    .build();
//        }
//    }
//
//    /**
//     * 상품 상세 위시리스트 추가 / 삭제
//     * @return
//     */
//    @PostMapping("/wish/save")
//    public ResponseData saveProductDetailWish(@RequestBody HashMap<String, Object> requestObject) {
//
//        try {
//            // List<BannerVO> bannerVOList = bannerService.searchMainBanner();
//
//            return ResponseData.builder()
//                    .code(HttpStatus.OK.value())
//                    .message(HttpStatus.OK.getReasonPhrase())
//                    .data(null)
//                    .build();
//
//        } catch (Exception ex) {
//            log.error(ex.getMessage());
//            return ResponseData.builder()
//                    .code(HttpStatus.BAD_REQUEST.value())
//                    .message(ex.getMessage())
//                    .build();
//        }
//    }
//
//    /**
//     * 상품 상세 구매하기
//     * @return
//     */
//    @PostMapping("/buy")
//    public ResponseData buyProductDetail(@RequestBody HashMap<String, Object> requestObject) {
//
//        try {
//            // List<BannerVO> bannerVOList = bannerService.searchMainBanner();
//
//            return ResponseData.builder()
//                    .code(HttpStatus.OK.value())
//                    .message(HttpStatus.OK.getReasonPhrase())
//                    .data(null)
//                    .build();
//
//        } catch (Exception ex) {
//            log.error(ex.getMessage());
//            return ResponseData.builder()
//                    .code(HttpStatus.BAD_REQUEST.value())
//                    .message(ex.getMessage())
//                    .build();
//        }
//    }
//
//    /**
//     * 상품 상세 견적 요청
//     * @return
//     */
//    @PostMapping("/estimate/apply")
//    public ResponseData applyProductDetailEstimate(@RequestBody HashMap<String, Object> requestObject) {
//
//        try {
//            // List<BannerVO> bannerVOList = bannerService.searchMainBanner();
//
//            return ResponseData.builder()
//                    .code(HttpStatus.OK.value())
//                    .message(HttpStatus.OK.getReasonPhrase())
//                    .data(null)
//                    .build();
//
//        } catch (Exception ex) {
//            log.error(ex.getMessage());
//            return ResponseData.builder()
//                    .code(HttpStatus.BAD_REQUEST.value())
//                    .message(ex.getMessage())
//                    .build();
//        }
//    }
//
//    /**
//     * 상품 상세 견적 요청 취소
//     * @return
//     */
//    @PostMapping("/estimate/cancel")
//    public ResponseData cancelProductDetailEstimate(@RequestBody HashMap<String, Object> requestObject) {
//
//        try {
//            // List<BannerVO> bannerVOList = bannerService.searchMainBanner();
//
//            return ResponseData.builder()
//                    .code(HttpStatus.OK.value())
//                    .message(HttpStatus.OK.getReasonPhrase())
//                    .data(null)
//                    .build();
//
//        } catch (Exception ex) {
//            log.error(ex.getMessage());
//            return ResponseData.builder()
//                    .code(HttpStatus.BAD_REQUEST.value())
//                    .message(ex.getMessage())
//                    .build();
//        }
//    }


    /*********************************************
     * 묶음 상품 상세화면
     *********************************************/

    /**
     * 묶음 상품 상세 조회
     * @param requestSearchProductVO
     * @return
     */
    @ApiOperation(value = "묶음 상품 상세 조회")
    @GetMapping("/bundle")
    public ResponseData searchBundleProductInfo(RequestSearchProductVO requestSearchProductVO) {

        try {
            BundleProductInfoVO bundleProductInfoVO = bundleProductService.searchBundleProductInfo(requestSearchProductVO);

            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(bundleProductInfoVO)
                    .build();

        } catch (BizException ex) {
            log.error(ex.getMessage());
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(ex.getMessage())
                    .build();
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(ex.getMessage())
                    .build();
        }
    }

    /**
     * 묶음 상품 연결 상품 목록 조회
     * 묶음 상품 상세 > 묶음 상품 조회
     * @param requestSearchProductVO
     * @return
     */
    @ApiOperation(value = "묶음 상품 연결 상품 목록 조회")
    @GetMapping("/bundle/product/list")
    public ResponseData searchBundleProductList(RequestSearchProductVO requestSearchProductVO) {

        try {
            List<SummaryBundleProductVO> items = bundleProductService.searchBundleProductList(requestSearchProductVO);

            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(items)
                    .build();

        } catch (BizException ex) {
            log.error(ex.getMessage());
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(ex.getMessage())
                    .build();
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(ex.getMessage())
                    .build();
        }
    }


//    /*********************************************
//     * 바이어 상품 상세화면
//     *********************************************/
//    /**
//     * 묶음 상품 정보 상세 조회
//     * @param requestSearchProductVO
//     * @return
//     */
//    @GetMapping("/buyer")
//    public ResponseData searchBuyerProduct(RequestSearchProductVO requestSearchProductVO) {
//
//        try {
//            BundleProductInfoVO bundleProductInfoVO = bundleProductService.searchBundleProductInfo(requestSearchProductVO);
//
//            return ResponseData.builder()
//                    .code(HttpStatus.OK.value())
//                    .message(HttpStatus.OK.getReasonPhrase())
//                    .data(bundleProductInfoVO)
//                    .build();
//
//        } catch (Exception ex) {
//            log.error(ex.getMessage());
//            return ResponseData.builder()
//                    .code(HttpStatus.BAD_REQUEST.value())
//                    .message(ex.getMessage())
//                    .build();
//        }
//    }

}