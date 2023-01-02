package com.ibk.sb.restapi.biz.service.mainbox.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class AlarmTargetResponseVO {

    // 사용자 ID
//    @JsonProperty("USER_ID")
    @JsonProperty("user_ID")
    private String USER_ID;

    // 이용기관 ID
//    @JsonProperty("UTLINSTT_ID")
    @JsonProperty("utlinstt_ID")
    private String UTLINSTT_ID;

    // 권한코드, 총관리자:C, 관리자:M, 직원:U
//    @JsonProperty("AUTHOR_CODE")
    @JsonProperty("author_CODE")
    private String AUTHOR_CODE;

    // 초대사용권한
//    @JsonProperty("IVT_AUTHOR_AT")
    @JsonProperty("ivt_AUTHOR_AT")
    private String IVT_AUTHOR_AT;

    // 스토어구매사용권한
//    @JsonProperty("PURCHS_AUTHOR_AT")
    @JsonProperty("purchs_AUTHOR_AT")
    private String PURCHS_AUTHOR_AT;

    // 디폴트여부
//    @JsonProperty("DFLT_AT")
    @JsonProperty("dflt_AT")
    private String DFLT_AT;

    // 대표사업장여부
//    @JsonProperty("REPRSNT_BPLC_AT")
    @JsonProperty("reprsnt_BPLC_AT")
    private String REPRSNT_BPLC_AT;

    // 최초 등록한 이용기관ID
//    @JsonProperty("REGIST_UTLINSTT")
    @JsonProperty("regist_UTLINSTT")
    private String REGIST_UTLINSTT;

    // 최초 등록한 등록자 ID
//    @JsonProperty("REGIST_ID")
    @JsonProperty("regist_ID")
    private String REGIST_ID;

    // 최초 등록 일시
//    @JsonProperty("REGIST_DT")
    @JsonProperty("regist_DT")
    private Date REGIST_DT;

    // 최초 등록 이용기관 이후 수정기관
//    @JsonProperty("UPDT_UTLINSTT")
    @JsonProperty("updt_UTLINSTT")
    private String UPDT_UTLINSTT;

    // 최초 등록자 이후 수정 사용자ID
//    @JsonProperty("UPDT_ID")
    @JsonProperty("updt_ID")
    private String UPDT_ID;

    // 최초 등록 이후 수정 일시
//    @JsonProperty("UPDT_DT")
    @JsonProperty("updt_DT")
    private Date UPDT_DT;
}
