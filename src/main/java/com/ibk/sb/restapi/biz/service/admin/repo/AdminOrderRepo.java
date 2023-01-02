package com.ibk.sb.restapi.biz.service.admin.repo;

import com.ibk.sb.restapi.biz.service.admin.vo.AdminOrderProductVO;
import com.ibk.sb.restapi.biz.service.admin.vo.AdminOrderVO;
import com.ibk.sb.restapi.biz.service.admin.vo.AdminSellerVO;
import com.ibk.sb.restapi.biz.service.admin.vo.RequestOrderSearchVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AdminOrderRepo {

    //주문목록
    List<AdminOrderVO> searchOrderList(RequestOrderSearchVO requestOrderSearchVO);

    //주문상품목록
    List<AdminOrderProductVO> searchOrderProductList(@Param("ordnInfoId") String ordnInfoId, @Param("ordnSttsId") String ordnSttsId);
}
