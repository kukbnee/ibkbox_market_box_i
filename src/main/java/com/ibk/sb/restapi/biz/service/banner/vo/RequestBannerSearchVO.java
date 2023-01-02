package com.ibk.sb.restapi.biz.service.banner.vo;

import com.ibk.sb.restapi.biz.service.admin.vo.RequestSearchVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@Alias("RequestBannerSearchVO")
public class RequestBannerSearchVO extends RequestSearchVO {

    // Banner type ID
    @ApiModelProperty(name = "typeId", value = "배너 타입 ID")
    private String typeId;

    // 상태 조회 조건
    @ApiModelProperty(name = "statusCode", value = "상태 조회 조건", example = "off, display, close, stanby")
    private String statusCode;

}
