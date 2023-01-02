package com.ibk.sb.restapi.biz.service.order.repo;

import com.ibk.sb.restapi.biz.service.order.vo.OrderReturnVO;
import com.ibk.sb.restapi.biz.service.order.vo.RequestReturnVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 반품 repo
 * */
@Mapper
public interface OrderReturnRepo {

    // 주문(구매자/판매자)정보 가져오기
    public OrderReturnVO orderProductInfo(RequestReturnVO requestReturnVO);

    // 반품상세 정보
    public OrderReturnVO orderProductReturnInfo(RequestReturnVO requestReturnVO);

    // 반품목록
    public List<OrderReturnVO> orderProductReturnList(RequestReturnVO requestReturnVO);

    // 반품정보 업데이트
    public Integer updateReturnState(RequestReturnVO requestReturnVO);

    // 반품정보 이력등록
    public Integer addReturnStateHistory(RequestReturnVO requestReturnVO);

    // 반품합계
    public Integer returnListsTotal(RequestReturnVO requestReturnVO);

}
