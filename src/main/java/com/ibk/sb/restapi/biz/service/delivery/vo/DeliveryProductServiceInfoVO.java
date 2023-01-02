package com.ibk.sb.restapi.biz.service.delivery.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ibk.sb.restapi.app.common.vo.BaseTableVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

/**
 * Table : TB_BOX_MKT_DVRY_ESTT_M
 * Name : 상품 화물서비스 견적 정보
 */
@Getter
@Setter
@NoArgsConstructor
@Alias("DeliveryProductServiceInfoVO")
@JsonIgnoreProperties({
        "usyn", "delyn", "rgsnUserId", "rgsnTs", "amnnUserId", "amnnTs"
})
public class DeliveryProductServiceInfoVO extends BaseTableVO {

    // 상품 정보 ID
    private String pdfInfoId;

    // 정보 순번
    private String infoSqn;

    // 수량
    private Integer qty;

    // 공통 상품 단위 ID
    private String comPdfUtId;

    // 겅텅 싱픔 단위 명
    private String comPdfUtName;

    // 견적 금액
    private Integer esttAmt;

    // 공통 가격 단위 ID
    private String comPrcUtId;

    // 공통 가격 단위 명
    private String comPrcUtName;

    // 업체 정보 ID(화물서비스 업체ID)
    private String entpInfoId;

    // 업체 정보 명
    private String entpInfoName;
}
