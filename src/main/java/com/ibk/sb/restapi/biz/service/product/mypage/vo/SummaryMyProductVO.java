package com.ibk.sb.restapi.biz.service.product.mypage.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ibk.sb.restapi.app.common.vo.BaseTableVO;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

/**
 * 마이페이지 상품관리 상품 목록 VO
 */
@Getter
@Setter
@Alias("SummaryMyProductVO")
@JsonIgnoreProperties({
        "usyn", "delyn", "rgsnUserId", "rgsnTs", "amnnUserId", "amnnTs",
        "totalCnt", "rgsnUserName", "amnnUserName"
})
public class SummaryMyProductVO extends BaseTableVO {

    // 묶음상품 번호 ID
    private String bunInfId;

    // 바이어 상품 번호 ID
    private String buyerInfId;

    // 정보 순번
    private int infoSqn;

    // 대표상품 유무
    private String mainYn;

    // 상품 정보 ID
    private String pdfInfoId;

    // 상품 명
    private String pdfNm;

    // 상품 카테고리 ID
    private String pdfCtgyName;

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

    // 상품 상태 ID
    private String pdfSttsId;

    // 상품 상태 명
    private String pdfSttsName;

    // 상품 진행 여부(진열 여부)
    private String pdfPgrsYn;

    // 주문수량 제한 사용여부
    private String ordnQtyLmtnUsyn;

    // 주문 최소 수량
    private String ordnMnmmQty;

    // 주문 최대 수량 여부
    private String ordnMxmmQtyYn;

    // 주문 최대 수량
    private String ordnMxmmQty;

    // 체크박스 플래그
    private boolean checked = false;

    // 가격 협의 여부
    private String prcDscsYn;
}
