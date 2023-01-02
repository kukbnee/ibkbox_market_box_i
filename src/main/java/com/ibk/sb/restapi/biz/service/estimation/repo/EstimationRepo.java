package com.ibk.sb.restapi.biz.service.estimation.repo;

import com.ibk.sb.restapi.biz.service.estimation.vo.EstimationCntVO;
import com.ibk.sb.restapi.biz.service.estimation.vo.EstimationVO;
import com.ibk.sb.restapi.biz.service.estimation.vo.RequestSearchEstimationVO;
import com.ibk.sb.restapi.biz.service.estimation.vo.SummaryEstimationInfoVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EstimationRepo {

//    public List<EstimationVO> selectEstimationList(RequestSearchEstimationVO requestSearchEstimationVO);
//
//    public Integer insertEstimationInfo(EstimationVO estimationVO);
//
//    public Integer insertEstimationDetail(EstimationVO estimationVO);
//
//    public Integer insertEstimationStatusHistory(EstimationVO estimationVO);
//
//    public Integer updateEstimationInfoStatus(EstimationVO estimationVO);
//
//    public Integer deleteEstimation(EstimationVO estimationVO);

    // 마이페이지 > 문의 > 견적발송
    public Integer insertEstimationInfo(RequestSearchEstimationVO requestSearchEstimationVO);
    public Integer insertEstimationDelivery(RequestSearchEstimationVO requestSearchEstimationVO);
    public Integer insertEstimationInfoHistory(RequestSearchEstimationVO requestSearchEstimationVO);
    public Integer insertEstimationRlon(RequestSearchEstimationVO requestSearchEstimationVO);
    public Integer insertEstimationRcar(RequestSearchEstimationVO requestSearchEstimationVO);
    public Integer insertEstimationInes(RequestSearchEstimationVO requestSearchEstimationVO);
    public EstimationVO searchEstimationInqrRcvrUsisId(RequestSearchEstimationVO requestSearchEstimationVO);
    public EstimationVO searchEstimationInqrDpmpUsisId(RequestSearchEstimationVO requestSearchEstimationVO);

    // 마이페이지 > 견적요청 > 견적취소(판매자)
    public Integer updateEstimationInfo(RequestSearchEstimationVO requestSearchEstimationVO);
    public Integer updateEstimationInquR(RequestSearchEstimationVO requestSearchEstimationVO);
    public Integer updateEstimationInes(RequestSearchEstimationVO requestSearchEstimationVO);
    public Integer insertEstimationInesForCancel(RequestSearchEstimationVO requestSearchEstimationVO);
    public SummaryEstimationInfoVO searchEstimationForCancel(RequestSearchEstimationVO requestSearchEstimationVO);
    public EstimationVO searchEstimationInqrInfoId(RequestSearchEstimationVO requestSearchEstimationVO);

    // 마이페이지 > 견적요청 > 목록(보낸견적/받은견적)
    public List<SummaryEstimationInfoVO> searchEstimationInfoList(RequestSearchEstimationVO requestSearchEstimationVO);
    public EstimationCntVO searchEstimationSumCnt(RequestSearchEstimationVO requestSearchEstimationVO);

    // 마이페이지 > 문의 > 견적상세
    public SummaryEstimationInfoVO searchEstimationInfo(RequestSearchEstimationVO requestSearchEstimationVO);
}
