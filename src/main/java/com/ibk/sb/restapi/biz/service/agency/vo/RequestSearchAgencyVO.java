package com.ibk.sb.restapi.biz.service.agency.vo;

import com.ibk.sb.restapi.app.common.vo.PageVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

/**
 * 에이전시 검색용 VO
 */
@Getter
@Setter
@NoArgsConstructor
@Alias("RequestSearchAgencyVO")
public class RequestSearchAgencyVO extends PageVO {

    // 상품 ID
    @ApiModelProperty(name = "pdfId", value = "상품 ID")
    private String pdfId;

    // 파일정보 ID
    @ApiModelProperty(name = "fileId", value = "파일정보 ID")
    private String fileId;

    // 신규파일정보 ID
    @ApiModelProperty(name = "newFileId", value = "신규파일정보 ID")
    private String newFileId;

    // 신규상품 ID
    @ApiModelProperty(name = "newPdfId", value = "신규상품 ID")
    private String newPdfId;

    // 보낸 이용기관 ID
    @ApiModelProperty(name = "senUsisId", value = "보낸 이용기관 ID")
    private String senUsisId;

    // 보낸 이용자 ID
    @ApiModelProperty(name = "senUserId", value = "보낸 이용자 ID")
    private String senUserId;

    // 수신 이용기관 ID
    @ApiModelProperty(name = "recUsisId", value = "수신 이용기관 ID")
    private String recUsisId;

    // 수신 판매자 ID
    @ApiModelProperty(name = "recUserId", value = "수신 판매자 ID")
    private String recUserId;

    // 에이전시 요청 메시지
    @ApiModelProperty(name = "pcsnCon", value = "에이전시 요청 메시지")
    private String pcsnCon;

    // 요청상태
    @ApiModelProperty(name = "pcsnsttsId", value = "요청상태")
    private String pcsnsttsId;

    // 등록자
    @ApiModelProperty(name = "rgsnUserId", value = "등록자")
    private String rgsnUserId;

    // 관리자 에이전시 요청 ID
    @ApiModelProperty(name = "agenReqId", value = "관리자 에이전시 요청 ID")
    private String agenReqId;

    // 상품 에이전시 요청 ID
    @ApiModelProperty(name = "agenInfId", value = "상품 에이전시 요청 ID")
    private String agenInfId;

    // 상품 에이전시 요청 신규 ID
    @ApiModelProperty(name = "newAgenInfId", value = "상품 에이전시 요청 신규 ID")
    private String newAgenInfId;

    // 상품 판매유형
    @ApiModelProperty(name = "pdfSttsId", value = "상품 판매유형")
    private String pdfSttsId;

    // 상품 진열여부
    @ApiModelProperty(name = "pdfPgrsYn", value = "상품 진열여부")
    private String pdfPgrsYn;

    // 상품 파일유형
    @ApiModelProperty(name = "filePtrnId", value = "상품 파일유형")
    private String filePtrnId;

    // 회원 승인여부
    @ApiModelProperty(name = "mmbrsttsId", value = "회원 승인여부")
    private String mmbrsttsId;

    // 회원 유형
    @ApiModelProperty(name = "mmbrtypeId", value = "회원 유형")
    private String mmbrtypeId;

    // 회원 사용여부
    @ApiModelProperty(name = "mmbrUseYn", value = "회원 사용여부")
    private String mmbrUseYn;

    // 보낸/받은 검색 타입
    @ApiModelProperty(name = "agenSearchType", value = "보낸/받은 검색 타입", example = "rec(받은요청), sen(보낸요청)")
    private String agenSearchType;

    // 검색 필터
    @ApiModelProperty(name = "agenSearchFilter", value = "검색 필터", example = "COC01001(요청),COC01003(승인),COC01004(반려),COC01005(취소)")
    private String agenSearchFilter;


    // 로그인 이용기관 ID
    @ApiModelProperty(hidden = true)
    private String loginUsisId;

    // 로그인 사용자 ID
    @ApiModelProperty(hidden = true)
    private String loginUserId;

}
