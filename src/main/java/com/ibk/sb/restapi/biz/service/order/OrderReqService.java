package com.ibk.sb.restapi.biz.service.order;

import com.ibk.sb.restapi.app.common.constant.AlarmCode;
import com.ibk.sb.restapi.app.common.constant.ComCode;
import com.ibk.sb.restapi.app.common.constant.StatusCode;
import com.ibk.sb.restapi.app.common.util.BizException;
import com.ibk.sb.restapi.app.common.util.FileUtil;
import com.ibk.sb.restapi.app.common.vo.CustomUser;
import com.ibk.sb.restapi.app.common.vo.ResponseData;
import com.ibk.sb.restapi.biz.service.alarm.AlarmService;
import com.ibk.sb.restapi.biz.service.alarm.vo.RequestAlarmVO;
import com.ibk.sb.restapi.biz.service.basket.repo.BasketRepo;
import com.ibk.sb.restapi.biz.service.estimation.EstimationService;
import com.ibk.sb.restapi.biz.service.estimation.repo.EstimationRepo;
import com.ibk.sb.restapi.biz.service.estimation.vo.EstimationProductVO;
import com.ibk.sb.restapi.biz.service.estimation.vo.RequestSearchEstimationVO;
import com.ibk.sb.restapi.biz.service.order.repo.OrderReqDetailRepo;
import com.ibk.sb.restapi.biz.service.order.repo.OrderReqRepo;
import com.ibk.sb.restapi.biz.service.order.repo.OrderReqStlmRepo;
import com.ibk.sb.restapi.biz.service.order.vo.req.*;
import com.ibk.sb.restapi.biz.service.seller.repo.SellerStoreRepo;
import com.ibk.sb.restapi.biz.service.seller.vo.SellerInfoVO;
import com.ibk.sb.restapi.biz.service.user.UserService;
import com.ibk.sb.restapi.biz.service.user.vo.CompanyVO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class OrderReqService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private final OrderReqRepo orderReqRepo;

    private final OrderReqDetailRepo orderReqDetailRepo;

    private final OrderReqStlmRepo orderReqStlmRepo;

    private final OrderDvryService orderDvryService;

    private final SellerStoreRepo sellerStoreRepo;

    private final BasketRepo basketRepo;

    private final UserService userService;

    private final EstimationRepo estimationRepo;

    private final FileUtil fileUtil;

    // 알림 서비스
    private final AlarmService alarmService;

    public enum OrderApiKind {
        OrderApiSave, // 주문등록
        OrderApiProductDeliveryAmt, // 주문 > 운임체크(화물서비스)
        OrderApiProductDeliveryList // 배송정보입력 > 화물서비스
    }

    private final EstimationService estimationService;

    private AbstractMap.SimpleImmutableEntry<String, String> authInSvc() {
        // 로그인 정보
        String loginUserId = null;
        String loginUtlinsttId = null;
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof CustomUser) {
            CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            loginUserId = user.getLoginUserId();
            loginUtlinsttId = user.getUtlinsttId();
        }
