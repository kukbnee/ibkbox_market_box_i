package com.ibk.sb.restapi.biz.api.admin.order;

import com.ibk.sb.restapi.app.common.util.BizException;
import com.ibk.sb.restapi.app.common.vo.PagingVO;
import com.ibk.sb.restapi.app.common.vo.ResponseData;
import com.ibk.sb.restapi.biz.service.admin.AdminOrderService;
import com.ibk.sb.restapi.biz.service.admin.vo.AdminOrderVO;
import com.ibk.sb.restapi.biz.service.admin.vo.RequestOrderSearchVO;
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

@Api(tags = {"운영자 포탈 - 주문 관리"})
@RestController
@Slf4j
@RequestMapping(path = {"/api/admin/order", "/api/mk/v1/admin/order"}, produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
public class AdminOrderController {

    private final AdminOrderService service;

    @ApiOperation(value = "주문 목록")
    @GetMapping("/list")
    public ResponseData list(RequestOrderSearchVO requestOrderSearchVO) {

        try {
            PagingVO<AdminOrderVO> list = service.searchOrderList(requestOrderSearchVO);
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

    @ApiOperation(value = "견적 보기")
    @ApiImplicitParam(name = "esttInfoId", value = "견적 ID", dataType = "String")
    @GetMapping("/estim/detail")
    public ResponseData estimDetail(@RequestParam("esttInfoId") String esttInfoId) {

        try {
            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(service.searchEstimation(esttInfoId))
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
