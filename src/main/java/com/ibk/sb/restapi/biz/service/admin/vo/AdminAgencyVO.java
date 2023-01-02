package com.ibk.sb.restapi.biz.service.admin.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ibk.sb.restapi.app.common.vo.BaseTableVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@NoArgsConstructor
@Alias("AdminAgencyVO")
@JsonIgnoreProperties({
        "delYn", "imgUrl", "amnnUserId", "amnnUserName", "amnnTs",
        "totalCnt", "rnum"
})
public class AdminAgencyVO extends BaseTableVO {

    @ApiModelProperty(name = "agenReqId", value = "관리자 에이전시 요청 ID")
    private String agenReqId;

    @ApiModelProperty(name = "selrUsisId", value = "판매자 이용기관 ID")
    private String selrUsisId;

    @ApiModelProperty(name = "bplcNm", value = "회사명")
    private String bplcNm;

    @ApiModelProperty(name = "pcsnsttsId", value = "처리상태 ID")
    private String pcsnsttsId;

    @ApiModelProperty(name = "pcsnsttsNm", value = "처리 상태 명")
    private String pcsnsttsNm;

    @ApiModelProperty(name = "rprsntvNm", value = "회사 대표자명")
    private String rprsntvNm;

    @ApiModelProperty(name = "pcsnCon", value = "처리 내용")
    private String pcsnCon;

    @ApiModelProperty(hidden = true)
    private String loginUserId;
}