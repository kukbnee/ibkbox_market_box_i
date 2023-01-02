package com.ibk.sb.restapi.biz.service.order.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

/**
 * 상품 지역별 배송정보 VO
 * */
@Getter
@Setter
@NoArgsConstructor
@Alias("OrderProductPearDvryMVO")
@JsonIgnoreProperties({
        "pdfInfoId"
})
public class OrderProductPearDvryMVO {

    // 상품정보ID
    private String pdfInfoId;

    // 정보순번
    private String infoSqn;

    // 시도
    private String trl;

    // 시군구
    private String ctcocrwd;

    // 금액
    private Integer amt;

    // 가격단위명
    private String comPrcUtNm;

}
