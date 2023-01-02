package com.ibk.sb.restapi.biz.service.event.repo;

import com.ibk.sb.restapi.biz.service.event.vo.*;
import com.ibk.sb.restapi.biz.service.event.vo.mypage.RequestSearchEventMyVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EventProductRepo {

    // 이벤트 상품 목록
    public List<SummaryEventProductVO> selectEventProductList(RequestSearchEventVO requestSearchEventVO);

    // 참여가능 여부 체크
    public String selectEventCheck(RequestSearchEventVO requestSearchEventVO);

    // 참여가능 상품 목록
    public List<EventProductVO> selectEventSelrProductList(RequestSearchEventVO requestSearchEventVO);

    // 판매자 이벤트 참여
    public Integer selrSaveEvent(SummaryEventProductSelrVO summaryEventProductSelrVO);

    // 판매자 이벤트 상품 정렬
    public Integer selrSaveProductSort(SummaryEventProductSelrVO summaryEventProductSelrVO);

    // 판매자 이벤트 취소
    public Integer selrCancelEvent(SummaryEventProductSelrVO summaryEventProductSelrVO);

    // 마이페이지 > 이벤트 참여상품 목록
    List<EventProductVO> searchEventMyProduct(RequestSearchEventMyVO requestSearchEventMyVO);
}
