package com.ibk.sb.restapi.biz.service.banner.repo;

import com.ibk.sb.restapi.biz.service.banner.vo.BannerStatusVO;
import com.ibk.sb.restapi.biz.service.banner.vo.BannerVO;
import com.ibk.sb.restapi.biz.service.banner.vo.RequestBannerSearchVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BannerRepo {

    // 베너 리스트 조회
    List<BannerVO> selectBannerList(@Param("typeId") String typeId);

    // 베너 리스트 조회
    List<BannerVO> selectBannerPaging(RequestBannerSearchVO params);

    BannerVO selectBannerInfo(@Param("banInfId") String banInfId);

    BannerStatusVO selectBannerCurrentStatus();

    int insertBannerInfo(@Param("typeId") String typeId,
                         @Param("params") BannerVO params);

    int updateBannerInfo(@Param("typeId") String typeId,
                         @Param("params") BannerVO params);

    int deleteBannerInfo(@Param("params") BannerVO params);

}
