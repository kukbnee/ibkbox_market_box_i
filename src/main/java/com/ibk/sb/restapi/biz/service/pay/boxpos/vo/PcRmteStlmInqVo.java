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
public class PcRmteStlmInqVo {

    // 연계결제일련번호
    @JsonProperty("lnkStlmSrn")
    private Long lnkStlmSrn;

    // 결제연계결제일련번호
    @JsonProperty("stlmRgsnSrn")
    private Long stlmRgsnSrn;

    // 가맹자사업자등록번호
    @JsonProperty("afstBzn")
    private String afstBzn;

    // 가맹점명
    @JsonProperty("afstNm")
    private String afstNm;

    // 제휴사그룹코드
    @JsonProperty("alcmGrpCd")
    private String alcmGrpCd;

    // 사용자 ID
    @JsonProperty("userId")
    private String userId;

    // 연계결제채널구분코드
    @JsonProperty("lnkStlmChnlDcd")
    private String lnkStlmChnlDcd;

    // 진행상태코드
    @JsonProperty("pgrsSttsCd")
    private String pgrsSttsCd;

    // 결제내용
    @JsonProperty("stlmCon")
    private String stlmCon;

    // 메모내용
    @JsonProperty("memoCon")
    private String memoCon;

    // 결제내용메모
    @JsonProperty("stlmConMemo")
    private String stlmConMemo;

    // 전체합계금액
    @JsonProperty("allSumAmt")
    private Long allSumAmt;

    // 공급가금액
    @JsonProperty("sppcAmt")
    private Long sppcAmt;

    // 과세금액
    @JsonProperty("txtnAmt")
    private Long txtnAmt;

    // 비과세금액
    @JsonProperty("noneTxtnAmt")
    private Long noneTxtnAmt;

    // 휴대폰인식번호
    @JsonProperty("clphRcgnNo")
    private String clphRcgnNo;

    // 봉사금액
    @JsonProperty("svicAmt")
    private Long svicAmt;

    // 할부개월수
    @JsonProperty("inslMntsCnt")
    private Integer inslMntsCnt;

    // 결제취소요청여부
    @JsonProperty("stclRqstYn")
    private String stclRqstYn;

    // 삭제여부
    @JsonProperty("delYn")
    private String delYn;

    // 등록일시
    @JsonProperty("rgsnTs")
    private String rgsnTs;

    // 단말기 ID
    @JsonProperty("trunId")
    private String trunId;

    // 대표자명
    @JsonProperty("rprsntvNm")
    private String rprsntvNm;

    // Pay요청구분
    @JsonProperty("payRqstDcdNm")
    private String payRqstDcdNm;

    // 결제수단
    @JsonProperty("stlmWayDcdDesc")
    private String stlmWayDcdDesc;

    // 카드종류
    @JsonProperty("acqrNm")
    private String acqrNm;

    // 카드번호
    @JsonProperty("cardNo")
    private String cardNo;

    // 점주 이름
    @JsonProperty("trnDsncNm")
    private String trnDsncNm;

    // 주소
    @JsonProperty("bsunAdr")
    private String bsunAdr;

    // 승인번호
    @JsonProperty("athzNo")
    private String athzNo;

    // 승인일자
    @JsonProperty("athzYmd")
    private String athzYmd;

    // 승인시분
    @JsonProperty("athzHms")
    private String athzHms;

    // 주소
    @JsonProperty("adrNm")
    private String adrNm;

    // 가맹점번호
    @JsonProperty("afstNo")
    private String afstNo;

    // 사용자명
    @JsonProperty("userNm")
    private String userNm;

    // 대표전화번호
    @JsonProperty("reprsntTelNo")
    private String reprsntTelNo;

    @JsonProperty("alcmLnkStlmUqn")
    private String alcmLnkStlmUqn;

    @JsonProperty("stlmWayDcdNm")
    private String stlmWayDcdNm;

    @JsonProperty("bsacSrn")
    private String bsacSrn;

    @JsonProperty("bsacNm")
    private String bsacNm;

    @JsonProperty("bsacNo")
    private String bsacNo;
}
