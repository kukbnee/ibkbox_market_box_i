package com.ibk.sb.restapi.biz.service.batch.repo;

import com.ibk.sb.restapi.biz.service.seller.vo.SellerInfoVO;
import com.ibk.sb.restapi.biz.service.user.vo.CompanyVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface BatchRepo {

    // 가입 사업자 주요 정보 갱신
    int batchMktFfpcInfo(CompanyVO companyVO);

    // 판매자 정보 갱신
    int batchSelrInfo(SellerInfoVO sellerInfoVO);

    int batchOrderInfo(@Param("utlinsttId") String utlinsttId,
                       @Param("mmbrsttsId") String mmbrsttsId);
}
