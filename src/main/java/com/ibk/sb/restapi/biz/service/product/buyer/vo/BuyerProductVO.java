package com.ibk.sb.restapi.biz.service.product.buyer.vo;

import com.ibk.sb.restapi.biz.service.product.single.vo.SingleProductVO;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

/**
 * Table : TB_BOX_MKT_BUYER_PDF_L
 * Name : 바이어 상품 목록 정보
 */
@Getter
@Setter
@Alias("BuyerProductVO")
public class BuyerProductVO extends SingleProductVO {

    // 바이어 상품 번호 ID
    private String buyerInfId;

    // 정보 순번
    private String infoSqn;
}
