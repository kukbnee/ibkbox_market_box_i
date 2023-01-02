package com.ibk.sb.restapi.biz.api.sellerstore;

import com.ibk.sb.restapi.app.common.util.BizException;
import com.ibk.sb.restapi.app.common.vo.PageVO;
import com.ibk.sb.restapi.app.common.vo.PagingVO;
import com.ibk.sb.restapi.app.common.vo.ResponseData;
import com.ibk.sb.restapi.biz.service.product.bundle.BundleProductService;
import com.ibk.sb.restapi.biz.service.product.bundle.vo.SummaryBundleInfoVO;
import com.ibk.sb.restapi.biz.service.product.common.vo.RequestSearchProductVO;
import com.ibk.sb.restapi.biz.service.product.single.SingleProductService;
import com.ibk.sb.restapi.biz.service.product.single.vo.SummarySingleProductVO;
import com.ibk.sb.restapi.biz.service.seller.SellerStoreService;
import com.ibk.sb.restapi.biz.service.seller.vo.RequestSellerVO;
import com.ibk.sb.restapi.biz.service.seller.vo.SellerCategoryVO;
import com.ibk.sb.restapi.biz.service.seller.vo.SellerStoreHeaderVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = {"커머스 박스 - 판매자 스토어 API"})
@RestController
@Slf4j
@RequestMapping(path={"/api/product/seller", "/api/mk/v1/product/seller"}, produces = {"application/json"})
@RequiredArgsConstructor
public class SellerStoreController {

    private final SellerStoreService sellerStoreService;

    private final SingleProductService singleProductService;

    private final BundleProductService bundleProductService;

    /**
     * 판매자 스토어 헤더 정보 조회
     * @param selrUsisId
     * @return
     */
    @ApiOperation(value = "판매자 스토어 헤더 정보 조회")
    @ApiImplicitParam(name = "selrUsisId", value = "판매자 이용기관 ID")
    @GetMapping("/store/header")
    public ResponseData searchSellerHeaderInfo(@RequestParam(name = "selrUsisId")String selrUsisId) {

        try {

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
            log.error("Fail Trace", ex);
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(ex.getMessage())
                    .build();
        }
    }

    /**
     * 판매자 스토어 카테고리 목록 조회
     * @param requestSellerVO
     * @return
     */
    @ApiOperation(value = "판매자 스토어 카테고리 목록 조회")
    @ApiImplicitParam(name = "requestSellerVO", value = "RequestSellerVO")
    @PostMapping("/category/list")
    public ResponseData searchSellerCategoryList(@RequestBody RequestSellerVO requestSellerVO) {

        try {
            List<SellerCategoryVO> sellerStoreHeaderVO = sellerStoreService.searchSellerCategoryList(requestSellerVO.getSelrUsisId());

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
            log.error("Fail Trace", ex);
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(ex.getMessage())
                    .build();
        }
    }

    /**
     * 판매자 스토어 배너이미지 목록 조회
     * @param requestSellerVO
     * @return
     *      판매자 스토어 배경이미지, Key : sellerBgImg, Value : SellerFileVO
     *      판매자 스토어 배너 리스트, Key : sellerBannerList,  Value : List<SellerFileVO>
     */
    @ApiOperation(value = "판매자 스토어 배너이미지 목록 조회")
    @ApiImplicitParam(name = "requestSellerVO", value = "RequestSellerVO")
    @PostMapping("/banner/list")
    public ResponseData searchSellerBannerList(@RequestBody RequestSellerVO requestSellerVO) {

        try {

            // 판매자 스토어 배너이미지 정보 조회
            Map<String, Object> resultMap = sellerStoreService.searchSellerBannerList(requestSellerVO.getSelrUsisId());

            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(resultMap)
                    .build();

        } catch (BizException ex) {
            log.error(ex.getMessage());
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(ex.getMessage())
                    .build();
        } catch (Exception ex) {
            log.error("Fail Trace", ex);
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(ex.getMessage())
                    .build();
        }
    }

