package com.ibk.sb.restapi.biz.service.pay.boxpos.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestUtlAplcInfoVo {

    // 조회조건
    private String inqCndt;

    // 조회데이터
    private String inqData;
}
