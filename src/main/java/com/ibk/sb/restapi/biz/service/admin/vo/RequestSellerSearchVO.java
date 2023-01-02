package com.ibk.sb.restapi.biz.service.admin.vo;

import com.ibk.sb.restapi.app.common.vo.PageVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@Alias("RequestSellerSearchVO")
public class RequestSellerSearchVO extends PageVO {

    // 조회 조건 (회사명)
    @ApiModelProperty(name = "searchCompanyName", value = "조회 조건 (회사명)")
    private String searchCompanyName;

    // 조회 조건 (회사명)
    @ApiModelProperty(name = "searchRprsntvName", value = "조회 조건 (대표자 명)")
    private String searchRprsntvName;

    // 조회 조건 (판매자 유형 코드)
    @ApiModelProperty(name = "mmbrsttsId", value = "조회 조건 (판매자 유형 코드)")
    private String mmbrsttsId;



}
