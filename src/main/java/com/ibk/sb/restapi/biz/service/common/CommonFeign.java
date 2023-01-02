package com.ibk.sb.restapi.biz.service.common;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;

@FeignClient(name = "test-api", url = "${feign.test-api.url}")
public interface CommonFeign {

    @PostMapping("/api/mb/v1/mnbmenu/leftMenuInq")
    Map<String, Object> getOrganEmpInfo(@RequestHeader Map<String, String> header, @RequestBody Map<String, String> body);
}
