package com.ibk.sb.restapi.biz.service.review.repo;

import com.ibk.sb.restapi.biz.service.file.vo.FileInfoVO;
import com.ibk.sb.restapi.biz.service.review.vo.RequestSearchReviewVO;
import com.ibk.sb.restapi.biz.service.review.vo.ReviewFileVO;
import com.ibk.sb.restapi.biz.service.review.vo.ReviewHeaderVO;
import com.ibk.sb.restapi.biz.service.review.vo.ReviewVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ReviewRepo {

    ReviewHeaderVO selectProductDetailReviewHeader(RequestSearchReviewVO requestSearchReviewVO);

    List<ReviewVO> selectProductDetailReviewImageList(RequestSearchReviewVO requestSearchReviewVO);

    // 상품 상세 구매후기 목록 조회
    List<ReviewFileVO> selectReviewFileList(@Param("revInfId") String revInfId);

    List<ReviewVO> selectReviewList(RequestSearchReviewVO requestSearchReviewVO);

    // 상품 상세 구매후기 등록
    Integer insertReview(ReviewVO reviewVO);

    // 상품 상세 구매후기 파일 등록
    Integer insertReviewFile(ReviewFileVO reviewFileVO);

    // 상품 상세 구매후기 수정
    Integer updateReview(ReviewVO reviewVO);

    // 상품 상세 구매후기 삭제
    Integer deleteReview(ReviewVO reviewVO);

    // 상품 상세 구매후기 파일 삭제
    Integer deleteReviewFile(@Param("revInfId") String revInfId);
}
