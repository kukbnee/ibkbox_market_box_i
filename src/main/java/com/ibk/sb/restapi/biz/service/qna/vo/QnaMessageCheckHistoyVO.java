package com.ibk.sb.restapi.biz.service.qna.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ibk.sb.restapi.app.common.vo.BaseTableVO;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@Alias("QnaMessageCheckHistoyVO")
@JsonIgnoreProperties({
        "usyn", "delyn", "rgsnUserId", "rgsnTs", "amnnUserId", "amnnTs"
})
public class QnaMessageCheckHistoyVO extends BaseTableVO {

    // 문의 정보 ID
    private String inqrInfoId;

    // 정보 순번
    private int infoSqn;

    // 확인 여부
    private String cnfaYn;

}