    /**
     * 판매자 스토어 상품 헤더 정보 조회
     * @param requestSearchProductVO
     * @return
     *      개별상품 총 개수, Key : singleSize, Value : Integer
     *      번들상품 총 개수, Key : bundleSize,  Value : Integer
     */
    @ApiOperation(value = "판매자 스토어 상품 헤더 정보 조회")
    @GetMapping("/product/header")
    @Transactional
    public ResponseData searchProductHeaderinfo(RequestSearchProductVO requestSearchProductVO) {

        try {

            HashMap<String, Integer> resultMap = new HashMap<>();

            Integer singleSize = 0;
            // 개별상품 리스트 조회
            PagingVO<SummarySingleProductVO> productVOList = singleProductService.searchSingleProductList(requestSearchProductVO);
            // 개별상품의 사이즈가 0보다 크면 index 0 번째 항목의 totalCnt 셋팅
            if(productVOList.getList().size() > 0) {
                singleSize = productVOList.getList().get(0).getTotalCnt();
            }
            Integer bundleSize = 0;
            // 번들상품 리스트 조회
            PagingVO<SummaryBundleInfoVO> bundleProductInfoList = bundleProductService.searchBundleProductInfoList(requestSearchProductVO);
            // 번들상품의 사이즈가 0보다 크면 index 0 번째 항목의 totalCnt 셋팅
            if(bundleProductInfoList.getList().size() > 0) {
                bundleSize = bundleProductInfoList.getList().get(0).getTotalCnt();
            }

            resultMap.put("singleSize", singleSize);
            resultMap.put("bundleSize", bundleSize);

            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(resultMap)
                    .build();

        } catch (BizException ex) {
            log.error(ex.getMessage());
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(ex.getMessage())
                    .build();
        } catch (Exception ex) {
            log.error("Fail Trace", ex);
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(ex.getMessage())
                    .build();
        }
    }

    /**
     * 판매자 스토어 개별상품 목록 조회
     * @param requestSearchProductVO
     * @return
     */
    @ApiOperation(value = "판매자 스토어 개별상품 목록 조회")
    @ApiImplicitParam(name = "requestSearchProductVO", value = "RequestSearchProductVO")
    @PostMapping("/single/list")
    public ResponseData searchSellerSingleProductList(@RequestBody RequestSearchProductVO requestSearchProductVO) {

        try {

            PagingVO<SummarySingleProductVO> productVOList = singleProductService.searchSingleProductList(requestSearchProductVO);

            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(productVOList)
                    .build();

        } catch (BizException ex) {
            log.error(ex.getMessage());
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(ex.getMessage())
                    .build();
        } catch (Exception ex) {
            log.error("Fail Trace", ex);
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(ex.getMessage())
                    .build();
        }
    }

    /**
     * 판매자 스토어 묶음상품 목록 조회
     * @param requestSearchProductVO
     * @return
     */
    @ApiOperation(value = "판매자 스토어 묶음상품 목록 조회")
    @ApiImplicitParam(name = "requestSearchProductVO", value = "RequestSearchProductVO")
    @PostMapping("/bundle/list")
    public ResponseData searchSellerBundleProductList(@RequestBody RequestSearchProductVO requestSearchProductVO) {

        try {

            // 상품 묶음상품 리스트 페이징 셋팅
            requestSearchProductVO.setItemPage(new PageVO(1, Integer.MAX_VALUE)); // items 의 페이징이다. 곧, 묶음상품 안의 상품 리스트에 대한 페이징

            PagingVO<SummaryBundleInfoVO> bundleProductInfoList = bundleProductService.searchBundleProductInfoList(requestSearchProductVO);

            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(bundleProductInfoList)
                    .build();

        } catch (BizException ex) {
            log.error(ex.getMessage());
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(ex.getMessage())
                    .build();
        } catch (Exception ex) {
            log.error("Fail Trace", ex);
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(ex.getMessage())
                    .build();
        }
    }
}
