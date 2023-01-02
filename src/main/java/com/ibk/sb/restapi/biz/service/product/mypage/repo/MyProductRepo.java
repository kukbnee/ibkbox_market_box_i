package com.ibk.sb.restapi.biz.service.product.mypage.repo;

import com.ibk.sb.restapi.biz.service.product.mypage.vo.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MyProductRepo {

    // 마이페이지 상품관리 개별상품 리스트 조회
    List<SummaryMySingleProductVO> selectMySingleProductList(RequestSearchMyProductVO requestSearchMyProductVO);

    // 마이페이지 상품관리 묶음상품 정보 리스트 조회
    List<SummaryMyBundleProductInfoVO> selectMyBundleProductInfoList(RequestSearchMyProductVO requestSearchMyProductVO);

    // 마이페이지 상품관리 바이어 상품 정보 리스트 조회
    List<SummaryMyBuyerProductInfoVO> selectMyBuyerProductInfoList(RequestSearchMyProductVO requestSearchMyProductVO);

    // 마이페이지 상품관리 묶음상품 리스트 조회
    List<SummaryMyProductVO> selectMyProductList(RequestSearchMyProductVO requestSearchMyProductVO);

}
