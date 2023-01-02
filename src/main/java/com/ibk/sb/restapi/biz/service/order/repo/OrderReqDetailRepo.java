package com.ibk.sb.restapi.biz.service.order.repo;

import com.ibk.sb.restapi.biz.service.order.vo.req.OrderReqProductVO;
import com.ibk.sb.restapi.biz.service.order.vo.req.RequestSearchOrderVO;
import com.ibk.sb.restapi.biz.service.order.vo.req.SummaryOrderInfoVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderReqDetailRepo {

    // 주문상세
    public SummaryOrderInfoVO searchOrderInfo(RequestSearchOrderVO requestSearchOrderVO);
    public List<OrderReqProductVO> searchOrderPdfList(RequestSearchOrderVO requestSearchOrderVO);

}
