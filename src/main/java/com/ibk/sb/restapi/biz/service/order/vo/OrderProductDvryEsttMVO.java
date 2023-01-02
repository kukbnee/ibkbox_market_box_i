package com.ibk.sb.restapi.biz.service.order.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

/**
 * 상품 화물서비스 견적정보 VO
 * */
@Getter
@Setter
@NoArgsConstructor
@Alias("OrderProductDvryEsttMVO")
@JsonIgnoreProperties({
        "pdfInfoId"
})
public class OrderProductDvryEsttMVO {

    // 상품정보ID
    private String pdfInfoId;

    // 정보순번
    private String infoSqn;

    // 수량
    private String qty;

    // 상품단위ID
    private String comPdfUtId;

    // 상품단위명
    private String comPdfUtNm;

    // 견적금액
    private String esttAmt;

    // 가격단위ID
    private String comPrcUtId;

    // 가격단위명
    private String comPrcUtNm;

}
