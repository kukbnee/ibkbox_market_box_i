package com.ibk.sb.restapi.biz.service.order.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

import java.util.List;

/**
 * 상품정보 VO
 * */
@Getter
@Setter
@NoArgsConstructor
@Alias("OrderProductVO")
public class OrderProductVO {

    // 구매가능 여부, 상품결제시 결제가능한 상품에 대한 상태
    private String payUsed;

    // 상품 결제 금액
    private Integer productPay;

    // 상품 배송비 금액
    private Integer productDeliveryPay;

    // 상품 구매 수량
    private Integer productQty;

    // 상품정보
    OrderProductInfMVO productInfo;

    // 상품 배송정보
    OrderProductDelLVO productDelInfo;

    // 판매정보
    OrderProductSalLVO productSalInfo;

    // 파일정보
    OrderProductFieRVO productFieInfo;

    // 수량별 배송정보
    List<OrderProductQtyDvryMVO> productQtyDvryInfo;

    // 지역별 배송정보
    List<OrderProductPearDvryMVO> productPearDvryInfo;

    // 화물서비스 기본정보
    OrderProductDvryMVO productDvryInfo;

    // 화물서비스 견적정보
    List<OrderProductDvryEsttMVO> productEsttInfo;

}
