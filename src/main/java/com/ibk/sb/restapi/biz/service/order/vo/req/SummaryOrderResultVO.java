package com.ibk.sb.restapi.biz.service.order.vo.req;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

/**
 * 주문 결과 VO
 */
@Getter
@Setter
@Alias("SummaryOrderResultVO")
@JsonIgnoreProperties({

})
public class SummaryOrderResultVO {

    //////
    // 결과
    //////
    private String resultStr;

}
