package com.ibk.sb.restapi.biz.api.admin.product;

import com.ibk.sb.restapi.app.common.util.BizException;
import com.ibk.sb.restapi.app.common.vo.PagingVO;
import com.ibk.sb.restapi.app.common.vo.ResponseData;
import com.ibk.sb.restapi.biz.service.admin.AdminSellerService;
import com.ibk.sb.restapi.biz.service.admin.vo.AdminProductRequestVO;
import com.ibk.sb.restapi.biz.service.admin.vo.AdminSellerVO;
import com.ibk.sb.restapi.biz.service.admin.vo.RequestSellerSearchVO;
import com.ibk.sb.restapi.biz.service.product.common.vo.RequestSearchProductVO;
import com.ibk.sb.restapi.biz.service.product.single.SingleProductService;
import com.ibk.sb.restapi.biz.service.product.single.vo.SummarySingleProductVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"운영자 포탈 - 상품 관리 / 판매자 상품 관리 API"})
@RestController
@Slf4j
@RequestMapping(path = {"/api/admin/product", "/api/mk/v1/admin/product"}, produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
public class AdminProductController {

    private final AdminSellerService service;
    private final SingleProductService productService;

    @ApiOperation(value = "(관리자) 판매사 상품 목록 조회")
    @GetMapping("/seller/list")
    public ResponseData list(RequestSellerSearchVO searchParams) {

        try {
            PagingVO<AdminSellerVO> list = service.searchSellerList(searchParams);
            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(list)
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

    @ApiOperation(value = "(관리자) 판매사 상품 상세 조회")
    @ApiImplicitParam(name = "selrUsisId", value = "이용기관 ID", dataType = "String")
    @GetMapping("/seller/detail")
    public ResponseData sellerDetail(@RequestParam("selrUsisId") String selrUsisId) {

        try {
            AdminSellerVO detail = service.searchSellerDeatil(selrUsisId);
            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(detail)
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
     * 판매자 상품 관리 / 등록 상품 목록 조회
     * @param params
     * @return
     */
    @ApiOperation(value = "(관리자) 판매사 등록 상품 목록 조회")
    @GetMapping("/seller/reg/list")
    public ResponseData myProductList(RequestSearchProductVO params) {

        try {
            params.setAdminSearchStatus(true);
            PagingVO<SummarySingleProductVO> list = productService.searchSingleProductList(params);
            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(list)
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
     * 판매자 상품 관리 / 에이전시 상품 목록 조회
     * @param params
     * @return
     */
    @ApiOperation(value = "(관리자) 에이전시 상품 목록 조회")
    @GetMapping("/agency/list")
    public ResponseData myAgencyProductList(RequestSearchProductVO params) {

        try {
            params.setAdminSearchStatus(true);
            params.setAgencyFlg("Y");
            PagingVO<SummarySingleProductVO> list = productService.searchSingleProductList(params);
            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(list)
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

    @ApiOperation(value = "(관리자) 판매사 등록 상품 판매중지")
    @ApiImplicitParam(name = "params", value = "AdminProductRequestVO")
    @PostMapping("/seller/reg/cancel")
    public ResponseData sellerRegCancel(@RequestBody AdminProductRequestVO params) {

        try {
            boolean result = service.updateSellerRegProductCancel(params);
            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(result)
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

    @ApiOperation(value = "(관리자) 판매사 등록 상품 판매중지상태 취소")
    @ApiImplicitParam(name = "params", value = "AdminProductRequestVO")
    @PostMapping("/seller/reg/recovery")
    public ResponseData sellerRegRecovery(@RequestBody AdminProductRequestVO params) {

        try {
            boolean result = service.updateSellerRegProductRecovery(params);
            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(result)
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

    @ApiOperation(value = "(관리자) 에이전시 상품 판매중지")
    @ApiImplicitParam(name = "params", value = "AdminProductRequestVO")
    @PostMapping("/agency/cancel")
    public ResponseData agencyCancel(@RequestBody AdminProductRequestVO params) {

        try {
            boolean result = service.updateAgencyProductCancel(params);
            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(result)
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

    @ApiOperation(value = "(관리자) 에이전시 상품 판매중지상태 취소")
    @ApiImplicitParam(name = "params", value = "AdminProductRequestVO")
    @PostMapping("/agency/recovery")
    public ResponseData agencyRecovery(@RequestBody AdminProductRequestVO params) {

        try {
            boolean result = service.updateAgencyProductRecovery(params);
            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(result)
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

}
