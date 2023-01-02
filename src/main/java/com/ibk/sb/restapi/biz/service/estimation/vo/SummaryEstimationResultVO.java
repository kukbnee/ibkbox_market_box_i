package com.ibk.sb.restapi.biz.service.estimation.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

/**
 * 견적 결과 VO
 */
@Getter
@Setter
@Alias("SummaryEstimationResultVO")
@JsonIgnoreProperties({

})
public class SummaryEstimationResultVO {

    //////
    // 결과
    //////

    // 결과 문자열
    private String resultStr;
}
