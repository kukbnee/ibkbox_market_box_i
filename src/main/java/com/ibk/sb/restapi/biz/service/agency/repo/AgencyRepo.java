package com.ibk.sb.restapi.biz.service.agency.repo;

import com.ibk.sb.restapi.biz.service.agency.vo.RequestSearchAgencyVO;
import com.ibk.sb.restapi.biz.service.agency.vo.SummaryAgencyReasonVO;
import com.ibk.sb.restapi.biz.service.agency.vo.SummaryAgencyRequestVO;
import com.ibk.sb.restapi.biz.service.agency.vo.SummaryAgencyVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AgencyRepo {

    // 정회원 여부 확인
    public List<Object> checkMyUserTypeAgency(RequestSearchAgencyVO requestSearchAgencyVO);

    // 관리자 에이전시 요청 여부 확인
    public List<Object> checkMyUserAgency(RequestSearchAgencyVO requestSearchAgencyVO);

    // 마이페이지 > 관리자 에이전시 요청 등록
    public Integer applyMyAgency(RequestSearchAgencyVO requestSearchAgencyVO);

    // 마이페이지 > 관리자 에이전시 요청취소
    public Integer applyMyAgencyCancel(RequestSearchAgencyVO requestSearchAgencyVO);

    // 마이페이지 > 관리자 에이전시 요청 이력 등록
    public Integer addMyAgencyHistory(RequestSearchAgencyVO requestSearchAgencyVO);

    // 마이페이지 > 관리자 에이전시 요청 이력삭제
    public Integer addMyAgencyHistoryCancel(RequestSearchAgencyVO requestSearchAgencyVO);

    // 마이페이지 > 관리자 에이전시 요청 상세
    public SummaryAgencyRequestVO applyAgencyMyDetail(RequestSearchAgencyVO requestSearchAgencyVO);

    // 상품 에이전시 참여가능 상품여부 확인
    public List<Object> checkAgencyInfProduct(RequestSearchAgencyVO requestSearchAgencyVO);

    // 상품 에이전시 승인이력 상품여부 확인
    public List<Object> checkAgencyInfApplyProduct(RequestSearchAgencyVO requestSearchAgencyVO);

    // 상품 에이전시 요청 등록
    public Integer addAgencyInfProduct(RequestSearchAgencyVO requestSearchAgencyVO);

    // 상품 에이전시 요청 이력 등록
    public Integer addAgencyInfProductHistory(RequestSearchAgencyVO requestSearchAgencyVO);

    // 마이페이지 > 상품 에이전시 상태별 전체값
    public Integer searchAgenInfMyStateTotal(RequestSearchAgencyVO requestSearchAgencyVO);

    // 마이페이지 > 상품 에이전시 보낸/받은 목록
    public List<SummaryAgencyVO> searchAgencyInfMyList(RequestSearchAgencyVO requestSearchAgencyVO);

    // 마이페이지 > 보낸요청 > 대기상태 > 취소
    public Integer updateAgencyInfMyCancel(RequestSearchAgencyVO requestSearchAgencyVO);

    // 마이페이지 > 받은요청 > 대기상태 > 반려
    public Integer updateAgencyInfMyReject(RequestSearchAgencyVO requestSearchAgencyVO);

    // 마이페이지 > 상품 에이전시 > 보낸요청 > 반려사유,
    // 마이페이지 > 상품 에이전시 > 받은요청 > 반려사유
    public SummaryAgencyReasonVO searchAgencyInfMyReason(RequestSearchAgencyVO requestSearchAgencyVO);

    // 마이페이지 > 상품 에이전시 > 에이전시 요청 이력 등록
    public Integer addAgencyInfMyHistory(RequestSearchAgencyVO requestSearchAgencyVO);

    // 마이페이지 > 상품 에이전시 > 받은요청 > 대기상태 > 승인가능여부 체크
    public SummaryAgencyVO updateAgencyInfMyApprovalInfo(RequestSearchAgencyVO requestSearchAgencyVO);

    // 마이페이지 > 상품 에이전시 > 받은요청 > 대기상태 > 승인시 플래그 변경
    public Integer updateAgencyInfMyApproval(RequestSearchAgencyVO requestSearchAgencyVO);

    // 마이페이지 > 상품 에이전시 > 받은요청 > 승인상태 > 취소 or 승인취소 해제, (상품승인취소 및 요청 플래그상태 변경)
    public Integer updateAgencyInfMyApprovalState(RequestSearchAgencyVO requestSearchAgencyVO);
    public Integer updateAgencyPdfInfMyApprovalState(RequestSearchAgencyVO requestSearchAgencyVO);

    // 에이전시 접수 정보 조회
    SummaryAgencyVO selectAgencyInfo(@Param("agenInfId") String agenInfId);

    List<SummaryAgencyVO> selectAgencyInfoListByPdfId(@Param("pdfInfoId")String pdfInfoId);



}
