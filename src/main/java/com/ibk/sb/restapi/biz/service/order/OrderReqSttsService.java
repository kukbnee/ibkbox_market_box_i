package com.ibk.sb.restapi.biz.service.order;

import com.ibk.sb.restapi.app.common.constant.AlarmCode;
import com.ibk.sb.restapi.app.common.constant.ComCode;
import com.ibk.sb.restapi.app.common.constant.StatusCode;
import com.ibk.sb.restapi.app.common.vo.CustomUser;
import com.ibk.sb.restapi.app.common.vo.ResponseData;
import com.ibk.sb.restapi.biz.service.alarm.AlarmService;
import com.ibk.sb.restapi.biz.service.alarm.vo.RequestAlarmVO;
import com.ibk.sb.restapi.biz.service.order.repo.OrderReqRepo;
import com.ibk.sb.restapi.biz.service.order.repo.OrderReqStlmRepo;
import com.ibk.sb.restapi.biz.service.order.repo.OrderReqSttsRepo;
import com.ibk.sb.restapi.biz.service.order.vo.req.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
public class OrderReqSttsService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private final OrderReqSttsRepo orderReqSttsRepo;

    private final OrderReqRepo orderReqRepo;

    private final OrderReqStlmRepo orderReqStlmRepo;

    private final OrderDvryService orderDvryService;

    private final AlarmService alarmService;

    private AbstractMap.SimpleImmutableEntry<String, String> authInSvc() {
        // 로그인 정보
        String loginUserId = null;
        String loginUtlinsttId = null;
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof CustomUser) {
            CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            loginUserId = user.getLoginUserId();
            loginUtlinsttId = user.getUtlinsttId();
//        } else {
//            loginUserId = "box00802";
//            loginUtlinsttId = "C0000013";
        }
        return new AbstractMap.SimpleImmutableEntry<>(loginUserId, loginUtlinsttId);
    }

    /**
     * 구매자 > 수령확인
     * @return
     */
    public ResponseData orderProductReceiptCheck(RequestSearchOrderVO requestSearchOrderVO) {
        /*
         * 현재 상태 : 배송중
         * update 할 상태 : 배송완료
         */

        // 로그인 정보
        String loginUserId = null;
        String loginUtlinsttId = null;
        AbstractMap.SimpleImmutableEntry<String, String> auth = authInSvc();
        if (auth.getKey() != null && auth.getValue() != null) {
            loginUserId = auth.getKey();
            loginUtlinsttId = auth.getValue();
        } else {
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(StatusCode.COM0005.getMessage()) // COM0005(잘못된 접근입니다.)
                    .build();
        }
        requestSearchOrderVO.setLoginUsisId(loginUtlinsttId);
        requestSearchOrderVO.setLoginUserId(loginUserId);
        requestSearchOrderVO.setRgsnUserId(requestSearchOrderVO.getLoginUserId()); // 등록 사용자 ID

        // validation(입력값)
        String validateRes = validateReqValForReceiptCheck(requestSearchOrderVO);
        if (!validateRes.equals("")) {
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(validateRes)
                    .build();
        }

        // validation(입력값) // 구매자
        OrderReqProductVO ordnPdfInfo = orderReqSttsRepo.searchOrderPdf(requestSearchOrderVO);
        if (ordnPdfInfo == null) {
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(StatusCode.ORDER0002.getMessage()) // 주문정보가 올바르지 않습니다.
                    .build();
        }

        // validation(주문 상태 ID)
        if (ordnPdfInfo.getDvryPtrnId().equals(ComCode.GDS02004.getCode())) {
            if (!Arrays.asList(ComCode.ODS00001.getCode(), ComCode.ODS00003.getCode())
                    .contains(ordnPdfInfo.getOrdnSttsId())) { // 주문(ODS00001), 배송중(ODS00003)
                return ResponseData.builder()
                        .code(HttpStatus.BAD_REQUEST.value())
                        .message(StatusCode.ORDER0026.getMessage()) // 주문 또는 배송중 상태가 아닙니다.
                        .build();
            }
        } else {
            if (!ordnPdfInfo.getOrdnSttsId().equals(ComCode.ODS00003.getCode())) { // 배송중(ODS00003)
                return ResponseData.builder()
                        .code(HttpStatus.BAD_REQUEST.value())
                        .message(StatusCode.ORDER0016.getMessage()) // 배송중 상태가 아닙니다.
                        .build();
            }
        }

        // 공통 처리

        // DB 처리(주문상품정보)
        requestSearchOrderVO.setOrdnSttsId(ComCode.ODS00004.getCode()); // 배송완료(ODS00004)
        orderReqSttsRepo.updateOrderPdfStts(requestSearchOrderVO);

        // DB 처리(주문상품정보 이력)
        orderReqSttsRepo.insertOrderPdfH(requestSearchOrderVO);

        return ResponseData.builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .build();
    }

    /**
     * 판매자 > 주문취소
     * -조건1. 판매자만 가능
     * -조건2. 주문상태(ODS00001)에서만 가능
     * -처리1. 주문정보와 연결된 모든 주문상품에 대해 주문상태(주문취소 승인 ODS00002) 업데이트 및 이력 등록
     * @return
     */
    public ResponseData orderProductCancel(RequestSearchOrderVO requestSearchOrderVO) throws Exception {
        /*
         * 현재 상태 : 주문
         * update 할 상태 : 주문취소
         */

        // 로그인 정보
        String loginUserId = null;
        String loginUtlinsttId = null;
        AbstractMap.SimpleImmutableEntry<String, String> auth = authInSvc();
        if (auth.getKey() != null && auth.getValue() != null) {
            loginUserId = auth.getKey();
            loginUtlinsttId = auth.getValue();
        } else {
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(StatusCode.COM0005.getMessage()) // COM0005(잘못된 접근입니다.)
                    .build();
        }
        requestSearchOrderVO.setLoginUsisId(loginUtlinsttId);
        requestSearchOrderVO.setLoginUserId(loginUserId);
        requestSearchOrderVO.setRgsnUserId(requestSearchOrderVO.getLoginUserId()); // 등록 사용자 ID

        // validation(입력값)
        String validateRes = validateReqValForCancel(requestSearchOrderVO);
        if (!validateRes.equals("")) {
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(validateRes)
                    .build();
        }

        logger.debug("ordnPdfInfoSqnList : " + requestSearchOrderVO.getOrdnPdfInfoSqnList().toString());
        for (OrderReqProductVO ordnPdfInfoSqn : requestSearchOrderVO.getOrdnPdfInfoSqnList()) {
            requestSearchOrderVO.setOrdnPdfInfoSqn(ordnPdfInfoSqn.getInfoSqn());

            // validation(입력값) // 판매자
            OrderReqProductVO ordnPdfInfo = orderReqSttsRepo.searchOrderPdf(requestSearchOrderVO);
            if (ordnPdfInfo == null) {
                return ResponseData.builder()
                        .code(HttpStatus.BAD_REQUEST.value())
                        .message(StatusCode.ORDER0002.getMessage()) // 주문정보가 올바르지 않습니다.
                        .build();
            }

            // validation(주문 상태 ID)
            if (!ordnPdfInfo.getOrdnSttsId().equals(ComCode.ODS00001.getCode())) { // 주문(ODS00001)
                return ResponseData.builder()
                        .code(HttpStatus.BAD_REQUEST.value())
                        .message(StatusCode.ORDER0019.getMessage()) // 주문 상태가 아닙니다.
                        .build();
            }

            // 공통 처리

            // DB 처리(주문상품정보)
            requestSearchOrderVO.setOrdnSttsId(ComCode.ODS00002.getCode()); // 주문취소(ODS00002)
            orderReqSttsRepo.updateOrderPdfStts(requestSearchOrderVO);

            // DB 처리(주문상품정보 이력)
            orderReqSttsRepo.insertOrderPdfH(requestSearchOrderVO);

            // 알림 요청 인스턴스 생성
            RequestAlarmVO requestAlarmVO = alarmService.getAlarmRequest(AlarmCode.AlarmCodeEnum.CANCEL_PRODUCT_ORDER, ordnPdfInfo.getOrdnInfoId() + "&buyer", new Object[]{ordnPdfInfo.getCnttNoId()});
            // 알림 발송
            alarmService.sendMarketAlarm(requestAlarmVO, ordnPdfInfo.getPucsUsisId());
        }

        return ResponseData.builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .build();
    }

    /**
     * 구매자 > 결제취소
     * -조건1. 구매자만 가능
     * -조건2. 주문취소 승인(ODS00002)에서만 가능
     * -처리1. 주문정보와 연결된 모든 주문상품에 대해 주문상태(주문취소 완료 ODS00008) 업데이트 및 이력 등록
     * @param requestSearchOrderVO
     * @return
     */
    public ResponseData orderProductPayCancel(RequestSearchOrderVO requestSearchOrderVO) {
        /*
         * 현재 상태 : 주문취소 승인
         * update 할 상태 : 주문취소 완료
         */

        // 로그인 정보
        String loginUserId = null;
        String loginUtlinsttId = null;
        AbstractMap.SimpleImmutableEntry<String, String> auth = authInSvc();
        if (auth.getKey() != null && auth.getValue() != null) {
            loginUserId = auth.getKey();
            loginUtlinsttId = auth.getValue();
        } else {
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(StatusCode.COM0005.getMessage()) // COM0005(잘못된 접근입니다.)
                    .build();
        }
        requestSearchOrderVO.setLoginUsisId(loginUtlinsttId);
        requestSearchOrderVO.setLoginUserId(loginUserId);
        requestSearchOrderVO.setRgsnUserId(requestSearchOrderVO.getLoginUserId()); // 등록 사용자 ID

        // validation(입력값)
        String validateRes = validateReqValForPayCancel(requestSearchOrderVO);
        if (!validateRes.equals("")) {
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(validateRes)
                    .build();
        }

        logger.debug("ordnPdfInfoSqnList : " + requestSearchOrderVO.getOrdnPdfInfoSqnList().toString());
        for (OrderReqProductVO ordnPdfInfoSqn : requestSearchOrderVO.getOrdnPdfInfoSqnList()) {
            requestSearchOrderVO.setOrdnPdfInfoSqn(ordnPdfInfoSqn.getInfoSqn());

            // validation(입력값) // 구매자
            OrderReqProductVO ordnPdfInfo = orderReqSttsRepo.searchOrderPdf(requestSearchOrderVO);
            if (ordnPdfInfo == null) {
                return ResponseData.builder()
                        .code(HttpStatus.BAD_REQUEST.value())
                        .message(StatusCode.ORDER0002.getMessage()) // 주문정보가 올바르지 않습니다.
                        .build();
            }

            // validation(주문 상태 ID)
            if (!ordnPdfInfo.getOrdnSttsId().equals(ComCode.ODS00002.getCode())) { // 주문취소 승인(ODS00002)
                return ResponseData.builder()
                        .code(HttpStatus.BAD_REQUEST.value())
                        .message(StatusCode.ORDER0033.getMessage()) // 주문취소 승인 상태가 아닙니다.
                        .build();
            }

            // 공통 처리

            // DB 처리(주문상품정보)
            requestSearchOrderVO.setOrdnSttsId(ComCode.ODS00008.getCode()); // 주문취소 완료(ODS00008)
            orderReqSttsRepo.updateOrderPdfStts(requestSearchOrderVO);

            // DB 처리(주문상품정보 이력)
            orderReqSttsRepo.insertOrderPdfH(requestSearchOrderVO);
        }

        OrderReqStlmVO orderReqStlmVO = new OrderReqStlmVO(requestSearchOrderVO.getOrdnInfoId());
        orderReqStlmVO.setRgsnUserId(requestSearchOrderVO.getRgsnUserId());

        // validation(주문 정보)
        SummaryOrdnStlmVO summaryOrdnStlmVO = orderReqStlmRepo.searchOrderInfo(orderReqStlmVO);
        if (summaryOrdnStlmVO == null) {
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(StatusCode.ORDER0002.getMessage())
                    .build();
        } else {
            if (summaryOrdnStlmVO.getStlmInfoId() == null) {
                return ResponseData.builder()
                        .code(HttpStatus.BAD_REQUEST.value())
                        .message(StatusCode.ORDER0002.getMessage())
                        .build();
            } else {
                orderReqStlmVO.setStlmInfoId(summaryOrdnStlmVO.getStlmInfoId());
                orderReqStlmVO.setStlmsttsId(ComCode.PYS01002.getCode()); // 결제취소(PYS01002)
                // DB 처리(결제 정보)
                orderReqStlmRepo.updatePdfPayLForPayCancel(orderReqStlmVO);
            }
        }

        // DB 처리(결제 정보 이력)
        orderReqStlmRepo.insertPdfPayH(orderReqStlmVO);

        return ResponseData.builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .build();
    }

    /**
     * 판매자 > 배송요청
     * -조건1. 판매자만 가능
     * -조건2. 주문상태(ODS00001)에서만 가능
     * -처리1. 지정된 주문에 대해 주문상태(배송중 ODS00003)로 업데이트
     * @return
     */
    public ResponseData orderProductDeliveryRequest(RequestSearchOrderVO requestSearchOrderVO) throws Exception {
        /*
         * 현재 상태 : 주문
         * update 할 상태 : 주문
         * 현재 배송 유형 : 직접배송, 무료배송
         * update 할 배송 유형 : 화물서비스
         */

        // 로그인 정보
        String loginUserId = null;
        String loginUtlinsttId = null;
        AbstractMap.SimpleImmutableEntry<String, String> auth = authInSvc();
        if (auth.getKey() != null && auth.getValue() != null) {
            loginUserId = auth.getKey();
            loginUtlinsttId = auth.getValue();
        } else {
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(StatusCode.COM0005.getMessage()) // COM0005(잘못된 접근입니다.)
                    .build();
        }
        requestSearchOrderVO.setLoginUsisId(loginUtlinsttId);
        requestSearchOrderVO.setLoginUserId(loginUserId);
        requestSearchOrderVO.setRgsnUserId(requestSearchOrderVO.getLoginUserId()); // 등록 사용자 ID

        // validation(입력값)
        String validateRes = validateReqValForDeliveryRequest(requestSearchOrderVO);
        if (!validateRes.equals("")) {
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(validateRes)
                    .build();
        }

        // validation(입력값) // 판매자

        // validation(배송 유형 ID, 주문 상태 ID)
        OrderReqDeliveryVO orderReqDeliveryVO = orderReqRepo.searchOrderProductDelivery(requestSearchOrderVO); // 배송정보입력에서 쓰는 repo method 와 같은 repo method 이다.
        if (orderReqDeliveryVO == null) {
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(StatusCode.ORDER0002.getMessage()) // ORDER0002(주문정보가 올바르지 않습니다.)
                    .build();
        } else {
            if (!Arrays.asList(ComCode.GDS02002.getCode(), ComCode.GDS02003.getCode())
                    .contains(orderReqDeliveryVO.getDvryPtrnId())) { // 배송 유형이 직접배송, 무료배송인지 검증한다
                return ResponseData.builder()
                        .code(HttpStatus.BAD_REQUEST.value())
                        .message(StatusCode.ORDER0024.getMessage()) // 직접배송 또는 무료배송인 경우 가능합니다.
                        .build();
            }
            if (!orderReqDeliveryVO.getOrdnSttsId().equals(ComCode.ODS00001.getCode())) { // 주문(ODS00001)
                return ResponseData.builder()
                        .code(HttpStatus.BAD_REQUEST.value())
                        .message(StatusCode.ORDER0019.getMessage()) // 주문 상태가 아닙니다.
                        .build();
            }
        }

        // 공통 처리

        /*
         * 운송의뢰 처리.
         * 배송업체에 운송의뢰(feign 호출)를 한다.
         * 운송의뢰 성공(feign 성공)시 커머스 쪽 DB 처리를 한다.
         */
        OrderDvryVO orderDvryVO = new OrderDvryVO();
        List<OrderDvryPdfVO> orderDvryPdfVOList = new ArrayList<>();
        OrderDvryPdfVO orderDvryPdfVO = new OrderDvryPdfVO();
        orderDvryPdfVO.setOrdnInfoId(requestSearchOrderVO.getOrdnInfoId());
        orderDvryPdfVO.setInfoSqn(requestSearchOrderVO.getOrdnPdfInfoSqn());
        orderDvryPdfVO.setPdfInfoId(orderReqDeliveryVO.getPdfInfoId());
        orderDvryPdfVO.setPdfNm(orderReqDeliveryVO.getPdfNm());
        orderDvryPdfVO.setPdfQty(orderReqDeliveryVO.getPdfQty());
        orderDvryPdfVO.setPdfPrc(orderReqDeliveryVO.getPdfPrc());
        // orderDvryPdfVO.setDvrynone(orderReqDeliveryVO.getDvrynone()); // 직접배송/무료배송일 때 책정된 배송비
        orderDvryPdfVO.setDvrynone(requestSearchOrderVO.getDvrynone()); // 배송정보입력 때 견적나온 배송비
        orderDvryPdfVOList.add(orderDvryPdfVO);
        orderDvryVO.setOrderDvryPdfVOList(orderDvryPdfVOList);
        orderDvryVO.setRlontfZpcd(orderReqDeliveryVO.getRlontfZpcd());
        orderDvryVO.setRlontfAdr(orderReqDeliveryVO.getRlontfAdr());
        orderDvryVO.setRlontfDtad(orderReqDeliveryVO.getRlontfDtad());
        orderDvryVO.setRecvZpcd(orderReqDeliveryVO.getRecvZpcd());
        orderDvryVO.setRecvAdr(orderReqDeliveryVO.getRecvAdr());
        orderDvryVO.setRecvDtad(orderReqDeliveryVO.getRecvDtad());
        orderDvryVO.setRecv(orderReqDeliveryVO.getRecv());
        orderDvryVO.setRecvCnplone(orderReqDeliveryVO.getRecvCnplone());
        orderDvryVO.setEsmDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date())); // 배송요청 전, 운임체크를 하기 때문에, 견적일자는 오늘일자로 set
        orderDvryVO.setRgsnUserId(requestSearchOrderVO.getLoginUserId());
        orderDvryVO.setOrdnInfoId(requestSearchOrderVO.getOrdnInfoId());
        OrderDvryResVO orderDvryResVO = null;
        orderDvryResVO = orderDvryService.requestDvryDvryChunil(orderDvryVO, OrderDvryService.DvryServiceKind.OrderReqSttsService);

        if (orderDvryResVO != null &&
                orderDvryResVO.getErrMsg() != null &&
                orderDvryResVO.getErrMsg().equals("")) {
            /*
             * 운송의뢰 성공
             */

            // DB 처리(주문 화물서비스 배송정보) // insert
            requestSearchOrderVO.setTrspreqsNo(orderDvryResVO.getReqnumNo()); // 운송의뢰 번호
            requestSearchOrderVO.setMainnbNo(orderDvryResVO.getMainnbNoList().get(0).get("MAINNB").toString()); // 운송장 번호
            orderReqSttsRepo.insertOrderDvryForDvryInput(requestSearchOrderVO);

            // DB 처리(주문 구매자 배송정보) // delete
            orderReqSttsRepo.deleteOrderPDvryForDvryInput(requestSearchOrderVO);

            // DB 처리(주문상품정보)
            requestSearchOrderVO.setDvryPtrnId(ComCode.GDS02001.getCode()); // GDS02001(화물서비스 이용)
            requestSearchOrderVO.setDvryInfoId(ComCode.DELIVERY_INFO_DVRY_R.getCode()); // ODS02001(화물서비스 배송정보)
            orderReqSttsRepo.updateOrderPdfForDvryInput(requestSearchOrderVO);

            // DB 처리(주문상품정보 이력)
            orderReqSttsRepo.insertOrderPdfH(requestSearchOrderVO);

            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .build();
        } else {
            String messageStr = StatusCode.ORDER0035.getMessage(); // 배송요청 중 오류가 발생하였습니다.
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(messageStr)
                    .build();
        }
    }

    /**
     * 판매자 > 배송정보입력 > 운송장번호
     * @param requestSearchOrderVO
     * @return
     */
    public ResponseData orderProductDeliveryUpdate(RequestSearchOrderVO requestSearchOrderVO) {
        /*
         * 현재 상태 : 주문
         * update 할 상태 : 배송중
         * 현재 배송 유형 : 직접배송, 무료배송
         */

        // 로그인 정보
        String loginUserId = null;
        String loginUtlinsttId = null;
        AbstractMap.SimpleImmutableEntry<String, String> auth = authInSvc();
        if (auth.getKey() != null && auth.getValue() != null) {
            loginUserId = auth.getKey();
            loginUtlinsttId = auth.getValue();
        } else {
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(StatusCode.COM0005.getMessage()) // COM0005(잘못된 접근입니다.)
                    .build();
        }
        requestSearchOrderVO.setLoginUsisId(loginUtlinsttId);
        requestSearchOrderVO.setLoginUserId(loginUserId);
        requestSearchOrderVO.setRgsnUserId(requestSearchOrderVO.getLoginUserId()); // 등록 사용자 ID

        // validation(입력값)
        String validateRes = validateReqValForDeliveryUpdate(requestSearchOrderVO);
        if (!validateRes.equals("")) {
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(validateRes)
                    .build();
        }

        // validation(입력값) // 판매자
        OrderReqProductVO ordnPdfInfo = orderReqSttsRepo.searchOrderPdf(requestSearchOrderVO);
        if (ordnPdfInfo == null) {
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(StatusCode.ORDER0002.getMessage()) // 주문정보가 올바르지 않습니다.
                    .build();
        }

        // validation(주문 상태 ID)
        if (!ordnPdfInfo.getOrdnSttsId().equals(ComCode.ODS00001.getCode())) { // 주문(ODS00001)
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(StatusCode.ORDER0019.getMessage()) // 주문 상태가 아닙니다.
                    .build();
        }

        // validation(배송 유형 ID)
        if (!Arrays.asList(ComCode.GDS02002.getCode(), ComCode.GDS02003.getCode())
                .contains(ordnPdfInfo.getDvryPtrnId())) { // 직접배송(GDS02002), 무료배송(GDS02003)
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(StatusCode.ORDER0024.getMessage()) // 직접배송 또는 무료배송인 경우 가능합니다.
                    .build();
        }

        // 공통 처리

        // DB 처리(주문 구매자 배송정보) // 운송장번호, 택배사 유형 ID, 기타택배사명
        orderReqSttsRepo.updateOrderPDvry(requestSearchOrderVO);

        // DB 처리(주문상품정보)
        requestSearchOrderVO.setOrdnSttsId(ComCode.ODS00003.getCode()); // 배송중(ODS00003)
        orderReqSttsRepo.updateOrderPdfStts(requestSearchOrderVO);

        // DB 처리(주문상품정보 이력)
        orderReqSttsRepo.insertOrderPdfH(requestSearchOrderVO);

        return ResponseData.builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .build();
    }

    /**
     * 입력값 검증
     * @param requestSearchOrderVO
     * @return
     */
    private String validateReqValForReceiptCheck(RequestSearchOrderVO requestSearchOrderVO) {

        if (requestSearchOrderVO.getOrdnInfoId() == null || requestSearchOrderVO.getOrdnInfoId().equals("")) {
            return StatusCode.ORDER0017.getMessage(); // 주문 정보 ID를 확인바랍니다.
        }
        if (requestSearchOrderVO.getOrdnPdfInfoSqn() == null || requestSearchOrderVO.getOrdnPdfInfoSqn().equals("")) {
            return StatusCode.ORDER0018.getMessage(); // 주문 정보 순번을 확인바랍니다.
        }

        return "";
    }

    private String validateReqValForCancel(RequestSearchOrderVO requestSearchOrderVO) {

        if (requestSearchOrderVO.getOrdnInfoId() == null || requestSearchOrderVO.getOrdnInfoId().equals("")) {
            return StatusCode.ORDER0017.getMessage(); // 주문 정보 ID를 확인바랍니다.
        }
//        if (requestSearchOrderVO.getOrdnPdfInfoSqn() == null || requestSearchOrderVO.getOrdnPdfInfoSqn().equals("")) {
//            return StatusCode.ORDER0018.getMessage(); // 주문 정보 순번을 확인바랍니다.
//        }
        if (requestSearchOrderVO.getOrdnPdfInfoSqnList() == null || requestSearchOrderVO.getOrdnPdfInfoSqnList().size() == 0) {
            return StatusCode.ORDER0034.getMessage(); // 주문 정보 순번 리스트를 확인바랍니다.
        }

        return "";
    }

    private String validateReqValForPayCancel(RequestSearchOrderVO requestSearchOrderVO) {

        if (requestSearchOrderVO.getOrdnInfoId() == null || requestSearchOrderVO.getOrdnInfoId().equals("")) {
            return StatusCode.ORDER0017.getMessage(); // 주문 정보 ID를 확인바랍니다.
        }
//        if (requestSearchOrderVO.getOrdnPdfInfoSqn() == null || requestSearchOrderVO.getOrdnPdfInfoSqn().equals("")) {
//            return StatusCode.ORDER0018.getMessage(); // 주문 정보 순번을 확인바랍니다.
//        }
        if (requestSearchOrderVO.getOrdnPdfInfoSqnList() == null || requestSearchOrderVO.getOrdnPdfInfoSqnList().size() == 0) {
            return StatusCode.ORDER0034.getMessage(); // 주문 정보 순번 리스트를 확인바랍니다.
        }

        return "";
    }

    private String validateReqValForDeliveryUpdate(RequestSearchOrderVO requestSearchOrderVO) {

        if (requestSearchOrderVO.getOrdnInfoId() == null || requestSearchOrderVO.getOrdnInfoId().equals("")) {
            return StatusCode.ORDER0017.getMessage(); // 주문 정보 ID를 확인바랍니다.
        }
        if (requestSearchOrderVO.getOrdnPdfInfoSqn() == null || requestSearchOrderVO.getOrdnPdfInfoSqn().equals("")) {
            return StatusCode.ORDER0018.getMessage(); // 주문 정보 순번을 확인바랍니다.
        }
        if (requestSearchOrderVO.getPcsvcmpPtrnId() == null || requestSearchOrderVO.getPcsvcmpPtrnId().equals("")) {
            return StatusCode.ORDER0022.getMessage(); // 택배사 유형 ID를 확인바랍니다.
        } else {
            if (requestSearchOrderVO.getPcsvcmpPtrnId().equals(ComCode.ODS01013.getCode())) { // 기타(직접입력)(ODS01013)
                if (requestSearchOrderVO.getPcsvcmpNm() == null || requestSearchOrderVO.getPcsvcmpNm().equals("")) {
                    return StatusCode.ORDER0027.getMessage(); // 기타 택배사명을 확인바랍니다.
                }
            } else {
                if (requestSearchOrderVO.getPcsvcmpNm() != null && !requestSearchOrderVO.getPcsvcmpNm().equals("")) {
                    return StatusCode.ORDER0027.getMessage(); // 기타 택배사명을 확인바랍니다.
                }
            }
        }
        if (requestSearchOrderVO.getMainnbNo() == null || requestSearchOrderVO.getMainnbNo().equals("")) {
            return StatusCode.ORDER0023.getMessage(); // 운송장 번호를 확인바랍니다.
        }

        return "";
    }

    private String validateReqValForDeliveryRequest(RequestSearchOrderVO requestSearchOrderVO) {
        if (requestSearchOrderVO.getEntpInfoId() == null || requestSearchOrderVO.getEntpInfoId().equals("")) {
            return StatusCode.ORDER0032.getMessage(); // 배송 업체 정보 ID를 확인바랍니다.
        }
        if (requestSearchOrderVO.getOrdnInfoId() == null || requestSearchOrderVO.getOrdnInfoId().equals("")) {
            return StatusCode.ORDER0017.getMessage(); // 주문 정보 ID를 확인바랍니다.
        }
        if (requestSearchOrderVO.getOrdnPdfInfoSqn() == null || requestSearchOrderVO.getOrdnPdfInfoSqn().equals("")) {
            return StatusCode.ORDER0018.getMessage(); // 주문 정보 순번을 확인바랍니다.
        }
        if (requestSearchOrderVO.getDvrynone() == null || requestSearchOrderVO.getDvrynone() <= 0) {
            return StatusCode.ORDER0005.getMessage(); // 배송비를 확인바랍니다.
        }

        return "";
    }

}
