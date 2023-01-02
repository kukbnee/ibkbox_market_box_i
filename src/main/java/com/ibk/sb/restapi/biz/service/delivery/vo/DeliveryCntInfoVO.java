package com.ibk.sb.restapi.biz.service.delivery.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ibk.sb.restapi.app.common.vo.BaseTableVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

/**
 * Table : TB_BOX_MKT_QTY_DVRY_M
 * Name : 상품 수량병 배송 정보
 */
@Getter
@Setter
@NoArgsConstructor
@Alias("DeliveryCntInfoVO")
@JsonIgnoreProperties({
        "usyn", "delyn", "rgsnUserId", "rgsnTs", "amnnUserId", "amnnTs",
        "totalCnt", "rgsnUserName", "amnnUserName"
})
public class DeliveryCntInfoVO extends BaseTableVO {

    // 상품 정보 ID
    private String pdfInfoId;

    // 정보 순번
    private String infoSqn;

    // 수량
    private String qty;

    // 공통 상품 단위 ID
    private String comPdfUtId;

    // 공통 상품 단위 명
    private String comPdfUtName;

    // 금액
    private String amt;

    // 공통 가격 단위 ID
    private String comPrcUtId;

    // 공통 가격 단위 명
    private String comPrcUtName;
}
