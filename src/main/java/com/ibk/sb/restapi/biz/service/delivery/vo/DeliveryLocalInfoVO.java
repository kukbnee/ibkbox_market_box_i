package com.ibk.sb.restapi.biz.service.delivery.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ibk.sb.restapi.app.common.vo.BaseTableVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

/**
 * Table : TB_BOX_MKT_PEAR_DVRY_M
 * Name : 상품 지역별 배송 정보
 */
@Getter
@Setter
@NoArgsConstructor
@Alias("DeliveryLocalInfoVO")
@JsonIgnoreProperties({
        "usyn", "delyn", "rgsnUserId", "rgsnTs", "amnnUserId", "amnnTs"
})
public class DeliveryLocalInfoVO extends BaseTableVO {

    // 상품 정보 ID
    private String pdfInfoId;

    // 정보 순번
    private String infoSqn;

    // 시도
    private String trl;

    // 시군군
    private String ctcocrwd;

    // 금액
    private String amt;

    // 공통 가격 단위 ID
    private String comPrcUtId;

    // 공통 가격 단위 명
    private String comPrcUtName;
}
