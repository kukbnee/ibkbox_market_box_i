package com.ibk.sb.restapi.biz.service.faq.repo;

import com.ibk.sb.restapi.biz.service.faq.vo.FaqVO;
import com.ibk.sb.restapi.biz.service.faq.vo.RequestSearchFaqVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FaqRepo {

    List<FaqVO> selectFaqList(RequestSearchFaqVO requestSearchFaqVO);

    FaqVO selectFaqDetail(RequestSearchFaqVO requestSearchFaqVO);

    Integer faqSave(FaqVO faqVO);

    Integer faqUpdate(FaqVO faqVO);

    Integer faqDelete(FaqVO faqVO);

}
