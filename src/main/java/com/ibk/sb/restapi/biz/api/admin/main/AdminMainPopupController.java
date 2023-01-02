package com.ibk.sb.restapi.biz.api.admin.main;

import com.ibk.sb.restapi.app.common.util.BizException;
import com.ibk.sb.restapi.app.common.vo.PagingVO;
import com.ibk.sb.restapi.app.common.vo.ResponseData;
import com.ibk.sb.restapi.biz.service.popup.PopupService;
import com.ibk.sb.restapi.biz.service.popup.vo.PopupVO;
import com.ibk.sb.restapi.biz.service.popup.vo.RequestPopupSearchVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"운영자 포탈 - 메인 관리 / 팝업 관리 API"})
@RestController
@Slf4j
@RequestMapping(path = {"/api/admin/popup", "/api/mk/v1/admin/popup"}, produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
public class AdminMainPopupController {

    private final PopupService service;


    @ApiOperation(value = "(관리자) 팝업 목록 조회")
    @GetMapping("/main/list")
    public ResponseData mainPopupList(RequestPopupSearchVO searchParams) {

        try {
            PagingVO<PopupVO> list = service.searchPopupPaging(searchParams);
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

    @ApiOperation(value = "(관리자) 팝업 상세 조회")
    @ApiImplicitParam(name = "popupInfId", value = "팝업 ID", dataType = "String")
    @GetMapping("/main/detail")
    public ResponseData mainDetail(@RequestParam("popupInfId") String popupInfId) {

        try {
            PopupVO detail = service.searchPopupInfo(popupInfId);
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

    @ApiOperation(value = "(관리자) 팝업 등록")
    @ApiImplicitParam(name = "params", value = "PopupVO")
    @PostMapping("/main/save")
    public ResponseData mainSave(@RequestBody PopupVO params) {

        try {
            boolean result = service.savePopup(params);
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

    @ApiOperation(value = "(관리자) 팝업 삭제")
    @ApiImplicitParam(name = "list", value = "List<String> ids")
    @PostMapping("/main/delete")
    public ResponseData mainDelete(@RequestBody List<String> list) {

        try {
            boolean result = service.deletePopup(list);
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
