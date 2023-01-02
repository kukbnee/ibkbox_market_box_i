package com.ibk.sb.restapi.app.common.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AlarmCode {

    /**
     * 알림 관련 Constant
     *
     * 알림아이디의 경우
     * 개발서버와 운영서버 운영자포탈에서 발급된 ID(자동발급)가 서로 다를 수 있으므로
     * Bean, Profile 로 관리하지 않고 ID는 properties로 따로 처리
     */
    
    /*
    투자박스 알림발송 분류 코드 (MNB)
     */

    // BOX 알림 대분류
    public final static String ALRT_LRDV_DCD_BOX = "0003";
    // 마켓박스 알림 발송 중분류 코드 (생산자네트워크)
    public final static String ALRT_MDDV_CD_MARKET = "APP004"; // 투자의 경우 APP00046
    // BOX 서비스 구분 코드 (마켓)
    public final static String IBKBOX_SVC_DCD = "MK";

    /*
    알림 발송 대상 메인박스 권한 Enum
     */
    public enum AuthCodeEnum {
        AUTH_CODE_C("C", "총괄관리자"),
        AUTH_CODE_M("M", "관리자"),
        AUTH_CODE_U("U", "직원");

        private final String code;
        private final String name;

        AuthCodeEnum(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public String getCode() { return code; }
        public String getName() { return name; }
    }

    /*
    알림 코드 Enum
     */
    public enum AlarmCodeEnum {

        // 견적 관련
        ESTIMATE_REQUEST(
                estimateRequestId,
                "견적 발송",
                "alarm.template.estimate.request",
                alarmPageMarketHostUrl + "/mypage/estimation/list"
        ),
        ESTIMATE_CANCEL(
                estimateCancelId,
                "견적 취소",
                "alarm.template.estimate.cancel",
                alarmPageMarketHostUrl + "/mypage/estimation/list"
        ),
        ESTIMATE_PAY(
                estimatePayId,
                "견적 결제",
                "alarm.template.estimate.pay",
                alarmPageMarketHostUrl + "/mypage/ordermanagement/order?" // {ordnInfoId}&selr
        ),

        // 에이전시 관련
        AGENCY_APPROVAL(
                agencyApprovalId,
                "에이전시 권한 허용",
                "alarm.template.agency.approval",
                alarmPageMarketHostUrl + "/mypage/agency"
        ),
        AGENCY_REQUEST(
                agencyRequestId,
                "에이전시 요청",
                "alarm.template.agency.request",
                alarmPageMarketHostUrl + "/mypage/agency"
        ),
        AGENCY_REQUEST_APPROVAL(
                agencyRequestApprovalId,
                "에이전시 요청 승인",
                "alarm.template.agency.request-approval",
                alarmPageMarketHostUrl + "/mypage/agency"
        ),
        AGENCY_REQUEST_CANCEL(
                agencyRequestCancelId,
                "에이전시 요청 반려",
                "alarm.template.agency.request-cancel",
                alarmPageMarketHostUrl + "/mypage/agency"
        ),
        AGENCY_PRODUCT_CHANGE(
                agencyProductChangeId,
                "상품정보 변경",
                "alarm.template.agency.product-change",
                alarmPageMarketHostUrl + "/product/detail/" // {pdfInfoId}
        ),
        AGENCY_PRODUCT_QNA(
                agencyProductQnaId,
                "받은 문의",
                "alarm.template.agency.product-qna",
                alarmPageMarketHostUrl + "/mypage/qnauser/list"
        ),

        // 배송 관련
        DELIVERY_SHIPPING(
                deliveryShippingId,
                "상품 배송중", "" +
                "alarm.template.delivery.shipping",
                alarmPageMarketHostUrl + "/mypage/ordermanagement/order?" // {ordnInfoId}&buyer
        ),
        DELIVERY_SHIPPED(
                deliveryShippedId,
                "상품 배송완료",
                "alarm.template.delivery.shipped",
                alarmPageMarketHostUrl + "/mypage/ordermanagement/order?" // {ordnInfoId}&buyer
        ),

        // 관리자
        ADMIN_QNA(adminQnaId,
                "관리자 문의",
                "alarm.template.admin.qna",
                alarmPageMarketHostUrl + "/mypage/qnauser/list"
        ),

        // 반품 관련
        RETURN_REQUEST(
                returnRequestId,
                "상품 반품요청",
                "alarm.template.return.request",
                alarmPageMarketHostUrl + "/mypage/ordermanagement/return/detail/" // {ordnInfoId}&selr
        ),
        RETURN_COMPLETE(
                returnCompleteId,
                "상품 반품완료",
                "alarm.template.return.complete",
                alarmPageMarketHostUrl + "/mypage/ordermanagement/return/detail/" // {ordnInfoId}&selr
        ),
        RETURN_IMPOSSIBLE(
                returnImpossibleId,
                "상품 반품불가",
                "alarm.template.return.impossible",
                alarmPageMarketHostUrl + "/mypage/ordermanagement/return/detail/" // {ordnInfoId}&selr
        ),
        CANCEL_PRODUCT_ORDER(
                cancelOrderId,
                "주문 취소",
                "alarm.template.cancel.order",
                alarmPageMarketHostUrl + "/mypage/ordermanagement/order/" // {ordnInfoId}&buyer
        );

        private final String alarmId;
        private final String title;
        private final String templateId;
        private final String baseUrl;

        AlarmCodeEnum(String alarmId, String title, String templateId, String baseUrl) {
            this.alarmId = alarmId;
            this.title = title;
            this.templateId = templateId;
            this.baseUrl = baseUrl;
        }

        public String getAlarmId() {
            return alarmId;
        }
        public String getTitle() {
            return title;
        }
        public String getTemplateId() { return templateId; }
        public String getBaseUrl() {return baseUrl;}
    }


    // 마켓박스 호스트 URL
    private static String alarmPageMarketHostUrl;

    // 마켓박스 호스트 URL Setter
    @Value("${mkt.alarm.page.host.url}")
    public void setAlarmPageMarketHostUrl(String alarmPageMarketHostUrl) {
        AlarmCode.alarmPageMarketHostUrl = alarmPageMarketHostUrl;
    }

    /*
    마켓박스 알림 ID
     */
    // 견적 발송
    private static String estimateRequestId;
    // 견적 취소
    private static String estimateCancelId;
    // 견적 결제
    private static String estimatePayId;

    // 에이전시 권한 허용
    private static String agencyApprovalId;
    // 에이전시 요청
    private static String agencyRequestId;
    // 에이전시 요청 승인
    private static String agencyRequestApprovalId;
    // 에이전시 요청 반려
    private static String agencyRequestCancelId;
    // 상품정보 변경
    private static String agencyProductChangeId;
    // 받은 문의
    private static String agencyProductQnaId;

    // 상품 배송중
    private static String deliveryShippingId;
    // 상품 배송완료
    private static String deliveryShippedId;

    // 관리자 문의
    private static String adminQnaId;

    // 상품 반품요청
    private static String returnRequestId;
    // 상품 반품완료
    private static String returnCompleteId;
    // 상품 반품불가
    private static String returnImpossibleId;

    // 주문 취소
    private static String cancelOrderId;

    @Value("${adm.alarm.estimate-request.id}")
    public void setEstimateRequestId(String estimateRequestId) { AlarmCode.estimateRequestId = estimateRequestId; }
    @Value("${adm.alarm.estimate-cancel.id}")
    public void setEstimateCancelId(String estimateCancelId) { AlarmCode.estimateCancelId = estimateCancelId; }
    @Value("${adm.alarm.estimate-pay.id}")
    public void setEstimatePayId(String estimatePayId) { AlarmCode.estimatePayId = estimatePayId; }
    @Value("${adm.alarm.agency-approval.id}")
    public void setAgencyApprovalId(String agencyApprovalId) { AlarmCode.agencyApprovalId = agencyApprovalId; }
    @Value("${adm.alarm.agency-request.id}")
    public void setAgencyRequestId(String agencyRequestId) { AlarmCode.agencyRequestId = agencyRequestId; }
    @Value("${adm.alarm.agency-request-approval.id}")
    public void setAgencyRequestApprovalId(String agencyRequestApprovalId) { AlarmCode.agencyRequestApprovalId = agencyRequestApprovalId; }
    @Value("${adm.alarm.agency-request-cancel.id}")
    public void setAgencyRequestCancelId(String agencyRequestCancelId) { AlarmCode.agencyRequestCancelId = agencyRequestCancelId; }
    @Value("${adm.alarm.agency-product-change.id}")
    public void setAgencyProductChangeId(String agencyProductChangeId) { AlarmCode.agencyProductChangeId = agencyProductChangeId; }
    @Value("${adm.alarm.agency-product-qna.id}")
    public void setAgencyProductQnaId(String agencyProductQnaId) { AlarmCode.agencyProductQnaId = agencyProductQnaId; }
    @Value("${adm.alarm.delivery-shipping.id}")
    public void setDeliveryShippingId(String deliveryShippingId) { AlarmCode.deliveryShippingId = deliveryShippingId; }
    @Value("${adm.alarm.delivery-shipped.id}")
    public void setDeliveryShippedId(String deliveryShippedId) { AlarmCode.deliveryShippedId = deliveryShippedId; }
    @Value("${adm.alarm.admin-qna.id}")
    public void setAdminQnaId(String adminQnaId) { AlarmCode.adminQnaId = adminQnaId; }
    @Value("${adm.alarm.return-request.id}")
    public void setReturnRequestId(String returnRequestId) { AlarmCode.returnRequestId = returnRequestId; }
    @Value("${adm.alarm.return-complete.id}")
    public void setReturnCompleteId(String returnCompleteId) { AlarmCode.returnCompleteId = returnCompleteId; }
    @Value("${adm.alarm.return-impossible.id}")
    public void setReturnImpossibleId(String returnImpossibleId) { AlarmCode.returnImpossibleId = returnImpossibleId; }
    @Value("${adm.alarm.cancel-order.id}")
    public void setCancelOrderId(String cancelOrderId) { AlarmCode.cancelOrderId = cancelOrderId; }

    /*
    알림 대분류 Enumeration
    메인박스 기준 대분류에 따라 수신목록 탭처리가 필요할 수 있으므로 따로 Enum 정리
     */
//    public enum AlrtLrdvDcdEnum {
//        FINANCE("0001", "금융"),
//        NEWS("0002", "뉴스"),
//        BOX("0003", "BOX"),
//        MY("0004", "MY"),
//        NOTICE("0005", "공지"),
//        STORE("0006", "스토어");
//
//        private final String code;
//        private final String name;
//
//        AlrtLrdvDcdEnum(String code, String name) {
//            this.code = code;
//            this.name = name;
//        }
//
//        public String getCode() {
//            return code;
//        }
//        public String getName() {
//            return name;
//        }
//    }
}
