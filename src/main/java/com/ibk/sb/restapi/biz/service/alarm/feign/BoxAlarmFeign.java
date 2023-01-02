package com.ibk.sb.restapi.biz.service.alarm.feign;

import com.ibk.sb.restapi.app.common.vo.BoxListResponseVO;
import com.ibk.sb.restapi.app.common.vo.BoxMsgResponseVO;
import com.ibk.sb.restapi.app.config.MainBoxFeignConfig;
import com.ibk.sb.restapi.biz.service.alarm.vo.ReceiveAlarmCountVO;
import com.ibk.sb.restapi.biz.service.alarm.vo.ReceiveAlarmVO;
import com.ibk.sb.restapi.biz.service.alarm.vo.RequestAlarmVO;
import com.ibk.sb.restapi.biz.service.alarm.vo.RequestCheckAlarmVO;
import com.ibk.sb.restapi.biz.service.alarm.vo.RequestAlarmResponseVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "box-open-api-alarm", url = "${feign.box-open-api.url}", configuration = MainBoxFeignConfig.class)
public interface BoxAlarmFeign {

    /**
     * BOX API 수신 알림 목록 조회
     * @param body
     * @return
     */
    @PostMapping("/api/cm/v1/cms051/alrtRcvCtlgInq")
    BoxListResponseVO<ReceiveAlarmVO> getInvestBoxReceiveAlarmList(@RequestBody Map<String, String> body);

    /**
     * BOX API 수신 알림 건수 조회
     * idmbId : 개인회원 ID | usisId : 이용기관 ID
     * @param body
     * @return
     */
    @PostMapping("/api/cm/v1/cms051/alrtRcvNbiInq")
    BoxListResponseVO<ReceiveAlarmCountVO> getAlarmCount(@RequestBody Map<String, String> body);

    /**
     * BOX API 알림 메시지 발송
     * @param body
     * @return
     */
    @PostMapping("/api/cm/v1/cms053/alrtSndgReg")
    RequestAlarmResponseVO postSendAlarm(@RequestBody RequestAlarmVO body);

    /**
     * BOX API 알림 메시지 수신 확인 발송
     * @param body
     * @return
     */
    @PostMapping("/api/cm/v1/cms052/alrtRcvFnsgPscnUpd")
    BoxMsgResponseVO postCheckAlarm(@RequestBody RequestCheckAlarmVO body);
}
