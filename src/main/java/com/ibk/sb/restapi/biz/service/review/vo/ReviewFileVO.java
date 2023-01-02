package com.ibk.sb.restapi.biz.service.review.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ibk.sb.restapi.app.common.vo.BaseTableVO;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

/**
 * Name : 상품 구매후기 파일 정보
 * Table : TB_BOX_MKT_REV_FIE_L
 */
@Getter
@Setter
@Alias("ReviewFileVO")
@JsonIgnoreProperties({
        "usyn", "delyn", "rgsnUserId", "rgsnTs", "amnnUserId", "amnnTs",
        "totalCnt", "rnum"
})
public class ReviewFileVO extends BaseTableVO {

    // 구매 후기 ID
    private String revInfId;

    // 파일 ID
    private String fileId;

    // 정보 순번
    private String infoSqn;
}
