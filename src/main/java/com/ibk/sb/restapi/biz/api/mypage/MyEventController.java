package com.ibk.sb.restapi.biz.api.mypage;

import com.ibk.sb.restapi.app.common.util.BizException;
import com.ibk.sb.restapi.app.common.vo.ResponseData;
import com.ibk.sb.restapi.biz.service.event.EventService;
import com.ibk.sb.restapi.biz.service.event.vo.mypage.RequestSearchEventMyVO;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping(path={"/api/my/event", "/api/mk/v1/my/event"}, produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
public class MyEventController {

    private final EventService eventService;

    /**
     * 마이페이지 이벤트 목록 조회
     * 마이페이지 > 이벤르 페이지 리스트 조회
     * tabCode : 탭 검색코드("" : 전체, ETS01001 : 접수, ETS01002 : 선정, ETS01003 : 미선정)
     * pgstId : 이벤트상태(ETS00001(진행중), ETS00002(준비중), ETS00003(마감)), ""(전체)
     * @return
     */
    @ApiOperation(value = "마이페이지 이벤트 목록 조회")
    @GetMapping("/list")
    public ResponseData SearchEventList(RequestSearchEventMyVO requestSearchEventMyVO) {
        try {

            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(eventService.searchEventParticiList(requestSearchEventMyVO))
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
     * 마이페이지 이벤트 상세 조회
     * 마이페이지 > 이벤르 페이지 신청현황
     * @return
     */
    @ApiOperation(value = "마이페이지 이벤트 상세 조회")
    @GetMapping("/detail")
    public ResponseData searchEventMyDetail(RequestSearchEventMyVO requestSearchEventMyVO) {
        try {

            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(eventService.searchEventMyDetail(requestSearchEventMyVO))
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
     * 마이페이지 이벤트 신청현황 상품 목록 조회
     * 마이페이지 > 이벤르 페이지 신청현황 상품목록
     * pickType : Y(선정),N(선정을 제외한 나머지)
     * @return
     */
    @ApiOperation(value = "마이페이지 이벤트 신청현황 상품 목록 조회")
    @GetMapping("/product")
    public ResponseData SearchEventMyProduct(RequestSearchEventMyVO requestSearchEventMyVO) {
        try {

            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(eventService.searchEventMyProduct(requestSearchEventMyVO))
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
