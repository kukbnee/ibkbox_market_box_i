package com.ibk.sb.restapi.biz.api.event;

import com.ibk.sb.restapi.app.common.util.BizException;
import com.ibk.sb.restapi.app.common.vo.ResponseData;
import com.ibk.sb.restapi.biz.service.banner.BannerService;
import com.ibk.sb.restapi.biz.service.banner.vo.BannerVO;
import com.ibk.sb.restapi.biz.service.event.EventService;
import com.ibk.sb.restapi.biz.service.event.vo.RequestSearchEventVO;
import com.ibk.sb.restapi.biz.service.event.vo.SummaryEventProductSelrVO;
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

@Api(tags = {"커머스 박스 - 이벤트 API"})
@RestController
@Slf4j
@RequestMapping(path={"/api/event", "/api/mk/v1/event"}, produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    private final BannerService bannerService;

    /**
     * 이벤트배너 목록 조회
     * @param bantypeId
     * @return
     */
    @ApiOperation(value = "이벤트배너 목록 조회")
    @ApiImplicitParam(name = "bantypeId", value = "배너 타입 ID", dataType = "String")
    @GetMapping("/banner/list")
    public ResponseData SearchEventBanner(@RequestParam("bantypeId") String bantypeId) {

        try {
            List<BannerVO> summaryBannerVOList = bannerService.searchBannerList(bantypeId);

            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(summaryBannerVOList)
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
     * 이벤트 목록 조회
     * @return
     */
    @ApiOperation(value = "이벤트 목록 조회")
    @GetMapping("/list")
    public ResponseData searchEventList(RequestSearchEventVO requestSearchEventVO) {
        try {

            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(eventService.searchEventPageList(requestSearchEventVO))
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
     * 이벤트 진행중 상품 목록 조회
     * 이벤트 상세 조회
     * @return
     */
    @ApiOperation(value = "이벤트 진행중 상품 목록 조회 - 이벤트 상세 조회")
    @GetMapping("/detail")
    public ResponseData searchEventDetail(RequestSearchEventVO requestSearchEventVO) {
        try {

            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(eventService.searchEventDetail(requestSearchEventVO))
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
     * 이벤트 판매자참여 참여가능여부 조회
     * 이벤트 참여가능 여부
     * @return
     */
    @ApiOperation(value = "이벤트 판매자참여 참여가능여부 조회 - 이벤트 참여가능 여부")
    @GetMapping("/check/partici")
    public ResponseData checkEventPartici(RequestSearchEventVO requestSearchEventVO) {
        try {

            return eventService.checkEventPartici(requestSearchEventVO);

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
     * 이벤트 판매자 상품 검색
     * @param
     * @return
     */
    @ApiOperation(value = "이벤트 판매자 상품 검색")
    @GetMapping("/product")
    public ResponseData searchEventProduct(RequestSearchEventVO requestSearchEventVO) {
        try {

            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(eventService.searchEventProduct(requestSearchEventVO))
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
     * 이벤트 상태별 Total 조회
     * @return
     */
    @ApiOperation(value = "이벤트 상태별 Total 조회")
    @GetMapping("/state/total")
    public ResponseData searchEventStateTotal(){
        try {
            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(eventService.searchEventStateTotal())
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
     * 이벤트 판매자 참여 등록
     * @param
     * @return
     */
    @ApiOperation(value = "이벤트 판매자 상품 검색")
    @ApiImplicitParam(name = "summaryEventProductSelrVO", value = "SummaryEventProductSelrVO")
    @PostMapping("/selr/save")
    public ResponseData selrSaveEvent(@RequestBody SummaryEventProductSelrVO summaryEventProductSelrVO) {
        try {

            return eventService.selrSaveEvent(summaryEventProductSelrVO);

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
     * 이벤트 판매자참여 취소
     * @param
     * @return
     */
    @ApiOperation(value = "이벤트 판매자참여 취소")
    @ApiImplicitParam(name = "summaryEventProductSelrVO", value = "SummaryEventProductSelrVO")
    @PostMapping("/selr/cancel")
    public ResponseData selrCancelEvent(@RequestBody SummaryEventProductSelrVO summaryEventProductSelrVO) {
        try {

            return eventService.selrCancelEvent(summaryEventProductSelrVO);

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
