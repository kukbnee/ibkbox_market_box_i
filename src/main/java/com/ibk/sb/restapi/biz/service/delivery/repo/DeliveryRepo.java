package com.ibk.sb.restapi.biz.service.delivery.repo;

import com.ibk.sb.restapi.biz.service.delivery.vo.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DeliveryRepo {

    // 상품 배송 정보 조회
    DeliveryinfoVO selectDeliveryInfo(@Param("pdfInfoId") String pdfInfoId);

    // 상품 화물서비스 기본 정보 조회
    DeliveryBaseInfoVO selectDeliveryBaseInfo(@Param("pdfInfoId") String pdfInfoId);

    List<DeliveryProductServiceInfoVO> selectDeliveryProductInfo(@Param("pdfInfoId") String pdfInfoId);

    List<DeliveryLocalInfoVO> selectDeliveryLocalInfo(@Param("pdfInfoId") String pdfInfoId);

    List<DeliveryCntInfoVO> selectDeliveryCntInfo(@Param("pdfInfoId") String pdfInfoId);

    // 상품 배송 정보 등록
    Integer insertDeliveryinfo(DeliveryinfoVO deliveryinfo);

    // 상품 배송 정보 수정
    Integer updateDeliveryinfo(DeliveryinfoVO deliveryinfo);

    // 상품 배송 정보 수정(화물서비스 업체 ID)
    Integer updateDeliveryinfoForEntpInfoId(DeliveryinfoVO deliveryinfoVO);

    // 상품 화물서비스 기본 정보 등록
    Integer insertDeliveryBaseInfo(DeliveryBaseInfoVO deliveryBaseInfoVO);

    // 상품 화물서비스 기본 정보 수정
    Integer updateDeliveryBaseInfo(DeliveryBaseInfoVO deliveryBaseInfoVO);

    // 상품 화물서비스 견적 정보 등록
    Integer insertDeliveryProductServiceInfo(DeliveryProductServiceInfoVO deliveryProductServiceInfoVO);

    // 상품 화물서비스 견적 정보 삭제
    Integer deleteDeliveryProductServiceInfo(@Param("pdfInfoId") String pdfInfoId);

    // 지역별 배송비 정보 등록
    Integer insertDeliveryLocalInfo(DeliveryLocalInfoVO deliveryLocalInfoVO);

    // 지역별 배송비 정보 삭제
    Integer deleteDeliveryLocalInfo(@Param("pdfInfoId") String pdfInfoId);

    // 수량별 배송비 정보 등록
    Integer insertDeliveryCntInfo(DeliveryCntInfoVO deliveryCntInfoVO);

    // 수량별 배송비 정보 삭제
    Integer deleteDeliveryCntInfo(@Param("pdfInfoId") String pdfInfoId);

    /*
     * 주문 / 배송 관련
     */

    // 주문 화물서비스 배송정보 조회 by 원송장(운송장) 번호
    DeliveryOrderProductServiceInfoVO selectDeliveryOrderProductServiceInfoByMainnbNo(@Param("mainnbNo")String mainnbNo);

    // 주문 상품 정보 조회 by 주문정보ID, 정보 순번
    DeliveryOrderProductVO selectDeliveryOrderProductById(@Param("ordnInfoId")String ordnInfoId,
                                                          @Param("infoSqn")String infoSqn,
                                                          @Param("rgsnUserId")String rgsnUserId);

    // 주문 상품 정보 스테이터스 수정
    Integer updateDeliveryStatus(DeliveryOrderProductVO deliveryOrderProductVO);

    // 주문 화물서비스 배송정보 조회 by 주문정보ID, 정보 순번
    DeliveryOrderProductServiceInfoVO selectDeliveryOrderProductServiceInfo(@Param("ordnInfoId")String ordnInfoId,
                                                                            @Param("infoSqn")int infoSqn);

    // 주문 직접수령 배송정보 조회 by 주문정보ID, 정보 순번
    DeliveryOrderDirectPickUpInfoVO selectDeliveryOrderDirectPickUpInfo(@Param("ordnInfoId")String ordnInfoId,
                                                                        @Param("infoSqn")int infoSqn);

    // 주문 구매자 배송정보 by 주문정보ID, 정보 순번
    DeliveryOrderBuyerDeliveryInfoVO selectDeliveryOrderBuyerDeliveryInfo(@Param("ordnInfoId")String ordnInfoId,
                                                                          @Param("infoSqn")int infoSqn);
}
