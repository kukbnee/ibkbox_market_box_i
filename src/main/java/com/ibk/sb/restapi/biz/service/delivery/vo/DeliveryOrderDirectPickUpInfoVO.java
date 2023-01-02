package com.ibk.sb.restapi.biz.service.delivery.vo;

import com.ibk.sb.restapi.app.common.vo.BaseTableVO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

/**
 * Table : TB_BOX_MKT_ORDER_UDVR_R
 * Name : 주문 직접수령 배송정보
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Alias("DeliveryOrderDirectPickUpInfoVO")
public class DeliveryOrderDirectPickUpInfoVO extends BaseTableVO {

    // 주문 정보 ID
    private String ordnInfoId;

    // 정보 순번
    private String infoSqn;

    // 수령인 우편번호
    private String recvZpcd;

    // 수령인 주소
    private String recvAdr;

    // 수령인 상세주소
    private String recvDtad;
}
