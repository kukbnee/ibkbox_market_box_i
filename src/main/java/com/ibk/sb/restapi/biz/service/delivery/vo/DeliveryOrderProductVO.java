package com.ibk.sb.restapi.biz.service.delivery.vo;

import com.ibk.sb.restapi.app.common.vo.BaseTableVO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

import java.sql.Date;

/**
 * Table : TB_BOX_MKT_ORDER_PDF_R
 * Name : 주문상품정보
 * Etc : 천일화물 배송 스테이터스 변경시, 해당 테이블 select/update 용으로 사용
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Alias("DeliveryOrderProductVO")
public class DeliveryOrderProductVO extends BaseTableVO {

    // 주문 정보 ID
    private String ordnInfoId;

    // 정보 순번
    private String infoSqn;

    // 주문 유형 ID
    private String ordnPtrnId;

    // 상품 카테고리 ID
    private String pdfCtgyId;

    // 상품명
    private String pdfNm;

    // 수량
    private Integer qty;

    // 판매가격
    private Long pdfPrc;

    // 총 금액
    private Long ttalAmt;

    // 배송 유형 ID
    private String dvryPtrnId;

    // 배송 정보 ID
    private String dvryInfoId;

    // 주문 상태 ID
    private String ordnSttsId;

    // 상품 정보 ID
    private String pdfInfoId;

    // 결제 정보 ID
    private String stlmInfoId;

    // 쿼리 실행 및 데이터 수정 날짜
    private String resultAmnnTs;
}
