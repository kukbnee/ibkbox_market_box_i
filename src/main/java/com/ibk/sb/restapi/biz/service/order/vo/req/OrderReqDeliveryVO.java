package com.ibk.sb.restapi.biz.service.order.vo.req;

import com.ibk.sb.restapi.app.common.vo.BaseTableVO;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@Alias("OrderReqDeliveryVO")
public class OrderReqDeliveryVO extends BaseTableVO {

    // 주문 정보 ID
    private String ordnInfoId;

    // 정보 순번
    private Integer infoSqn;

    // 판매자 ID
    private String selrId;

    // 운송의뢰 번호
    private String trspreqsNo;

    // 운송장 번호
    private String mainnbNo;

    // 배송비
    private Integer dvrynone;

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

    // 택배사 유형 ID
    private String pcsvcmpPtrnId;

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

    // 총 금액
    private Integer ttalAmt;

    // 배송 유형 ID
    private String dvryPtrnId;

    // 주문 상태 ID
    private String ordnSttsId;

    // 업체 정보 ID
    private String entpInfoId;

    /*
     * 최대물량 이상 배송시, 배송하는 업체 정보ID
     * 상품판매자가 화물 배송 등록시 등록한 최대 수량 이상으로 구매자가 구매를 할 시, 최대수량이 가장 높은 배송업체 ID가 셋팅됨
     */
    private String entpInfoIdMax;

    //////
    // 견적
    //////

    // 처리 상태 ID
    private String pcsnSttsId;

    /*
     * 업체정보 getter
     */
    public String getEntpInfoId() {
        // 업체정보 ID 가 null 일 경우, 최대물량 배송 업체 정보 ID 를 셋팅
        if(this.entpInfoId == null || this.entpInfoId.equals("")) {
            this.entpInfoId = this.entpInfoIdMax;
        }
        return this.entpInfoId;
    }
}
