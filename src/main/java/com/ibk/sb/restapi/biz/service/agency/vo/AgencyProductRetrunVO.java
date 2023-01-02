package com.ibk.sb.restapi.biz.service.agency.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

import java.sql.Timestamp;

/**
 * 상품 반품/교환 정보 VO
 ***/
@Getter
@Setter
@NoArgsConstructor
@Alias("AgencyProductRetrunVO")
public class AgencyProductRetrunVO {

    // 상품ID
    private String pdfInfoId;

    // 반품교환기간
    private String rtgdInrcTrm;

    // 반품비용
    private String rtgdExp;

    // 반품교환절차
    private String rtgdInrcPrcd;

    // 반품교환불가
    private String rtgdInrcDsln;

    // 등록 사용자ID
    private String rgsnUserId;

}
