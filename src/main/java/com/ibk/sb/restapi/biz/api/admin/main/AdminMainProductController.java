package com.ibk.sb.restapi.biz.api.admin.main;

import com.ibk.sb.restapi.app.common.util.BizException;
import com.ibk.sb.restapi.app.common.vo.PagingVO;
import com.ibk.sb.restapi.app.common.vo.ResponseData;
import com.ibk.sb.restapi.biz.service.product.bundle.BundleProductService;
import com.ibk.sb.restapi.biz.service.product.bundle.vo.SummaryBundleInfoVO;
import com.ibk.sb.restapi.biz.service.product.common.vo.RequestSearchProductVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"운영자 포탈 - 메인 관리 / 묶음 상품 관리 API"})
@RestController
@Slf4j
@RequestMapping(path = {"/api/admin/product", "/api/mk/v1/admin/product"}, produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
public class AdminMainProductController {

    private final BundleProductService service;

    @ApiOperation(value = "(관리자) 메인 묶음 상품 목록 조회")
    @GetMapping("/bundle/list")
    public ResponseData searchBundleList(RequestSearchProductVO searchParams) {

        try {
            PagingVO<SummaryBundleInfoVO> list = service.searchBundleProductInfoList(searchParams);
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

    @ApiOperation(value = "(관리자) 묶음 상품 메인 설정")
    @ApiImplicitParam(name = "searchParams", value = "List<RequestSearchProductVO> searchParams")
    @PostMapping("/bundle/main/used")
    public ResponseData updateBundleMainUsed(@RequestBody List<RequestSearchProductVO> searchParams) {
        try {

            service.updateBundleMainUsed(searchParams);

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
}
