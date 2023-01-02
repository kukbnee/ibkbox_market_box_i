package com.ibk.sb.restapi.biz.service.product.bundle.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ibk.sb.restapi.app.common.vo.BaseTableVO;
import com.ibk.sb.restapi.biz.service.file.vo.FileInfoVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

import java.util.List;

/**
 * Name : 상품 묶음상품 정보
 * Table : TB_BOX_MKT_PDF_BUN_M
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Alias("BundleProductInfoVO")
@JsonIgnoreProperties({
        "httpIp", "httpInfo", "rgsnTs", "rgsnUserId", "amnnTs", "amnnUserId"
})
public class BundleProductInfoVO extends BaseTableVO {

    // 묶음상품 번호 ID
    @ApiModelProperty(name = "bunInfId", value = "묶음상품 번호 ID")
    private String bunInfId;

    // 판매자 이용기관 ID
    @ApiModelProperty(name = "selrUsisId", value = "판매자 이용기관 ID")
    private String selrUsisId;

    // 묶음상품 파일 ID
    @ApiModelProperty(name = "fileId", value = "묶음상품 파일 ID")
    private String fileId;

    // 상품명
    @ApiModelProperty(name = "pdfNm", value = "상품명")
    private String pdfNm;

    // 상품 내용
    @ApiModelProperty(name = "pdfCon", value = "상품 내용")
    private String pdfCon;

    // 메인 노출 여부
    @ApiModelProperty(name = "mainYn", value = "메인 노출 여부")
    private String mainYn;

    /*
     * Main Box 연동정보
     */
    // 이용기관(회사) 명
    @ApiModelProperty(name = "selrUsisName", value = "이용기관(회사) 명")
    private String selrUsisName;

    // 묶음상품 파일 명
    @ApiModelProperty(name = "fileNm", value = "묶음상품 파일 명")
    private String fileNm;
}
