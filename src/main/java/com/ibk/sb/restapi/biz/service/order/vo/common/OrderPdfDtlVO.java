package com.ibk.sb.restapi.biz.service.order.vo.common;

import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

/**
 * 주문 상품 조회 VO
 */
@Getter
@Setter
@Alias("OrderPdfDtlVO")
public class OrderPdfDtlVO {

    // 주문 정보 ID
    private String ordnInfoId;

    // 상품 정보 ID
    private String pdfInfoId;

    // 수량
    private Integer qty;

    // 총 금액
    private Integer ttalAmt;

    // 등록 일시 문자열
    private String rgsnTsStr;

    // 구매후기 작성 여부
    private String isReviewed;

}
