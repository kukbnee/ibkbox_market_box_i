package com.ibk.sb.restapi.biz.api.admin.inquiry;

import com.ibk.sb.restapi.app.common.util.BizException;
import com.ibk.sb.restapi.app.common.vo.ResponseData;
import com.ibk.sb.restapi.biz.service.adminqna.AdminQnaService;
import com.ibk.sb.restapi.biz.service.adminqna.vo.AdminQnaAnswerVO;
import com.ibk.sb.restapi.biz.service.adminqna.vo.AdminQnaVO;
import com.ibk.sb.restapi.biz.service.adminqna.vo.DetailAdminQnaVO;
import com.ibk.sb.restapi.biz.service.adminqna.vo.RequestSearchAdminQnaVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"운영자 포탈 - 고객지원 관리 / 문의관리 API"})
@RestController
@Slf4j
@RequestMapping(path = {"/api/admin/inquiry", "/api/mk/v1/admin/inquiry"}, produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
public class AdminInquiryController {

    private final AdminQnaService adminQnaService;

    /**
     * 운영자포털 > 커머스BOX관리 > 고객지원관리 > 문의관리 > 목록
     * @param requestSearchAdminQnaVO
     * @return
     */
    @ApiOperation(value = "(관리자) 문의 목록 조회")
    @GetMapping("/list")
    public ResponseData inquiryList(RequestSearchAdminQnaVO requestSearchAdminQnaVO) {
        try{
            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(adminQnaService.searchAdminInquiryList(requestSearchAdminQnaVO))
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
     * 운영자포털 > 커머스BOX관리 > 고객지원관리 > 문의관리 > 목록 > 상세
     * @param requestSearchAdminQnaVO
     * @return
     */
    @ApiOperation(value = "(관리자) 문의 상세 조회")
    @GetMapping("/detail")
    public ResponseData inquiryDetail(RequestSearchAdminQnaVO requestSearchAdminQnaVO) {
        try{
            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(adminQnaService.searchAdminInquiryDetail(requestSearchAdminQnaVO))
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
     * 운영자포털 > 커머스BOX관리 > 고객지원관리 > 문의관리 > 목록 > 상세 > 문의 > 삭제
     * @param adminQnaVO
     * @return
     */
    @ApiOperation(value = "(관리자) 문의 삭제")
    @ApiImplicitParam(name = "adminQnaVO", value = "AdminQnaVO")
    @PostMapping("/detail/del")
    public ResponseData inquiryDel(@RequestBody AdminQnaVO adminQnaVO) {
        try{
            adminQnaService.inquiryDel(adminQnaVO);

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
     * 운영자포털 > 커머스BOX관리 > 고객지원관리 > 문의관리 > 목록 > 상세 > 답변 > 등록
     * @param adminQnaAnswerVO
     * @return
     */
    @ApiOperation(value = "(관리자) 문의 답변 등록")
    @ApiImplicitParam(name = "adminQnaAnswerVO", value = "AdminQnaAnswerVO")
    @PostMapping("")
    public ResponseData inquirySave(@RequestBody AdminQnaAnswerVO adminQnaAnswerVO) {
        try{
            adminQnaService.inquirySave(adminQnaAnswerVO);

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
     * 운영자포털 > 커머스BOX관리 > 고객지원관리 > 문의관리 > 목록 > 상세 > 답변 > 수정
     * @param adminQnaAnswerVO
     * @return
     */
    @ApiOperation(value = "(관리자) 문의 답변 수정")
    @ApiImplicitParam(name = "adminQnaAnswerVO", value = "AdminQnaAnswerVO")
    @PostMapping("/update")
    public ResponseData inquiryUpdate(@RequestBody AdminQnaAnswerVO adminQnaAnswerVO) {
        try{
            adminQnaService.inquiryUpdate(adminQnaAnswerVO);

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
