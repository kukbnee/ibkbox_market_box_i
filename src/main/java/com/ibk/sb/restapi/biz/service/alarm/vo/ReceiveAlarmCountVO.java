package com.ibk.sb.restapi.biz.service.alarm.vo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

@Getter
@Setter
public class ReceiveAlarmCountVO {

    /**
     * 알림 수신 카운트 Response
     */

    // 알림중분류코드
    private String alrtMddvCd;

    // 건수
    private String cnt;

    // 알림대분류코드
    private String alrtLrdvDcd;

    public Integer getCnt() {
        return StringUtils.hasLength(cnt) ? Integer.parseInt(cnt.replaceAll("[^0-9]", "")) : 0;
    }

}
