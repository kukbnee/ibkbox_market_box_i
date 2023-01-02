package com.ibk.sb.restapi.biz.service.mainbox.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlarmTargetVO {

    public AlarmTargetVO() {}

    public AlarmTargetVO(String usisId, String idmbId) {
        this.usisId = usisId;
        this.idmbId = idmbId;
    }

    // 이용기관 아이디
    private String usisId;

    // 개인회원 아이디
    private String idmbId;
}
