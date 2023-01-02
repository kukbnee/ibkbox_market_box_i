package com.ibk.sb.restapi.biz.service.product.bundle.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ibk.sb.restapi.biz.service.product.single.vo.SingleProductVO;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

/**
 * Name :  상품 묶음상품 목록 정보
 * Table : TB_BOX_MKT_PDF_BUN_L
 */
@Getter
@Setter
@Alias("BundleProductVO")
@JsonIgnoreProperties({
        "usyn", "delyn", "rgsnUserId", "rgsnTs", "amnnUserId", "amnnTs",
        "totalCnt", "rgsnUserName", "amnnUserName"
})
public class BundleProductVO extends SingleProductVO {

    // 묶음상품 번호 ID
    private String bunInfId;

    // 정보 순번
    private int infoSqn;

    // 대표상품 유무
    private String mainYn;


}
