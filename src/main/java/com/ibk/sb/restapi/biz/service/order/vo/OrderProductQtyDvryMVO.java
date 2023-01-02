package com.ibk.sb.restapi.biz.service.order.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

/**
 * 상품 수량별 배송정보 VO
 * */
@Getter
@Setter
@NoArgsConstructor
@Alias("OrderProductQtyDvryMVO")
@JsonIgnoreProperties({
        "pdfInfoId"
})
public class OrderProductQtyDvryMVO {

    // 상품정보ID
    private String pdfInfoId;

    // 정보순번
    private String infoSqn;

    // 수량
    private Integer qty;

    // 수량단위명
    private String comPdfUtNm;

    // 금액
    private Integer amt;

    // 금액단위명
    private String comPrcUtNm;

}
