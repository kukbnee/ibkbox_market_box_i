package com.ibk.sb.restapi.biz.api.admin.member;

import com.ibk.sb.restapi.app.common.util.BizException;
import com.ibk.sb.restapi.app.common.vo.PagingVO;
import com.ibk.sb.restapi.app.common.vo.ResponseData;
import com.ibk.sb.restapi.biz.service.admin.AdminSellerService;
import com.ibk.sb.restapi.biz.service.admin.vo.AdminSellerTypeVO;
import com.ibk.sb.restapi.biz.service.admin.vo.AdminSellerVO;
import com.ibk.sb.restapi.biz.service.admin.vo.RequestSellerSearchVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"운영자 포탈 - 판매자 관리 API"})
@RestController
@Slf4j
@RequestMapping(path = {"/api/admin/seller", "/api/mk/v1/admin/seller"}, produces={"application/json"})
@RequiredArgsConstructor
public class AdminSellerController {

    private final AdminSellerService service;

    @ApiOperation(value = "(관리자) 판매자 유형 목록 조회")
    @GetMapping("/type/list")
    public ResponseData memberTypeList() {

        try {
            List<AdminSellerTypeVO> list = service.searchSellerTypeList();
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

    @ApiOperation(value = "(관리자) 판매사 목록 조회")
    @GetMapping("/list")
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

    @ApiOperation(value = "(관리자) 판매사 판매 자격박탈")
    @ApiImplicitParam(name = "params", value = "AdminSellerVO")
    @PostMapping("/roleoff")
    public ResponseData roleoff(@RequestBody AdminSellerVO params) {

        try {
            boolean result = service.updateRoleOff(params);
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

    @ApiOperation(value = "(관리자) 판매사 판매 자격박탈상태 해제")
    @ApiImplicitParam(name = "params", value = "AdminSellerVO")
    @PostMapping("/cancel/roleoff")
    public ResponseData roleoffCancel(@RequestBody AdminSellerVO params) {

        try {
            boolean result = service.updateRoleOffCancel(params);
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
