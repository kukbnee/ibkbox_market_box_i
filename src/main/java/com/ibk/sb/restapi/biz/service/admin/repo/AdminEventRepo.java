package com.ibk.sb.restapi.biz.service.admin.repo;

import com.ibk.sb.restapi.biz.service.admin.vo.AdminEventProductVO;
import com.ibk.sb.restapi.biz.service.admin.vo.RequestEventSearchProductVO;
import com.ibk.sb.restapi.biz.service.event.vo.EventProductVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AdminEventRepo {

    // 운영자포탈 > 이벤트 신청 상품 목록
    List<EventProductVO> selectAdminEventProductList(RequestEventSearchProductVO params);

    // 운영자포탈 > 이벤트 상품 추가 목록
    List<EventProductVO> selectAdminEventAllProductList(RequestEventSearchProductVO params);

    int updateEventProductApproved(@Param("params") AdminEventProductVO params);

    int insertEventProductApproved(AdminEventProductVO vo);

    AdminEventProductVO selectEventProductApproved(@Param("params") AdminEventProductVO params);

    int deleteEventProductApproved(@Param("params") AdminEventProductVO params);

    int updateEventProductSort(@Param("params") AdminEventProductVO params);

}
