package com.ibk.sb.restapi.biz.service.delivery.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ibk.sb.restapi.app.common.vo.BaseTableVO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

/**
 * Table : TB_BOX_MKT_PDF_DVRY_M
 * Name : 상품 화물서비스 기본 정보
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Alias("DeliveryBaseInfoVO")
@JsonIgnoreProperties({
        "usyn", "delyn", "rgsnUserId", "rgsnTs", "amnnUserId", "amnnTs",
        "totalCnt", "rgsnUserName", "amnnUserName"
})
public class DeliveryBaseInfoVO extends BaseTableVO {

    // 상품 정보 ID
    private String pdfInfoId;

    // 파일 ID
    private String fileId;

    // 파일명
    private String fileNm;

    // 출고지 우편번호
    private String rlontfZpcd;

    // 출고지 주소
    private String rlontfAdr;

    // 출고지 상세주소
    private String rlontfDtad;

    // 제품포장 단위 ID
    private String prdtpcknUtId;

    // 제품포장 단위 명
    private String prdtpcknUtName;

    // 내품가액(1box당)
    private String dchGdsPrc;

    // 최대상품수(1box당)
    private String mxmmGdsCnt;

    // 박스 가로
    private String prdtBrdh;

    // 박스 세로
    private String prdtVrtc;

    // 박스 높이
    private String prdtAhgd;

    // 박스 무게 (kg)
    private String prdtWgt;


}
