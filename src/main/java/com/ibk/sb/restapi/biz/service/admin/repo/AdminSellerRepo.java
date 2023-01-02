package com.ibk.sb.restapi.biz.service.admin.repo;

import com.ibk.sb.restapi.biz.service.admin.vo.AdminSellerVO;
import com.ibk.sb.restapi.biz.service.admin.vo.RequestSellerSearchVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AdminSellerRepo {

    List<AdminSellerVO> selectSellerList(RequestSellerSearchVO params);

    int updateSellerStatus(@Param("mmbrsttsId") String mmbrsttsId,
                           @Param("selrUsisId") String selrUsisId);

    int updateProductStatus(@Param("pdfInfoId") String pdfInfoId,
                            @Param("pdfSttsId") String pdfSttsId);

    AdminSellerVO selectSellerDeatil(@Param("selrUsisId") String selrUsisId);
}
