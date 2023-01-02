package com.ibk.sb.restapi.biz.service.common.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ibk.sb.restapi.app.common.vo.BaseTableVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

/**
 * Name : 공통코드 상세 목록 VO
 */
@Getter
@Setter
@Alias("SummaryComCodeListVO")
@NoArgsConstructor
public class SummaryComCodeListVO {

    // 그룹 코드
    private String grpCdId;

    // 그룹 상세 코드
    private String comCdId;

    // 명칭
    private String comCdNm;

    // 순서
    private String lnpSqn;

}
