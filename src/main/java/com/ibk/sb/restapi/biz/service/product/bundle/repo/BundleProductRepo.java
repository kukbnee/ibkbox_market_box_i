package com.ibk.sb.restapi.biz.service.product.bundle.repo;


import com.ibk.sb.restapi.biz.service.product.bundle.vo.BundleProductInfoVO;
import com.ibk.sb.restapi.biz.service.product.bundle.vo.BundleProductVO;
import com.ibk.sb.restapi.biz.service.product.bundle.vo.SummaryBundleProductVO;
import com.ibk.sb.restapi.biz.service.product.common.vo.RequestSearchProductVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BundleProductRepo {

    List<SummaryBundleProductVO> selectBundleProductList(RequestSearchProductVO requestProductInfoSearchVO);

    // 상품 묶음상품 정보 등록
    Integer insertBundleProductInfo(BundleProductInfoVO bundleProductInfoVO);

    // 상품 묶음상품 정보 수정
    Integer updateBundleProductInfo(BundleProductInfoVO bundleProductInfoVO);

    // 상품 묶음상품 목록 등록
    Integer insertBundleProduct(BundleProductVO bundleProductVO);

    // 상품 묶음상품 목록 삭제
    Integer deleteBundleProductList(@Param("bunInfId")String bunInfId);

    // 상품 묶음상품 메인유무 설정
    Integer updateBundleMainUsed(RequestSearchProductVO searchParams);

//    public BundleProductVO selectBundleProduct(@Param("bunInfId") String gdsId);
//
//    public Integer updateBundleProduct(BundleProductVO bundleProductVO);
//
//    public Integer deleteBundleProducts(@Param("bunInfId") String bunInfId);
//
//    public Integer deleteBundleProduct(@Param("gdsId") String gdsId);
}