package com.ibk.sb.restapi.biz.service.delivery.vo;

import com.ibk.sb.restapi.app.common.vo.BaseTableVO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

/**
 * Table : TB_BOX_MKT_ORDER_PDVRY_R
 * Name : 주문 구매자 배송정보
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Alias("DeliveryOrderBuyerDeliveryInfoVO")
public class DeliveryOrderBuyerDeliveryInfoVO extends BaseTableVO {

    // 주문 정보 ID
    private String ordnInfoId;

    // 정보 순번
    private String infoSqn;

    // 택배사 유형 ID
    private String pcsvcmpPtrnId;

    // 택배사 명
    private String pcsvcmpNm;

    // 운송장 번호
    private String mainnbNo;

    // 배송비
    private Long dvrynone;

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
