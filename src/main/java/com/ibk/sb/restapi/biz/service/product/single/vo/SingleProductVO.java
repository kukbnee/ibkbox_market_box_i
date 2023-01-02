package com.ibk.sb.restapi.biz.service.product.single.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ibk.sb.restapi.app.common.vo.BaseTableVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@Alias("SingleProductVO")
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({
        "usyn", "delyn", "rgsnUserId", "rgsnTs", "amnnUserId", "amnnTs",
        "totalCnt", "rnum"
})
public class SingleProductVO extends BaseTableVO {

    // 상품 정보 ID
    @ApiModelProperty(name = "pdfInfoId", value = "상품 정보 ID")
    private String pdfInfoId;

    // 판매자 이용기관 ID
    @ApiModelProperty(name = "selrUsisId", value = "판매자 이용기관 ID")
    private String selrUsisId;

    // 상품 카테고리 ID
    @ApiModelProperty(name = "pdfCtgyId", value = "상품 카테고리 ID")
    private String pdfCtgyId;

    // 상품 진행 여부
    @ApiModelProperty(name = "pdfPgrsYn", value = "상품 진행 여부")
    private String pdfPgrsYn;

    // 상품 상태 ID
    @ApiModelProperty(name = "pdfSttsId", value = "상품 상태 ID")
    private String pdfSttsId;

    // 상품명
    @ApiModelProperty(name = "pdfNm", value = "상품명")
    private String pdfNm;

    // 모델명
    @ApiModelProperty(name = "mdlnm", value = "모델명")
    private String mdlnm;

    // 요약 설명
    @ApiModelProperty(name = "brfDesc", value = "요약 설명")
    private String brfDesc;

    // 간략 설명
    @ApiModelProperty(name = "brfSubDesc", value = "간략 설명")
    private String brfSubDesc;

    // 상세 설명
    @ApiModelProperty(name = "dtlDesc", value = "상세 설명")
    private String dtlDesc;

    // 특허 정보 여부
    @ApiModelProperty(name = "ptntInfoYn", value = "특허 정보 여부")
    private String ptntInfoYn;

    // 에이전시 접수 정보 ID
    @ApiModelProperty(name = "agenInfId", value = "에이전시 접수 정보 ID")
    private String agenInfId;
}
