package com.ibk.sb.restapi.biz.service.order.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

/**
 * 결제/주문 검색요청 상품 VO
 * */
@Getter
@Setter
@NoArgsConstructor
@Alias("RequestProductVO")
public class RequestProductVO {

    // 결제/주문 상품정보 ID
    private String pdfInfoId;

    // 상품구매수량
    private Integer orderQty;

    // 상품진열상태
    private String pdfPgrsYn;

    // 상품판매상태
    private String pdfSttsId;

}
