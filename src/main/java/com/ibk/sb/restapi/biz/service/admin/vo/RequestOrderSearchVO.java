package com.ibk.sb.restapi.biz.service.admin.vo;

import com.ibk.sb.restapi.app.common.vo.PageVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@Alias("RequestOrderSearchVO")
public class RequestOrderSearchVO extends PageVO {

    //검색어
    @ApiModelProperty(name = "searchText", value = "검색어")
    private String searchText;

    //검색타입(주문번호, 구매자명, 판매자명, 상품명)
    @ApiModelProperty(name = "searchType", value = "검색타입(주문번호, 구매자명, 판매자명, 상품명)")
    private String searchType;

    //주문상태
    @ApiModelProperty(name = "ordnSttsId", value = "주문상태")
    private String ordnSttsId;

}
