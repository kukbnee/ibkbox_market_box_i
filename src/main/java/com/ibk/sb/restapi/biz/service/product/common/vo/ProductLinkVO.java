package com.ibk.sb.restapi.biz.service.product.common.vo;

import com.ibk.sb.restapi.app.common.vo.BaseTableVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

/**
 * Table : TB_BOX_MKT_PDF_LINK_R
 * Name : 상품 제품 링크 정보
 */
@Getter
@Setter
@NoArgsConstructor
@Alias("ProductLinkVO")
public class ProductLinkVO extends BaseTableVO {

    // 상품 정보 ID
    private String pdfInfoId;

    // 정보 순번
    private String infoSqn;

    // 영상 제목
    private String linkTtl;

    // 영상 URL
    private String linkUrl;
}
