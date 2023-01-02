package com.ibk.sb.restapi.biz.service.patent.kipris.vo;

import com.ibk.sb.restapi.app.common.vo.PageVO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestSearchKiprisVO extends PageVO {

    private String applicantNumber;

}
