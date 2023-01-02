package com.ibk.sb.restapi.biz.service.product.mypage.vo;

import com.ibk.sb.restapi.app.common.vo.BaseTableVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@NoArgsConstructor
@Alias("SummaryMySingleProductVO")
public class SummaryMySingleProductVO extends BaseTableVO {

    // 상품 정보 ID
    private String pdfInfoId;

    // 판매자 이용기관 ID
    private String selrUsisId;

    // 상품 카테고리 ID
    private String pdfCtgyId;

    // 상품 카테고리 명
    private String pdfCtgyName;

    // 상품 진행 여부
    private String pdfPgrsYn;

    // 상품 상태 ID
    private String pdfSttsId;

    // 상품 상태 명
    private String pdfSttsName;

    // 상품 명
    private String pdfNm;

    // 에이전시 접수 정보 ID
    private String agenInfId;

    // 판매가격
    private String pdfPrc;

    // 할인가
    private String salePrc;

    // 가격 협의 여부
    private String prcDscsYn;

    // 견적 사용 여부
    private String esttUseEn;

    // 공통 가격 단위 ID
    private String comPrcutId;

    // 공통 가격 단위 명
    private String comPrcutName;

    // 주문수량 제한 사용여부
    private String ordnQtyLmtnUsyn;

    // 주문 최소 수량
    private String ordnMnmmQty;

    // 주문 최대 수량 여부
    private String ordnMxmmQtyYn;

    // 주문 최대 수량
    private String ordnMxmmQty;


}
