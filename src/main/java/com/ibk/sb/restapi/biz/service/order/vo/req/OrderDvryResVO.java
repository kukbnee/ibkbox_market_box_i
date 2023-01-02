package com.ibk.sb.restapi.biz.service.order.vo.req;

import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

import java.util.List;
import java.util.Map;

/**
 * 운송의뢰 결과 전달용 VO(운송의뢰번호, 송장번호)
 */
@Getter
@Setter
@Alias("OrderDvryResVO")
public class OrderDvryResVO {

    // 운송의뢰번호
    private String reqnumNo;

    // 송장번호 리스트
    List<Map<String, String>> mainnbNoList;

    // 에러 메시지
    private String errMsg;

}
