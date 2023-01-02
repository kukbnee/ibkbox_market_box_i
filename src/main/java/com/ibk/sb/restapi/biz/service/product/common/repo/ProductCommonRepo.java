package com.ibk.sb.restapi.biz.service.product.common.repo;

import com.ibk.sb.restapi.biz.service.product.common.vo.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProductCommonRepo {

    // 상품 파일 정보 리스트 조회
    List<ProductFileVO> selectProductFileList(@Param("pdfInfoId") String pdfInfoId,
                                           @Param("filePtrnId") String filePtrnId,
                                           @Param("infoSqn") String infoSqn);

    // 상품 제품 영상 정보 리스트 조회
    List<ProductVideoVO> selectProductVideoList(@Param("pdfInfoId") String pdfInfoId);

    // 상품 제품 링크 정보 리스트 조회
    List<ProductLinkVO> selectProductLinkList(@Param("pdfInfoId") String pdfInfoId);

    // 상품 키워드 리스트 조회
    List<ProductKeywordVO> selectProductKeyWordList(@Param("pdfInfoId") String pdfInfoId);

    // 상품 판매 정보 조회
    ProductSaleVO selectProductSale(@Param("pdfInfoId") String pdfInfoId);

    // 상품 반품/교환 정보 조회
    ProductReturnVO selectProductReturn(@Param("pdfInfoId") String pdfInfoId);

    // 상품 키워드 등록
    Integer insertProductKeyWord(ProductKeywordVO productKeywordVO);

    // 상품 키워드 삭제
    Integer deleteProductKeyWord(@Param("pdfInfoId") String pdfInfoId);

    // 상품 판매 정보 등록
    Integer insertProductSale(ProductSaleVO productSaleVO);

    // 상품 판매 정보 수정
    Integer updateProductSale(ProductSaleVO productSaleVO);

    // 상품 이미지 파일 정보 등록
    Integer insertProductFile(ProductFileVO productFileVO);

    // 상품 이미지 파일 정보 삭제
    Integer deleteProductFile(@Param("pdfInfoId") String pdfInfoId);

    // 상품 제품 영상 정보 등록
    Integer insertProductVideo(ProductVideoVO productVideoVO);

    // 상품 제품 링크 정보 등록
    Integer insertProductLink(ProductLinkVO productLinkVO);

    // 상품 제품 영상 정보 삭제
    Integer deleteProductVideo(@Param("pdfInfoId") String pdfInfoId);

    // 상품 제품 링크 정보 삭제
    Integer deleteProductLink(@Param("pdfInfoId") String pdfInfoId);

    // 상품 반품/교환 정보 등록
    Integer insertProductReturn(ProductReturnVO productReturnVO);

    // 상품 반품/교환 정보 수정
    Integer updateProductReturn(ProductReturnVO productReturnVO);

    // 상품 조회 이력 등록
    Integer insertProductSearchHistory(ProductSearchHistoryVO productSearchHistoryVO);

}
