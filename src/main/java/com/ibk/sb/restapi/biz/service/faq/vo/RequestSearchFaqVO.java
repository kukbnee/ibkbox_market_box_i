package com.ibk.sb.restapi.biz.service.faq.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ibk.sb.restapi.app.common.vo.PageVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

/**
 * 검색용 VO
 */
@Getter
@Setter
@NoArgsConstructor
@Alias("RequestSearchFaqVO")
@JsonIgnoreProperties({
        "usyn", "delyn", "rgsnUserId", "rgsnTs", "amnnUserId", "amnnTs",
        "totalCnt", "rnum"
})
public class RequestSearchFaqVO extends PageVO {

    // faq 번호
    @ApiModelProperty(name = "faqInfId", value = "FAQ ID")
    private String faqInfId;

    // 제목
    @ApiModelProperty(name = "ttl", value = "제목")
    private String ttl;

    // 내용
    @ApiModelProperty(name = "con", value = "내용")
    private String con;

}
