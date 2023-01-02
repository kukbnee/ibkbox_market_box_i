package com.ibk.sb.restapi.biz.service.order.vo.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

/**
 * 결제 정보 VO
 */
@Getter
@Setter
@AllArgsConstructor
@Alias("OrderReqStlmVO")
public class OrderReqStlmVO {

    // 결제 정보 ID
    @JsonProperty("stlmInfoId")
    private String stlmInfoId;

    // 주문 정보 ID
    @JsonProperty("ordnInfoId")
    private String ordnInfoId;

    // 체결 번호 ID
    @JsonProperty("cnttNoId")
    private String cnttNoId;

    // 결제유형 ID
    @JsonProperty("stlmptrnId")
    private String stlmptrnId;

    // 결제상태 ID
    @JsonProperty("stlmsttsId")
    private String stlmsttsId;

    // 요청 정보
    @JsonProperty("rqstInfo")
    private String rqstInfo;

    // 결과 정보
    @JsonProperty("rsltInfo")
    private String rsltInfo;

    // 금액
    @JsonProperty("amt")
    private Integer amt;

    // 결제 결과 ID
    @JsonProperty("stlmRsltId")
    private String stlmRsltId;

    //////
    // 분기조건
    //////

//    // 결제 완료 확인 여부(결제 여부 조회해서 결제했으면 'Y')
//    private String isStlmInqResOk;

    //////
    // 기타
    //////

    // 로그인 이용기관 ID
    private String loginUsisId;

    // 로그인 사용자 ID
    private String loginUserId;

    // 등록 사용자 ID
    private String rgsnUserId;

    public OrderReqStlmVO(String ordnInfoId) {
        this.ordnInfoId = ordnInfoId;
    }
}
