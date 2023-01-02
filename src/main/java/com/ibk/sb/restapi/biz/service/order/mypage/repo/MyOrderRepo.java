package com.ibk.sb.restapi.biz.service.order.mypage.repo;

import com.ibk.sb.restapi.biz.service.order.mypage.vo.RequestSearchMyOrderVO;
import com.ibk.sb.restapi.biz.service.order.mypage.vo.SummarySalesBuyVO;
import com.ibk.sb.restapi.biz.service.order.mypage.vo.SummarySalesProductVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 마이페이지 구매/판매 Repo
 */
@Mapper
public interface MyOrderRepo {

    // 판매 / 구매내역 리스트 조회
    List<SummarySalesBuyVO> selectMyOrderBuyList(RequestSearchMyOrderVO requestSearchMyOrderVO);

    // 주문 상품 정보 리스트 조회
    List<SummarySalesProductVO> selectMyOrderProduct(@Param("ordnInfoId")String ordnInfoId,
                                                     @Param("filePtrnId")String filePtrnId);

    // 주문 상품 정보 리스트 조회(한꺼번에 조회)
    List<SummarySalesProductVO> selectMyOrderProductList(RequestSearchMyOrderVO requestSearchMyOrderVO);
}

