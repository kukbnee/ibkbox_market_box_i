package com.ibk.sb.restapi.biz.service.agency.repo;

import com.ibk.sb.restapi.biz.service.agency.vo.AgencyProductFileVO;
import com.ibk.sb.restapi.biz.service.agency.vo.AgencyProductRetrunVO;
import com.ibk.sb.restapi.biz.service.agency.vo.RequestSearchAgencyVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AgencyProductCopyRepo {

    /*
     FILE_ID가 있는 테이블인 경우 파일정보테이블 정보를 복사
     */
    public Integer productCopyFile(AgencyProductFileVO agencyProductFileVO);

    //상품기본정보, TB_BOX_MKT_PDF_INF_M, 에이전시 승인을 받고 상품이 복사되었을때 상품의 첫 상태는 진열안함, 판매안함임.
    public Integer productCopyPdfInfM(RequestSearchAgencyVO requestSearchAgencyVO);

    //상품판매정보, TB_BOX_MKT_PDF_SAL_L
    public Integer productCopyPdfSalL(RequestSearchAgencyVO requestSearchAgencyVO);

    //상품키워드정보, TB_BOX_MKT_PDF_KWR_R
    public Integer productCopyPdfKwrR(RequestSearchAgencyVO requestSearchAgencyVO);

    //상품특허정보, TB_BOX_MKT_PDF_PAT_R, 특허는 원판매자 사업자등록번호를 기준으로 검색되기 때문에 에이전시는 복사된 원판매자의 특허정보를 수정할 수 없다.
    public Integer productCopyPdfPatR(RequestSearchAgencyVO requestSearchAgencyVO);

    //상품제품영상정보, TB_BOX_MKT_PDF_VID_R
    public Integer productCopyPdfVidR(RequestSearchAgencyVO requestSearchAgencyVO);

    //상품제품영상정보 파일목록, TB_BOX_MKT_PDF_VID_R
    public List<AgencyProductFileVO> productCopyPdfVidRFiles(RequestSearchAgencyVO requestSearchAgencyVO);

    //상품이미지파일정보, TB_BOX_MKT_PDF_FIE_R
    public Integer productCopyPdfFieR(RequestSearchAgencyVO requestSearchAgencyVO);

    //상품이미지파일정보 파일목록, TB_BOX_MKT_PDF_FIE_R
    public List<AgencyProductFileVO> productCopyPdfFieRFiles(RequestSearchAgencyVO requestSearchAgencyVO);

    //상품화물서비스기본정보, TB_BOX_MKT_PDF_DVRY_M
    public Integer productCopyPdfDvryM(RequestSearchAgencyVO requestSearchAgencyVO);

    //상품화물서비스기본정보 파일목록, TB_BOX_MKT_PDF_DVRY_M
    public List<AgencyProductFileVO> productCopyPdfDvryMFiles(RequestSearchAgencyVO requestSearchAgencyVO);

    //상품화물서비스견적정보, TB_BOX_MKT_DVRY_ESTT_M
    public Integer productCopyDvryEsttM(RequestSearchAgencyVO requestSearchAgencyVO);

    //상품반품/교환정보, TB_BOX_MKT_PDF_RTIN_L, 원판매자가 아닌 에이전시가 최근에 등록한 상품의 반품/교환정보를 불러온다
    public AgencyProductRetrunVO searchProductCopyPdfRtinL(RequestSearchAgencyVO requestSearchAgencyVO);

    //상품반품/교환정보, 정보등록, 없는경우 공란으로 등록
    public Integer addProductCopyPdfRtinL(AgencyProductRetrunVO agencyProductRetrunVO);

    //상품배송정보, TB_BOX_MKT_PDF_DEL_L
    public Integer productCopyPdfDelL(RequestSearchAgencyVO requestSearchAgencyVO);

    //상품지역별배송정보, TB_BOX_MKT_PEAR_DVRY_M
    public Integer productCopyPearDvryM(RequestSearchAgencyVO requestSearchAgencyVO);

    //상품수량별배송정보, TB_BOX_MKT_QTY_DVRY_M
    public Integer productCopyQtyDvryM(RequestSearchAgencyVO requestSearchAgencyVO);

}
