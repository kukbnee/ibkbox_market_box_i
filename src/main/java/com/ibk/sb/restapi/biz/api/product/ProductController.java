package com.ibk.sb.restapi.biz.api.product;

import com.ibk.sb.restapi.app.common.constant.ComCode;
import com.ibk.sb.restapi.app.common.util.BizException;
import com.ibk.sb.restapi.app.common.vo.PageVO;
import com.ibk.sb.restapi.app.common.vo.PagingVO;
import com.ibk.sb.restapi.app.common.vo.ResponseData;
import com.ibk.sb.restapi.biz.service.banner.BannerService;
import com.ibk.sb.restapi.biz.service.banner.vo.BannerVO;
import com.ibk.sb.restapi.biz.service.banner.vo.SummaryBannerVO;
import com.ibk.sb.restapi.biz.service.category.CategoryService;
import com.ibk.sb.restapi.biz.service.category.vo.CategoryVO;
import com.ibk.sb.restapi.biz.service.category.vo.ResponseCategoryVO;
import com.ibk.sb.restapi.biz.service.common.CommonService;
import com.ibk.sb.restapi.biz.service.product.bundle.BundleProductService;
import com.ibk.sb.restapi.biz.service.product.bundle.vo.SummaryBundleInfoVO;
import com.ibk.sb.restapi.biz.service.product.single.SingleProductService;
import com.ibk.sb.restapi.biz.service.product.single.vo.SummarySingleProductVO;
import com.ibk.sb.restapi.biz.service.product.common.vo.RequestSearchProductVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = {"커머스 박스 - 상품 API"})
@RestController
@Slf4j
@RequestMapping(path = {"/api/product", "/api/mk/v1/product"}, produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
public class ProductController {

    private final BannerService bannerService;

    private final CategoryService categoryService;

    private final SingleProductService singleProductService;

    private final BundleProductService bundleProductService;

    private final CommonService commonService;

    /**
     * 상품 배너 목록 조회
     * @return
     */
    @ApiOperation(value = "상품 배너 목록 조회")
    @GetMapping("/banner/list")
    public ResponseData searchProductList() {

        try {

            // 상품 베너 코드
            String banTypeId = ComCode.BANNER_PRODUCT.getCode();

            List<BannerVO> bannerVOList = bannerService.searchBannerList(banTypeId);

            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(bannerVOList)
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
     * 상품 카테고리 목록 조회
     * 상품 카테고리 리스트 조회(대분류)
     * @param oneCtgyId : 1차 분류 코드
     * @return
     */
    @ApiOperation(value = "상품 카테고리 목록 조회")
    @ApiImplicitParam(name = "oneCtgyId", value = "1차 분류 코드", example = "01")
    @GetMapping("/category/list")
    public ResponseData searchCategoryList(@RequestParam(name = "oneCtgyId") String oneCtgyId) {

        try {
            // 대분류 코드 검색 조건 셋팅
            CategoryVO requsetCategoryVO = new CategoryVO(oneCtgyId, null, null, null, null);

            List<ResponseCategoryVO> categoryVOList = categoryService.searchCategoryList(requsetCategoryVO);

            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(categoryVOList)
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
     * 상품 전체 카테고리 목록 조회
     * @param parentCode
     * @return
     */
    @ApiOperation(value = "상품 전체 카테고리 목록 조회")
    @ApiImplicitParam(name = "parentCode", value = "부모 카테고리 코드")
    @GetMapping("/category/depth/list")
    public ResponseData searchCategoryDepthList(@RequestParam("parentCode") String parentCode) {
        try {

            return categoryService.searchCategoryDepthList(parentCode);

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
     * 상품 목록 조회
     * @param requestSearchProductVO
     *          orderByDate : 최신순(등록순) 플래그
     *          popularFlg  : 인기상품 플래그
     *          inquFlg     : 문의순 플래그
     *          pdfInfoCon  : 상품 검색 조건
     * @return
     */
    @ApiOperation(value = "상품 목록 조회")
    @GetMapping("/list")
    public ResponseData searchProductList(RequestSearchProductVO requestSearchProductVO) {

        try {

            // 상품 공통 파일 정보 구분 코드 ID 셋팅 - 상품 이미지
            requestSearchProductVO.setFilePtrnId(ComCode.GDS05001.getCode());
            // 상품 조회 이력 유형 ID 셋팅 - 상품 상세 조회
            requestSearchProductVO.setPrhsPtrnId(ComCode.HTS00001.getCode());

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
            log.error(ex.getMessage());
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(ex.getMessage())
                    .build();
        }
    }

    /**
     * 묶음 상품 목록 조회
     * @param requestSearchProductVO
     * @return
     */
    @ApiOperation(value = "상품 목록 조회")
    @GetMapping("/bundle/list")
    public ResponseData searchBundleProductList(RequestSearchProductVO requestSearchProductVO) {

        try {
            // 상품 묶음상품 리스트 페이징 셋팅
            requestSearchProductVO.setItemPage(new PageVO(1, 2));

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
            log.error(ex.getMessage());
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(ex.getMessage())
                    .build();
        }
    }

    /**
     * 공통코드 그룹 목록 조회
     * @return
     */
    @ApiOperation(value = "공통코드 그룹 목록 조회")
    @ApiImplicitParam(name = "grpCdTag", value = "그룹 코드 태그")
    @GetMapping("/comcode/group")
    public ResponseData searchComCodeGroupList(@RequestParam(name = "grpCdTag") String grpCdTag){
        return commonService.searchComCodeGroupList(grpCdTag);
    }

    /**
     * 공통코드 그룹 상세 목록 조회
     * @return
     */
    @ApiOperation(value = "공통코드 그룹 상세 목록 조회")
    @ApiImplicitParam(name = "grpCdId", value = "그룹 코드 ID")
    @GetMapping("/comcode/group/list")
    public ResponseData searchComCodeList(@RequestParam(name = "grpCdId") String grpCdId){
        return commonService.searchComCodeList(grpCdId);
    }

}