package com.ibk.sb.restapi.biz.service.product.common.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ibk.sb.restapi.app.common.vo.BaseTableVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@Alias("ProductKeywordVO")
@JsonIgnoreProperties({
        "usyn", "delyn", "rgsnUserId", "rgsnTs", "amnnUserId", "amnnTs",
        "totalCnt", "rnum"
})
public class ProductKeywordVO extends BaseTableVO {

    // 상품 정보 ID
    private String pdfInfoId;

    // 정보 순번
    private String infoSqn;

    // 키워드
    private String kwr;
}
