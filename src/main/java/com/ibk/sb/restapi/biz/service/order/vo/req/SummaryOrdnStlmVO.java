package com.ibk.sb.restapi.biz.service.order.vo.req;

import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@Alias("SummaryOrdnStlmVO")
public class SummaryOrdnStlmVO {

    // 결제 정보 ID
    private String stlmInfoId;

    // 주문 정보 ID
    private String ordnInfoId;

}
