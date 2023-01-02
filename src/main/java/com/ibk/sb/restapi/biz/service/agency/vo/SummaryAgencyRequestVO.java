package com.ibk.sb.restapi.biz.service.agency.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ibk.sb.restapi.app.common.vo.BaseTableVO;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

import java.sql.Timestamp;

/**
*   에이전시 요청 상태정보 VO
* */
@Getter
@Setter
@Alias("SummaryAgencyRequestVO")
@JsonIgnoreProperties({
        "usyn", "delyn", "rgsnUserId", "rgsnTs", "amnnUserId", "amnnTs",
        "totalCnt", "rnum"
})
public class SummaryAgencyRequestVO extends BaseTableVO {

    // 에이전시 ID
    private String agenReqId;

    // 내용
    private String pcsnCon;

    // 상태
    private String pcsnsttsId;

    // 요청일
    private Timestamp reqDate;

    // 상태에 대한 처리일
    private Timestamp stateDate;

}
