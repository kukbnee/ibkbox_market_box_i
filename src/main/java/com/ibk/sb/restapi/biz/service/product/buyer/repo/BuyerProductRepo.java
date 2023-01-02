package com.ibk.sb.restapi.biz.service.product.buyer.repo;

import com.ibk.sb.restapi.biz.service.product.buyer.vo.BuyerProductInfoVO;
import com.ibk.sb.restapi.biz.service.product.buyer.vo.BuyerProductVO;
import com.ibk.sb.restapi.biz.service.product.buyer.vo.RequestBuyerProductInfoSearchVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BuyerProductRepo {

    public List<BuyerProductInfoVO> selectBuyerProductInfoList(RequestBuyerProductInfoSearchVO requestBuyerProductInfoSearchVO);

    // 바이어 상품 정보 조회
    BuyerProductInfoVO selectBuyerProductInfo(RequestBuyerProductInfoSearchVO requestBuyerProductInfoSearchVO);

    // 마이페이지 상품관리 바이어 상품 정보 등록
    Integer insertBuyerProductInfo(BuyerProductInfoVO buyerProductInfoVO);

    // 마이페이지 상품관리 바이어 상품 정보 수정
    Integer updateBuyerProductInfo(BuyerProductInfoVO buyerProductInfoVO);

    // 바이어 상품 정보 삭제
    Integer deleteBuyerProductInfo(BuyerProductInfoVO buyerProductInfoVO);

    // List<BuyerProductVO> selectBuyerProductList(RequestBuyerProductInfoSearchVO RequestBuyerProductInfoSearchVO);

    // public BuyerProductVO selectBuyerProduct(@Param("bunInfId") String gdsId);

    // 마이페이지 상품관리 바이어 상품 등록
    Integer insertBuyerProduct(BuyerProductVO buyerProductVO);

    // 마이페이지 상품관리 바이어 상품 삭제
    Integer deleteBuyerProduct(@Param("buyerInfId") String buyerInfId);
}
