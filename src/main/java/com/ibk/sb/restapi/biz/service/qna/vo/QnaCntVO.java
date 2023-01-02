package com.ibk.sb.restapi.biz.service.qna.vo;

import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

/**
 * 문의 건수 VO
 */
@Getter
@Setter
@Alias("QnaCntVO")
public class QnaCntVO {

    // 보낸 문의 건수
    private Integer dpmpSumCnt;

    // 받은 문의 건수
    private Integer rcvrSumCnt;

}
