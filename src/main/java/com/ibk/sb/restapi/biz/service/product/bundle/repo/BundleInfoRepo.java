package com.ibk.sb.restapi.biz.service.product.bundle.repo;

import com.ibk.sb.restapi.biz.service.product.bundle.vo.BundleProductInfoVO;
import com.ibk.sb.restapi.biz.service.product.bundle.vo.SummaryBundleInfoVO;
import com.ibk.sb.restapi.biz.service.product.common.vo.RequestSearchProductVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BundleInfoRepo {

    // 묶음 상품 정보 리스트 조회
    List<SummaryBundleInfoVO> selectBundleInfoList(RequestSearchProductVO requestProductInfoSearchVO);

    // 묶음 제품 정보 상세 조회
    BundleProductInfoVO selectBundleProductInfo(@Param("bunInfId") String bunInfId);

    // 묶음 제품 정보 상세 정보 조회 이력 추가
    Integer insertBundleProductViewHistory(String bunInfId, String loginUsisId, String loginUserId);

    // 마이페이지 상품관리 묶음상품 삭제
    Integer deleteBundleProductInfo(BundleProductInfoVO bundleProductInfoVO);
}
