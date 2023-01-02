package com.ibk.sb.restapi.biz.service.order.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

import java.util.List;

/**
* 결제화면 요청 VO
* */
@Getter
@Setter
@NoArgsConstructor
@Alias("RequestPayVO")
public class RequestPayVO {

    // 구매자 이용기관 ID
    private String pucsUsisId;

    // 구매자 사용자 ID
    private String pucsUserId;

    // 견적ID
    private String esttInfoId;

    // 견적상태
    private String pcsnSttsId;

    // 배송지 주소
    private String addr;

    // 상품목록
    List<RequestProductVO> products;

}