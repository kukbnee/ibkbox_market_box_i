package com.ibk.sb.restapi.biz.service.order.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

/**
 * 상품정보 VO
 * */
@Getter
@Setter
@NoArgsConstructor
@Alias("OrderProductInfMVO")
public class OrderProductInfMVO {

    // 상품정보ID
    private String pdfInfoId;

    // 판매자 이용기관 ID
    private String selrUsisId;

    // 판매자 이용기관 이름
    private String bplcNm;

    // 판매자 카테고리 ID
    private String pdfCtgyId;

    // 카테고리 정보
    private String ctgyData;

    // 상품 진행 여부
    private String pdfPgrsYn;

    // 상품 상태 ID
    private String pdfSttsId;

    // 상품명
    private String pdfNm;

    // 모델명
    private String mdlnm;

    // 요약설명
    private String brfDesc;

    // 간략설명
    private String brfSubDesc;

    // 특허정보 여부
    private String ptntInfoYn;

    // 에이전시 접수 정보 ID
    private String agenInfId;

    // 에이전시 여부
    private String pdfAgenState;

}
