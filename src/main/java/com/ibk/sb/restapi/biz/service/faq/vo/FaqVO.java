package com.ibk.sb.restapi.biz.service.faq.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ibk.sb.restapi.app.common.vo.BaseTableVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

import java.sql.Timestamp;

/**
 * Name : 상품 위시리스트 정보
 * Table : TB_BOX_MKT_GDS_WISH_INF_R
 */
@Getter
@Setter
@Alias("FaqVO")
@JsonIgnoreProperties({
        "usyn", "delyn", "rgsnUserId", "rgsnTs", "amnnUserId", "amnnTs",
        "totalCnt", "rnum","rgsnUserName", "amnnUserName","rvsRnum"
})
public class FaqVO extends BaseTableVO {

    // FAQ 정보 ID
    @ApiModelProperty(name = "faqInfId", value = "FAQ 정보 ID")
    private String faqInfId;

    // 정보 순번
    @ApiModelProperty(name = "infoSqn", value = "정보 순번")
    private Integer infoSqn;

    // 제목
    @ApiModelProperty(name = "ttl", value = "제목")
    private String ttl;

    // 내용
    @ApiModelProperty(name = "con", value = "내용")
    private String con;

}
