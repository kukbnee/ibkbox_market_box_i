package com.ibk.sb.restapi.biz.service.user.repo;

import com.ibk.sb.restapi.biz.service.user.vo.CompanyVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


@Mapper
public interface UserRepo {

    CompanyVO selectUserCompanyInfo(@Param("utlinsttId") String utlinsttId);

}
