package com.ibk.sb.restapi.biz.service.delivery.vo;

import com.ibk.sb.restapi.app.common.vo.BaseTableVO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;
import org.springframework.security.core.parameters.P;

/**
 * Table : TB_BOX_MKT_ORDER_DVRY_R
 * Name : 주문 화물서비스 배송정보
 * Etc : 천일화물 배송 스테이터스 변경시, 해당 테이블 select 용으로 사용
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Alias("DeliveryOrderProductServiceInfoVO")
public class DeliveryOrderProductServiceInfoVO extends BaseTableVO {

    // 주문 정보 ID
    private String ordnInfoId;

    // 정보 순번
    private Integer infoSqn;

    // 운송의뢰 번호
    private String trspreqsNo;

    // 운송장 번호
    private String mainnbNo;

    // 배송비
    private Long dvrynone;

    // 출고 우편번호
    private String rlontfZpcd;

    // 출고 주소
    private String rlontfAdr;

    // 출고 상세주소
    private String rlontfDtad;

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

    // 업체 정보 ID
    private String entpInfoId;

    // 업체명
    private String entpNm;

}
