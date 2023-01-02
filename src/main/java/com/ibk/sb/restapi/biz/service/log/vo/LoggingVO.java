package com.ibk.sb.restapi.biz.service.log.vo;

import com.ibk.sb.restapi.app.common.constant.BatchStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class LoggingVO {

    private String id;
    private String serviceName;
    private String methodName;
    private BatchStatus status;
    private String description;
    private String createUserId;
    private String createDate;
    private String updateUserId;
    private String updateDate;

}
