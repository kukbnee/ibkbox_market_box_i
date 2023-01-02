package com.ibk.sb.restapi.biz.service.product.buyer.vo;


import com.ibk.sb.restapi.app.common.vo.PageVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

import java.util.HashMap;

@Getter
@Setter
@NoArgsConstructor
@Alias("RequestBuyerProductInfoSearchVO")
public class RequestBuyerProductInfoSearchVO extends PageVO {

    // 바이어 상품 번호 ID
    private String buyerInfId;

    // 바이어 상품 등록 이용기관 ID
    private String selrUsisId;

    // 바이어 상품 등록 사용자 ID
    private String rgsnUserId;

    private String productName;

    private HashMap<String, String> category;

    private String orderByDate;

    private String orderByPrice;

}
