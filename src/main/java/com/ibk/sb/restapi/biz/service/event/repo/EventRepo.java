package com.ibk.sb.restapi.biz.service.event.repo;

import com.ibk.sb.restapi.biz.service.admin.vo.AdminEventVO;
import com.ibk.sb.restapi.biz.service.event.vo.RequestSearchEventVO;
import com.ibk.sb.restapi.biz.service.event.vo.SummaryEventTotalVO;
import com.ibk.sb.restapi.biz.service.event.vo.SummaryEventVO;
import com.ibk.sb.restapi.biz.service.event.vo.mypage.RequestSearchEventMyVO;
import com.ibk.sb.restapi.biz.service.event.vo.mypage.SummaryEventMyParticiInfoVO;
import com.ibk.sb.restapi.biz.service.event.vo.mypage.SummaryEventMyParticiVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EventRepo {

    // 특정 이벤트 목록 조회
    List<SummaryEventVO> selectEventList(RequestSearchEventVO requestSearchEventVO);

    // 이벤트 상세
    SummaryEventVO selectEventDetail(RequestSearchEventVO requestSearchEventVO);

    // 이벤트 상태별 total
    SummaryEventTotalVO searchEventStateTotal();

    // 마이페이지 > 회사 이벤트 참여정보
    List<SummaryEventMyParticiVO> selectEventMyParticiList(RequestSearchEventMyVO requestSearchEventMyVO);

    // 마이페이지 > 회사 이벤트 참여정보+이벤트 검색조건 반영 목록
    List<SummaryEventMyParticiInfoVO> selectEventMyParticiInfoList(RequestSearchEventMyVO requestSearchEventMyVO);

    // 마이페이지 > 마이페이지 이벤트 현황상세
    SummaryEventVO searchEventMyDetail(RequestSearchEventMyVO requestSearchEventMyVO);

    SummaryEventVO selectEventDetailInfo(@Param("evntInfId") String evntInfId);

    int insertEventInfo(@Param("params") AdminEventVO params);

    int updateEventInfo(@Param("params") AdminEventVO params);

    int deleteEventInfo(@Param("params") AdminEventVO params);

}
