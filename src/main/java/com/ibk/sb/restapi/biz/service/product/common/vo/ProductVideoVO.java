package com.ibk.sb.restapi.biz.service.product.common.vo;

import com.ibk.sb.restapi.app.common.vo.BaseTableVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

/**
 * Table : TB_BOX_MKT_PDF_VID_R
 * Name : 상품 제품 영상 정보
 */
@Getter
@Setter
@NoArgsConstructor
@Alias("ProductVideoVO")
public class ProductVideoVO extends BaseTableVO {

    // 상품 정보 ID
    private String pdfInfoId;

    // 정보 순번
    private String infoSqn;

    // 파일 ID(섬네일 이미지 ID)
    private String fileId;

    //  파일 명(섬네일 이미지 명)
    private String fileNm;

    // 영상 제목
    private String pictTtl;

    // 영상 URL
    private String pictUrl;

    // 프론트 표시용 파일 타입 - video
    private String fileType = "video";
}
