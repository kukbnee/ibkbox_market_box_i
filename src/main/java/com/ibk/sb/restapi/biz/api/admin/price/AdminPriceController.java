package com.ibk.sb.restapi.biz.api.admin.price;

import com.ibk.sb.restapi.app.common.util.BizException;
import com.ibk.sb.restapi.app.common.vo.PagingVO;
import com.ibk.sb.restapi.app.common.vo.ResponseData;
import com.ibk.sb.restapi.biz.service.admin.AdminPriceService;
import com.ibk.sb.restapi.biz.service.admin.vo.AdminPriceVO;
import com.ibk.sb.restapi.biz.service.admin.vo.RequestPriceSearchVO;
import com.ibk.sb.restapi.biz.service.event.vo.SummaryEventVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"운영자 포탈 - 판매 관리"})
@RestController
@Slf4j
@RequestMapping(path = {"/api/admin/price", "/api/mk/v1/admin/price"}, produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
public class AdminPriceController {

    private final AdminPriceService service;

    @ApiOperation(value = "판매사별 총 판매 금액")
    @GetMapping("/selr/list")
    public ResponseData selrList(RequestPriceSearchVO requestPriceSearchVO) {

        try {
            PagingVO<AdminPriceVO> list = service.searchSelrList(requestPriceSearchVO);
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

    @ApiOperation(value = "에이전시 총 판매 금액")
    @GetMapping("/agency/list")
    public ResponseData agencyList(RequestPriceSearchVO requestPriceSearchVO) {

        try {
            PagingVO<AdminPriceVO> list = service.searchAgencyList(requestPriceSearchVO);
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

    @ApiOperation(value = "이벤트별 총 판매 금액")
    @GetMapping("/event/list")
    public ResponseData eventList(RequestPriceSearchVO requestPriceSearchVO) {

        try {
            PagingVO<SummaryEventVO> list = service.searchEventList(requestPriceSearchVO);
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
}
