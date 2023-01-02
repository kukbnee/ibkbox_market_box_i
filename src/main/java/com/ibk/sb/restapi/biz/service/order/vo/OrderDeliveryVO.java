package com.ibk.sb.restapi.biz.service.order.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

/**
 * 구매자 배송정보 VO
 * */
@Getter
@Setter
@NoArgsConstructor
@Alias("OrderDeliveryVO")
public class OrderDeliveryVO {

    // 수령인
    private String recv;

    // 주소
    private String adr;

    // 상세주소
    private String dtlAdr;

    // 우편주소
    private String zpcd;

    // 연락처1
    private String cnplone;

    // 연락처2
    private String cnpltwo;

    // 다음주문도 같은 배송지 사용여부
    private String dlplUseYn;

    // 회원상태코드
    private String mmbrsttsId;

}
