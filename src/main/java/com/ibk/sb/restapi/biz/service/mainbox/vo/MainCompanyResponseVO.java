package com.ibk.sb.restapi.biz.service.mainbox.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MainCompanyResponseVO {

    @JsonProperty("STATUS")
    private String STATUS;

    @JsonProperty("RSLT_LIST")
    private List<MainCompanyVO> RSLT_LIST;


}
