package com.ibk.sb.restapi.biz.service.estimation.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ibk.sb.restapi.app.common.vo.BaseTableVO;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@Alias("EstimationVO")
@JsonIgnoreProperties({

})
public class EstimationVO extends BaseTableVO {

    // 견적 정보 ID
    private String esttInfoId;

    // 발신자 이용기관 ID
    private String dpmpUsisId;

    // 수신자 이용기관 ID
    private String rcvrUsisId;

    // 문의 정보 ID
    private String inqrInfoId;

    // 문의 정보 순번
    private Integer inqrInfoSqn;

    // 문의 견적 연관 순번
    private Integer inesSqn;

}