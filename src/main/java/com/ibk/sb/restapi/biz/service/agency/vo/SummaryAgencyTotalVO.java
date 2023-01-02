package com.ibk.sb.restapi.biz.service.agency.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ibk.sb.restapi.app.common.vo.BaseTableVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

/**
 * 에이전시 리스트 전체값 VO
 */
@Getter
@Setter
@NoArgsConstructor
@Alias("SummaryAgencyTotalVO")
@JsonIgnoreProperties({
        "usyn", "delyn", "rgsnUserId", "rgsnTs", "amnnUserId", "amnnTs",
        "totalCnt", "rgsnUserName", "amnnUserName"
})
public class SummaryAgencyTotalVO extends BaseTableVO {

    // 보낸요청 Total
    private Integer senAgenTotal;

    // 받은요청 Total
    private Integer recAgenTotal;

}