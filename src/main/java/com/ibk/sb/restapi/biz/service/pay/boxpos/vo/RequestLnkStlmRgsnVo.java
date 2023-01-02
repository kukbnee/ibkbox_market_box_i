package com.ibk.sb.restapi.biz.service.pay.boxpos.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestLnkStlmRgsnVo {

    // 가맹사업자등록번호
    private String afstBzn;

    // 사용자ID
    private String userId;

    // 제휴사그룹코드
    private String alcmGrpCd;

    // 제휴사연계결제고유번호
    private String alcmLnkStlmUqn;

    // 휴대폰인식번호
    private String clphRcgnNo;

    // 메모내용
    private String memoCon;

    // 결제내용
    private String stlmCon;

    // 기기 유형값
    private String deviceType;

    // 결제내용메모
    private String stlmConMemo;

    // 요청자 유형(구매자, 가맹점)
    private String type;

    // 전체합계금액
    private Long allSumAmt;

    // 공급가금액
    private Long sppcAmt;

    // 과세금액
    private Long txtnAmt;

    // 비과세금액
    private Long noneTxtnAmt;
}
