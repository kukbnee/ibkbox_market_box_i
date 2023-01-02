package com.ibk.sb.restapi.biz.service.patent.repo;

import com.ibk.sb.restapi.biz.service.patent.vo.PatentVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PatentRepo {

    // 상품 리스트 조회
    List<PatentVO> searchPatentList(@Param("pdfInfoId") String pdfInfoId);

    // 상품 특허 등록
    Integer insertPatent(PatentVO patentVO);

    // 상품 특허 삭제
    Integer deletePatent(@Param("pdfInfoId") String pdfInfoId);
}
