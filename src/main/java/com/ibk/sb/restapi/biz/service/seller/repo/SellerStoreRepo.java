package com.ibk.sb.restapi.biz.service.seller.repo;

import com.ibk.sb.restapi.biz.service.seller.vo.SellerCategoryVO;
import com.ibk.sb.restapi.biz.service.seller.vo.SellerFileVO;
import com.ibk.sb.restapi.biz.service.seller.vo.SellerInfoVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SellerStoreRepo {

    // 판매자 스토어 카테고리 정보 조회
    List<SellerCategoryVO> selectSellerCategoryList(@Param("selrUsisId") String selrUsisId);

    /*
     * 판매자 정보
     */
    // 판매자 정보 조회
    SellerInfoVO selectSellerInfo(@Param("selrUsisId") String selrUsisId);

    // 판매자 정보 등록
    Integer insertSellerInfo(SellerInfoVO sellerInfoVO);

    // 판매자 정보 수정(회사 소개)
    Integer updateSellerInfo(SellerInfoVO sellerInfoVO);

    // 판매자 이미지 정보 리스트 조회
    List<SellerFileVO> selectSellerFileList(@Param("selrUsisId") String selrUsisId,
                                            @Param("imgptrnId") String imgptrnId);

    // 판매자 이미지 정보 등록
    Integer insertSellerFile(SellerFileVO sellerFileVO);

    // 판매자 이미지 정보 삭제
    Integer deleteSellerFile(SellerFileVO sellerFileVO);

    // 판매자 통신판매업 신고번호 수정
    Integer updateCsbStmtno(SellerInfoVO sellerInfoVO);


}
