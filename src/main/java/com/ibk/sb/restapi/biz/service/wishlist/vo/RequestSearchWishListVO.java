package com.ibk.sb.restapi.biz.service.wishlist.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ibk.sb.restapi.app.common.vo.BaseTableVO;
import com.ibk.sb.restapi.app.common.vo.PageVO;
import com.ibk.sb.restapi.app.common.vo.PagingVO;
import com.ibk.sb.restapi.biz.service.product.single.vo.SingleProductVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

/**
 * Name : 상품 위시리스트 정보
 * Table : TB_BOX_MKT_PDF_WISH_R
 */
@Getter
@Setter
@Alias("RequestSearchWishListVO")
@JsonIgnoreProperties({
        "useYn", "rgsnTs", "rgsnUserId", "amnnTs", "amnnUserId"
})
public class RequestSearchWishListVO extends PageVO {

    // 구매자 이용기관(회사) ID
    @ApiModelProperty(name = "loginUtlinsttId", value = "구매자 이용기관(회사) ID")
    private String loginUtlinsttId;

    // 구매자 ID
    @ApiModelProperty(name = "loginUserId", value = "구매자 ID")
    private String loginUserId;

    // 상품 정보 ID
    @ApiModelProperty(name = "pdfInfoId", value = "상품 정보 ID")
    private String pdfInfoId;

    // 상품 판매상태
    @ApiModelProperty(name = "pdfSttsId", value = "상품 판매상태")
    private String pdfSttsId;

    // 상품 파일유형
    @ApiModelProperty(name = "filePtrnId", value = "상품 파일유형")
    private String filePtrnId;

    // 상품 진행여부
    @ApiModelProperty(name = "pdfPgrsYn", value = "상품 진행여부")
    private String pdfPgrsYn;

}
