package com.ibk.sb.restapi.biz.service.order.repo;

import com.ibk.sb.restapi.biz.service.order.vo.req.OrderReqStlmVO;
import com.ibk.sb.restapi.biz.service.order.vo.req.RequestSearchOrdnStlmVO;
import com.ibk.sb.restapi.biz.service.order.vo.req.SummaryOrdnStlmVO;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderReqStlmRepo {

    // 결제 정보 등록
    public Integer insertPdfPayL(OrderReqStlmVO orderReqStlmVO);

    // 결제 정보 수정(결제상태 ID)
    public Integer updatePdfPayL(OrderReqStlmVO orderReqStlmVO);

    // 결제 정보 수정(결제상태 ID)
    public Integer updatePdfPayLForPayCancel(OrderReqStlmVO orderReqStlmVO);

    // 결제 정보 이력 등록
    public Integer insertPdfPayH(OrderReqStlmVO orderReqStlmVO);

    // 주문 정보 조회
    public SummaryOrdnStlmVO searchOrderInfo(OrderReqStlmVO orderReqStlmVO);

    // 주문 정보 수정(결제 정보 ID)
    public Integer updateOrderInfoForStlm(OrderReqStlmVO orderReqStlmVO);

    // 주문 상품 정보의 주문 상태 ID 수정
    public Integer updateOrderPdfSttsForStlm(OrderReqStlmVO orderReqStlmVO);

    // 주문 상품 정보의 이력 등록
    public Integer insertOrderPdfHForStlm(OrderReqStlmVO orderReqStlmVO);

}
