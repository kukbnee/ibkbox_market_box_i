package com.ibk.sb.restapi.biz.api.faq;

import com.ibk.sb.restapi.app.common.util.BizException;
import com.ibk.sb.restapi.app.common.vo.ResponseData;
import com.ibk.sb.restapi.biz.service.faq.FaqService;
import com.ibk.sb.restapi.biz.service.faq.vo.RequestSearchFaqVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping(path={"/api/faq", "/api/mk/v1/faq"}, produces = {"application/json"})
@RequiredArgsConstructor
public class FaqController {
    private final FaqService faqService;

    /**
     * FAQ 목록 조회
     *@return
     */
    @GetMapping("/list")
    public ResponseData searchFaqList(RequestSearchFaqVO requestSearchFaqVO) {

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
}