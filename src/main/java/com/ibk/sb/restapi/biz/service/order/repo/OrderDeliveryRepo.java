package com.ibk.sb.restapi.biz.service.order.repo;

import com.ibk.sb.restapi.biz.service.order.vo.OrderDeliveryVO;
import com.ibk.sb.restapi.biz.service.order.vo.RequestPayVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 주문 회원정보 repo
 * */
@Mapper
public interface OrderDeliveryRepo {

    // 구매자 배송정보 가져오기
    public OrderDeliveryVO getDeliveryInfo(RequestPayVO requestPayVO);
    
}
