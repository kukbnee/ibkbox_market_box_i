package com.ibk.sb.restapi.biz.service.pay.boxpos.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@Alias("LnkStlmCnclVO")
@NoArgsConstructor
@AllArgsConstructor
public class LnkStlmCnclVO {

    private String lnkStlmSrn;          // 연계결제일련번호
    private String type;                // 요청자 유형(B: 구매자, F: 가맹점)
    private String deviceType;          // 기기 유형(PC: PC, M: 모바일)
}
