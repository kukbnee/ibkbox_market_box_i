package com.ibk.sb.restapi.biz.service.agency.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ibk.sb.restapi.app.common.vo.BaseTableVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

import java.sql.Timestamp;

/**
 * 에이전시 요청 사유 VO
 */
@Getter
@Setter
@NoArgsConstructor
@Alias("SummaryAgencyReasonVO")
@JsonIgnoreProperties({
        "usyn", "delyn", "rgsnUserId", "rgsnTs", "amnnUserId", "amnnTs",
        "totalCnt", "rgsnUserName", "amnnUserName"
})
public class SummaryAgencyReasonVO extends BaseTableVO {

    // 상품 에이전시요청 ID
    private String agenInfId;

    // 요청상품ID
    private String pdfInfoId;

    // 처리상태 ID
    private String pcsnsttsId;

    // 처리 내용
    private String pcsnCon;

    // 처리일
    private Timestamp reasonDate;

}
