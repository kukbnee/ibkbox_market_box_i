package com.ibk.sb.restapi.biz.service.mainbox.feign;

import com.ibk.sb.restapi.app.common.vo.BoxListResponseVO;
import com.ibk.sb.restapi.app.config.MainBoxFeignConfig;
import com.ibk.sb.restapi.app.config.MarketBoxFeignConfig;
import com.ibk.sb.restapi.biz.service.mainbox.vo.AlarmTargetResponseVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "box-open-api-mnb-mktkey", url = "${feign.box-open-api.url}", configuration = MarketBoxFeignConfig.class)
public interface MainBoxByMktKeyFeign {

    /**
     * 메인BOX 알림 대상 리스트 조회
     * @param body
     * @return
     */
    @PostMapping("/api/mb/v1/notice/user/info")
    BoxListResponseVO<AlarmTargetResponseVO> getMainBoxAlarmTargetList(@RequestBody Map<String, String> body);
}
