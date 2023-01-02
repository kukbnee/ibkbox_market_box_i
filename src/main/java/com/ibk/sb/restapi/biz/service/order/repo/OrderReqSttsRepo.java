package com.ibk.sb.restapi.biz.service.order.repo;

import com.ibk.sb.restapi.biz.service.order.vo.req.OrderReqProductVO;
import com.ibk.sb.restapi.biz.service.order.vo.req.RequestSearchOrderVO;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderReqSttsRepo {

    // 수령확인

    public OrderReqProductVO searchOrderPdf(RequestSearchOrderVO requestSearchOrderVO);

    public Integer updateOrderPdfStts(RequestSearchOrderVO requestSearchOrderVO);

    public Integer insertOrderPdfH(RequestSearchOrderVO requestSearchOrderVO); // 주문상품정보 이력 insert

    public Integer updateOrderPDvry(RequestSearchOrderVO requestSearchOrderVO); // 택배사, 운송장번호 update

    public Integer insertOrderDvryForDvryInput(RequestSearchOrderVO requestSearchOrderVO); // 주문 화물서비스 배송정보 insert(판매자 > 배송정보입력 > 화물서비스 > 배송요청)

    public Integer deleteOrderPDvryForDvryInput(RequestSearchOrderVO requestSearchOrderVO); // 주문 구매자 배송정보 delete(판매자 > 배송정보입력 > 화물서비스 > 배송요청)

    public Integer updateOrderPdfForDvryInput(RequestSearchOrderVO requestSearchOrderVO); // 주문상품정보 배송유형 update(판매자 > 배송정보입력 > 화물서비스 > 배송요청)
}
