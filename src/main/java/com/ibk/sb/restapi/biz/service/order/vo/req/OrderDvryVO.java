package com.ibk.sb.restapi.biz.service.order.vo.req;

import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

import java.util.List;

@Getter
@Setter
@Alias("OrderDvryVO")
public class OrderDvryVO {

    // 주문 정보 ID
    private String ordnInfoId;

    // 견적 일자
    private String esmDate;

    // 출고인
    private String rlontf;

    // 출고인 연락처1
    private String rlontfCnplone;

    // 출고 우편번호
    private String rlontfZpcd;

    // 출고 주소
    private String rlontfAdr;

    // 출고 상세주소
    private String rlontfDtad;

    // 수령인 우편번호
    private String recvZpcd;

    // 수령인 주소
    private String recvAdr;

    // 수령인 상세주소
    private String recvDtad;

    // 수령인
    private String recv;

    // 수령인 연락처1
    private String recvCnplone;

    // 등록 사용자 ID
    private String rgsnUserId;

    // 업체 정보 ID
    private String entpInfoId;

    // 상품 정보
    private List<OrderDvryPdfVO> orderDvryPdfVOList;

}
