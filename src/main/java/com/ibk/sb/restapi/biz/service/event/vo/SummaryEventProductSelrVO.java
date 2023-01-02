package com.ibk.sb.restapi.biz.service.event.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ibk.sb.restapi.app.common.vo.BaseTableVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

import java.util.List;

/**
 * 등록용 이벤트상품 VO
 */
@Getter
@Setter
@Alias("SummaryEventProductSelrVO")
@JsonIgnoreProperties({
        "usyn", "delyn", "rgsnUserId", "rgsnTs", "amnnUserId", "amnnTs",
        "totalCnt", "rnum"
})
public class SummaryEventProductSelrVO extends BaseTableVO {

    @ApiModelProperty(name = "evntInfId", value = "이벤트 정보 ID")
    private String evntInfId;

    @ApiModelProperty(name = "selrUsisId", value = "판매자 이용기관(회사) ID")
    private String selrUsisId;

    @ApiModelProperty(name = "selrUserId", value = "판매자 ID")
    private String selrUserId;

    @ApiModelProperty(name = "pcsnsttsId", value = "이벤트 참여상태")
    private String pcsnsttsId;

    @ApiModelProperty(name = "items", value = "상품 정보 ID")
    private List<SummaryEventProductVO> items;
}
