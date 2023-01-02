package com.ibk.sb.restapi.biz.service.alarm.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class HeaderAlarmListVO {

    // 신규 미수신건 확인
    private String unreadYn;
    // 알림 리스트
    private List<ReceiveAlarmVO> list;
}
