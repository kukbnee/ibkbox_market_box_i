package com.ibk.sb.restapi.biz.service.order.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

/**
 * 상품배송정보 VO
 * */
@Getter
@Setter
@NoArgsConstructor
@Alias("OrderProductDelLVO")
@JsonIgnoreProperties({
        "pdfInfoId"
})
public class OrderProductDelLVO {

    // 상품정보ID
    private String pdfInfoId;

    // 배송타입
    private String dvryTypeId;

    // 배송유형
    private String dvryPtrnId;

    // 배송비 유형
    private String dvrynonePtrnId;

    // 배송업체명
    private String entpNm;

    // 배송기본가
    private Integer dvryBscprce;

}
