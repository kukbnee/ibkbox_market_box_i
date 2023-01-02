package com.ibk.sb.restapi.biz.service.product.common.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ibk.sb.restapi.app.common.vo.BaseTableVO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

/**
 * Name : 상품 반품/교환 정보
 * Table : TB_BOX_MKT_PDF_RTIN_L
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Alias("ProductReturnVO")
@JsonIgnoreProperties({
        "usyn", "delyn", "rgsnUserId", "rgsnTs", "amnnUserId", "amnnTs",
        "totalCnt", "rnum"
})
public class ProductReturnVO extends BaseTableVO {

    // 상품 정보 ID
    private String pdfInfoId;

    // 반품 교환 기간
    private String rtgdInrcTrm;

    // 반품 비용
    private String rtgdExp;

    // 반품 교환 절차
    private String rtgdInrcPrcd;

    // 반품 교환 불가
    private String rtgdInrcDsln;
}
