package com.ibk.sb.restapi.biz.service.product.bundle.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ibk.sb.restapi.app.common.vo.BaseTableVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@NoArgsConstructor
@Alias("SummaryBundleProductVO")
@JsonIgnoreProperties({
        "usyn", "delyn", "rgsnUserId", "rgsnTs", "amnnUserId", "amnnTs"
})
public class SummaryBundleProductVO extends BaseTableVO {

    // 묶음 상품 번호 ID
    private String bunInfId;

    // 정보 순번
    private int infoSqn;

    // 대표상품 유무
    private String mainYn;


    // 상품 정보 ID
    private String pdfInfoId;

    // 판매자 이용기관 ID
    private String selrUsisId;

    // 판매자 이용기관 명
    private String selrUsisName;

    // 상품 명
    private String pdfNm;

    // 요약 설명
    private String brfDesc;

    // 에이전시 접수 정보 ID
    private String agenInfId;

    // 에이전시 상품 여부
    private String agenState;

    // 상품 진행 여부(진열 여부)
    private String pdfPgrsYn;

    // 상품 상태 ID(판매중(GDS00001))
    private String pdfSttsId;

    // 상품 가격 협의 여부
    private String prcDscsYn;

    // 판매가격
    private String pdfPrc;

    // 할인가
    private String salePrc;

    // 공통 가격 단위 ID
    private String comPrcutId;

    // 공통 가격 단위 명
    private String comPrcutName;


    // 위시 리스트 추가 플래그
    private String wishFlg;


    // 위시 리스트 추가 수
    //      : 제품이 위시 리스트에 추가된 숫자
    private String wishCnt;
}
