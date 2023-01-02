package com.ibk.sb.restapi.biz.service.common.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

/**
 * Name : 공통코드 그룹 VO
 */
@Getter
@Setter
@Alias("SummaryComCodeGroupVO")
@NoArgsConstructor
public class SummaryComCodeGroupVO {

    // 그룹ID
    private String grpCdId;

    // 명칭
    private String grpCdNm;

}
