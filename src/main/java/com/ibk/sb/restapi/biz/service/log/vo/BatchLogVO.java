package com.ibk.sb.restapi.biz.service.log.vo;

import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

import java.sql.Timestamp;

@Getter
@Setter
@Alias("BatchLogVO")
public class BatchLogVO {

    private int batchLogSeq;

    private String batchExecScheduler;

    private String batchExecMethod;

    private String batchStatus;

    private String batchResultDesc;

    private Timestamp createDateTime;

    private Timestamp modifyDateTime;
}
