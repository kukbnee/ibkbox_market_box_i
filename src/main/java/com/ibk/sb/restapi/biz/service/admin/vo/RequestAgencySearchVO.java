package com.ibk.sb.restapi.biz.service.admin.vo;

import com.ibk.sb.restapi.app.common.vo.PageVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@Alias("RequestAgencySearchVO")
public class RequestAgencySearchVO extends PageVO {

    // 조회 조건 (회사명)
    @ApiModelProperty(name = "searchCompanyName", value = "조회 조건 (회사명)")
    private String searchCompanyName;

    // 조회 조건 (상태코드)
    @ApiModelProperty(name = "statusCode", value = "조회 조건 (상태코드)")
    private String statusCode;

}
