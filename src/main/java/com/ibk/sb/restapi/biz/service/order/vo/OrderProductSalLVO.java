package com.ibk.sb.restapi.biz.service.order.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

/**
 * 상품판매정보 VO
 * */
@Getter
@Setter
@NoArgsConstructor
@Alias("OrderProductSalLVO")
@JsonIgnoreProperties({
        "pdfInfoId"
})
public class OrderProductSalLVO {

    // 상품정보ID
    private String pdfInfoId;

    // 판매가격
    private Integer pdfPrc;

    // 판매가격단위명
    private String comPrcutNm;

    // 상품단위명
    private String comPdfutNm;

    // 할인가
    private Integer salePrc;

    // 가격 협의 여부
    private String prcDscsYn;

    // 견적 사용 유무
    private String esttUseEn;

    // 주문수량 제한 사용여부
    private String ordnQtyLmtnUsyn;

    // 주문 최소 수량
    private Integer ordnMnmmQty;

    // 주문 최대 수량 여부
    private String ordnMxmmQtyYn;

    // 주문 최대 수량
    private Integer ordnMxmmQty;

    // 판매자 이용기관 ID
    private String selrUsisId;

}
