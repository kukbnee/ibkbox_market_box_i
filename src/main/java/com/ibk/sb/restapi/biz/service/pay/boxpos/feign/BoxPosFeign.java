package com.ibk.sb.restapi.biz.service.pay.boxpos.feign;

import com.ibk.sb.restapi.app.config.BoxPosFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "box-pos-app", url = "${feign.box-open-api.url}", configuration = BoxPosFeignConfig.class)
public interface BoxPosFeign {

    /**
     * POS 이용신청정보조회 NoAuth
     * @param body
     * @return
     */
    @PostMapping("api/cm/v1/cps003/posUtlAplcInfoInqNoAuth")
    Map<String, Object> posUtlAplcInfoInqNoAuth(@RequestBody Map<String, String> body);

    /**
     * BOX POS 제휴사 연계결제 등록
     * @param body
     * @return
     */
    @PostMapping("api/cm/v1/cps026/lnkStlmRgsn")
    Map<String, Object> lnkStlmRgsn(@RequestBody Map<String, Object> body);

    /**
     * BOX POS 제휴사 연계결제 결제 여부 조회
     * @param body
     * @return
     */
    @PostMapping("api/cm/v1/cps026/lnkStlmPgrsInq")
    Map<String, Object> lnkStlmPgrsInq(@RequestBody Map<String, Object> body);

    /**
     * BOX POS PC원격결제 조회
     * @param body
     * @return
     */
    @PostMapping("api/cm/v1/cps025/pcRmteStlmInq")
    Map<String, Object> pcRmteStlmInq(@RequestBody Map<String, Object> body);

    /**
     * BOX POS PC원격결제 취소요청 푸시발송
     * @param body
     * @return
     */
    @PostMapping("api/cm/v1/cps025/pcRmteStlmCnclRqstPush")
    Map<String, Object> pcRmteStlmCnclRqstPush(Map<String, Object> body);

    /**
     * BOX POS 제휴사 연계결제 취소
     * @param body
     * @return
     */
    @PostMapping("api/cm/v1/cps026/lnkStlmCncl")
    Map<String, Object> lnkStlmCncl(Map<String, Object> body);
}
