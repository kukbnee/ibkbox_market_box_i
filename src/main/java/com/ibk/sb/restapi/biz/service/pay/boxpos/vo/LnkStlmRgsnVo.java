package com.ibk.sb.restapi.biz.service.pay.boxpos.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LnkStlmRgsnVo {

    // 연계결제일련번호
    @JsonProperty("lnkStlmSrn")
    private String lnkStlmSrn;

    // URL
    @JsonProperty("url")
    private String url;

    // 요청자 유형(구매자, 가맹점)
    @JsonProperty("type")
    private String type;
}
