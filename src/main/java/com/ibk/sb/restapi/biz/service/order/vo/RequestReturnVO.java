package com.ibk.sb.restapi.biz.service.order.vo;

import com.ibk.sb.restapi.app.common.vo.PageVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

/**
 * 반품 VO
 * */
@Getter
@Setter
@NoArgsConstructor
@Alias("RequestReturnVO")
public class RequestReturnVO extends PageVO {

    // 주문정보ID
    @ApiModelProperty(name = "ordnInfoId", value = "주문정보ID")
    private String ordnInfoId;

    // 주문정보순번
    @ApiModelProperty(name = "infoSqn", value = "주문정보순번")
    private Integer infoSqn;

    // 상품파일타입
    @ApiModelProperty(name = "filePtrnId", value = "상품파일타입")
    private String filePtrnId;

    // 검색기준 : 구매자(buyer), 판매자(selr)
    @ApiModelProperty(name = "returnType", value = "검색기준 : 구매자(buyer), 판매자(selr)")
    private String returnType;

    // 이용기관 ID
    @ApiModelProperty(name = "usisId", value = "이용기관 ID")
    private String usisId;

    // 사용자 ID
    @ApiModelProperty(name = "userId", value = "사용자 ID")
    private String userId;

    // 주문상태
    @ApiModelProperty(name = "ordnSttsId", value = "주문상태")
    private String ordnSttsId;

}
