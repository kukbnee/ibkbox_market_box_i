package com.ibk.sb.restapi.biz.service.category.repo;

import com.ibk.sb.restapi.biz.service.category.vo.CategoryActiveVO;
import com.ibk.sb.restapi.biz.service.category.vo.CategoryVO;
import com.ibk.sb.restapi.biz.service.category.vo.ResponseCategoryVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryRepo {

    // 카테고리 정보 조회
    List<ResponseCategoryVO> selectCategoryList(CategoryVO categoryVO);

    // 카테고리 레벨별 목록 조회
    List<CategoryActiveVO> selectCategoryDepthList(Integer depth, String parentCode);

}
