package com.ibk.sb.restapi.biz.service.event.vo.mypage;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ibk.sb.restapi.app.common.vo.BaseTableVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

/**
 * 마이페이지 > 기업 참여 이벤트 리스트 조회용 이벤트 VO
 */
@Getter
@Setter
@NoArgsConstructor
@Alias("SummaryEventMyParticiVO")
@JsonIgnoreProperties({
        "usyn", "delyn", "rgsnUserId", "rgsnTs", "amnnUserId", "amnnTs",
        "totalCnt", "rnum"
})
public class SummaryEventMyParticiVO extends BaseTableVO {

    // 이벤트 ID
    private String evntInfId;

    // 참여한 이벤트 상태
    private String itemDetail;

    // 종합상태 코드
    private String stateCode;

}
