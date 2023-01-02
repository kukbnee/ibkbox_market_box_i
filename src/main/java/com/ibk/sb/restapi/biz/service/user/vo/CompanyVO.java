package com.ibk.sb.restapi.biz.service.user.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ibk.sb.restapi.app.common.vo.BaseTableVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

/**
 * Table : TB_BOX_MKT_FFPC_INF_M
 * Name : 가입사업장 주요 정보
 */
@Getter
@Setter
@NoArgsConstructor
@Alias("CompanyVO")
public class CompanyVO extends BaseTableVO {

    @ApiModelProperty(name = "이용기관 ID", example = "C9999999")
    private String utlinsttId;

    @ApiModelProperty(name = "이용기관 명", example = "테스트 기관")
    private String bplcNm;

    @ApiModelProperty(name = "이용기관 대표자 명", example = "테스터")
    private String rprsntvNm;

    @ApiModelProperty(name = "이용기관의 설립일자", example = "20220901")
    private String fondDe;

    // 이용기관의 설립연차
    @ApiModelProperty(hidden = true)
    private String yearCnt;
}
