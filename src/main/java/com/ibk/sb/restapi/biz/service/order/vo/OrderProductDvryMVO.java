package com.ibk.sb.restapi.biz.service.order.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

/**
 * 상품 화물서비스 기본정보 VO
 * */
@Getter
@Setter
@NoArgsConstructor
@Alias("OrderProductDvryMVO")
@JsonIgnoreProperties({
        "pdfInfoId"
})
public class OrderProductDvryMVO {

    // 상품정보 ID
    private String pdfInfoId;

    // 파일 ID
    private String fileId;

    // 파일이미지명
    private String fileNm;

    // 파일이미지경로
    private String filePath;

    // 출고지 우편번호
    private String rlontfZpcd;

    // 출고지 주소
    private String rlontfAdr;

    // 출고지 상세주소
    private String rlontfDtad;

    // 제품포장 단위ID
    private String prdtpcknUtId;

    // 제품포장 단위명
    private String prdtpcknUtNm;

    // 내품가액
    private String dchGdsPrc;

    // 제품가로
    private String prdtBrdh;

    // 제품세로
    private String prdtVrtc;

    // 제품 높이
    private String prdtAhgd;

    // 제품 무게
    private String prdtWgt;

}
