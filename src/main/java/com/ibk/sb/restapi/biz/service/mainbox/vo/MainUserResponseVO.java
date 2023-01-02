package com.ibk.sb.restapi.biz.service.mainbox.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MainUserResponseVO {

    @JsonProperty("STATUS")
    private String STATUS;

    @JsonProperty("RSLT_DATA")
    private MainUserVO RSLT_DATA;

}
