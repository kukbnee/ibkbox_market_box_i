package com.ibk.sb.restapi.biz.service.admin.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@NoArgsConstructor
@Alias("AdminProductRequestVO")
public class AdminProductRequestVO {

    // 판매상품 ID
    @ApiModelProperty(name = "pdfInfoId", value = "판매상품 ID")
    private String pdfInfoId;

}
