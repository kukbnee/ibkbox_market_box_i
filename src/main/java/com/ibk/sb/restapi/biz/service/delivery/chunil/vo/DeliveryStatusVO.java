package com.ibk.sb.restapi.biz.service.delivery.chunil.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.apache.ibatis.type.Alias;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Alias("RequestUpdateDeliveryStatusVO")
public class DeliveryStatusVO {

    // 원송장(운송장) 번호
    @JsonProperty("RTNNUM")
    @ApiModelProperty(name = "RTNNUM", value = "원송장(운송장) 번호")
    private String RTNNUM = "";

    // 주문상태 ID
    @JsonProperty("ORDNSTTSID")
    @ApiModelProperty(name = "ORDNSTTSID", value = "주문상태 ID")
    private String ORDNSTTSID = "";

    // 호출 사업자명
    @ApiModelProperty(name = "callApiBzNm", value = "호출 사업자명")
    private String callApiBzNm ="";

    // 쿼리 실행 및 데이터 수정 날짜
    @ApiModelProperty(hidden = true)
    private String rsDate = "";

    // API 호출 성공 여부
    //      성공(true)/실패(false)
    @ApiModelProperty(hidden = true)
    private boolean flg;

    // 실패시 사유 메세지
    @ApiModelProperty(hidden = true)
    private String msg = "";
}
