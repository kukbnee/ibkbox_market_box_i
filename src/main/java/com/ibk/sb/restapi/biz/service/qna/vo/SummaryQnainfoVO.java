package com.ibk.sb.restapi.biz.service.qna.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ibk.sb.restapi.app.common.vo.BaseTableVO;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@Alias("SummaryQnainfoVO")
@JsonIgnoreProperties({
        "usyn", "delyn", "rgsnUserId", "rgsnTs", "amnnUserId", "amnnTs"
})
public class SummaryQnainfoVO extends BaseTableVO {

    // 문의정보 ID
    private String inqrInfoId;

    // 상품 정보 ID
    private String pdfInfoId;


    // 문의자 ID
    private String inquUserId;

    // 문의자 이용기관 ID
    private String inquUsisId;

    // 문의자 이용기관 명
    private String inquUsisName;

    // 문의자 이용기관 대표자 명
    private String inquRprsntvNm;


    // 판매자 이용기관 ID
    private String selrUsisId;

    // 판매자 이용기관 명
    private String selrUsisName;

    // 판매자 이용기관 대표자 명
    private String selrRprsntvNm;


    // 상품 명
    private String pdfNm;

    // 카테고리 명
    private String pdfCtgyName;


    // 상품 진행 여부(진열 여부)
    private String pdfPgrsYn;

    // 상품 상태 ID(판매중(GDS00001))
    private String pdfSttsId;

    // 상품 가격협의 여부
    private String prcDscsYn;


    // 에이전시 접수 정보 ID
    private String agenInfId;

    // 판매가격
    private String pdfPrc;

    // 할인가
    private String salePrc;


    // 공통 가격 단위 ID
    private String comPrcutId;

    // 공통 가격 단위 명
    private String comPrcutName;


    // 최근 메세지 날짜
    private String lastMessageDt;

    // 메세지 확인 여부
    //      로그인한 유저가 수신인인 문의 메세지 중에서 가장 최신 메세지를 확인(조회)했는지 여부
    private String cnfaYn;

    // 문의자 여부
    //      로그인한 유저가 문의한 사람(구매자)이면 Y, 판매자면 N
    private String inquYn;

    // 문의 건수
    private QnaCntVO qnaCnt;
}
