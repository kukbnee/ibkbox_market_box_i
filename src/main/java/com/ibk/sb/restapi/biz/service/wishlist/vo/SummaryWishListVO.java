package com.ibk.sb.restapi.biz.service.wishlist.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ibk.sb.restapi.app.common.vo.BaseTableVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@NoArgsConstructor
@Alias("SummaryWishListVO")
@JsonIgnoreProperties({
        "usyn", "delyn", "rgsnUserId", "rgsnTs", "amnnUserId", "amnnTs",
        "totalCnt", "rgsnUserName", "amnnUserName","rnum", "rvsRnum"
})
public class SummaryWishListVO extends BaseTableVO {

    // 위시리스트 등록자 이용기관 ID
    private String pucsUsisId;

    // 위시리스트 등록자 ID
    private String pucsId;

    // 상품 ID
    private String pdfInfoId;

    // 상품 판매가
    private String pdfPrc;

    // 상품 판매가 단위코드
    private String comPrcutId;

    // 상품 판매가 단위이름
    private String pdfSttsName;

    // 상품 할인가
    private String salePrc;

    // 상품 가격협의 여부
    private String prcDscsYn;

    // 상품 견적여부
    private String esttUseEn;

    // 상품 판매자 이용기관 ID
    private String selrUsisId;

    // 상품명
    private String pdfNm;

    // 상품 상태코드
    private String pdfSttsId;

    // 상품 진행 여부(진열 여부)
    private String pdfPgrsYn;

    // 상품 상태이름
    private String comPrcutName;

    // 판매자 회사명
    private String bplcNm;

    // 에이전시 상품인지 여부
    private String agenPdfYn;

    // 요약 설명
    private String brfDesc;

    // 주문수량 제한 사용여부
    private String ordnQtyLmtnUsyn;

    // 주문 최소 수량
    private Integer ordnMnmmQty;

    // 주문 최대 수량
    private Integer ordnMxmmQty;

}
