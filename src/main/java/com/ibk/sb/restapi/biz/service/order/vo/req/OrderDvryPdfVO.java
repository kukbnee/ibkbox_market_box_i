package com.ibk.sb.restapi.biz.service.order.vo.req;

import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@Alias("OrderDvryPdfVO")
public class OrderDvryPdfVO {

    // 주문 정보 ID
    private String ordnInfoId;

    // 정보 순번
    private Integer infoSqn;


    //////
    // 상품
    //////

    // 상품명
    private String pdfNm;

    // 상품 정보 ID
    private String pdfInfoId;

    // 수량
    private Integer pdfQty;

    // 판매 가격
    private Integer pdfPrc;

    // 배송비
    private Integer dvrynone;

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

    // 제품포장 단위 ID
    private String prdtpcknUtId;

    // 상품 카테고리 ID
    private String pdfCtgyId;

    // 파일 ID(상품 등록시 화물서비스 란에 등록한 이미지 파일)
    private String fileId;

}
