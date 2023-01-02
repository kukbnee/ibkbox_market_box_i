package com.ibk.sb.restapi.biz.service.delivery.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ibk.sb.restapi.app.common.vo.BaseTableVO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

/**
 * Table : TB_BOX_MKT_PDF_DEL_L
 * Name : 상품 배송 정보
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Alias("DeliveryinfoVO")
@JsonIgnoreProperties({
        "usyn", "delyn", "rgsnUserId", "rgsnTs", "amnnUserId", "amnnTs",
        "totalCnt", "rnum"
})
public class DeliveryinfoVO extends BaseTableVO {

    // 상품 정보 ID
    private String pdfInfoId;

    // 배송 타입 ID
    private String dvryTypeId;

    // 배송 타입 명
    private String dvryTypeName;

    // 배송 유형 ID
    private String dvryPtrnId;

    // 배송 유형 명
    private String dvryPtrnName;

    // 배송비 유형 ID
    private String dvrynonePtrnId;

    // 배송비 유형 명
    private String dvrynonePtrnName;

    // 업체 정보 ID
    private String entpInfoId;

    // 업체 정보 명
    private String entpInfoName;

    // 배송 기본가
    private int dvryBscprce;
}
