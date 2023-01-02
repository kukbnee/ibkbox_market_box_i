package com.ibk.sb.restapi.biz.service.estimation.repo;

import com.ibk.sb.restapi.biz.service.estimation.vo.EstimationDeliveryVO;
import com.ibk.sb.restapi.biz.service.estimation.vo.RequestSearchEstimationVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EstimationDeliveryRepo {

    // 마이페이지 > 문의 > 화물서비스 선택
    public List<EstimationDeliveryVO> searchDvryEntp(RequestSearchEstimationVO requestSearchEstimationVO);
}
