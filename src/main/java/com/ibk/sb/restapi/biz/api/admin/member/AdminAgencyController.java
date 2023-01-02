package com.ibk.sb.restapi.biz.api.admin.member;

import com.ibk.sb.restapi.app.common.util.BizException;
import com.ibk.sb.restapi.app.common.vo.PagingVO;
import com.ibk.sb.restapi.app.common.vo.ResponseData;
import com.ibk.sb.restapi.biz.service.admin.AdminAgencyService;
import com.ibk.sb.restapi.biz.service.admin.vo.AdminAgencyVO;
import com.ibk.sb.restapi.biz.service.admin.vo.RequestAgencySearchVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"운영자 포탈 - 에이전시 등록 요청 관리 API"})
@RestController
@Slf4j
@RequestMapping(path = {"/api/admin/agency", "/api/mk/v1/admin/agency"}, produces={"application/json"})
@RequiredArgsConstructor
public class AdminAgencyController {

    private final AdminAgencyService service;

    @ApiOperation(value = "(관리자) 에이전시 등록요청 목록 조회")
    @GetMapping("/list")
    public ResponseData list(RequestAgencySearchVO searchParams) {

        try {
            PagingVO<AdminAgencyVO> list = service.searchAgencyAuthRequestList(searchParams);
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

    @ApiOperation(value = "(관리자) 에이전시 등록요청 승인")
    @ApiImplicitParam(name = "params", value = "AdminAgencyVO")
    @PostMapping("/approve")
    public ResponseData approve(@RequestBody AdminAgencyVO params) {

        try {
            boolean result = service.requestApprove(params);
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

    @ApiOperation(value = "(관리자) 에이전시 등록요청 반려")
    @ApiImplicitParam(name = "params", value = "AdminAgencyVO")
    @PostMapping("/reject")
    public ResponseData reject(@RequestBody AdminAgencyVO params) {

        try {
            boolean result = service.requestReject(params);
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

    @ApiOperation(value = "(관리자) 에이전시 등록요청 승인 권한해제")
    @ApiImplicitParam(name = "params", value = "AdminAgencyVO")
    @PostMapping("/roleoff")
    public ResponseData approvalCancel(@RequestBody AdminAgencyVO params) {

        try {
            boolean result = service.requestApprovalCancel(params);
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

    @ApiOperation(value = "(관리자) 에이전시 등록요청 반려취소")
    @ApiImplicitParam(name = "params", value = "AdminAgencyVO")
    @PostMapping("/cancel/reject")
    public ResponseData rejectCancel(@RequestBody AdminAgencyVO params) {

        try {
            boolean result = service.requestRejectCancel(params);
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

    @ApiOperation(value = "(관리자) 에이전시 등록요청 권한해제상태 해제취소")
    @ApiImplicitParam(name = "params", value = "AdminAgencyVO")
    @PostMapping("/cancel/roleoff")
    public ResponseData reverseCancel(@RequestBody AdminAgencyVO params) {

        try {
            boolean result = service.requestReverseCancel(params);
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
