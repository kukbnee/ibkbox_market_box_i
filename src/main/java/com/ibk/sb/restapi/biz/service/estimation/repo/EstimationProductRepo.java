package com.ibk.sb.restapi.biz.service.estimation.repo;

import com.ibk.sb.restapi.biz.service.estimation.vo.EstimationProductVO;
import com.ibk.sb.restapi.biz.service.estimation.vo.RequestSearchEstimationVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EstimationProductRepo {
    public Integer insertEstimationProduct(EstimationProductVO estimationProductVO);

    // 마이페이지 > 문의 > 견적상세
    public List<EstimationProductVO> searchEstimationProduct(RequestSearchEstimationVO requestSearchEstimationVO);

    // 마이페이지 > 문의 > 추가할 상품 검색
    public List<EstimationProductVO> searchProductAdd(RequestSearchEstimationVO requestSearchEstimationVO);

    // 주문 > 운임체크(화물서비스) 등
    public List<EstimationProductVO> searchProductForDeliveryAmt(RequestSearchEstimationVO requestSearchEstimationVO);
}
