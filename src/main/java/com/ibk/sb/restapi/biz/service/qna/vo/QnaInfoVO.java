package com.ibk.sb.restapi.biz.service.qna.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ibk.sb.restapi.app.common.vo.BaseTableVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@Alias("QnaInfoVO")
@JsonIgnoreProperties({
        "usyn", "delyn", "rgsnUserId", "rgsnTs", "amnnUserId", "amnnTs"
})
public class QnaInfoVO extends BaseTableVO {

    // 문의 정보 ID
    @ApiModelProperty(name = "inqrInfoId", value = "문의 정보 ID")
    private String inqrInfoId;

    // 상품 정보 ID
    @ApiModelProperty(name = "pdfInfoId", value = "상품 정보 ID")
    private String pdfInfoId;

    // 문의자 이용기관
    @ApiModelProperty(name = "inquUsisId", value = "문의자 이용기관")
    private String inquUsisId;

    // 문의자 ID
    @ApiModelProperty(name = "inquUserId", value = "문의자 ID")
    private String inquUserId;
}
