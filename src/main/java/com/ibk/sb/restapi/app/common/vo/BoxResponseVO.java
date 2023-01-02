package com.ibk.sb.restapi.app.common.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoxResponseVO<T> {

    @JsonProperty("STATUS")
    private String STATUS;

    @JsonProperty("MESSAGE")
    private String MESSAGE;

    @JsonProperty("RSLT_DATA")
    private T RSLT_DATA;
}
