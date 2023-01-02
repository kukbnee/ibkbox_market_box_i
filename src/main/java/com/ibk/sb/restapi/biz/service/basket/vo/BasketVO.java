package com.ibk.sb.restapi.biz.service.basket.vo;

import com.ibk.sb.restapi.app.common.vo.BaseTableVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@NoArgsConstructor
@Alias("BasketVO")
public class BasketVO extends BaseTableVO {

    // 구매자 이용기관 ID
    @ApiModelProperty(name = "pucsUsisId", value = "구매자 이용기관 ID")
    private String pucsUsisId;

    // 구매자 ID
    @ApiModelProperty(name = "pucsId", value = "구매자 ID")
    private String pucsId;

    // 상품 정보 ID
    @ApiModelProperty(name = "pdfInfoId", value = "상품 정보 ID")
    private String pdfInfoId;

    // 상품 수
    @ApiModelProperty(name = "pdfCnt", value = "상품 수")
    private String pdfCnt;

    // 단위 ID
    @ApiModelProperty(name = "utId", value = "단위 ID")
    private String utId;
}
