package com.ibk.sb.restapi.biz.service.order.mypage.vo;


import com.ibk.sb.restapi.app.common.vo.BaseTableVO;
import com.ibk.sb.restapi.biz.service.common.vo.SummaryComCodeListVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

import java.util.List;

/**
 * 마이페이지 > 구매내역 리스트 VO
 */
@Getter
@Setter
@NoArgsConstructor
@Alias("SummarySalesBuyVO")
public class SummarySalesBuyVO extends BaseTableVO {

    // 주문 정보 ID
    private String ordnInfoId;

    // 체결 번호 ID
    private String cnttNoId;

    // 결제정보 ID
    private String stlmInfoId;

    // 구매자 이용기관 ID
    private String pucsUsisId;

    // 구매자 이용기관 명
    private String pucsUsisNm;

    // 구매자 ID
    private String pucsId;

    // 구매자 명
    private String pucsNm;

    // 판매자 이용기관
    private String selrUsisId;

    // 판매자 이용기관 명
    private String selrUsisNm;

    // 판매자 ID
    private String selrId;

    // 판매자 명
    private String selrNm;

    // 주문 총 금액
    private int ttalAmt;

    //////////
    // 결제 정보
    //////////

//    // 결제 정보 ID
//    private String stlmInfoId;
//
//    // 주문 정보 ID
//    private String ordnInfoId;

    // 결제유형 ID
    private String stlmptrnId;

    // 결제상태 ID
    private String stlmsttsId;

    // 요청 정보
    private String rqstInfo;

    // 결과 정보
    private String rsltInfo;

    // 금액
    private Integer amt;

    // 결제 결과 ID
    private String stlmRsltId;

    //////////
    // 주문 상품
    //////////

    // 주문 상품 정보 리스트
    private List<SummarySalesProductVO> items;

    //////
    // 코드
    //////

    // 공통관련코드(처리 상태)
    private List<SummaryComCodeListVO> pcsvcmpPtrnCodes;

}

