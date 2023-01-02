package com.ibk.sb.restapi.biz.service.product.mypage.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

/**
 * 상품관리 헤더 VO
 */
@Getter
@Setter
@NoArgsConstructor
@Alias("MyProductHeaderVO")
public class MyProductHeaderVO {

    // 개별 상품 개수
    private int singleProductCnt;

    // 묶음 상품 개수
    private int bundleProductCnt;

    // 바이어 상품 개수
    private int buyerProductCnt;
}
