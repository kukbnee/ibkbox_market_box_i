package com.ibk.sb.restapi.biz.service.order.mypage.vo;

import com.ibk.sb.restapi.app.common.vo.BaseTableVO;
import com.ibk.sb.restapi.app.common.vo.PageVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

/**
 * 마이페이지 > 구매/판매 > 주문상품정보 리스트 VO
 */
@Getter
@Setter
@NoArgsConstructor
@Alias("SummarySalesProductVO")
public class SummarySalesProductVO extends BaseTableVO {

    // 주문정보 ID
    private String ordnInfoId;

    // 정보 순번
    private int infoSqn;

    // 주문 유형 ID
    private String ordnPtrnId;

    // 상품 카테고리 ID
    private String pdfCtgyId;

    // 상품 카테고리 명
    private String pdfCtgyNm;

    // 상품 명
    private String pdfNm;

    // 수량
    private String qty;

    // 판매가격
    private String pdfPrc;

    // 총 금액
    private String ttalAmt;

    // 배송 유형 ID
    private String dvryPtrnId;

    // 배송 정보 ID
    private String dvryInfoId;

    // 주문 상태 ID
    private String ordnSttsId;

    // 구매후기 작성 여부
    private String isReviewed;

    /*
     * 판매자 정보
     */
    // 판매자 이용기관 ID
    private String selrUsisId;

    // 판매자 이용기관 명
    private String selrUsisNm;

    // 판매자 이용기관 대표자 명
    private String rprsntvNm;

    // 판매자 타입 ID
    private String mmbrtypeId;

    // 판매자 인증여부 플래그
    private String userAuthYn;

    // 판매자 ID
    private String selrId;

    // 판매자 명
    private String selrNm;


    /*
     * 구매자 정보
     */
    // 구매자 이용기관 ID
    private String pucsUsisId;

    // 구매자 이용기관 ID
    private String pucsUsisNm;

    // 구매자 ID
    private String pucsId;

    // 판매자 ID
    private String pucsNm;


    /*
     * 상품정보
     */
    // 상품 정보 ID
    private String pdfInfoId;

    // 에이전시 접수 정보 ID
    private String agenInfId;

    // 가격협의 여부
    private String prcDscsYn;


    /*
     * 주소확인
     */
    // 수령인 우편번호
    private String recvZpcd;

    // 수령인 주소
    private String recvAdr;

    // 수령인 상세주소
    private String recvDtad;

    // 운송의뢰번호
    private String trspreqsNo;

    // 운송장 번호
    private String mainnbNo;

    // 운송장 번호 url
    private String mainnbNoUrl;

    // 택배사 유형 ID
    private String pcsvcmpPtrnId;

    // 택배사 명
    private String pcsvcmpNm;

    // 배송비
    private Long dvrynone;

    // 업체 정보 ID(화물서비스 업체)
    private String entpInfoId;

    // 업체명(화물서비스 업체)
    private String entpNm;

    // 출고 우편번호
    private String rlontfZpcd;

    // 출고 주소
    private String rlontfAdr;

    // 출고 상세주소
    private String rlontfDtad;

    /*
     * 견적 정보
     */
    // 견적 정보 ID
    private String esttInfoId;

}
