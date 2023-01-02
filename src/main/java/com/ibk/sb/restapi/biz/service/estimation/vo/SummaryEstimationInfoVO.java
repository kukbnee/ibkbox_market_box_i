package com.ibk.sb.restapi.biz.service.estimation.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ibk.sb.restapi.app.common.vo.BaseTableVO;
import com.ibk.sb.restapi.biz.service.common.vo.SummaryComCodeListVO;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

import java.sql.Timestamp;
import java.util.List;

/**
 * 조회용 견적 정보 VO
 */
@Getter
@Setter
@Alias("SummaryEstimationInfoVO")
@JsonIgnoreProperties({

})
public class SummaryEstimationInfoVO extends BaseTableVO {

    // 로그인 이용기관 ID
    private String loginUsisId;

    // 로그인 사용자 ID
    private String loginUserId;

    // 발신자 이용기관 ID
    private String dpmpUsisId;

    // 발신자 사용자 ID
    private String dpmpUserId;

    // 수신자 이용기관 ID
    private String rcvrUsisId;

    // 수신자 사용자 ID
    private String rcvrUserId;

    // 주문 정보 ID
    private String ordnInfoId;

    // 등록 사용자 ID
    private String rgsnUserId;

    // 수정 사용자 ID
    private String amnnUserId;

    // 견적 정보 ID
    private String esttInfoId;

    // 처리 상태 ID
    private String pcsnSttsId;

    // 처리 내용
    private String pcsnCon;

    // 판매자 이용기관 ID
    private String selrUsisId;

    // 배송 유형 ID
    private String dvryPtrnId;

    // 업체 정보 ID
    private String entpInfoId;

    // 업체명
    private String entpNm;

    // 배송비
    private Integer dvrynone;

    // 수령지 우편번호
    private String rcarZpcd;

    // 수령지 주소
    private String rcarAdr;

    // 수령지 상세주소
    private String rcarDtlAdr;

    // 수령인
    private String rcarNm;

    // 수령인 연락처1
    private String rcarCnplone;

    // 출고 우편번호
    private String rlontfZpcd;

    // 출고 주소
    private String rlontfAdr;

    // 출고 상세주소
    private String rlontfDtad;

    // 견적 상품 정보
    private List<EstimationProductVO> items;

    //////////
    // 배송 업체
    //////////

    // 배송 업체
    private List<EstimationDeliveryVO> dvryEntps;

    //////
    // 코드
    //////

    // 공통관련코드(처리 상태)
    private List<SummaryComCodeListVO> pcsnSttsCodes;

    // 공통관련코드(배송 유형)
    private List<SummaryComCodeListVO> dvryPtrnCodes;

    // 공통관련코드(견적 상품 유형)
    private List<SummaryComCodeListVO> esttPdfPtrnCodes;

    // 공통관련코드(제품포장 단위)
    private List<SummaryComCodeListVO> prdtpcknUtCodes;

    //////
    // 문의
    //////

    // 문의 정보 ID
    private String inqrInfoId;

    // 정보 순번
    private Integer inqrInfoSqn;

    // 연관 순번
    private Integer inesSqn;

    //////////////////
    // 가입사업장 주요 정보
    //////////////////

    // 이용기관 대표자 명
    private String rprsntvNm;

    // 이용기관 명
    private String bplcNm;

    // 이용기관 대표 전화번호
    private String reprsntTelno;

    // 이용기관 명
    private String rcvrBplcNm;

    //////
    // 조회
    //////

    // 처리 상태 명
    private String pcsnSttsNm;

    // 등록 일시 문자열
    private String rgsnTsStr;

    // 상품 총개수
    private String pdfCnt;

    // 상품 총금액
    private String pdfSum;

    // 파일명
    private String fileNm;

    // 파일 경로
    private String filePath;

    // 카테고리 데이터
    private String ctgyData;

    // 카테고리 3단계
    private String tms4ClsfNm;

    // 연동 상품 정보 ID
    private String gearPdfInfoId;

    // 상품명
    private String pdfNm;

    // 공통 가격단위 명
    private String comPrcutNm;

    // 상품 진행 여부(진열 여부)
    private String pdfPgrsYn;

    // 상품 상태 ID(판매중(GDS00001))
    private String pdfSttsId;

    // 상품 가격 협의 여부
    private String prcDscsYn;

    // 에이전시 상품 여부
    private String agenState;

    // 에이전시 접수 정보 ID
    private String agenInfId;

    // 주문 상태 ID
    private String ordnSttsId;

    //////////
    // 인감 정보
    //////////

    // 인감 이미지 파일 ID
    private String rgslImgFileId;

    // 인감 이미지 URL
    private String rgslImgFileUrl;

    // 인감 이미지 수정일
    private Timestamp rgslImgAmnnTs;

}
