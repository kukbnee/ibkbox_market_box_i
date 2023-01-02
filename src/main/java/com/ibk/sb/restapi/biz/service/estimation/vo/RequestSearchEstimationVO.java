package com.ibk.sb.restapi.biz.service.estimation.vo;

import com.ibk.sb.restapi.app.common.vo.PageVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

import java.util.List;

/**
 * 등록용 견적 정보 VO
 */
@Getter
@Setter
@Alias("RequestSearchEstimationVO")
public class RequestSearchEstimationVO extends PageVO {

    // 발신자 이용기관 ID
    @ApiModelProperty(name = "dpmpUsisId", value = "발신자 이용기관 ID")
    private String dpmpUsisId;

    // 발신자 사용자 ID
    @ApiModelProperty(name = "dpmpUserId", value = "발신자 사용자 ID")
    private String dpmpUserId;

    // 수신자 이용기관 ID
    @ApiModelProperty(name = "rcvrUsisId", value = "수신자 이용기관 ID")
    private String rcvrUsisId;

    // 수신자 사용자 ID
    @ApiModelProperty(name = "rcvrUserId", value = "수신자 사용자 ID")
    private String rcvrUserId;

    // 주문 정보 ID
    @ApiModelProperty(name = "ordnInfoId", value = "주문 정보 ID")
    private String ordnInfoId;

    // 등록 사용자 ID
    @ApiModelProperty(name = "rgsnUserId", value = "등록 사용자 ID")
    private String rgsnUserId;

    // 수정 사용자 ID
    @ApiModelProperty(name = "amnnUserId", value = "수정 사용자 ID")
    private String amnnUserId;

    // 견적 정보 ID
    @ApiModelProperty(name = "esttInfoId", value = "견적 정보 ID")
    private String esttInfoId;

    // 처리 상태 ID
    @ApiModelProperty(name = "pcsnSttsId", value = "처리 상태 ID")
    private String pcsnSttsId;

    // 처리 내용
    @ApiModelProperty(name = "pcsnCon", value = "처리 내용")
    private String pcsnCon;

    // 판매자 이용기관 ID
    @ApiModelProperty(name = "selrUsisId", value = "판매자 이용기관 ID")
    private String selrUsisId;

    // 배송 유형 ID
    @ApiModelProperty(name = "dvryPtrnId", value = "배송 유형 ID")
    private String dvryPtrnId;

    // 업체 정보 ID
    @ApiModelProperty(name = "entpInfoId", value = "업체 정보 ID")
    private String entpInfoId;

    // 배송비
    @ApiModelProperty(name = "dvrynone", value = "배송비")
    private Integer dvrynone;

    // 수령지 우편번호
    @ApiModelProperty(name = "rcarZpcd", value = "수령지 우편번호")
    private String rcarZpcd;

    // 수령지 주소
    @ApiModelProperty(name = "rcarAdr", value = "수령지 주소")
    private String rcarAdr;

    // 수령지 상세주소
    @ApiModelProperty(name = "rcarDtlAdr", value = "수령지 상세주소")
    private String rcarDtlAdr;

    // 수령인
    @ApiModelProperty(name = "rcarNm", value = "수령인")
    private String rcarNm;

    // 수령인 연락처1
    @ApiModelProperty(name = "rcarCnplone", value = "수령인 연락처1")
    private String rcarCnplone;

    // 출고 우편번호
    @ApiModelProperty(name = "rlontfZpcd", value = "출고 우편번호")
    private String rlontfZpcd;

    // 출고 주소
    @ApiModelProperty(name = "rlontfAdr", value = "출고 주소")
    private String rlontfAdr;

    // 출고 상세주소
    @ApiModelProperty(name = "rlontfDtad", value = "출고 상세주소")
    private String rlontfDtad;

    // 견적 상품 정보
    @ApiModelProperty(name = "items", value = "List<EstimationProductVO>")
    private List<EstimationProductVO> items;

    //////////
    // 인감 정보
    //////////

    // 인감 이미지 파일 ID
    @ApiModelProperty(name = "rgslImgFileId", value = "인감 이미지 파일 ID")
    private String rgslImgFileId;

    //////
    // 문의
    //////

    // 문의 정보 ID
    @ApiModelProperty(name = "inqrInfoId", value = "문의 정보 ID")
    private String inqrInfoId;

    // 문의 정보 순번
    @ApiModelProperty(name = "inqrInfoSqn", value = "문의 정보 순번")
    private Integer inqrInfoSqn;

    // 문의 내용
    @ApiModelProperty(name = "inqrCon", value = "문의 내용")
    private String inqrCon;

    // 문의 견적 연관 순번
    @ApiModelProperty(name = "inesSqn", value = "문의 견적 연관 순번")
    private Integer inesSqn;

    //////////
    // 조회 조건
    //////////

    // 보낸견적(sen), 받은견적(rec)
    @ApiModelProperty(name = "estimationSearchType", value = "보낸견적(sen), 받은견적(rec)")
    private String estimationSearchType;

    // 상품 진행 여부
    @ApiModelProperty(name = "pdfPgrsYn", value = "상품 진행 여부")
    private String pdfPgrsYn;

    // 상품 상태 ID
    @ApiModelProperty(name = "pdfSttsId", value = "상품 상태 ID")
    private String pdfSttsId;

    // 파일 유형 ID
    @ApiModelProperty(name = "filePtrnId", value = "파일 유형 ID")
    private String filePtrnId;

    // 상품명
    @ApiModelProperty(name = "pdfNm", value = "상품명")
    private String pdfNm;

    // 상품 정보 ID
    @ApiModelProperty(name = "pdfInfoId", value = "상품 정보 ID")
    private String pdfInfoId;

    // 로그인 이용기관 ID
    @ApiModelProperty(hidden = true)
    private String loginUsisId;

    // 로그인 사용자 ID
    @ApiModelProperty(hidden = true)
    private String loginUserId;

}
