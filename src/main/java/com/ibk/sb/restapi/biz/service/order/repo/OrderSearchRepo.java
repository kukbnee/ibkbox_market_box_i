package com.ibk.sb.restapi.biz.service.order.repo;

import com.ibk.sb.restapi.biz.service.order.vo.common.OrderPdfDtlVO;
import com.ibk.sb.restapi.biz.service.product.common.vo.RequestSearchProductVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderSearchRepo {

    public OrderPdfDtlVO searchOrdnPdfDtl(RequestSearchProductVO requestSearchProductVO); // 주문 상품 조회

}
