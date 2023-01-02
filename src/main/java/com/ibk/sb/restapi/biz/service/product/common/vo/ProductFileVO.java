package com.ibk.sb.restapi.biz.service.product.common.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ibk.sb.restapi.app.common.vo.BaseTableVO;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

/**
 * Table : TB_BOX_MKT_PDF_FIE_R
 * Name : 상품 이미지 파일 정보
 */
@Getter
@Setter
@Alias("ProductFileVO")
@JsonIgnoreProperties({
        "usyn", "delyn", "rgsnUserId", "rgsnTs", "amnnUserId", "amnnTs",
        "totalCnt", "rnum"
})
public class ProductFileVO extends BaseTableVO {

    // 상품 정보 ID
    private String pdfInfoId;

    // 파일 ID
    private String fileId;

    // 정보 순번
    private String infoSqn;

    // 파일 유형 ID
    private String filePtrnId;

    // 프론트 표시용 파일 타입 - img
    private String fileType = "img";
}
