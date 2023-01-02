package com.ibk.sb.restapi.biz.service.delivery.chunil.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

/**
 * 배송 취소 API VO
 */
@Getter
@Setter
@Alias("RequestCancelDeliveryVO")
public class RequestCancelDeliveryVO {


    // 운송 의뢰 번호
    @JsonProperty("REQNUM")
    @ApiModelProperty(name = "REQNUM", value = "운송 의뢰 번호")
    private String REQNUM;

    // 요청자ID(“IBKAPI” 고정값)
    @JsonProperty("USERID")
    @ApiModelProperty(name = "USERID", value = "요청자ID(“IBKAPI” 고정값)")
    private String USERID = "IBKAPI";

    public String getParam() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("REQNUM:").append(setParam(this.REQNUM));
        sb.append(",").append("USERID:").append(setParam(this.USERID));
        sb.append("}");
        return sb.toString();
    }

    private String setParam(String param) {
        StringBuilder sb = new StringBuilder();
        sb.append("\"");
        sb.append(param);
        sb.append("\"");
        return sb.toString();
    }
}
