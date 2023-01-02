package com.ibk.sb.restapi.biz.service.order.vo.esm;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ibk.sb.restapi.app.common.vo.BaseTableVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

/**
 * 견적 상품정보 VO
 * */
@Getter
@Setter
@NoArgsConstructor
@Alias("OrderProductEsmVO")
@JsonIgnoreProperties({
        "esttInfoId",
        "usyn", "delyn", "rgsnUserId", "rgsnTs", "amnnUserId", "amnnTs",
        "totalCnt", "rgsnUserName", "amnnUserName","rnum", "rvsRnum"
})
public class OrderProductEsmVO extends BaseTableVO {

    // 견적 정보 ID
    private String esttInfoId;

    // 정보 순번
    private Integer infoSqn;

    // 판매자 이용기관 ID
    private String selrUsisId;

    // 판매자 회사명
    private String bplcNm;

    // 상품명
    private String pdfNm;

    // 판매가격
    private Integer pdfPrc;

    // 판매가격 단위명
    private String comPrcUtNm;

    // 주문수량
    private Integer ordnQty;

    // 주문수량 단위명
    private String comPdfUtNm;

    // 견적 상품 유형ID
    private String esttPdfPtrnId;

    // 연동 상품정보 ID
    private String gearPdfInfoId;

    // 에이전시 접수 정보 ID
    private String agenInfId;

    // 에이전시 상품 여부
    private String agenState;

}
