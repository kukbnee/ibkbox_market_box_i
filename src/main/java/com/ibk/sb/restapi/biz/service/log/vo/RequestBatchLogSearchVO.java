package com.ibk.sb.restapi.biz.service.log.vo;

import com.ibk.sb.restapi.app.common.vo.PageVO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestBatchLogSearchVO extends PageVO {

    private String batchStatus;
}
