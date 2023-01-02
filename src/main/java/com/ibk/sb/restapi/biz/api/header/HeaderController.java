package com.ibk.sb.restapi.biz.api.header;

import com.ibk.sb.restapi.app.common.constant.ComCode;
import com.ibk.sb.restapi.app.common.util.BizException;
import com.ibk.sb.restapi.app.common.vo.PagingVO;
import com.ibk.sb.restapi.app.common.vo.ResponseData;
import com.ibk.sb.restapi.biz.service.alarm.AlarmService;
import com.ibk.sb.restapi.biz.service.alarm.vo.HeaderAlarmListVO;
import com.ibk.sb.restapi.biz.service.product.bundle.BundleProductService;
import com.ibk.sb.restapi.biz.service.product.single.SingleProductService;
import com.ibk.sb.restapi.biz.service.product.single.vo.SummarySingleProductVO;
import com.ibk.sb.restapi.biz.service.product.common.vo.RequestSearchProductVO;
import com.ibk.sb.restapi.biz.service.user.UserService;
import com.ibk.sb.restapi.biz.service.user.vo.UserHeaderVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Api(tags = {"커머스 박스 - 헤더 API"})
@RestController
@Slf4j
@RequestMapping(path = {"/api/header", "/api/mk/v1/header"}, produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
public class HeaderController {

    private final UserService userService;

    private final SingleProductService singleProductService;

    private final AlarmService alarmService;

    /**
     * 헤더 기본 개인 정보 조회
     *
     * @return
     */
    @ApiOperation(value = "헤더 기본 개인 정보 조회")
    @GetMapping("/info")
    public ResponseData searchUserHeaderInfo() {
        try {

            UserHeaderVO userHeaderVO = userService.searchUserHeaderInfo();

            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(userHeaderVO)
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
     * 헤더 전체 상품 목록 조회
     * @return
     */
    @ApiOperation(value = "헤더 전체 상품 목록 조회")
    @GetMapping("/single/list")
    public ResponseData searchHeaderSingleList(RequestSearchProductVO requestProductInfoSearchVO) {

        try {
            // 상품 공통 파일 정보 구분 코드 ID 셋팅 - 상품 이미지
            requestProductInfoSearchVO.setFilePtrnId(ComCode.GDS05001.getCode());
            // 상품 조회 이력 유형 ID 셋팅 - 상품 상세 조회
            requestProductInfoSearchVO.setPrhsPtrnId(ComCode.HTS00001.getCode());
            // 헤더 전체 상품 리스트 조회
            PagingVO<SummarySingleProductVO> productVOList = singleProductService.searchSingleProductList(requestProductInfoSearchVO);

            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(productVOList)
                    .build();

        } catch (BizException ex) {
            log.error(ex.getMessage());
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(ex.getMessage())
                    .build();
        } catch(Exception ex) {
            log.error(ex.getMessage());
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(ex.getMessage())
                    .build();
        }
    }

    /**
     * 헤더 알림 목록 조회
     * @return
     */
    @ApiOperation(value = "헤더 알림 목록 조회")
    @GetMapping("/alarm/list")
    public ResponseData searchReceiveMarketHeaderAlarmList() {
        try {
            HeaderAlarmListVO alarmList = alarmService.searchReceiveMarketHeaderAlarmList();

            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(alarmList)
                    .build();
        } catch (BizException bx) {
            log.error(bx.getMessage());
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(bx.getMessage())
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
     * 헤더 미확인 신규 알림 존재유무판단
     * @return
     */
    @ApiOperation(value = "헤더 미확인 신규 알림 존재유무판단")
    @GetMapping("/alarm/unread")
    public ResponseData searchUnreadReceiveMarketAlarm() {
        try {
            boolean isUnread = alarmService.searchUnreadReceiveMarketAlarm();
            Map<String, String> result = new HashMap<>();
            result.put("unreadYn", isUnread ? "Y" : "N");

            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(result)
                    .build();
        } catch (BizException bx) {
            log.error(bx.getMessage());
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(bx.getMessage())
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
     * 헤더 알림 수신 확인
     * @param body
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "헤더 알림 수신 확인")
    @ApiImplicitParam(name = "body", value = "HashMap<String, String>")
    @PostMapping("/alarm/check")
    public ResponseData checkReceiveAlarm(@RequestBody HashMap<String, String> body) throws Exception {

        try {
            boolean isUnread = alarmService.checkReceiveAlarm(body.get("alrtSndgNo"));
            Map<String, String> result = new HashMap<>();
            result.put("unreadYn", isUnread ? "Y" : "N");

            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(result)
                    .build();
        } catch (BizException bx) {
            log.error(bx.getMessage());
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(bx.getMessage())
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
