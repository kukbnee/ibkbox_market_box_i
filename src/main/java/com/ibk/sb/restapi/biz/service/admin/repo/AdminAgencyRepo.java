package com.ibk.sb.restapi.biz.service.admin.repo;

import com.ibk.sb.restapi.biz.service.admin.vo.AdminAgencyVO;
import com.ibk.sb.restapi.biz.service.admin.vo.RequestAgencySearchVO;
import com.ibk.sb.restapi.biz.service.agency.vo.RequestSearchAgencyVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AdminAgencyRepo {

    List<AdminAgencyVO> selectAgencyAuthList(RequestAgencySearchVO params);

    int updateAgencyAuth(@Param("pcsnsttsId") String pcsnsttsId,
                         @Param("agenReqId") String agenReqId,
                         String loginUserId);

    int updateSellerType(@Param("mmbrtypeId") String mmbrtypeId,
                         @Param("selrUsisId") String selrUsisId,
                         String loginUserId);

    // 이력등록
    public Integer addAgencyInfMyHistory(AdminAgencyVO params);
}
