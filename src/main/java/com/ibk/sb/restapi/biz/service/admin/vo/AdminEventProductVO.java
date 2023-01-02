package com.ibk.sb.restapi.biz.service.admin.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ibk.sb.restapi.app.common.vo.BaseTableVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@NoArgsConstructor
@Alias("AdminEventProductVO")
@JsonIgnoreProperties({
        "delYn", "imgUrl", "rgsnTs", "rgsnUserId", "rgsnUserName", "amnnUserId", "amnnUserName", "amnnTs",
        "totalCnt", "rnum", "rvsRnum", "usyn", "delyn", "imgFileId"
})
public class AdminEventProductVO extends BaseTableVO {

    @ApiModelProperty(name = "evntInfId", value = "이벤트 정보 ID", example = "35b6229d-73d7-4bb3-9f4f-ddd2b095fb79", required = true)
    private String evntInfId;

    @ApiModelProperty(name = "pdfInfoId", value = "상품 ID", example = "6924de70-c5dd-4490-8b7f-88ad3d27b7b6", required = true)
    private String pdfInfoId;

    @ApiModelProperty(name = "pcsnsttsId", value = "처리상태 ID", hidden = true)
    private String pcsnsttsId;

    @ApiModelProperty(name = "expuSqc", value = "노출 순서", hidden = true)
    private Integer expuSqc;

    @ApiModelProperty(name = "rcipptrnId", value = "접수유형 ID", hidden = true)
    private String rcipptrnId;

    @ApiModelProperty(hidden = true)
    private int maxInfoSqn;
    @ApiModelProperty(hidden = true)
    private int maxExpuSqc;

}
