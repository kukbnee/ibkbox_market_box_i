package com.ibk.sb.restapi.biz.service.order.vo;

import com.ibk.sb.restapi.biz.service.order.vo.req.OrderReqStlmVO;
import com.ibk.sb.restapi.biz.service.seller.vo.SellerInfoVO;
import lombok.*;
import org.apache.ibatis.type.Alias;

import java.util.List;

/**
 * 결제화면 결과 VO
 * */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Alias("SummaryPayVO")
public class SummaryPayVO {

    // 구매자 배송정보
    OrderDeliveryVO DeliveryInfo;

    // 판매자 정보
    SellerInfoVO sellerInfoVO;

    // 합계 상품 결제 금액
    private Integer totalProductPay;

    // 합계 상품 배송비 금액
    private Integer totalProductDeliveryPay;

    // 결제에 사용할 주문 정보 ID 채번용 VO
    OrderReqStlmVO orderReqStlmVO;

    // 상품정보
    List<OrderProductVO> products;

}
