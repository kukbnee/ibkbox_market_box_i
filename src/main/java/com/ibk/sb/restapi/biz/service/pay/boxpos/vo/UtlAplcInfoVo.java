package com.ibk.sb.restapi.biz.service.pay.boxpos.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UtlAplcInfoVo {

    // 사용자ID
    @JsonProperty("userId")
    private String userId;

    // 가맹점회원ID
    @JsonProperty("aflnShpMmbrId")
    private String aflnShpMmbrId;

    // 신청자명
    @JsonProperty("apctNm")
    private String apctNm;

    // 이용기관ID
    @JsonProperty("usisId")
    private String usisId;

    // 휴대폰인식번호
    @JsonProperty("clphRcgnNo")
    private String clphRcgnNo;

    // 단말기ID
    @JsonProperty("trunId")
    private String trunId;

    // 가맹점명
    @JsonProperty("afstNm")
    private String afstNm;

    // 가맹점사업자등록번호
    @JsonProperty("afstBzn")
    private String afstBzn;

    // 대표자명
    @JsonProperty("rpprNm")
    private String rpprNm;

    // 담당자명
    @JsonProperty("rsprNm")
    private String rsprNm;

    // 대표자전화번호
    @JsonProperty("rpprTpn")
    private String rpprTpn;

    // 담당자전화번호
    @JsonProperty("rsprTpn")
    private String rsprTpn;

    // 은행구분코드
    @JsonProperty("bankDcd")
    private String bankDcd;

    // 정산계좌번호
    @JsonProperty("adjsAcntNo")
    private String adjsAcntNo;

    // 거래상태코드
    @JsonProperty("trnScd")
    private String trnScd;

    // 카드승인여부
    @JsonProperty("cardAudit")
    private String cardAudit;

    // 대표자여부
    @JsonProperty("rpprYn")
    private String rpprYn;

    // 등록자ID
    @JsonProperty("rgsrId")
    private String rgsrId;

    // 등록일시
    @JsonProperty("rgsnTs")
    private String rgsnTs;

    // 시스템최종변경ID
    @JsonProperty("sysLsmdId")
    private String sysLsmdId;

    // 시스템최종변경일시
    @JsonProperty("sysLsmdTs")
    private String sysLsmdTs;

    // 배치설정시간
    @JsonProperty("sttcPushStupHms")
    private String sttcPushStupHms;

    // 추천영업점
    @JsonProperty("rcmdBobCd")
    private String rcmdBobCd;

    // 담당자이메일주소
    @JsonProperty("rsprEad")
    private String rsprEad;

    // 포스메뉴사용여부
    @JsonProperty("isPosMenu")
    private String isPosMenu;

    //
    @JsonProperty("afstAnm")
    private String afstAnm;

    @JsonProperty("txtnDcd")
    private String txtnDcd;

    @JsonProperty("zpayDcd")
    private String zpayDcd;

//    // VAN사용여부
//    @JsonProperty("vanUseYn")
//    private String vanUseYn;
//
//    // 실적여부
//    @JsonProperty("acrsYn")
//    private String acrsYn;
}
