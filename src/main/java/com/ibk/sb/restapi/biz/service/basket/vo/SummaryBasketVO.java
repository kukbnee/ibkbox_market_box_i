package com.ibk.sb.restapi.biz.service.basket.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ibk.sb.restapi.app.common.vo.BaseTableVO;
import com.ibk.sb.restapi.biz.service.delivery.vo.DeliveryCntInfoVO;
import com.ibk.sb.restapi.biz.service.delivery.vo.DeliveryLocalInfoVO;
import com.ibk.sb.restapi.biz.service.delivery.vo.DeliveryProductServiceInfoVO;
import com.ibk.sb.restapi.biz.service.delivery.vo.DeliveryinfoVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Alias("SummaryBasketVO")
@JsonIgnoreProperties({
        "usyn", "delyn", "rgsnUserId", "rgsnTs", "amnnUserId", "amnnTs",
        "totalCnt", "rgsnUserName", "amnnUserName"
})
public class SummaryBasketVO extends BaseTableVO {

    // 상품 정보 ID
    private String pdfInfoId;

    // 상품 수
    private String pdfCnt;

    // 단위 ID
    private String utId;

    // 판매자 이용기관 ID
    private String selrUsisId;

    // 판매자 이용기관 명
    private String selrUsisName;

    // 상품 상태 ID
    private String pdfSttsId;

    // 상품 상태 명
    private String pdfSttsName;

    // 상품 진행 여부(진열 여부)
    private String pdfPgrsYn;

    // 에이전시 접수 정보 ID
    private String agenInfId;

    // 상품 명
    private String pdfNm;

    // 판매가격
    private String pdfPrc;

    // 할인가
    private String salePrc;

    // 가격 협의 여부
    private String prcDscsYn;

    // 주문수량 제한 사용여부
    private String ordnQtyLmtnUsyn;

    // 주문 최소 수량
    private String ordnMnmmQty;

    // 주문 최대 수량 여부
    private String ordnMxmmQtyYn;

    // 주문 최대 수량
    private String ordnMxmmQty;

    // 공통 가격 단위 ID
    private String comPrcutId;

    // 공통 가격 단위 명
    private String comPrcutName;

    // 상품 선택 플래그
    private String selectedFlg;

    // 상품 배송 정보
    private DeliveryinfoVO deliveryinfo;

    // 화물 서비스 견적 정보 리스트
    private List<DeliveryProductServiceInfoVO> deliveryProductServiceInfoList;

    // 상품 지역별 배송 정보 리스트
    private List<DeliveryLocalInfoVO> deliveryLocalInfoList;

    // 상품 수량별 배송 정보 리스트
    private List<DeliveryCntInfoVO> deliveryCntInfoList;


}
