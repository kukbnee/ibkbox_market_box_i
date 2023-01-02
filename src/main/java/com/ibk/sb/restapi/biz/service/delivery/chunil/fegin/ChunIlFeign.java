package com.ibk.sb.restapi.biz.service.delivery.chunil.fegin;

import com.ibk.sb.restapi.app.config.MarketBoxFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "chun-il-api", url = "${feign.box-open-api.url}", configuration = MarketBoxFeignConfig.class)
public interface ChunIlFeign {

    /**
     * 품목 / 포장 단위 리스트 조회
     * @param jsQuery
     * @return
     */
    @PostMapping(value = "/api/chunil/v1/chunilEsti/selcodlst_ibk", consumes = "application/json;charset=UTF-8")
    String searchDeliveryCodeList(@RequestParam(name = "jsQuery") String jsQuery);

    /**
     * 배송 운임 체크 API
     * @param jsQuery
     * @return
     */
    @PostMapping(value = "/api/chunil/v1/chunilEsti/selamtchk_ibk", consumes = "application/json;charset=UTF-8")
    String checkDeliveryCost(@RequestParam(name = "jsQuery") String jsQuery);

    /**
     * 배송 요청 API
     * @param jsQuery
     * @return
     */
    @PostMapping(value = "/api/chunil/v1/chunilEsti/insibkreq_ibk", consumes = "application/json;charset=UTF-8")
    String requestDelivery(@RequestParam(name = "jsQuery") String jsQuery);

    /**
     * 운송 취소 API
     * @param jsQuery
     * @return
     */
    @PostMapping(value = "/api/chunil/v1/chunilEsti/updibkccl_ibk", consumes = "application/json;charset=UTF-8")
    String cancelDelivery(@RequestParam(name = "jsQuery") String jsQuery);
}
