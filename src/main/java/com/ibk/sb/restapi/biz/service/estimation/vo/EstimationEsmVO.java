package com.ibk.sb.restapi.biz.service.estimation.vo;

import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

/**
 * 상품별 배송비 VO
 */
@Getter
@Setter
@Alias("EstimationEsmVO")
public class EstimationEsmVO {

    // 상품 정보 ID
    private String pdfInfoId;

    // 순번
    private String seqNum;

    // 배송비
    private Integer dvrynone;

}
