package com.ibk.sb.restapi.biz.api.admin.event;

import com.ibk.sb.restapi.app.common.util.BizException;
import com.ibk.sb.restapi.app.common.vo.PagingVO;
import com.ibk.sb.restapi.app.common.vo.ResponseData;
import com.ibk.sb.restapi.biz.service.admin.AdminEventService;
import com.ibk.sb.restapi.biz.service.admin.vo.AdminEventProductVO;
import com.ibk.sb.restapi.biz.service.admin.vo.AdminEventVO;
import com.ibk.sb.restapi.biz.service.admin.vo.RequestEventSearchProductVO;
import com.ibk.sb.restapi.biz.service.event.vo.EventProductVO;
import com.ibk.sb.restapi.biz.service.event.vo.RequestSearchEventVO;
import com.ibk.sb.restapi.biz.service.event.vo.SummaryEventVO;
import com.ibk.sb.restapi.biz.service.product.single.SingleProductService;
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

@Api(tags = {"운영자 포탈 - 이벤트 관리 / 이벤트 관리 API"})
@RestController
@Slf4j
@RequestMapping(path = {"/api/admin/event", "/api/mk/v1/admin/event"}, produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
public class AdminEventController {

    private final AdminEventService service;
    private final SingleProductService productService;

    @ApiOperation(value = "(관리자) 이벤트 목록 조회")
    @GetMapping("/list")
    public ResponseData list(RequestSearchEventVO searchParams) {
        try {
            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(service.searchEventList(searchParams))
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

    @ApiOperation(value = "(관리자) 이벤트 상세보기")
    @ApiImplicitParam(name = "evntInfId", value = "이벤트 ID", dataType = "String")
    @GetMapping("/detail")
    public ResponseData sellerDetail(@RequestParam("evntInfId") String evntInfId) {

        try {
            SummaryEventVO detail = service.searchEventDetail(evntInfId);
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

    @ApiOperation(value = "(관리자) 이벤트 등록")
    @ApiImplicitParam(name = "params", value = "AdminEventVO")
    @PostMapping()
    public ResponseData saveEvent(@RequestBody AdminEventVO params) {

        try {
            boolean result = service.saveEventInfo(params);
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

    @ApiOperation(value = "(관리자) 이벤트 삭제")
    @ApiImplicitParam(name = "params", value = "AdminEventVO")
    @PostMapping("/del")
    public ResponseData deleteEvent(@RequestBody AdminEventVO params) {

        try {
            boolean result = service.deleteEventInfo(params);
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

    @ApiOperation(value = "(관리자) 이벤트 신청상품 승인완료 상품 목록 조회")
    @GetMapping("/apply/list")
    public ResponseData eventProductList(RequestEventSearchProductVO searchParams) {

        try {
            PagingVO<EventProductVO> list = service.searchEventProductList(searchParams);
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

    @ApiOperation(value = "(관리자) 이벤트 신청 상품 목록 조회")
    @GetMapping("/product/parti/list")
    public ResponseData eventRequestProductList(RequestEventSearchProductVO searchParams) {

        try {
            PagingVO<EventProductVO> list = service.searchEventRequestProductList(searchParams);
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

    @ApiOperation(value = "(관리자) 이벤트 상품추가 상품 검색")
    @GetMapping("/product/list")
    public ResponseData eventEveryProductList(RequestEventSearchProductVO searchParams) {

        try {
            PagingVO<EventProductVO> list = service.searchEventEveryProductList(searchParams);
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

    @ApiOperation(value = "(관리자) 이벤트 상품추가 상품 승인")
    @PostMapping("/product/apply")
    public ResponseData addEventProduct(@RequestBody List<AdminEventProductVO> list) {

        try {
            boolean result = service.addEventProduct(list);
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


    @ApiOperation(value = "(관리자) 이벤트 신청 상품 승인")
    @PostMapping("/product/parti/apply")
    public ResponseData approveEventProduct(@RequestBody List<AdminEventProductVO> list) {

        try {
            boolean result = service.approveEventProduct(list);
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

    @ApiOperation(value = "(관리자) 이벤트 상품추가 상품 취소")
    @PostMapping("/product/apply/cancel")
    public ResponseData cancelAddEventProduct(@RequestBody List<AdminEventProductVO> list) {

        try {
            boolean result = service.cancelAddEventProduct(list);
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

    @ApiOperation(value = "(관리자) 이벤트 목록 순번 저장")
    @PostMapping("/update/sort")
    public ResponseData saveEventProductSort(@RequestBody List<AdminEventProductVO> list) {

        try {
            boolean result = service.saveEventProductSort(list);
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
