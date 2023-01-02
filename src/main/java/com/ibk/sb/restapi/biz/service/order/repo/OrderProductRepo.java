package com.ibk.sb.restapi.biz.service.order.repo;

import com.ibk.sb.restapi.biz.service.order.vo.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 주문 상품관련 repo
 * */
@Mapper
public interface OrderProductRepo {

    // 주문가능 상품정보 여부 체크
    public Integer orderCheckProductInfo(RequestProductVO requestProductVO);

    // 상품 판매정보 가져오기
    public OrderProductSalLVO getProductSalL(OrderProductSalLVO orderProductSalLVO);

    // 상품 정보 가져오기
    public OrderProductInfMVO getProductInfM(OrderProductInfMVO orderProductInfMVO);

    // 상품 배송정보 가져오기
    public OrderProductDelLVO getProductDelL(OrderProductDelLVO orderProductDelLVO);

    // 상품 이미지정보 가져오기
    public OrderProductFieRVO getProductFieR(OrderProductFieRVO orderProductFieRVO);

    // 상품 수량별 배송정보 가져오기
    public List<OrderProductQtyDvryMVO> getProductQtyDvryM(String pdfInfoId);

    // 상품 지역별 배송정보 가져오기
    public List<OrderProductPearDvryMVO> getProductPearDvryM(String pdfInfoId);

    // 상품 화물서비스 기본정보 가져오기
    public OrderProductDvryMVO getProductDvryM(OrderProductDvryMVO orderProductDvryMVO);

    // 상품 화물서비스 견적정보 가져오기
    public List<OrderProductDvryEsttMVO> getProductDvryEsttM(String pdfInfoId);

}
