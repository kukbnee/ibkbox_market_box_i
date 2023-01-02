package com.ibk.sb.restapi.biz.service.agency.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ibk.sb.restapi.app.common.vo.BaseTableVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

import java.sql.Timestamp;
import java.util.List;

/**
 * 에이전시 리스트 조회용 VO
 */
@Getter
@Setter
@NoArgsConstructor
@Alias("SummaryAgencyVO")
@JsonIgnoreProperties({
        "usyn", "delyn", "rgsnUserId", "rgsnTs", "amnnUserId", "amnnTs",
        "totalCnt", "rgsnUserName", "amnnUserName","athzPdfInfoId","rvsRnum"
})
public class SummaryAgencyVO extends BaseTableVO {

    // 에이전시 요청 ID
    private String agenInfId;

    // 보낸 이용기관 ID
    private String senUsisId;

    // 보낸 이용기관 회사명
    private String senBplcNm;

    // 보낸 이용기관 대표자명
    private String senRprsntvNm;

    // 보낸 이용자 ID
    private String senUserId;

    // 받은 이용기관 ID
    private String recUsisId;

    // 받은 이용기관 회사명
    private String recBplcNm;

    // 받은 이용기관 대표자명
    private String recRprsntvNm;

    // 받은 이용자 ID
    private String recUserId;

    // 에이전시 요청 상태
    private String pcsnsttsId;

    // 에이전시 상태 요청일
    private Timestamp agenRegDate;

    // 상품 ID
    private String pdfInfoId;

    // 상품 이용기관 명
    private String bplcNm;

    // 상품 이용기관 대표자 명
    private String rprsntvNm;

	// 상품명
    private String pdfNm;

	// 상품판매가
    private String pdfPrc;

    // 상품판매가단위명
    private String comCdNm;

    // 상품 할인가
    private String salePrc;

    // 상품 가격협의 여부
    private String prcDscsYn;

    // 상품 견적여부
    private String esttUseEn;

    // 에이전시 상품 여부
    private String pdfAgenState;

    // 상품 카테고리 데이터
    private String ctgyData;

    // 에이전시 승인상품 ID
    private String athzPdfInfoId;

}
