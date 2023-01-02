package com.ibk.sb.restapi.biz.service.review.vo;

import com.ibk.sb.restapi.app.common.vo.PageVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@Alias("RequestSearchReviewVO")
public class RequestSearchReviewVO extends PageVO {

    // 구매 후기 ID
    @ApiModelProperty(name = "revInfId", value = "구매 후기 ID")
    private String revInfId;

    // 상품 정보 ID
    @ApiModelProperty(name = "pdfInfoId", value = "상품 정보 ID")
    private String pdfInfoId;
}
