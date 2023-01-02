package com.ibk.sb.restapi.biz.service.order.repo;

import com.ibk.sb.restapi.biz.service.order.vo.RequestPayVO;
import com.ibk.sb.restapi.biz.service.order.vo.esm.OrderDeliveryEsmVO;
import com.ibk.sb.restapi.biz.service.order.vo.esm.OrderProductEsmVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 견적서에서 주문으로 연결되는 repo
 * */
@Mapper
public interface OrderEsttRepo {

    // 견적정보 정상유무 확인
    public Integer orderCheckEsmInfo(RequestPayVO requestPayVO);

    // 견적 상품 정보 가져오기
    public List<OrderProductEsmVO> getProductEsmPdfList(RequestPayVO requestPayVO);

    // 견적 배송 정보 가져오기
    public OrderDeliveryEsmVO getProductEsmDelivery(RequestPayVO requestPayVO);

}
