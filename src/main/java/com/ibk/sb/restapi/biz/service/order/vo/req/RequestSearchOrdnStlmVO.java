package com.ibk.sb.restapi.biz.service.order.vo.req;

import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@Alias("RequestSearchOrdnStlmVO")
public class RequestSearchOrdnStlmVO {

    // 결제 정보 ID
    private String stlmInfoId;

    // 주문 정보 ID
    private String ordnInfoId;

    // 결제유형 ID
    private String stlmptrnId;

    // 결제상태 ID
    private String stlmsttsId;

    // 요청 정보
    private String rqstInfo;

    // 결과 정보
    private String rsltInfo;

    // 금액
    private Integer amt;

    // 결제 결과 ID
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

}
