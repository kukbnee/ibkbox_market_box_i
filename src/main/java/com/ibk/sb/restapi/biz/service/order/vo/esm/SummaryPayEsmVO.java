package com.ibk.sb.restapi.biz.service.order.vo.esm;

import com.ibk.sb.restapi.biz.service.order.vo.OrderDeliveryVO;
import com.ibk.sb.restapi.biz.service.order.vo.esm.OrderProductEsmVO;
import com.ibk.sb.restapi.biz.service.order.vo.req.OrderReqStlmVO;
import com.ibk.sb.restapi.biz.service.seller.vo.SellerInfoVO;
import lombok.*;
import org.apache.ibatis.type.Alias;

import java.util.List;

/**
 * 결제화면 견적 결과 VO
 * */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Alias("SummaryPayEsmVO")
public class SummaryPayEsmVO {

    // 합계 상품 결제 금액
    private Integer totalProductPay;

    // 합계 상품 배송비 금액
    private Integer totalProductDeliveryPay;

    // 배송 유형 ID
    private String dvryPtrnId;

    // 견적 정보 ID
    private String esttInfoId;

    // 판매자 정보
    SellerInfoVO sellerInfoVO;

    // 구매자 배송정보
    OrderDeliveryVO deliveryInfo;

    //배송정보
    OrderDeliveryEsmVO deliveryEsmInfo;

    // 결제에 사용할 주문 정보 ID 채번용 VO
    OrderReqStlmVO orderReqStlmVO;

    //상품정보
    List<OrderProductEsmVO> products;

}
