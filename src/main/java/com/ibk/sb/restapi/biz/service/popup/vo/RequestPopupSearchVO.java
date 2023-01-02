package com.ibk.sb.restapi.biz.service.popup.vo;

import com.ibk.sb.restapi.biz.service.admin.vo.RequestSearchVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@Alias("RequestPopupSearchVO")
public class RequestPopupSearchVO extends RequestSearchVO {

    // 상태 조회 조건
    @ApiModelProperty(name = "statusCode", value = "상태 조회 조건", example = "display, close, stanby")
    private String statusCode;

}