//        loginUserId = "box14601";
//        loginUtlinsttId = "C0000151";
        return new AbstractMap.SimpleImmutableEntry<>(loginUserId, loginUtlinsttId);
    }

    /**
     * 주문하기
     * @return
     */
    public ResponseData orderProductPay(RequestSearchOrderVO requestSearchOrderVO) throws Exception {
        /*
         * 결제하기를 누를 때이다.
         * 입력 VO 를 바탕으로, 각 테이블에 insert 한다. 입력 VO 에 없는 정보(selrUsisId 등)는 DB 에서 읽어온다.
         * 견적에서 왔느냐 장바구니에서 왔느냐에 따라 적절하게 분기한다(예를 들어, 견적에서 온 경우 견적상품(GDS03004) 으로 set 한다).
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

        requestSearchOrderVO.setPucsUsisId(requestSearchOrderVO.getLoginUsisId()); // 구매자 이용기관 ID
        requestSearchOrderVO.setPucsId(requestSearchOrderVO.getLoginUserId()); // 구매자 사용자 ID

        requestSearchOrderVO.setRgsnUserId(requestSearchOrderVO.getLoginUserId()); // 등록 사용자 ID

        // validation(주문가능권한)
        // 주문가능권한(정회원, 에이전시, 준회원)
        SellerInfoVO sellerInfoVO = sellerStoreRepo.selectSellerInfo(requestSearchOrderVO.getLoginUsisId());
        if (sellerInfoVO == null) {
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(StatusCode.LOGIN_NOT_USER_INFO.getMessage())
                    .build();
        }
        if (!Arrays.asList(ComCode.REGULAR_MEMBER.getCode(), ComCode.AGENCY_MEMBER.getCode(), ComCode.ASSOCIATE_MEMBER.getCode())
                .contains(sellerInfoVO.getMmbrtypeId())) {
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(StatusCode.ORDER0001.getMessage())
                    .build();
        }

        // validation(입력값)
        String validateRes = validateReqValForSave(requestSearchOrderVO, OrderApiKind.OrderApiSave);
        if (!validateRes.equals("")) {
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(validateRes)
                    .build();
        }

        // validation(입력값)
        validateRes = validateReqValForStlmSave(requestSearchOrderVO.getOrderReqStlmVO());
        if (!validateRes.equals("")) {
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(validateRes)
                    .build();
        }

        // 공통 처리
//        String ordnInfoId = UUID.randomUUID().toString();
//        String ordnInfoId = "test0705-" + UUID.randomUUID().toString();
        // validation(주문 정보 ID)
        String ordnInfoId = requestSearchOrderVO.getOrderReqStlmVO().getOrdnInfoId(); // 미리 채번된 주문 정보 ID
        requestSearchOrderVO.setOrdnInfoId(ordnInfoId); // 주문 정보 ID
//        String[] ordnInfoIds = ordnInfoId.split("-");
//        String dateStr = new SimpleDateFormat("yyyyMMdd").format(new Date());
//        requestSearchOrderVO.setCnttNoId(dateStr + "-" + ordnInfoIds[0]);
        requestSearchOrderVO.setCnttNoId(requestSearchOrderVO.getOrderReqStlmVO().getCnttNoId()); // 프런트에서 온 체결 번호 ID
//        requestSearchOrderVO.setCnttNoId(dateStr + "-" + ordnInfoIds[0] + "-" + ordnInfoIds[1]);

        // 변수 처리(견적 여부)
        boolean isEsm = false;
        if (requestSearchOrderVO.getEsttInfoId() != null && !requestSearchOrderVO.getEsttInfoId().equals("")) {
            isEsm = true;
        }

        // 값 처리(판매자)
        String pdfInfoIdForSelr = "";
        for (OrderReqProductVO vo : requestSearchOrderVO.getProducts()) {
            if (vo.getPdfInfoId() != null && !vo.getPdfInfoId().equals("")) {
                pdfInfoIdForSelr = vo.getPdfInfoId();
                break;
            }
        }
        OrderReqProductVO selrInfo = orderReqRepo.searchPdfInfo(pdfInfoIdForSelr);
        requestSearchOrderVO.setSelrUsisId(selrInfo.getSelrUsisId());
        requestSearchOrderVO.setSelrId(selrInfo.getSelrId());

        // DB 처리(주문 정보)
        orderReqRepo.insertOrderInfo(requestSearchOrderVO);

        List<OrderReqDeliveryVO> dvryRequestList = new ArrayList<>();
        List<OrderReqProductVO> productVOList = requestSearchOrderVO.getProducts();
        AtomicInteger indexHolder = new AtomicInteger();
//        productVOList.forEach(x -> {
        for (OrderReqProductVO x : productVOList) {
            x.setOrdnInfoId(requestSearchOrderVO.getOrdnInfoId());
//            Integer infoSqn = indexHolder.getAndIncrement(); // 0 부터 시작

            if(indexHolder.get() < 0 || indexHolder.get() > Integer.MAX_VALUE - 1) {
                throw new BizException(StatusCode.COM0000);
            }
            Integer infoSqn = indexHolder.incrementAndGet(); // 1 부터 시작

            x.setInfoSqn(infoSqn);
            x.setOrdnSttsId(ComCode.ODS00001.getCode()); // 주문(ODS00001)
            x.setRgsnUserId(requestSearchOrderVO.getRgsnUserId());
            x.setDvryInfoId(retrieveDvryInfoId(x.getDvryPtrnId())); // 배송정보 유형
            if (isEsm) {
                /*
                 * 견적
                 */
                x.setOrdnPtrnId(ComCode.GDS03004.getCode()); // 견적상품
                if (x.getPdfInfoId() == null || x.getPdfInfoId().equals("")) {
//                    x.setPdfInfoId(x.getEsttPdfPtrnId()); // 직접입력인 경우이다
                    x.setPdfInfoId(""); // 직접입력인 경우이다
                }
            } else {
                /*
                 * 장바구니
                 */
                x.setOrdnPtrnId(ComCode.GDS03001.getCode()); // 개별상품
                OrderReqProductVO pdfInfo = orderReqRepo.searchPdfInfo(x.getPdfInfoId());
                if (pdfInfo == null) {
                    return ResponseData.builder()
                            .code(HttpStatus.BAD_REQUEST.value())
                            .message(StatusCode.ORDER0003.getMessage()) // 상품을 확인바랍니다.
                            .build();
                }
                x.setPdfCtgyId(pdfInfo.getPdfCtgyId());
                x.setPdfNm(pdfInfo.getPdfNm());
            }

            // DB 처리(주문 상품 정보)
            orderReqRepo.insertOrderPdf(x);

            OrderReqDeliveryVO orderReqDeliveryVO = new OrderReqDeliveryVO();
            orderReqDeliveryVO.setOrdnInfoId(requestSearchOrderVO.getOrdnInfoId());
            orderReqDeliveryVO.setInfoSqn(infoSqn);
            orderReqDeliveryVO.setDvrynone(x.getDvrynone());
            orderReqDeliveryVO.setRecv(requestSearchOrderVO.getRecv());
            orderReqDeliveryVO.setRecvCnplone(requestSearchOrderVO.getRecvCnplone());
            orderReqDeliveryVO.setRecvCnpltwo(requestSearchOrderVO.getRecvCnpltwo());
            orderReqDeliveryVO.setRecvZpcd(requestSearchOrderVO.getRecvZpcd());
            orderReqDeliveryVO.setRecvAdr(requestSearchOrderVO.getRecvAdr());
            orderReqDeliveryVO.setRecvDtad(requestSearchOrderVO.getRecvDtad());
            orderReqDeliveryVO.setRgsnUserId(requestSearchOrderVO.getRgsnUserId());

            // 값 처리(출고지)
            /*
             * 견적은 requestSearchOrderVO 에서 출고지를 꺼내고, 장바구니는 DB(상품 화물서비스 기본정보)에서 출고지를 읽는다.
             */
            if (x.getDvryPtrnId().equals(ComCode.GDS02001.getCode())) { // 화물서비스(GDS02001)
                if (isEsm) {
                    /*
                     * 견적
                     */
                    orderReqDeliveryVO.setRlontfZpcd(requestSearchOrderVO.getRlontfZpcd());
                    orderReqDeliveryVO.setRlontfAdr(requestSearchOrderVO.getRlontfAdr());
                    orderReqDeliveryVO.setRlontfDtad(requestSearchOrderVO.getRlontfDtad());
                    orderReqDeliveryVO.setEntpInfoId(requestSearchOrderVO.getEntpInfoId());
                } else {
                    /*
                     * 장바구니
                     */
                    OrderReqProductVO rlontfInfo = orderReqRepo.searchPdfInfo(x.getPdfInfoId());
                    if (rlontfInfo.getRlontfZpcd() == null || rlontfInfo.getRlontfZpcd().equals("")
                            || rlontfInfo.getRlontfAdr() == null || rlontfInfo.getRlontfAdr().equals("")) {
                        return ResponseData.builder()
                                .code(HttpStatus.BAD_REQUEST.value())
                                .message(StatusCode.ORDER0015.getMessage()) // 상품 출고지를 확인바랍니다.
                                .build();
                    }
                    orderReqDeliveryVO.setRlontfZpcd(rlontfInfo.getRlontfZpcd());
                    orderReqDeliveryVO.setRlontfAdr(rlontfInfo.getRlontfAdr());
                    orderReqDeliveryVO.setRlontfDtad(rlontfInfo.getRlontfDtad());
                    orderReqDeliveryVO.setEntpInfoId(rlontfInfo.getEntpInfoId());
                }
            }

            if (x.getDvryPtrnId().equals(ComCode.GDS02001.getCode())) { // 화물서비스
                // DB 처리(주문 배송 정보)
                orderReqRepo.insertOrderDvryR(orderReqDeliveryVO);
            } else if (x.getDvryPtrnId().equals(ComCode.GDS02002.getCode())) { // 직접배송
                // DB 처리(주문 배송 정보)
                orderReqRepo.insertOrderPdvryR(orderReqDeliveryVO);
            } else if (x.getDvryPtrnId().equals(ComCode.GDS02003.getCode())) { // 무료배송
                // DB 처리(주문 배송 정보)
                orderReqRepo.insertOrderPdvryR(orderReqDeliveryVO);
            } else if (x.getDvryPtrnId().equals(ComCode.GDS02004.getCode())) { // 구매자수령
                // DB 처리(주문 배송 정보)
                orderReqRepo.insertOrderUdvrR(orderReqDeliveryVO);
            }

            // DB 처리(주문 상품 정보 이력)
            orderReqRepo.insertOrderPdfH(x);

            // 값 처리(운송의뢰 처리시 사용)
            orderReqDeliveryVO.setPdfInfoId(x.getPdfInfoId());
            orderReqDeliveryVO.setPdfNm(x.getPdfNm());
            orderReqDeliveryVO.setPdfQty(x.getQty());
            orderReqDeliveryVO.setPdfPrc(x.getPdfPrc());
            orderReqDeliveryVO.setDvryPtrnId(x.getDvryPtrnId());
            dvryRequestList.add(orderReqDeliveryVO);
        }

        // DB 처리(구매자 정보(배송지 사용 여부))
        if (requestSearchOrderVO.getDlplUseYn().equals("Y")) {
            orderReqRepo.updatePucsInfoForY(requestSearchOrderVO);
        } else {
            orderReqRepo.updatePucsInfoForN(requestSearchOrderVO);
        }

        if (isEsm) {
            /*
             * 견적
             */
            // DB 처리(견적 정보(주문 정보 ID, 처리 상태 ID))
            requestSearchOrderVO.setPcsnSttsId(ComCode.ESS02002.getCode()); // 결제(ESS02002)
            orderReqRepo.updateEsmInfoForOrdnId(requestSearchOrderVO);
            // DB 처리(문의 견적 연관정보(처리 상태 ID))
            orderReqRepo.updateInesR(requestSearchOrderVO);
            // DB 처리(문의 상세 정보(내용))
            requestSearchOrderVO.setCon(StatusCode.ESTIMATION1005.getMessage()); // 견적이 결제되었습니다.
            orderReqRepo.updateInquR(requestSearchOrderVO);
            // DB 처리(견적 정보 변경 이력)
            RequestSearchEstimationVO requestSearchEstimationVO = new RequestSearchEstimationVO();
            requestSearchEstimationVO.setEsttInfoId(requestSearchOrderVO.getEsttInfoId());
            requestSearchEstimationVO.setRgsnUserId(requestSearchOrderVO.getRgsnUserId());
            requestSearchEstimationVO.setAmnnUserId("");
            requestSearchEstimationVO.setPcsnCon(StatusCode.ESTIMATION1005.getMessage()); // 처리 내용
            estimationRepo.insertEstimationInfoHistory(requestSearchEstimationVO);

            /*
             * 알림 서비스
             */
            // 알림 발송 기업사업장 주요 정보 조회
            CompanyVO companyVO = userService.searchUserCompanyInfo(requestSearchOrderVO.getPucsUsisId());
            // 알림 요청 인스턴스 생성
            RequestAlarmVO requestAlarmVO = alarmService.getAlarmRequest(AlarmCode.AlarmCodeEnum.ESTIMATE_PAY, requestSearchOrderVO.getOrdnInfoId() + "&selr", new Object[]{companyVO.getBplcNm()});
            // 알림 발송
            alarmService.sendMarketAlarm(requestAlarmVO, requestSearchOrderVO.getSelrUsisId());

        } else {
            /*
             * 장바구니
             */
//            if (false) { // 장바구니 삭제 보류
//                BasketVO basketVO = new BasketVO();
//                basketVO.setPucsUsisId(requestSearchOrderVO.getPucsUsisId());
//                basketVO.setPucsId(requestSearchOrderVO.getPucsId());
//                for (OrderReqProductVO x : requestSearchOrderVO.getProducts()) {
//                    // DB 처리(장바구니 상품 삭제)
//                    basketVO.setPdfInfoId(x.getPdfInfoId());
//                    basketRepo.deleteBasket(basketVO);
//                }
//            }
        }

        // DB 처리(결제 정보)
        settlementSave(requestSearchOrderVO.getOrderReqStlmVO());

        // DB 처리(세금계산서 발급 정보)

        SummaryOrderResultVO summaryOrderResultVO = new SummaryOrderResultVO();
        summaryOrderResultVO.setResultStr(requestSearchOrderVO.getOrdnInfoId());

        return ResponseData.builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(summaryOrderResultVO)
                .build();
    }

    private void settlementSave(OrderReqStlmVO orderReqStlmVO) {
        /*
         * 결제 정보 insert
         * 주문 정보 update(stlmInfoId)
         * 결제 정보 이력 insert(infoSqn 증가)
         */

        String stlmInfoId = UUID.randomUUID().toString();
        orderReqStlmVO.setStlmInfoId(stlmInfoId);
        // DB 처리(결제 정보)
        orderReqStlmRepo.insertPdfPayL(orderReqStlmVO);

        // DB 처리(주문 정보(결제 정보 ID))
        orderReqStlmRepo.updateOrderInfoForStlm(orderReqStlmVO);

        // DB 처리(결제 정보 이력)
        orderReqStlmRepo.insertPdfPayH(orderReqStlmVO);

    }

    /**
     * 견적서 주문하기
     * @return
     */
    public ResponseData orderProductEsmPay(RequestSearchOrderVO requestSearchOrderVO) throws Exception {
        /*
         * 결제하기를 누를 때이다.
         * 견적상품 등은 DB 에서 읽어 온다.
         */

        // validation(견적 정보 ID)
        if (requestSearchOrderVO.getEsttInfoId() == null || requestSearchOrderVO.getEsttInfoId().equals("")) {
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(StatusCode.ORDER0013.getMessage()) // 견적 정보 ID를 확인바랍니다.
                    .build();
        }

        // 공통 처리

        // DB 처리(견적 정보)
        OrderReqDeliveryVO esmInfo = orderReqRepo.searchEsmInfo(requestSearchOrderVO);
        if (esmInfo == null) {
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(StatusCode.ORDER0013.getMessage()) // 견적 정보 ID를 확인바랍니다.
                    .build();
        } else if (esmInfo.getPcsnSttsId() != null && !esmInfo.getPcsnSttsId().equals(ComCode.ESS02001.getCode())) {
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(StatusCode.ORDER0025.getMessage()) // 견적 정보 ID를 확인바랍니다.
                    .build();
        }

        // 값 처리(출고지)
        if (esmInfo.getDvryPtrnId().equals(ComCode.GDS02001.getCode())) { // 화물서비스
            requestSearchOrderVO.setRlontfZpcd(esmInfo.getRlontfZpcd());
            requestSearchOrderVO.setRlontfAdr(esmInfo.getRlontfAdr());
            requestSearchOrderVO.setRlontfDtad(esmInfo.getRlontfDtad());
            requestSearchOrderVO.setEntpInfoId(esmInfo.getEntpInfoId());
        }

        // 값 처리(수령지)
        if (esmInfo.getDvryPtrnId().equals(ComCode.GDS02001.getCode())) { // 화물서비스
            requestSearchOrderVO.setRecv(esmInfo.getRecv()); // 수령인
            requestSearchOrderVO.setRecvCnplone(esmInfo.getRecvCnplone()); // 수령인 연락처1
            requestSearchOrderVO.setRecvZpcd(esmInfo.getRecvZpcd()); // 수령인 우편번호
            requestSearchOrderVO.setRecvAdr(esmInfo.getRecvAdr()); // 수령인 주소
            requestSearchOrderVO.setRecvDtad(esmInfo.getRecvDtad()); // 수령인 상세주소
        } else if (esmInfo.getDvryPtrnId().equals(ComCode.GDS02002.getCode())) { // 직접배송
        } else if (esmInfo.getDvryPtrnId().equals(ComCode.GDS02003.getCode())) { // 무료배송
        } else if (esmInfo.getDvryPtrnId().equals(ComCode.GDS02004.getCode())) { // 구매자수령
            requestSearchOrderVO.setRecvZpcd(esmInfo.getRecvZpcd()); // 수령인 우편번호
            requestSearchOrderVO.setRecvAdr(esmInfo.getRecvAdr()); // 수령인 주소
            requestSearchOrderVO.setRecvDtad(esmInfo.getRecvDtad()); // 수령인 상세주소
        }

        // 값 처리(상품)
        List<OrderReqProductVO> esmPdfInfoList = orderReqRepo.searchEsmPdfInfoList(requestSearchOrderVO);
        for (OrderReqProductVO vo : esmPdfInfoList) {
        }
        requestSearchOrderVO.setProducts(esmPdfInfoList);

        return orderProductPay(requestSearchOrderVO);
    }

    /**
     * 주문상세 정보보기
     * @param requestSearchOrderVO
     * @return
     * @throws Exception
     */
    public ResponseData searchOrder(RequestSearchOrderVO requestSearchOrderVO) throws Exception {
        /*
         * 메인쿼리에서 주문정보 등을 읽는다.
         * 상품&배송 쿼리에서 상품&배송 정보 등을 읽는다.
         * 상품&배송 정보를 메인쿼리 VO products 에 set 한다.
         * 합계금액, 수령지도 메인쿼리 VO 에 set 한다.
         */

        // 로그인 정보
        String loginUserId = null;
        String loginUtlinsttId = null;
        AbstractMap.SimpleImmutableEntry<String, String> auth = authInSvc();
        if (auth.getKey() != null && auth.getValue() != null) {
            loginUserId = auth.getKey();
            loginUtlinsttId = auth.getValue();
        } else {
            // TODO 비로그인시 처리하기
            //            return ResponseData.builder()
            //                    .code(HttpStatus.BAD_REQUEST.value())
            //                    .message(StatusCode.COM0005.getMessage()) // COM0005(잘못된 접근입니다.)
            //                    .build();
        }
        requestSearchOrderVO.setLoginUsisId(loginUtlinsttId);
        requestSearchOrderVO.setLoginUserId(loginUserId);

        // 공통 처리

        // DB 처리(주문정보)
        SummaryOrderInfoVO result = orderReqDetailRepo.searchOrderInfo(requestSearchOrderVO);
        if (result != null) {
//            // 값 처리()
//            MyPageUserVO myPageUserVO = userService.searchUserCompanyInfo();
//            if (myPageUserVO != null) {
//                logger.debug("bplcNm: " + myPageUserVO.getBplcNm());
//            }
            List<OrderReqProductVO> orderReqProductVOList = orderReqDetailRepo.searchOrderPdfList(requestSearchOrderVO);
            if (orderReqProductVOList.size() > 0) {
                result.setProducts(orderReqProductVOList);

                // 값 처리(상품금액합계, 배송비합계 등)
                Integer pdfAmtSum = 0;
                Integer dvrynoneSum = 0;
                for (OrderReqProductVO vo : orderReqProductVOList) {
                    pdfAmtSum += vo.getTtalAmt();
                    dvrynoneSum += vo.getDvrynone();
                }
                // 값 처리(배송비합계 보정. 견적상품은 배송비를 보정한다)
                if (orderReqProductVOList.get(0).getOrdnPtrnId().equals(ComCode.GDS03004.getCode())) {
                    dvrynoneSum = orderReqProductVOList.get(0).getDvrynone();
                }
                result.setPdfAmtSum(pdfAmtSum); // 상품금액 합계
                result.setDvrynoneSum(dvrynoneSum); // 배송비 합계
                result.setStlmAmt(pdfAmtSum + dvrynoneSum); // 상품금액합계 + 배송비합계 = 결제금액

                // 값 처리(수령지)
                for (OrderReqProductVO vo : orderReqProductVOList) {
                    if (vo.getRecv() != null && !vo.getRecv().equals("") &&
                            vo.getRecvCnplone() != null && !vo.getRecvCnplone().equals("") &&
//                            vo.getRecvCnpltwo() != null && !vo.getRecvCnpltwo().equals("") &&
                            vo.getRecvZpcd() != null && !vo.getRecvZpcd().equals("") &&
                            vo.getRecvAdr() != null && !vo.getRecvAdr().equals("") &&
                            vo.getRecvDtad() != null && !vo.getRecvDtad().equals("")
                        ) {
                        result.setRecv(vo.getRecv());
                        result.setRecvCnplone(vo.getRecvCnplone());
                        result.setRecvCnpltwo(vo.getRecvCnpltwo());
                        result.setRecvZpcd(vo.getRecvZpcd());
                        result.setRecvAdr(vo.getRecvAdr());
                        result.setRecvDtad(vo.getRecvDtad());
                        break;
                    }
                }

                // 값 처리(수령지. 구매자수령의 결제상품만 있는 경우)
                if (result.getRecvZpcd() == null || result.getRecvZpcd().equals("")) {
                    for (OrderReqProductVO vo : orderReqProductVOList) {
                        if (vo.getRecvZpcd() != null && !vo.getRecvZpcd().equals("") &&
                                vo.getRecvAdr() != null && !vo.getRecvAdr().equals("") &&
                                vo.getRecvDtad() != null && !vo.getRecvDtad().equals("")
                        ) {
                            result.setRecv(vo.getRecv());
                            result.setRecvCnplone(vo.getRecvCnplone());
                            result.setRecvCnpltwo(vo.getRecvCnpltwo());
                            result.setRecvZpcd(vo.getRecvZpcd());
                            result.setRecvAdr(vo.getRecvAdr());
                            result.setRecvDtad(vo.getRecvDtad());
                            break;
                        }
                    }
                }

                // 값 처리(배송유형 등)
                for (OrderReqProductVO vo : orderReqProductVOList) {
                    if (vo.getDvryPtrnId() != null && !vo.getDvryPtrnId().equals("")) {
                        result.setDvryPtrnId(vo.getDvryPtrnId());
                        result.setDvryPtrnNm(vo.getDvryPtrnNm());
                        result.setEntpInfoId(vo.getEntpInfoId());
                        result.setEntpNm(vo.getEntpNm());
                        result.setPcsvcmpPtrnId(vo.getPcsvcmpPtrnId());
                        result.setPcsvcmpNm(vo.getPcsvcmpNm());
                        break;
                    }
                }
            }
        } else {
//            result = new SummaryOrderInfoVO();
        }

        if (result != null && result.getProducts() != null && result.getProducts().size() > 0) {
            result.getProducts().forEach(x -> {
                // 이미지 URL 셋팅
                x.setImgUrl(fileUtil.setImageUrl(x.getImgFileId()));

                // 운송장 번호 URL 세팅
                x.setMainnbNoUrl(x.getMainnbNo() == null || x.getMainnbNo().equals("") ? null : "https://www.chunil.co.kr/HTrace/HTrace_mob.jsp?transNo=" + x.getMainnbNo()); // TODO 주소 하드코딩 개선하기
            });
        }

        return ResponseData.builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(result)
                .build();
    }

    /**
     * 배송정보입력 > 화물서비스
     * 주문 > 운임체크(화물서비스)
     * @param requestSearchOrderVO
     * @param orderApiKind
     * @return
     * @throws Exception
     */
    public ResponseData searchOrderProductDeliveryList(RequestSearchOrderVO requestSearchOrderVO, OrderApiKind orderApiKind) throws Exception {
        /*
         * 화물서비스 api 호출
         * 배송정보입력이 있고, 운임체크가 있다.
         *      배송정보입력 : 직접배송, 무료배송인 경우이다. 운송장번호를 입력하거나, 화물서비스를 선택(곧 변경)할 수 있다.
         *          화면 상태 : 주문 후 화면에 있다
         *          배송 유형 : 직접배송, 무료배송에서 화물서비스로 바뀌는 경우이다
         *          출고지 : DB 에 있다
         *          수령지 : 사용자 입력(DB 에 있다)
         *          화물서비스업체 : 여러 업체 가능
         *      운임체크 : 판매자가 지정한 화물서비스로 운임체크
         *          화면 상태 : 주문 전 화면에 있다
         *          배송 유형 : 화물서비스여야만 한다
         *          출고지 : DB 에 있다
         *          수령지 : 사용자 입력(프런트에 있다)
         *          화물서비스업체 : DB 에 있다
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

        // validation(사용자)
        switch (orderApiKind) {
            case OrderApiProductDeliveryAmt: // 운임체크(화물서비스)
                break;
            case OrderApiProductDeliveryList: // 배송정보입력 > 화물서비스
                requestSearchOrderVO.setPucsUsisId(requestSearchOrderVO.getLoginUsisId()); // 이 경우에는 판매자의 loginUsisId 을 set 한다. 참고로, 배송정보입력 > 화물서비스는 판매자가 하는 작업이다.
                SummaryOrderInfoVO summaryOrderInfoVO = orderReqRepo.searchOrderInfo(requestSearchOrderVO);
                if (summaryOrderInfoVO == null) {
                    return ResponseData.builder()
                            .code(HttpStatus.BAD_REQUEST.value())
                            .message(StatusCode.ORDER0002.getMessage()) // ORDER0002(주문정보가 올바르지 않습니다.)
                            .build();
                }
                break;
        }

        // validation(입력값)
        String validationRes = validationReqValForProductDeliveryList(requestSearchOrderVO, orderApiKind);
        if (!validationRes.equals("")) {
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(validationRes)
                    .build();
        }

        // 공통 처리

        RequestSearchEstimationVO requestSearchEstimationVO = new RequestSearchEstimationVO();
        List<EstimationProductVO> items = new ArrayList<>();
        EstimationProductVO item = new EstimationProductVO();
        OrderReqDeliveryVO orderReqDeliveryVO = null;
        switch (orderApiKind) {
            case OrderApiProductDeliveryAmt: // 운임체크(화물서비스) // 주문전
                // DB 처리(출고지 set)
                orderReqDeliveryVO = orderReqRepo.searchProductDelivery(requestSearchOrderVO);
                if (orderReqDeliveryVO == null) {
                    return ResponseData.builder()
                            .code(HttpStatus.BAD_REQUEST.value())
                            .message(StatusCode.ORDER0002.getMessage()) // ORDER0002(주문정보가 올바르지 않습니다.)
                            .build();
                } else {
                    if ((orderReqDeliveryVO.getEntpInfoId() == null && orderReqDeliveryVO.getEntpInfoIdMax() == null)
                            || (orderReqDeliveryVO.getEntpInfoId().equals("") && orderReqDeliveryVO.getEntpInfoIdMax().equals(""))) { // 상품의 업체 정보 ID 검증
                        return ResponseData.builder()
                                .code(HttpStatus.BAD_REQUEST.value())
                                .message(StatusCode.ORDER0038.getMessage()) // ORDER0038(수량에 따른 배송 업체 정보 ID를 찾을 수 없습니다.)
                                .build();
                    }
                }
                requestSearchEstimationVO.setDvryPtrnId(ComCode.GDS02001.getCode()); // 화물서비스(GDS02001)
                requestSearchEstimationVO.setRlontfZpcd(orderReqDeliveryVO.getRlontfZpcd());
                requestSearchEstimationVO.setRlontfAdr(orderReqDeliveryVO.getRlontfAdr());
                requestSearchEstimationVO.setRlontfDtad(orderReqDeliveryVO.getRlontfDtad());
                requestSearchEstimationVO.setRcarZpcd(requestSearchOrderVO.getRecvZpcd());
                requestSearchEstimationVO.setRcarAdr(requestSearchOrderVO.getRecvAdr());
                requestSearchEstimationVO.setRcarDtlAdr(requestSearchOrderVO.getRecvDtad());
                requestSearchEstimationVO.setDvrynone(0);
                requestSearchEstimationVO.setEntpInfoId(orderReqDeliveryVO.getEntpInfoId()); // 상품 배송 정보의 업체 set

                item.setOrdnQty(requestSearchOrderVO.getPdfQty());
                item.setPdfNm(orderReqDeliveryVO.getPdfNm());
                item.setEsttPdfPtrnId(ComCode.ESS01001.getCode());
                item.setGearPdfInfoId(requestSearchOrderVO.getPdfInfoId());
                break;
            case OrderApiProductDeliveryList: // 배송정보입력 > 화물서비스 // 주문후 // 직접배송, 무료배송에서 화물서비스로 바뀌는 경우이다
                // DB 처리(출고지, 수령지, 상품을 set)
                orderReqDeliveryVO = orderReqRepo.searchOrderProductDelivery(requestSearchOrderVO);
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
                }
                requestSearchEstimationVO.setDvryPtrnId(ComCode.GDS02001.getCode()); // 화물서비스(GDS02001)
                requestSearchEstimationVO.setRlontfZpcd(orderReqDeliveryVO.getRlontfZpcd());
                requestSearchEstimationVO.setRlontfAdr(orderReqDeliveryVO.getRlontfAdr());
                requestSearchEstimationVO.setRlontfDtad(orderReqDeliveryVO.getRlontfDtad());
                requestSearchEstimationVO.setRcarZpcd(orderReqDeliveryVO.getRecvZpcd());
                requestSearchEstimationVO.setRcarAdr(orderReqDeliveryVO.getRecvAdr());
                requestSearchEstimationVO.setRcarDtlAdr(orderReqDeliveryVO.getRecvDtad());
                requestSearchEstimationVO.setRcarNm(orderReqDeliveryVO.getRecv());
                requestSearchEstimationVO.setRcarCnplone(orderReqDeliveryVO.getRecvCnplone());
                requestSearchEstimationVO.setDvrynone(orderReqDeliveryVO.getDvrynone());
                requestSearchEstimationVO.setEntpInfoId(""); // 직접배송 또는 무료배송 상품이어서, 상품 배송 정보의 업체 set 을 할 수 없다.

                item.setOrdnQty(orderReqDeliveryVO.getPdfQty());
                item.setPdfNm(orderReqDeliveryVO.getPdfNm());
                item.setEsttPdfPtrnId(ComCode.ESS01001.getCode());
                item.setGearPdfInfoId(orderReqDeliveryVO.getPdfInfoId());
                break;
        }

        items.add(item);
        requestSearchEstimationVO.setItems(items);
        EstimationService.DeliveryListServiceKind deliveryListServiceKind = EstimationService.DeliveryListServiceKind.None;
        switch (orderApiKind) {
            case OrderApiProductDeliveryAmt:
                deliveryListServiceKind = EstimationService.DeliveryListServiceKind.OrderReqService_ProductDeliveryAmt; // 주문 서비스(운임체크(화물서비스)) // 주문 전
                break;
            case OrderApiProductDeliveryList:
                deliveryListServiceKind = EstimationService.DeliveryListServiceKind.OrderReqService_ProductDeliveryList; // 주문 서비스(배송정보입력 > 화물서비스) // 주문 후 // 직접배송, 무료배송에서 화물서비스로 변경하는 케이스
                break;
        }
        return estimationService.searchDeliveryList(requestSearchEstimationVO, deliveryListServiceKind);
    }

    /**
     * 입력값 검증
     * @param requestSearchOrderVO
     * @param orderApiKind
     * @return
     */
    private String validateReqValForSave(RequestSearchOrderVO requestSearchOrderVO, OrderApiKind orderApiKind) {
        /*
         * 주문등록 api 요청에 대해, 입력값을 검증한다.
         * 배송유형에 맞게 입력값이 왔는지(주소가 있는지 등) 등을 검증한다.
         * 상품에 대해 주문 수량 검증, 상품명 검증 등.
         * 견적에서 왔느냐 장바구니에서 왔느냐에 따라 적절하게 분기한다(예를 들어, 장바구니에서 온 경우 화물서비스에 대해 수령지 입력값을 검증한다).
         */

        // 변수 처리(견적 여부)
        boolean isEsm = false;
        if (requestSearchOrderVO.getEsttInfoId() != null && !requestSearchOrderVO.getEsttInfoId().equals("")) {
            isEsm = true;
        }

        // validation(상품)
        if (requestSearchOrderVO.getProducts().size() <= 0) { // 상품 목록 사이즈 검증
            return StatusCode.ORDER0003.getMessage();
        }
        for (OrderReqProductVO vo : requestSearchOrderVO.getProducts()) {

            if (vo.getDvryPtrnId() == null || vo.getDvryPtrnId().equals("")) { // 배송 유형 검증
                return StatusCode.ORDER0011.getMessage(); // 배송 유형 ID를 확인바랍니다.
            }
            if (isEsm) {
                /*
                 * validation(견적)
                 */

                // validation(배송 유형)
                /*
                 * 화물서비스(GDS02001) : 출고 주소(DB), 수령지 주소(DB), 배송비(DB)
                 * 직접배송(GDS02002) : 수령지 주소, 배송비(DB)
                 * 무료배송(GDS02003) : 수령지 주소
                 * 구매자수령(GDS02004) : 수령지 주소(DB)
                 */
                if (vo.getDvryPtrnId().equals(ComCode.GDS02001.getCode())) {
                    /*
                     * 화물서비스(GDS02001)
                     */
                }
                if (vo.getDvryPtrnId().equals(ComCode.GDS02002.getCode())) {
                    /*
                     * 직접배송(GDS02002)
                     */
                    if (requestSearchOrderVO.getRecvZpcd() == null || requestSearchOrderVO.getRecvZpcd().equals("")
                            || requestSearchOrderVO.getRecvAdr() == null || requestSearchOrderVO.getRecvAdr().equals("")) { // 수령지 검증
                        return StatusCode.ORDER0004.getMessage();
                    }
                    if (vo.getDvrynone() == null || vo.getDvrynone() <= 0) { // 배송비 검증
                        return StatusCode.ORDER0005.getMessage();
                    }
                }
                if (vo.getDvryPtrnId().equals(ComCode.GDS02003.getCode())) {
                    /*
                     * 무료배송(GDS02003)
                     */
                    if (requestSearchOrderVO.getRecvZpcd() == null || requestSearchOrderVO.getRecvZpcd().equals("")
                            || requestSearchOrderVO.getRecvAdr() == null || requestSearchOrderVO.getRecvAdr().equals("")) { // 수령지 검증
                        return StatusCode.ORDER0004.getMessage();
                    }
                }
                if (vo.getDvryPtrnId().equals(ComCode.GDS02004.getCode())) {
                    /*
                     * 구매자수령(GDS02004)
                     */
                }

                // validation(상품)
                if (vo.getQty() == null || vo.getQty() <= 0) { // 주문 수량 검증
                    return StatusCode.ORDER0007.getMessage();
                }
                if (vo.getPdfNm() == null || vo.getPdfNm().equals("")) { // 상품명 검증
                    return StatusCode.ORDER0008.getMessage();
                }
//                if (vo.getPdfInfoId() == null || vo.getPdfInfoId().equals("")) { // 상품 정보 ID 검증
//                    return StatusCode.ORDER0009.getMessage();
//                }

            } else {
                /*
                 * validation(장바구니)
                 */

                // validation(배송 유형)
                /*
                 * 화물서비스(GDS02001) : 출고 주소(DB), 수령지 주소, 배송비
                 * 직접배송(GDS02002) : 수령지 주소, 배송비
                 * 무료배송(GDS02003) : 수령지 주소
                 */
                if (vo.getDvryPtrnId().equals(ComCode.GDS02001.getCode())) {
                    /*
                     * 화물서비스(GDS02001)
                     */
                    if (requestSearchOrderVO.getRecvZpcd() == null || requestSearchOrderVO.getRecvZpcd().equals("")
                            || requestSearchOrderVO.getRecvAdr() == null || requestSearchOrderVO.getRecvAdr().equals("")) { // 수령지 검증
                        return StatusCode.ORDER0004.getMessage();
                    }
                    if (vo.getDvrynone() == null || vo.getDvrynone() <= 0) { // 배송비 검증
                        return StatusCode.ORDER0005.getMessage();
                    }
//                    if (vo.getEntpInfoId().equals("")) { // 운송업체 검증
//                        return StatusCode.ORDER0006.getMessage();
//                    }
                }
                if (vo.getDvryPtrnId().equals(ComCode.GDS02002.getCode())) {
                    /*
                     * 직접배송(GDS02002)
                     */
                    if (requestSearchOrderVO.getRecvZpcd() == null || requestSearchOrderVO.getRecvZpcd().equals("")
                            || requestSearchOrderVO.getRecvAdr() == null || requestSearchOrderVO.getRecvAdr().equals("")) { // 수령지 검증
                        return StatusCode.ORDER0004.getMessage();
                    }
                    if (vo.getDvrynone() == null || vo.getDvrynone() <= 0) { // 배송비 검증
                        return StatusCode.ORDER0005.getMessage();
                    }
                }
                if (vo.getDvryPtrnId().equals(ComCode.GDS02003.getCode())) {
                    /*
                     * 무료배송(GDS02003)
                     */
                    if (requestSearchOrderVO.getRecvZpcd() == null || requestSearchOrderVO.getRecvZpcd().equals("")
                            || requestSearchOrderVO.getRecvAdr() == null || requestSearchOrderVO.getRecvAdr().equals("")) { // 수령지 검증
                        return StatusCode.ORDER0004.getMessage();
                    }
                }

                // validation(상품)
                if (vo.getQty() == null || vo.getQty() <= 0) { // 주문 수량 검증
                    return StatusCode.ORDER0007.getMessage();
                }
                if (vo.getPdfPrc() == null) { // 판매가격 검증
                    return StatusCode.ORDER0014.getMessage();
                }
//                if (vo.getPdfNm() == null || vo.getPdfNm().equals("")) { // 상품명 검증
//                    return StatusCode.ORDER0008.getMessage();
//                }
                if (vo.getPdfInfoId() == null || vo.getPdfInfoId().equals("")) { // 상품 정보 ID 검증
                    return StatusCode.ORDER0009.getMessage();
                }

            }
        }

        // validation(같은 배송지 사용 여부)
        if (requestSearchOrderVO.getDlplUseYn() == null || requestSearchOrderVO.getDlplUseYn().equals("")) {
            return StatusCode.ORDER0012.getMessage(); // 같은 배송지 사용 여부를 확인바랍니다.
        }

        return "";
    }

    private String validateReqValForStlmSave(OrderReqStlmVO orderReqStlmVO) {

        // validation(결제 정보)
        if (orderReqStlmVO == null) {
            return StatusCode.ORDER0017.getMessage();
        }

        // validation(주문 정보 ID)
        if (orderReqStlmVO.getOrdnInfoId() == null || orderReqStlmVO.getOrdnInfoId().equals("")) {
            return StatusCode.ORDER0017.getMessage();
        }

        // validation(체결 번호 ID)
        if (orderReqStlmVO.getCnttNoId() == null || orderReqStlmVO.getCnttNoId().equals("")) {
            return StatusCode.ORDER0031.getMessage();
        }

        // validation(결제유형 ID)
        if (orderReqStlmVO.getStlmptrnId() == null || orderReqStlmVO.getStlmptrnId().equals("")) {
            return StatusCode.ORDER0028.getMessage();
        }

        // validation(결제상태 ID)
        if (orderReqStlmVO.getStlmsttsId() == null || orderReqStlmVO.getStlmsttsId().equals("")) {
            return StatusCode.ORDER0029.getMessage();
        }

        // validation(금액)
        if (orderReqStlmVO.getAmt() == null || orderReqStlmVO.getAmt() <= 0) {
            return StatusCode.ORDER0030.getMessage();
        }

        return "";
    }

    /**
     * 입력값 검증
     * @param requestSearchOrderVO
     * @param orderApiKind
     * @return
     */
    private String validationReqValForProductDeliveryList(RequestSearchOrderVO requestSearchOrderVO, OrderApiKind orderApiKind) {
        switch (orderApiKind) {
            case OrderApiProductDeliveryAmt: // 운임체크(화물서비스)
                // validation(수령지)
                if (requestSearchOrderVO.getRecvZpcd() == null || requestSearchOrderVO.getRecvZpcd().equals("")
                        || requestSearchOrderVO.getRecvAdr() == null || requestSearchOrderVO.getRecvAdr().equals("")) { // 수령지 검증
                    return StatusCode.ORDER0004.getMessage();
                }

                // validation(상품)
                if (requestSearchOrderVO.getPdfInfoId() == null || requestSearchOrderVO.getPdfInfoId().equals("")) {
                    return StatusCode.ORDER0009.getMessage();
                }
                if (requestSearchOrderVO.getPdfQty() == null || requestSearchOrderVO.getPdfQty() <= 0) { // 주문 수량 검증
                    return StatusCode.ORDER0007.getMessage();
                }
                if (requestSearchOrderVO.getDvryPtrnId() == null || requestSearchOrderVO.getDvryPtrnId().equals("")) {
                    return StatusCode.ORDER0011.getMessage();
                } else {
                    if (!Arrays.asList(ComCode.GDS02001.getCode())
                            .contains(requestSearchOrderVO.getDvryPtrnId())) { // 배송 유형이 화물서비스인지 검증한다
                        return StatusCode.ORDER0011.getMessage();
                    }
                }
                break;
            case OrderApiProductDeliveryList: // 배송정보입력 > 화물서비스
                if (requestSearchOrderVO.getOrdnInfoId() == null || requestSearchOrderVO.getOrdnInfoId().equals("")) {
                    return StatusCode.ORDER0002.getMessage();
                }
                if (requestSearchOrderVO.getOrdnPdfInfoSqn() == null || requestSearchOrderVO.getOrdnPdfInfoSqn().equals("")) {
                    return StatusCode.ORDER0002.getMessage();
                }
                break;
        }

        return "";
    }

    private String retrieveDvryInfoId(String dvryPtrnId) {
        String result = "";
        if (dvryPtrnId.equals(ComCode.GDS02001.getCode())) {
            result = ComCode.DELIVERY_INFO_DVRY_R.getCode();
        } else if (dvryPtrnId.equals(ComCode.GDS02002.getCode())) {
            result = ComCode.DELIVERY_INFO_PDVRY_R.getCode();
        } else if (dvryPtrnId.equals(ComCode.GDS02003.getCode())) {
            result = ComCode.DELIVERY_INFO_PDVRY_R.getCode();
        } else if (dvryPtrnId.equals(ComCode.GDS02004.getCode())) {
            result = ComCode.DELIVERY_INFO_UDVR_R.getCode();
        }
        return result;
    }

}


