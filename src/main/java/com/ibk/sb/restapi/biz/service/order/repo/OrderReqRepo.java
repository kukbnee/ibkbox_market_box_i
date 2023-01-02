package com.ibk.sb.restapi.biz.service.order.repo;

import com.ibk.sb.restapi.biz.service.order.vo.req.*;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderReqRepo {

    // 주문등록

    public Integer insertOrderInfo(RequestSearchOrderVO requestSearchOrderVO);

    public Integer insertOrderPdf(OrderReqProductVO orderReqProductVO);

    public Integer insertOrderDvryR(OrderReqDeliveryVO orderReqDeliveryVO);

    public Integer insertOrderUdvrR(OrderReqDeliveryVO orderReqDeliveryVO);

    public Integer insertOrderPdvryR(OrderReqDeliveryVO orderReqDeliveryVO);

    public Integer insertOrderPdfH(OrderReqProductVO orderReqProductVO);

    public Integer updatePucsInfoForY(RequestSearchOrderVO requestSearchOrderVO); // 같은 배송지 사용 여부 update
    public Integer updatePucsInfoForN(RequestSearchOrderVO requestSearchOrderVO); // 같은 배송지 사용 여부 update

    public Integer updateEsmInfoForOrdnId(RequestSearchOrderVO requestSearchOrderVO); // 주문 정보 ID update

    public Integer updateInesR(RequestSearchOrderVO requestSearchOrderVO); // 문의 견적 연관정보 update(처리 상태 ID)

    public Integer updateInquR(RequestSearchOrderVO requestSearchOrderVO); // 문의 상세 정보 update(내용)

    public Integer updateOrderDvryForDvryInput(RequestSearchOrderVO requestSearchOrderVO); // 주문 화물서비스 배송정보 update(운송의뢰 번호, 운송장 번호)

    public SummaryOrderInfoVO searchOrderInfo(RequestSearchOrderVO requestSearchOrderVO);

    public List<OrderReqDeliveryVO> searchOrderProductDeliveryListEntp(RequestSearchOrderVO requestSearchOrderVO); // 주문의 운송업체 select(list)

    public List<OrderReqDeliveryVO> searchOrderProductDeliveryList(RequestSearchOrderVO requestSearchOrderVO); // 출고지, 수령지, 상품 select(list)

    public OrderReqDeliveryVO searchOrderProductDelivery(RequestSearchOrderVO requestSearchOrderVO); // 출고지, 수령지, 상품 select

    public List<OrderReqDeliveryVO> searchOrderProductDeliveryListForDvryCancel(RequestSearchOrderVO requestSearchOrderVO); // 운송의뢰 번호 select(list)

    public OrderReqDeliveryVO searchProductDelivery(RequestSearchOrderVO requestSearchOrderVO); // 출고지 select

    public OrderReqDeliveryVO searchEsmInfo(RequestSearchOrderVO requestSearchOrderVO);

    public List<OrderReqProductVO> searchEsmPdfInfoList(RequestSearchOrderVO requestSearchOrderVO);

    public OrderReqProductVO searchPdfInfo(@Param("pdfInfoId") String pdfInfoId); // 상품명, 상품 카테고리, 판매자 이용기관 ID 등 select

    public Integer insertOrderDchL(OrderDvryDchLVO orderDvryDchLVO); // 주문 배송 API 호출 이력 insert

}
