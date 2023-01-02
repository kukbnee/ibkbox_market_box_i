package com.ibk.sb.restapi.biz.service.order;

import com.ibk.sb.restapi.app.common.constant.AlarmCode;
import com.ibk.sb.restapi.app.common.constant.ComCode;
import com.ibk.sb.restapi.app.common.constant.StatusCode;
import com.ibk.sb.restapi.app.common.util.BizException;
import com.ibk.sb.restapi.app.common.vo.ResponseData;
import com.ibk.sb.restapi.biz.service.alarm.AlarmService;
import com.ibk.sb.restapi.biz.service.alarm.vo.RequestAlarmVO;
import com.ibk.sb.restapi.biz.service.delivery.DeliveryService;
import com.ibk.sb.restapi.biz.service.delivery.chunil.vo.DeliveryProductVO;
import com.ibk.sb.restapi.biz.service.delivery.chunil.vo.RequestCancelDeliveryVO;
import com.ibk.sb.restapi.biz.service.delivery.chunil.vo.RequestDeliveryVO;
import com.ibk.sb.restapi.biz.service.estimation.EstimationService;
import com.ibk.sb.restapi.biz.service.estimation.repo.EstimationProductRepo;
import com.ibk.sb.restapi.biz.service.estimation.vo.EstimationDeliveryVO;
import com.ibk.sb.restapi.biz.service.estimation.vo.EstimationProductVO;
import com.ibk.sb.restapi.biz.service.estimation.vo.RequestSearchEstimationVO;
import com.ibk.sb.restapi.biz.service.order.repo.OrderReqDetailRepo;
import com.ibk.sb.restapi.biz.service.order.repo.OrderReqRepo;
import com.ibk.sb.restapi.biz.service.order.vo.req.*;
import com.ibk.sb.restapi.biz.service.product.single.repo.SingleProductRepo;
import com.ibk.sb.restapi.biz.service.product.single.vo.SingleProductVO;
import com.ibk.sb.restapi.biz.service.user.UserService;
import com.ibk.sb.restapi.biz.service.user.vo.MyPageUserVO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
public class OrderDvryService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private final DeliveryService deliveryService;

    private final UserService userService;

    private final EstimationService estimationService;

    private final AlarmService alarmService;

    private final EstimationProductRepo estimationProductRepo;

    private final OrderReqRepo orderReqRepo;

    private final SingleProductRepo singleProductRepo;

    private final OrderReqDetailRepo orderReqDetailRepo;

    public enum DvryServiceKind {
        None,
        OrderReqService, // 주문시 운송의뢰
        OrderDvryService, // 운송의뢰(독립 API)
        OrderReqSttsService // 구매판매목록 > 배송정보입력 > 배송요청
    }

    /*
     * 천일화물 운송의뢰 feign api 호출
     */
    public OrderDvryResVO requestDvryDvryChunil(OrderDvryVO orderDvryVO, DvryServiceKind dvryServiceKind) throws Exception {
        /*
         * 운송의뢰 feign api 호출되는 케이스
         * 1.운송의뢰 API 에서 오는 경우
         * 2.구매판매목록 > 배송정보입력 > 배송요청에서 오는 경우
         *
         * reqPdfVOList : 운송의뢰 상품 리스트
         *
         * (참고) 운송의뢰시에는 상품별 배송비를 다시 가져온다. 왜냐하면, 견적의 경우 상품별로 배송비를 가지고 있지 않고, 배송비 합계만 가지고 있기 때문이다.
         */

        List<OrderDvryPdfVO> reqPdfVOList = orderDvryVO.getOrderDvryPdfVOList();
        for (OrderDvryPdfVO reqPdfVO : reqPdfVOList) {
            // 값 처리(제품 규격, 무게, 내품가액 등)
            RequestSearchEstimationVO validateReqVO = new RequestSearchEstimationVO();
            validateReqVO.setPdfInfoId(reqPdfVO.getPdfInfoId()); // 제품 규격, 무게, 내품가액 등이 없는 상품 정보 ID
            List<EstimationProductVO> validateResVOList = estimationProductRepo.searchProductForDeliveryAmt(validateReqVO);
            if (validateResVOList.size() == 1) {
                EstimationProductVO validateResVO = validateResVOList.get(0);
                reqPdfVO.setPrdtBrdh(validateResVO.getPrdtBrdh());
                reqPdfVO.setPrdtVrtc(validateResVO.getPrdtVrtc());
                reqPdfVO.setPrdtAhgd(validateResVO.getPrdtAhgd());
                reqPdfVO.setPrdtWgt(validateResVO.getPrdtWgt());
                reqPdfVO.setDchGdsPrc(validateResVO.getDchGdsPrc()); // 내품가액
                reqPdfVO.setPdfCtgyId(validateResVO.getPdfCtgyId());
                reqPdfVO.setPrdtpcknUtId(validateResVO.getPrdtpcknUtId()); // 제품포장 단위 ID
                reqPdfVO.setFileId(validateResVO.getFileId());
            } else {
//                return ResponseData.builder()
//                        .code(HttpStatus.BAD_REQUEST.value())
//                        .message(StatusCode.ESTIMATION0008.getMessage())
//                        .build();
            }
        }

        // 값 처리(보내시는 분)
        MyPageUserVO myPageUserVO = userService.searchMyPageUserInfo();
        if (myPageUserVO != null) {
            orderDvryVO.setRlontf(myPageUserVO.getBplcNm()); // 이용기관 명
            orderDvryVO.setRlontfCnplone(myPageUserVO.getReprsntTelno()); // 이용기관 대표 전화번호
        } else {
//            return ResponseData.builder()
//                    .code(HttpStatus.BAD_REQUEST.value())
//                    .message(StatusCode.LOGIN_NOT_USER_INFO.getMessage()) // 사용자 정보가 존재하지 않습니다
//                    .build();
        }

        // 값 처리(배송비)
        RequestSearchEstimationVO requestSearchEstimationVO = new RequestSearchEstimationVO();
        requestSearchEstimationVO.setRlontfZpcd(orderDvryVO.getRlontfZpcd());
        requestSearchEstimationVO.setRlontfAdr(orderDvryVO.getRlontfAdr());
        requestSearchEstimationVO.setRlontfDtad(orderDvryVO.getRlontfDtad());
        requestSearchEstimationVO.setRcarZpcd(orderDvryVO.getRecvZpcd());
        requestSearchEstimationVO.setRcarAdr(orderDvryVO.getRecvAdr());
        requestSearchEstimationVO.setRcarDtlAdr(orderDvryVO.getRecvDtad());
        requestSearchEstimationVO.setEntpInfoId(orderDvryVO.getEntpInfoId());
        List<EstimationProductVO> reqProductVOList = new ArrayList<>();
        for (OrderDvryPdfVO vo : reqPdfVOList) {
            EstimationProductVO estimationProductVO = new EstimationProductVO();
            estimationProductVO.setGearPdfInfoId(vo.getPdfInfoId());
            estimationProductVO.setOrdnQty(vo.getPdfQty());
            estimationProductVO.setPrdtBrdh(vo.getPrdtBrdh());
            estimationProductVO.setPrdtVrtc(vo.getPrdtVrtc());
            estimationProductVO.setPrdtAhgd(vo.getPrdtAhgd());
            estimationProductVO.setPrdtWgt(vo.getPrdtWgt());
            estimationProductVO.setDchGdsPrc(vo.getDchGdsPrc());
            reqProductVOList.add(estimationProductVO);
        }
        List<EstimationDeliveryVO> dvryEntps = null;
        switch (dvryServiceKind) {
            case OrderReqService: // 현재 사용 않고 있다.
                break;
            case OrderDvryService:
                /*
                 * 운송의뢰시에는 상품별 배송비를 다시 가져온다. 왜냐하면, 견적의 경우 상품별로 배송비를 가지고 있지 않고, 배송비 합계만 가지고 있기 때문이다.
                 * 운송의뢰 feign api 호출시 상품별 배송비가 필요하다.
                 */
                dvryEntps = estimationService.requestEsmCompany(requestSearchEstimationVO, reqProductVOList, myPageUserVO, EstimationService.DeliveryListServiceKind.None);
                break;
            case OrderReqSttsService: // 배송요청시에는 프런트에서 보내오는 배송비를 사용한다.
                break;
        }

        // 값 처리(IBK 제품코드)
        String ibkNum = "";
        // ibkNum = orderDvryVO.getOrdnInfoId();
        ibkNum = uuidFormat(orderDvryVO.getOrdnInfoId());

        RequestDeliveryVO chunilApiVO = new RequestDeliveryVO();
        chunilApiVO.setIBKNUM(ibkNum); // 거래번호(BOX 발급번호)-필요하실 경우만 // (길이)정의해 주세요
        chunilApiVO.setUSERID("IBKAPI"); // 요청자ID("IBKAPI" 고정값) // String 30
        chunilApiVO.setSAUPNO(bizrnoFormat(myPageUserVO.getBizrno())); // 사업자등록번호 // String 12
        chunilApiVO.setESTDAT(orderDvryVO.getEsmDate()); // 견적일자(YYYY-MM-DD) // String 10
        chunilApiVO.setBALZIP(orderDvryVO.getRlontfZpcd()); // 발송자 우편번호 // String 7
        chunilApiVO.setBALADD(orderDvryVO.getRlontfAdr()); // 발송자 주소 // String 200
        chunilApiVO.setBALADS(orderDvryVO.getRlontfDtad()); // 발송자 건물관리번호(도로명 주소인 경우) // String 25
        chunilApiVO.setDOCZIP(orderDvryVO.getRecvZpcd()); // 수신자 우편번호 // String 7
        chunilApiVO.setDOCADD(orderDvryVO.getRecvAdr()); // 수신자 주소 // String 200
        chunilApiVO.setDOCADS(orderDvryVO.getRecvDtad()); // 수신자 건물관리번호(도로명 주소인 경우) // String 25
        chunilApiVO.setBALNAM(orderDvryVO.getRlontf()); // 발송자 이름 // String 50
        chunilApiVO.setBALTEL(orderDvryVO.getRlontfCnplone()); // 발송자 전화번호 // String 15
        chunilApiVO.setDOCNAM(orderDvryVO.getRecv()); // 수신자 이름 // String 50
        chunilApiVO.setDOCTEL(orderDvryVO.getRecvCnplone()); // 수신자 전화번호 // String 15
        chunilApiVO.setPAYGBN("1"); // 선불/착불구분(선불:"1",착불:"2") : "1"값 고정 // String 1
        chunilApiVO.setPAYTYP(""); // 결제방법(현금:"CASH",POS:"POS"): ""값 고정 // String 4
        chunilApiVO.setRsCount(String.valueOf(reqPdfVOList.size()));

        List<DeliveryProductVO> rsVOList = new ArrayList<>();
        Integer seq = 1;
        for (OrderDvryPdfVO reqPdfVO : reqPdfVOList) {
            // 값 처리(포장단위코드)
            String prdtpcknUtId = "KT"; // 기타
            if (reqPdfVO.getPrdtpcknUtId().equals("DIS00001")) { // 박스(DIS00001)
                prdtpcknUtId = "BX";
            } else if (reqPdfVO.getPrdtpcknUtId().equals("DIS00002")) { // 파렛트(DIS00002)
                prdtpcknUtId = "PL";
            }

            // 값 처리(방문예정일)
            String schDat = "";
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            cal.add(Calendar.DATE, 2); // 2일 더하기
            schDat = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());

            // 값 처리(IBK 제품코드)
            String ibkItm = "";
            // ibkItm = reqPdfVO.getPdfInfoId();
            ibkItm = uuidFormat(reqPdfVO.getPdfInfoId());

            // 값 처리(품목코드)
            String itmgbn = "";
//            itmgbn = "CT0020"; // 테스트
            itmgbn = reqPdfVO.getPdfCtgyId(); // 커머스 상품 카테고리ID 넣음

            // 값 처리(첨부파일명)
            /*
             * 1.파일업로드 서비스 호출
             * 2.응답의 파일명을 운송의뢰 첨부파일명에 사용
             */
            String fileNm = "";
            if (reqPdfVO.getFileId() != null && !reqPdfVO.getFileId().equals("")) {
                try {
                    // TODO _ 20221017 파일 업로드 자체는 작업 완료, 아래의 예외처리는 작업 안함
                    // TODO 현재 예외 시 Transaction rolled back because it has been marked as rollback-only 발생해서 주석처리하였다. Transaction rolled back because it has been marked as rollback-only 안 생기게 하고 주석은 풀기
                    fileNm = deliveryService.DeliveryFileUpload(reqPdfVO.getFileId());
                } catch (BizException bx) {
                    logger.error("Business Exception ({}) : {}", bx.getErrorCode(), bx.getErrorMsg());
                } catch (Exception ex) {
                    logger.error("ex: " + ex.getMessage());
                }
            }

            // 값 처리(배송비)
            Integer estAmt = 0;
            switch (dvryServiceKind) {
                case OrderReqService: // 현재 사용 않고 있다.
                    break;
                case OrderDvryService: // 운송의뢰시에는 dvryEntps 의 배송비를 사용한다.
//                    if(dvryEntps != null && dvryEntps.size() > 0 && dvryEntps.get(0).getEstimationEsmVOList() != null && dvryEntps.get(0).getEstimationEsmVOList().size() > 0) {
//                        estAmt = dvryEntps.get(0).getEstimationEsmVOList().get(seq - 1).getDvrynone();
//                    }
//                    break;
                    // TODO : 보안점검사항 null dereference
                    // null 체크 및 index 확인 추가
                    if(     dvryEntps != null
                            && dvryEntps.size() > 0
                            && dvryEntps.get(0) != null // dvryEntps index 0 항목 null 체크 추가
                            && dvryEntps.get(0).getEstimationEsmVOList() != null
                            && dvryEntps.get(0).getEstimationEsmVOList().size() > 0) {

                        int idx = seq - 1;
                        // index 확인 조건 추가
                        if(     0 <= idx
                                && idx < dvryEntps.get(0).getEstimationEsmVOList().size()
                                && dvryEntps.get(0).getEstimationEsmVOList().get(idx) != null) {
                            estAmt = dvryEntps.get(0).getEstimationEsmVOList().get(idx).getDvrynone();
                        }
                    }
                    break;
                case OrderReqSttsService: // 배송요청시에는 프런트에서 보내오는 배송비를 사용한다.
                    estAmt = reqPdfVO.getDvrynone();
                    break;
            }

            DeliveryProductVO rsVO = new DeliveryProductVO();
            rsVO.setSEQNUM(String.valueOf(seq)); // 품목 순번 // Number 2
            rsVO.setRTNNUM(""); // 원송장번호(반품시 필수항목) // String 11
            rsVO.setUNTCOD(prdtpcknUtId); // 포장단위코드(포장단위 API정의서의 CODECD값) // String 10
            rsVO.setITMCNT(String.valueOf(reqPdfVO.getPdfQty())); // 수량 // Number 4
            rsVO.setWIDTHH(reqPdfVO.getPrdtBrdh()); // 부피_가로 // Number 3
            rsVO.setLENGTT(reqPdfVO.getPrdtVrtc()); // 부피_세로 // Number 3
            rsVO.setHEIGHT(reqPdfVO.getPrdtAhgd()); // 부피_높이 // Number 3
            rsVO.setWEIGHT(reqPdfVO.getPrdtWgt()); // 중량 // Number 4
            rsVO.setITMAMT(String.valueOf(reqPdfVO.getDchGdsPrc())); // 내품가격 // Number 9
            rsVO.setITMGBN(itmgbn); // 품목코드(품목 API정의서의 CODECD값) // String 15
            rsVO.setIBKITM(ibkItm); // IBK 제품코드 // (길이)정의해 주세요
            rsVO.setITMNAM(reqPdfVO.getPdfNm()); // 상품명 // String 100
            rsVO.setFILENM(fileNm); // 첨부파일명 // String 100 // TODO 첨부파일명 가져오기
            rsVO.setTRNNYN("Y"); // 배송가능여부(Y/N) // String 1
            rsVO.setTRNTXT(""); // 배송불가 메시지 // String 50
//            rsVO.setESTAMT(String.valueOf(reqPdfVO.getDvrynone())); // 총운임(기본운임+할증료+도선료) // Number 7 // TODO 총운임 확인하기
//            rsVO.setBASAMT(String.valueOf(reqPdfVO.getDvrynone())); // 기본운임 // Number 7
            rsVO.setESTAMT(String.valueOf(estAmt)); // 총운임(기본운임+할증료+도선료) // Number 7 // TODO 총운임 확인하기
            rsVO.setBASAMT(String.valueOf(estAmt)); // 기본운임 // Number 7
            rsVO.setADDAMT(""); // 할증료 // Number 7
            rsVO.setSHIAMT(""); // 도선료 // Number 7
            rsVO.setSCHDAT(schDat); // 방문예정일 // String 10
            rsVOList.add(rsVO);
            seq++;
        }
        chunilApiVO.setRs(rsVOList);
        logger.debug("chunilApiVO: " + chunilApiVO.getParam());

        Map<String, Object> resultMap = null;
        try {
            // feign 처리(운송의뢰)
            resultMap = deliveryService.requestDelivery(chunilApiVO);
            logger.debug("resultMap: " + resultMap);

            // DB 처리(주문 배송 API 호출 이력)
            for (OrderDvryPdfVO vo : reqPdfVOList) {
                OrderDvryDchLVO orderDvryDchLVO = new OrderDvryDchLVO();
                orderDvryDchLVO.setOrdnInfoId(orderDvryVO.getOrdnInfoId());
                orderDvryDchLVO.setInfoSqn(vo.getInfoSqn());
                orderDvryDchLVO.setRqstInfo(chunilApiVO.getParam());
                orderDvryDchLVO.setRsltInfo(resultMap.toString());
                orderDvryDchLVO.setRgsnUserId(orderDvryVO.getRgsnUserId());
                orderReqRepo.insertOrderDchL(orderDvryDchLVO); // TODO 내부망 DB PK 조정 후 주석 풀기
            }
        } catch (BizException bx) {
            logger.error("Business Exception ({}) : {}", bx.getErrorCode(), bx.getErrorMsg());
        } catch (Exception ex) {
            logger.error("ex: " + ex.getMessage());
        }
        /*
         * 참고 : 한 운송의뢰번호에 대한 여러 송장번호 중 하나라도 errMsg 값 존재시 운송의뢰번호 생성 오류(flag = false로 반환됨)
         * 실패유형01 : {REQNUM=, SCHDAT=, rs=[], flag=false, msg=DB처리오류 : Index: 0, Size: 0}
         * 실패유형02 : {REQNUM=, SCHDAT=2022-09-05, rs=[{SEQNUM=1, MAINNB=12217616877, RTNNUM=, errMsg=운송의뢰 저장오류}], flag=false, msg= 품목 1건에 대해 오류가 발생했습니다}
         * 실패유형03 : {REQNUM=, SCHDAT=2022-09-05, rs=[{SEQNUM=1, MAINNB=12217623576, RTNNUM=, errMsg=견적금액과 운송장금액 상이}], flag=false, msg= 품목 1건에 대해 오류가 발생했습니다}
         * 성공시 : {REQNUM=20220826-100319, SCHDAT=2022-09-05, rs=[{SEQNUM=1, MAINNB=12217627043, RTNNUM=, errMsg=}], flag=true, msg=}
         */

        OrderDvryResVO orderDvryResVO = new OrderDvryResVO();
        String errMsg = "";
        List<Map<String, String>> mainnbNoList = new ArrayList<>();
        if (resultMap == null) {
            errMsg = "null";
        } else {
            /*
             * resultMap 이 null 이 아닌 경우
             */
            List<Map<String, Object>> rsList = (List<Map<String, Object>>) resultMap.get("rs");
            if (rsList == null) {
                errMsg = "null";
            } else {
                /*
                 * rsList 가 null 이 아닌 경우
                 */
                for (Map<String, Object> rs : rsList) {
                    String seqNum = rs.get("SEQNUM").toString(); // 품목 순번
                    String mainnbNo = rs.get("MAINNB").toString(); // 송장번호 // String 11
                    errMsg = rs.get("errMsg").toString(); // 송장별 오류메시지
                    logger.error("mainnbNo: " + mainnbNo + ", errMsg: " + errMsg);
                    mainnbNoList.add(new HashMap<String, String>() {{
                        put("SEQNUM", seqNum);
                        put("MAINNB", mainnbNo);
                    }});
                }

                String flag = resultMap.get("flag").toString();
                if (flag == null || flag.equals("false")) {
                    errMsg = errMsg.equals("") ? "false" : errMsg;
                } else {
                    /*
                     * flag 이 'true' 인 경우
                     * 운송의뢰 성공시 유입
                     */
                    String reqnumNo = resultMap.get("REQNUM").toString(); // 운송의뢰번호 // String 15
                    logger.debug("reqnumNo: " + reqnumNo);
                    orderDvryResVO.setReqnumNo(reqnumNo);
                    orderDvryResVO.setMainnbNoList(mainnbNoList);

                    /*
                     * 운송의뢰(배송요청) 성공시 알림서비스
                     */
                    // 주문정보 조회
                    RequestSearchOrderVO requestSearchOrderVO = new RequestSearchOrderVO();
                    requestSearchOrderVO.setOrdnInfoId(orderDvryVO.getOrdnInfoId());
                    SummaryOrderInfoVO summaryOrderInfoVO = orderReqDetailRepo.searchOrderInfo(requestSearchOrderVO);

                    reqPdfVOList.forEach(x -> {
                        // 견적 발송 알림
                        try {
                            //상품 정보 검색
                            SingleProductVO singleProductVO = singleProductRepo.selectMySingleProduct(x.getPdfInfoId());
                            // 알림 요청 인스턴스 생성
                            RequestAlarmVO requestAlarmVO = alarmService.getAlarmRequest(AlarmCode.AlarmCodeEnum.DELIVERY_SHIPPING, null, new Object[]{singleProductVO.getPdfNm()});
                            // 알림 발송
                            alarmService.sendMarketAlarm(requestAlarmVO, summaryOrderInfoVO.getPucsUsisId());
                        } catch (BizException bx) {
                            logger.error("Business Exception ({}) : {}", bx.getErrorCode(), bx.getErrorMsg());
                        } catch (Exception e) {
                            logger.error(e.getMessage());
                        }
                    });
                }
            }
        }
        orderDvryResVO.setErrMsg(errMsg);

        return orderDvryResVO;
    }

    private String bizrnoFormat(String bizrno) {
        String result = "";
        if (bizrno != null && bizrno.length() == 10) {
            result = bizrno.substring(0, 3) + "-" + bizrno.substring(3, 5) + "-" + bizrno.substring(5, 10);
        }
        return result;
    }

    private String uuidFormat(String uuid) {
        String result = "";
        if (uuid != null && uuid.length() > 0) {
            String[] uuids = uuid.split("-");
            String dateStr = new SimpleDateFormat("yyyyMMdd").format(new Date());
            result = dateStr + "-" + uuids[0]; // 천일에서 저장오류 생겨서 UUID 잘라서 일부만 사용
        }
        return result;
    }

    /**
     * 운송의뢰
     * @param requestSearchOrderVO
     * @return
     */
    public ResponseData requestDvryDvry(RequestSearchOrderVO requestSearchOrderVO) throws Exception {

        // validation(배송 유형 ID, 주문 상태 ID)
        List<OrderReqDeliveryVO> orderReqDeliveryVOList = orderReqRepo.searchOrderProductDeliveryList(requestSearchOrderVO);
        OrderReqDeliveryVO orderReqDeliveryVO = null;
        if (orderReqDeliveryVOList.size() > 0) {
            orderReqDeliveryVO = orderReqDeliveryVOList.get(0);
            if (orderReqDeliveryVO == null) {
                return ResponseData.builder()
                        .code(HttpStatus.BAD_REQUEST.value())
                        .message(StatusCode.ORDER0002.getMessage()) // ORDER0002(주문정보가 올바르지 않습니다.)
                        .build();
            } else {
                if (!orderReqDeliveryVO.getOrdnSttsId().equals(ComCode.ODS00001.getCode())) { // 주문(ODS00001)
                    return ResponseData.builder()
                            .code(HttpStatus.BAD_REQUEST.value())
                            .message(StatusCode.ORDER0019.getMessage()) // 주문 상태가 아닙니다.
                            .build();
                }
            }
        } else {
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(StatusCode.ORDER0002.getMessage()) // ORDER0002(주문정보가 올바르지 않습니다.)
                    .build();
        }

        /*
         * 주문의 화물서비스 운송업체가 여러개라면, requestDvryDvryEntp 이 업체별로 불리우는 식으로 구현하였다(리팩토링하였다).
         */
        List<OrderDvryResVO> orderDvryResVOList = new ArrayList<>();
        List<OrderReqDeliveryVO> entpList = orderReqRepo.searchOrderProductDeliveryListEntp(requestSearchOrderVO);
        for (OrderReqDeliveryVO entp : entpList) {
            List<OrderReqDeliveryVO> orderReqDeliveryVOListEntp = new ArrayList<>();
            for (OrderReqDeliveryVO vo : orderReqDeliveryVOList) {
                if (vo.getEntpInfoId().equals(entp.getEntpInfoId())) {
                    orderReqDeliveryVOListEntp.add(vo);
                }
            }
            OrderReqDeliveryVO orderReqDeliveryVOEntp = orderReqDeliveryVOListEntp.get(0);
            OrderDvryResVO orderDvryResVO = requestDvryDvryEntp(requestSearchOrderVO, orderReqDeliveryVOListEntp, orderReqDeliveryVOEntp);
            orderDvryResVOList.add(orderDvryResVO);
        }

        boolean isErr = false;
        for (OrderDvryResVO resVO : orderDvryResVOList) {
            if (resVO != null &&
                    resVO.getErrMsg() != null &&
                    resVO.getErrMsg().equals("")) {
            } else {
                isErr = true;
            }
        }
        if (orderDvryResVOList.size() > 0 &&
                !isErr) {
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

    private OrderDvryResVO requestDvryDvryEntp(RequestSearchOrderVO requestSearchOrderVO, List<OrderReqDeliveryVO> orderReqDeliveryVOList, OrderReqDeliveryVO orderReqDeliveryVO) throws Exception {
        OrderDvryVO orderDvryVO = new OrderDvryVO();
        List<OrderDvryPdfVO> orderDvryPdfVOList = new ArrayList<>();
        for (OrderReqDeliveryVO vo : orderReqDeliveryVOList) {
            OrderDvryPdfVO orderDvryPdfVO = new OrderDvryPdfVO();
            orderDvryPdfVO.setOrdnInfoId(orderReqDeliveryVO.getOrdnInfoId());
            orderDvryPdfVO.setInfoSqn(vo.getInfoSqn());
            orderDvryPdfVO.setPdfInfoId(vo.getPdfInfoId());
            orderDvryPdfVO.setPdfNm(vo.getPdfNm());
            orderDvryPdfVO.setPdfQty(vo.getPdfQty());
            orderDvryPdfVO.setPdfPrc(vo.getPdfPrc());
            orderDvryPdfVO.setDvrynone(vo.getDvrynone()); // 주문 때 견적나온 배송비
            orderDvryPdfVOList.add(orderDvryPdfVO);
        }
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
        orderDvryVO.setRgsnUserId(orderReqDeliveryVO.getSelrId());
        orderDvryVO.setOrdnInfoId(orderReqDeliveryVO.getOrdnInfoId());

        OrderDvryResVO orderDvryResVO = null;
        EstimationService.DeliveryEntpKind entpKind = EstimationService.DeliveryEntpKind.Chunil; // 현재는 천일화물 밖에 없긴 하다.
        switch (entpKind) {
            case Chunil:
                orderDvryVO.setEntpInfoId(orderReqDeliveryVO.getEntpInfoId()); // 운송업체를 제한하고 있다.
                orderDvryResVO = requestDvryDvryChunil(orderDvryVO, DvryServiceKind.OrderDvryService); // 천일화물 운송의뢰 feign api 호출
                break;
        }

        if (orderDvryResVO != null &&
                orderDvryResVO.getErrMsg() != null &&
                orderDvryResVO.getErrMsg().equals("")) {
            /*
             * 운송의뢰 성공
             */

            // DB 처리(주문 화물서비스 배송정보) // update
            requestSearchOrderVO.setTrspreqsNo(orderDvryResVO.getReqnumNo()); // 운송의뢰 번호
            Integer i = 0;
            for (Map<String, String> mainnbNo : orderDvryResVO.getMainnbNoList()) {
                requestSearchOrderVO.setMainnbNo(mainnbNo.get("MAINNB").toString()); // 운송장 번호
                requestSearchOrderVO.setOrdnPdfInfoSqn(orderDvryPdfVOList.get(i++).getInfoSqn());
                orderReqRepo.updateOrderDvryForDvryInput(requestSearchOrderVO);
            }
        }

        return orderDvryResVO;
    }

    /**
     * 천일화물 운송취소
     * @param requestSearchOrderVO
     * @return
     */
    public ResponseData requestDvryCancelChunil(RequestSearchOrderVO requestSearchOrderVO) {

        // validation(배송 유형 ID, 주문 상태 ID)
        List<OrderReqDeliveryVO> orderReqDeliveryVOList = orderReqRepo.searchOrderProductDeliveryListForDvryCancel(requestSearchOrderVO); // 배송정보입력에서 쓰는 repo method 와 같은 repo method 이다.
        OrderReqDeliveryVO orderReqDeliveryVO = null;
        if (orderReqDeliveryVOList.size() > 0) {
            orderReqDeliveryVO = orderReqDeliveryVOList.get(0);
            if (orderReqDeliveryVO == null) {
                return ResponseData.builder()
                        .code(HttpStatus.BAD_REQUEST.value())
                        .message(StatusCode.ORDER0002.getMessage()) // ORDER0002(주문정보가 올바르지 않습니다.)
                        .build();
            } else {
                if (!orderReqDeliveryVO.getOrdnSttsId().equals(ComCode.ODS00008.getCode())) { // 주문취소완료(ODS00008)
                    return ResponseData.builder()
                            .code(HttpStatus.BAD_REQUEST.value())
                            .message(StatusCode.ORDER0036.getMessage()) // 주문취소 완료 상태가 아닙니다.
                            .build();
                }
            }
        } else {
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(StatusCode.ORDER0002.getMessage()) // ORDER0002(주문정보가 올바르지 않습니다.)
                    .build();
        }

        RequestCancelDeliveryVO chunilApiVO = new RequestCancelDeliveryVO();
        chunilApiVO.setREQNUM(orderReqDeliveryVO.getTrspreqsNo()); // 운송의뢰번호 // String 15
        chunilApiVO.setUSERID("IBKAPI"); // 요청자ID("IBKAPI" 고정값) // String 30
        logger.debug("chunilApiVO: " + chunilApiVO.getParam());

        Map<String, Object> resultMap = null;
        try {
            // feign 처리(운송취소)
            resultMap = deliveryService.cancelDelivery(chunilApiVO);
            logger.debug("resultMap: " + resultMap);

            // DB 처리(주문 배송 API 호출 이력)
            for (OrderReqDeliveryVO vo : orderReqDeliveryVOList) {
                OrderDvryDchLVO orderDvryDchLVO = new OrderDvryDchLVO();
                orderDvryDchLVO.setOrdnInfoId(orderReqDeliveryVO.getOrdnInfoId());
                orderDvryDchLVO.setInfoSqn(vo.getInfoSqn());
                orderDvryDchLVO.setRqstInfo(chunilApiVO.getParam());
                orderDvryDchLVO.setRsltInfo(resultMap.toString());
                orderDvryDchLVO.setRgsnUserId(orderReqDeliveryVO.getSelrId());
                orderReqRepo.insertOrderDchL(orderDvryDchLVO); // TODO 내부망 DB PK 조정 후 주석 풀기
            }
        } catch (BizException bx) {
            logger.error("Business Exception ({}) : {}", bx.getErrorCode(), bx.getErrorMsg());
        } catch (Exception ex) {
            logger.error("ex: " + ex.getMessage());
        }
        /*
         * 실패유형01 : {rs=[], REQNUM=20220412-100186, rsCount=0, flag=false, msg=해당 건에 대한 운송의뢰를 확인할 수 없습니다}
         * 실패유형02 : {rs=[{MAINNB=12217739245, IBKITM=test-0826-001, REQSTS=INS, CCLLYN=N}], REQNUM=20220831-100359, rsCount=1, flag=false, msg=운송의뢰 당일만 취소가 가능합니다}
         * 실패유형03 : {rs=[{MAINNB=12217812139, IBKITM=20220902-2280a9e9, REQSTS=CCL, CCLLYN=N}, {MAINNB=12217812142, IBKITM=20220902-2280a9e9, REQSTS=CCL, CCLLYN=N}], REQNUM=20220902-100428, rsCount=2, flag=false, msg=이미 취소처리 된 건입니다}
         * 성공시 : {rs=[{MAINNB=12217812139, IBKITM=20220902-2280a9e9, REQSTS=INS, CCLLYN=Y}, {MAINNB=12217812142, IBKITM=20220902-2280a9e9, REQSTS=INS, CCLLYN=Y}], REQNUM=20220902-100428, rsCount=2, flag=true, msg=}
         */

        OrderDvryResVO orderDvryResVO = new OrderDvryResVO();
        String errMsg = "";
        List<Map<String, String>> mainnbNoList = new ArrayList<>();
        if (resultMap == null) {
            errMsg = "null";
        } else {
            /*
             * resultMap 이 null 이 아닌 경우
             */
            errMsg = resultMap.get("msg").toString();
            List<Map<String, Object>> rsList = (List<Map<String, Object>>) resultMap.get("rs");
            if (rsList == null) {
                errMsg = "null";
            } else {
                /*
                 * rsList 가 null 이 아닌 경우
                 */
                for (Map<String, Object> rs : rsList) {
                    String mainnbNo = rs.get("MAINNB").toString(); // 송장번호 // String 11
                    String ccllYn = rs.get("CCLLYN").toString(); // 취소가능여부 // String 1
                    logger.debug("mainnbNo: " + mainnbNo + ", ccllYn: " + ccllYn);
                    mainnbNoList.add(new HashMap<String, String>() {{
                        put("CCLLYN", ccllYn);
                        put("MAINNB", mainnbNo);
                    }});
                }

                String flag = resultMap.get("flag").toString();
                if (flag == null || flag.equals("false")) {
                    errMsg = errMsg.equals("") ? "false" : errMsg;
                } else {
                    /*
                     * flag 이 'true' 인 경우
                     * 운송취소 성공시 유입
                     */
                    String reqnumNo = resultMap.get("REQNUM").toString(); // 운송의뢰번호 // String 15
                    logger.debug("reqnumNo: " + reqnumNo);
                    orderDvryResVO.setReqnumNo(reqnumNo);
                    orderDvryResVO.setMainnbNoList(mainnbNoList);
                }
            }
        }
        orderDvryResVO.setErrMsg(errMsg);

        if (orderDvryResVO != null &&
                orderDvryResVO.getErrMsg() != null &&
                orderDvryResVO.getErrMsg().equals("")) {
            /*
             * 운송취소 성공
             */

            // DB 처리
            // mainnbNo 로 ccllYn 값 update 한다면 ccllYn 컬럼 생성 필요

            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .build();
        } else {
            String messageStr = StatusCode.ORDER0037.getMessage(); // 운송취소 중 오류가 발생하였습니다.
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(messageStr)
                    .build();
        }
    }

}
