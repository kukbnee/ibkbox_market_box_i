package com.ibk.sb.restapi.biz.service.order.vo.req;

import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

/**
 * 주문 배송 API 호출 이력
 */
@Getter
@Setter
@Alias("OrderDvryDchLVO")
public class OrderDvryDchLVO {

    // 주문 정보 ID
    private String ordnInfoId;

    // 정보 순번
    private Integer infoSqn;

    // 요청 정보
    private String rqstInfo;

    // 결과 정보
    private String rsltInfo;

    // 등록 사용자 ID
    private String rgsnUserId;

}
