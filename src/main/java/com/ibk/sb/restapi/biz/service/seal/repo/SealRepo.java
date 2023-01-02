package com.ibk.sb.restapi.biz.service.seal.repo;

import com.ibk.sb.restapi.biz.service.seal.vo.SealVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SealRepo {

    // 인감 정보 조회
    SealVo selectSealInfo(@Param("utlinsttId") String utlinsttId);

    // 인감정보 등록
    int insertSealInfo(SealVo sealVo);

    // 인감정보 삭제
    int deleteSealInfo(@Param("utlinsttId") String utlinsttId);

}
