package com.ibk.sb.restapi.biz.service.product.common.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ibk.sb.restapi.app.common.vo.BaseTableVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

/**
 * Table : TB_BOX_MKT_PDF_FIE_R
 * Name : 상품 조회 이력
 */
@Getter
@Setter
@Alias("ProductSearchHistoryVO")
@JsonIgnoreProperties({
        "usyn", "delyn", "rgsnUserId", "rgsnTs", "amnnUserId", "amnnTs",
        "totalCnt", "rnum"
})
public class ProductSearchHistoryVO extends BaseTableVO {

    // 상품 정보 ID
    @ApiModelProperty(name = "pdfInfoId", value = "상품 정보 ID")
    private String pdfInfoId;

    // 조회 이용기관 ID
    @ApiModelProperty(name = "inqUsisId", value = "조회 이용기관 ID")
    private String inqUsisId;

    // 조회 사용자 ID
    @ApiModelProperty(name = "inqUserId", value = "조회 사용자 ID")
    private String inqUserId;

    // 조회 일시
    @ApiModelProperty(name = "inqTs", value = "조회 일시")
    private String inqTs;

    // 이력 유형 ID
    @ApiModelProperty(name = "prhsPtrnId", value = "이력 유형 ID")
    private String prhsPtrnId;
}
