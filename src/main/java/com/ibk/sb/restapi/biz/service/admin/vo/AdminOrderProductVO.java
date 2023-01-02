package com.ibk.sb.restapi.biz.service.admin.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@NoArgsConstructor
@Alias("AdminOrderProductVO")
public class AdminOrderProductVO {

    private String pdfNm; //상품명
    private String ctgyNm; //카테고리명
    private String pdfInfoId; //연관상품 ID
    private String agenState; //에이전시 상품 유무
    private String esttInfoId; //견적번호

    private String ordnPtrnId; //견적/개별상품 코드
    private String ordnPtrnNm; //견적/개별상품 명

    private String qty; //주문 수량
    private String pdfPrc; //주문 가격
    private String ttalAmt; //주문 합계

    private String dvryPtrnNm; //배송유형
    private String dvryInfoNm; //배송정보ID
    private String ordnSttsId; //주문상태 ID
    private String ordnSttsNm; //주문상태 명

}
