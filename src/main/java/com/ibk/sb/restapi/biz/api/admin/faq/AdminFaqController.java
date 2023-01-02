package com.ibk.sb.restapi.biz.api.admin.faq;

import com.ibk.sb.restapi.app.common.util.BizException;
import com.ibk.sb.restapi.app.common.vo.ResponseData;
import com.ibk.sb.restapi.biz.service.faq.FaqService;
import com.ibk.sb.restapi.biz.service.faq.vo.FaqVO;
import com.ibk.sb.restapi.biz.service.faq.vo.RequestSearchFaqVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"운영자 포탈 - 고객지원 관리 / FAQ관리 API"})
@RestController
@Slf4j
@RequestMapping(path = {"/api/admin/faq", "/api/mk/v1/admin/faq"}, produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
public class AdminFaqController {

    private final FaqService faqService;

    /**
     * 운영자포털 > 커머스BOX관리 > 고객지원관리 > FAQ관리 > 목록
     * @param requestSearchFaqVO
     * @return
     */
    @ApiOperation(value = "(관리자) FAQ 목록 조회")
    @GetMapping("/list")
    public ResponseData faqList(RequestSearchFaqVO requestSearchFaqVO) {

        try {
            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(faqService.searchFaqList(requestSearchFaqVO))
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
     * 운영자포털 > 커머스BOX관리 > 고객지원관리 > FAQ관리 > 목록 > 등록
     * @param faqVO
     * @return
     */
    @ApiOperation(value = "(관리자) FAQ 등록")
    @ApiImplicitParam(name = "faqVO", value = "FaqVO")
    @PostMapping("")
    public ResponseData faqSave(@RequestBody FaqVO faqVO) {
        try{
            return faqService.faqSave(faqVO);
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
     * 운영자포털 > 커머스BOX관리 > 고객지원관리 > FAQ관리 > 목록 > 상세
     * @param requestSearchFaqVO
     * @return
     */
    @ApiOperation(value = "(관리자) FAQ 상세 조회")
    @GetMapping("/detail")
    public ResponseData faqDetail(RequestSearchFaqVO requestSearchFaqVO) {
        try{
            return faqService.faqDetail(requestSearchFaqVO);
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
     * 운영자포털 > 커머스BOX관리 > 고객지원관리 > FAQ관리 > 목록 > 수정
     * @param faqVO
     * @return
     */
    @ApiOperation(value = "(관리자) FAQ 수정")
    @ApiImplicitParam(name = "faqVO", value = "FaqVO")
    @PostMapping("/update")
    public ResponseData faqUpdate(@RequestBody FaqVO faqVO) {
        try{
            return faqService.faqUpdate(faqVO);
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
     * 운영자포털 > 커머스BOX관리 > 고객지원관리 > FAQ관리 > 목록 > 삭제
     * @param faqVO
     * @return
     */
    @ApiOperation(value = "(관리자) FAQ 삭제")
    @ApiImplicitParam(name = "faqVO", value = "FaqVO")
    @PostMapping("/del")
    public ResponseData faqDelete(@RequestBody FaqVO faqVO) {
        try{
            return faqService.faqDelete(faqVO);

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
