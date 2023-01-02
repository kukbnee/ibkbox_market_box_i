package com.ibk.sb.restapi.biz.service.product.single.repo;

import com.ibk.sb.restapi.biz.service.product.common.vo.ProductReturnVO;
import com.ibk.sb.restapi.biz.service.product.common.vo.ProductSearchHistoryVO;
import com.ibk.sb.restapi.biz.service.product.single.vo.SingleProductVO;
import com.ibk.sb.restapi.biz.service.product.single.vo.SummarySingleProductVO;
import com.ibk.sb.restapi.biz.service.product.common.vo.RequestSearchProductVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SingleProductRepo {

    // 상품 리스트 조회
    List<SummarySingleProductVO> selectSingleProductList(RequestSearchProductVO requestProductInfoSearchVO);

    SummarySingleProductVO selectSingleProduct(RequestSearchProductVO requestProductInfoSearchVO);

    // 상품관리 개별상품 조회
    SingleProductVO selectMySingleProduct(@Param("pdfInfoId") String pdfInfoId);

    SingleProductVO selectPdfStts(@Param("pdfInfoId") String pdfInfoId);

    // 상품등록 반품/교환 정보 조회
    ProductReturnVO selectMyProductSellerLastRtin(@Param("utlinsttId") String utlinsttId);

    // 상품 기본 정보 등록
    Integer insertSingleProduct(SingleProductVO singleProductVO);

    // 상품 기본 정보 수정
    Integer updateSingleProduct(SingleProductVO singleProductVO);

    // 마이페이지 상품관리 개별상품 삭제
    Integer deleteSingleProduct(SingleProductVO singleProductVO);
}
