package com.ibk.sb.restapi.biz.service.order.vo.req;

import com.ibk.sb.restapi.app.common.vo.BaseTableVO;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@Alias("OrderReqProductVO")
public class OrderReqProductVO extends BaseTableVO {

    // 주문 정보 ID
    private String ordnInfoId;

    // 정보 순번
    private Integer infoSqn;

    // 주문 유형 ID
    private String ordnPtrnId;

    // 주문 유형 명
    private String ordnPtrnNm;

    // 상품 카테고리 ID
    private String pdfCtgyId;

    // 상품명
    private String pdfNm;

    // 수량
    private Integer qty;

    // 판매 가격
    private Integer pdfPrc;

    // 공통 가격 단위 ID
    private String comPrcutId;

    // 공통 상품 단위 ID
    private String comPdfutId;

    // 공통 가격단위 명
    private String comPrcutNm;

    // 공통 상품단위 명
    private String comPdfutNm;

    // 총 금액
    private Integer ttalAmt;

    // 배송 유형 ID
    private String dvryPtrnId;

    // 배송 유형 명
    private String dvryPtrnNm;

    // 업체 정보 ID
    private String entpInfoId;

    // 업체 정보 명
    private String entpNm;

    // 배송 정보 ID
    private String dvryInfoId;

    // 주문 상태 ID
    private String ordnSttsId;

    // 주문 상태 명
    private String ordnSttsNm;

    // 상품 정보 ID
    private String pdfInfoId;

    // 결제 정보 ID
    private String stlmInfoId;

    // 배송비
    private Integer dvrynone;

    // 출고 우편번호
    private String rlontfZpcd;

    // 출고 주소
    private String rlontfAdr;

    // 출고 상세주소
    private String rlontfDtad;

    // 주문 번호(채결번호)
    private String cnttNoId;

    //////
    // 견적
    //////

    // 견적 상품 유형 ID
    private String esttPdfPtrnId;

    //////
    // 조회
    //////

    // 판매자 이용기관 ID
    private String selrUsisId;

    // 구매자 이용기관 ID
    private String pucsUsisId;

    // 판매자 ID
    private String selrId;

    // 취소일자(주문취소승인(ODS00002))
    private String cancelSelrRgsnTs;

    // 취소일자(주문취소완료(ODS00008))
    private String cancelPucsRgsnTs;

    // 운송의뢰번호
    private String trspreqsNo;

    // 택배사 유형 ID
    private String pcsvcmpPtrnId;

    // 택배사 유형 명
    private String pcsvcmpPtrnNm;

    // 기타 택배사명
    private String pcsvcmpNm;

    // 운송장 번호
    private String mainnbNo;

    // 운송장 번호 url
    private String mainnbNoUrl;

    // 에이전시 상품인지 여부
    private String agenPdfYn;

    ////////
    // 수령지
    ////////

    // 수령인
    private String recv;

    // 수령인 연락처1
    private String recvCnplone;

    // 수령인 연락처2
    private String recvCnpltwo;

    // 수령인 우편번호
    private String recvZpcd;

    // 수령인 주소
    private String recvAdr;

    // 수령인 상세주소
    private String recvDtad;

}