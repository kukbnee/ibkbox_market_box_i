package com.ibk.sb.restapi.biz.service.log.vo;

import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

import java.sql.Timestamp;

@Getter
@Setter
@Alias("UserMenuAccessLogVO")
public class UserMenuAccessLogVO {

    private Integer menuAccessLogSeq;

    private String userId;

    private String menuId;

    private String menuUrl;

    private String ipAddress;

    private String browserType;

    private String createUserId;

    private Timestamp createDateTime;
}