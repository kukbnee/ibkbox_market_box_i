package com.ibk.sb.restapi.biz.service.event.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ibk.sb.restapi.app.common.vo.BaseTableVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;


/**
 * 이벤트 상태별 total값 VO
 */
@Getter
@Setter
@NoArgsConstructor
@Alias("SummaryEventTotalVO")
@JsonIgnoreProperties({
        "usyn", "delyn", "rgsnUserId", "rgsnTs", "amnnUserId", "amnnTs",
        "totalCnt", "rnum"
})
public class SummaryEventTotalVO extends BaseTableVO {

    // 전체
    private Integer eventTotalCnt;

    // 진행중
    private Integer eventIngCnt;

    // 준비중
    private Integer eventReadyCnt;

    // 마감
    private Integer eventEndCnt;

}
