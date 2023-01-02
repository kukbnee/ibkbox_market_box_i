package com.ibk.sb.restapi.biz.service.order.vo.req;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ibk.sb.restapi.app.common.vo.BaseTableVO;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

import java.util.List;

/**
 * 조회용 주문 정보 VO
 */
@Getter
@Setter
@Alias("SummaryOrderInfoVO")
@JsonIgnoreProperties({

})
public class SummaryOrderInfoVO extends BaseTableVO {

    // 주문 정보 ID
    private String ordnInfoId;

    // 체결 번호 ID
    private String cnttNoId;

    // 주문일자
    private String ordnRgsnTs;

    // 구매자 이용기관 ID
    private String pucsUsisId;

    // 구매자 이용기관 명
    private String pucsBplcNm;

    // 구매자 이용기관 대표자 명
    private String pucsRprsntvNm;

    // 구매자 ID
    private String pucsId;

    // 판매자 이용기관 ID
    private String selrUsisId;

    // 판매자 이용기관 명
    private String selrBplcNm;

    // 판매자 이용기관 대표자 명
    private String selrRprsntvNm;

    // 판매자 ID
    private String selrId;

    // 결제 정보 ID
    private String stlmInfoId;

    // 결제 상태 ID
    private String stlmsttsId;

    // 결제 상태 명
    private String stlmsttsNm;

    // 결제 유형 ID
    private String stlmptrnId;

    // 결제 유형 명
    private String stlmptrnNm;

    // 결제 결과 ID
    private String stlmRsltId;

    // 상품 목록
    private List<OrderReqProductVO> products;

    //////
    // 금액
    //////

    // 상품금액 합계
    private Integer pdfAmtSum;

    // 배송비 합계
    private Integer dvrynoneSum;

    // 결제금액
    private Integer stlmAmt;

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

    //////
    // 조회
    //////

    // 배송 유형 ID
    private String dvryPtrnId;

    // 배송 유형 명
    private String dvryPtrnNm;

    // 업체 정보 ID
    private String entpInfoId;

    // 업체 정보 명
    private String entpNm;

    // 택배사 유형 ID
    private String pcsvcmpPtrnId;

    // 기타 택배사명
    private String pcsvcmpNm;

}
