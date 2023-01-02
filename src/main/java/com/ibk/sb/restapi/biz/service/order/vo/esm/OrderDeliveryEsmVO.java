package com.ibk.sb.restapi.biz.service.order.vo.esm;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.apache.ibatis.type.Alias;

/**
 * 견적 배송정보 VO
 * */
@Getter
@Setter
@NoArgsConstructor
@Alias("OrderDeliveryEsmVO")
@JsonIgnoreProperties({
        "esttInfoId"
})
public class OrderDeliveryEsmVO {

    // 견적 정보 ID
    private String esttInfoId;

    // 판매자 이용기관 ID
    private String selrUsisId;

    // 배송 유형 ID
    private String dvryPtrnId;

    // 업체 정보 ID
    private String entpInfoId;

    // 업체 정보 명
    private String entpNm;

    // 배송비
    private Integer dvrynone;

    // 수령인
    private String rcarNm;

    // 수령자 연락처
    private String rcarCnplone;

    // 수령지 우편번호
    private String rcarZpcd;

    // 수령지 주소
    private String rcarAdr;

    // 수령지 상세주소
    private String rcarDtlAdr;

    // 출고지 우편번호
    private String rlontfZpcd;

    // 출고지 주소
    private String rlontfAdr;

    // 출고자 상세주소
    private String rlontfDtad;

}
