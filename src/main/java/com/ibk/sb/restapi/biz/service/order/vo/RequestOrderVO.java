package com.ibk.sb.restapi.biz.service.order.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

/**
 * 주문목록 요청 VO
 * */
@Getter
@Setter
@NoArgsConstructor
@Alias("RequestOrderVO")
public class RequestOrderVO {

    // 로그인 이용기관 ID
    private String loginUsisId;

    // 로그인 사용자 ID
    private String loginUserId;

    // 발신자 이용기관 ID
    private String dpmpUsisId;

    // 발신자 사용자 ID
    private String dpmpUserId;

    // 수신자 이용기관 ID
    private String rcvrUsisId;

    // 수신자 사용자 ID
    private String rcvrUserId;

    // 등록 사용자 ID
    private String rgsnUserId;

}
