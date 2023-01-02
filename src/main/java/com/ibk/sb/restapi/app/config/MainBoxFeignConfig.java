package com.ibk.sb.restapi.app.config;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

public class MainBoxFeignConfig {

    @Bean
    public RequestInterceptor requestInterceptor(@Value("${feign.mnb-api.key}") String mainBoxApiServiceKey) {
        return requestInterceptor -> requestInterceptor
                .header("Content-Type", "application/json; charset=utf-8")
                .header("Media-Type", "application/json; charset=utf-8")
                .header("appKey", mainBoxApiServiceKey);
    }
}
