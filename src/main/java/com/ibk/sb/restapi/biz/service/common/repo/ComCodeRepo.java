package com.ibk.sb.restapi.biz.service.common.repo;

import com.ibk.sb.restapi.biz.service.common.vo.SummaryComCodeGroupVO;
import com.ibk.sb.restapi.biz.service.common.vo.SummaryComCodeListVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ComCodeRepo {

    // 공통 코드 그룹 목록
    public List<SummaryComCodeGroupVO> searchComCodeGroupList(String grpCdTag);

    // 공통 코드 그룹 상세 목록
    public List<SummaryComCodeListVO> searchComCodeList(String grpCdId);

}
