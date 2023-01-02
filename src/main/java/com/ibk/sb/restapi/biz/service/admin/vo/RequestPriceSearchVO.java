package com.ibk.sb.restapi.biz.service.admin.vo;

import com.ibk.sb.restapi.app.common.vo.PageVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@Alias("RequestPriceSearchVO")
public class RequestPriceSearchVO extends PageVO {

    //검색어
    @ApiModelProperty(name = "searchText", value = "검색어")
    private String searchText;

    //검색타입
    @ApiModelProperty(name = "searchType", value = "검색타입")
    private String searchType;

    //금액 조건 필터
    @ApiModelProperty(name = "priceSort", value = "금액 조건 필터")
    private String priceSort;

}
