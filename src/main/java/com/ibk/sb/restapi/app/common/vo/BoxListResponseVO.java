package com.ibk.sb.restapi.app.common.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BoxListResponseVO<T> {

    @JsonProperty("STATUS")
    private String STATUS;

    @JsonProperty("MESSAGE")
    private String MESSAGE;

    /**
     * 문서상 API에 따라 리스트 객체가
     * RSLT_LIST 로 오는 경우가 있고 RSLT_DATA로 오는 경우가 있음
     *
     * RSLT_LIST : 기업정보 리스트 조회
     * RSLT_DATA : 알림 수신 리스트 조회, 알림 카운트 정보 리스트 조회
     *
     * DATA : 알림 수신 유저정보 조회
     */

    @JsonProperty("RSLT_LIST")
    private List<T> RSLT_LIST;

    @JsonProperty("RSLT_DATA")
    private List<T> RSLT_DATA;

    @JsonProperty("DATA")
    private List<T> DATA;
}
