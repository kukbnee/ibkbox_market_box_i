package com.ibk.sb.restapi.biz.service.estimation.vo;

import com.ibk.sb.restapi.app.common.vo.BaseTableVO;
import com.ibk.sb.restapi.biz.service.file.vo.FileInfoVO;
import com.ibk.sb.restapi.biz.service.product.common.vo.ProductFileVO;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

import java.util.List;

/**
 * 등록용 견적 상품 VO
 */
@Getter
@Setter
@Alias("EstimationProductVO")
public class EstimationProductVO extends BaseTableVO {

    // 견적 정보 ID
    private String esttInfoId;

    // 정보 순번
    private Integer infoSqn;

    // 판매자 이용기관 ID
    private String selrUsisId;

    // 상품명
    private String pdfNm;

    // 판매가격
    private String pdfPrc;

//    // 공통 가격 단위 ID
//    private String comPrcUtId;

    // 공통 가격 단위 ID
    private String comPrcutId;

    // 주문 수량
    private Integer ordnQty;

//    // 공통 상품 단위 ID
//    private String comPdfUtId;

    // 공통 상품 단위 ID
    private String comPdfutId;

    // 합계 금액
    private Integer sumAmt;

    // 견적 상품 유형 ID
    private String esttPdfPtrnId;

    // 연동 상품 정보 ID
    private String gearPdfInfoId;

    // 파일명
    private String fileNm;

    // 파일 경로
    private String filePath;

    // 제품 가로
    private String prdtBrdh;

    // 제품 세로
    private String prdtVrtc;

    // 제품 높이
    private String prdtAhgd;

    // 제품 무게
    private String prdtWgt;

    // 내품가액
    private Integer dchGdsPrc;

    // 최대상품수
    private String mxmmGdsCnt;

    // 제품포장 단위 ID
    private String prdtpcknUtId;

    // 파일 ID(상품 등록시 화물서비스 란에 등록한 이미지 파일)
    private String fileId;

    // 상품 카테고리 ID
    private String pdfCtgyId;

    // 카테고리 데이터
    private String ctgyData;

    // 카테고리 1단계
    private String tms2ClsfNm;

    // 카테고리 2단계
    private String tms3ClsfNm;

    // 카테고리 3단계
    private String tms4ClsfNm;

    // 카테고리 4단계
    private String tms5ClsfNm;

    // 에이전시 상품인지 여부
    private String agenPdfYn;

    // 상품 정보 ID
    private String pdfInfoId;

    // 공통 가격단위 명
    private String comPrcutNm;

    // 공통 상품단위 명
    private String comPdfutNm;

    // 할인가
    private String salePrc;

    // 가격 협의 여부
    private String prcDscsYn;

    // 견적 사용 유무
    private String esttUseEn;

    // 모델명
    private String mdlnm;

    // 요약 설명
    private String brfDesc;

    // 간략 설명
    private String brfSubDesc;

    // 상품 진행 여부(진열 여부)
    private String pdfPgrsYn;

    // 상품 상태 ID(판매중(GDS00001))
    private String pdfSttsId;

    // 상품 이미지 파일 정보 리스트
    private List<FileInfoVO> productFileList;

}
