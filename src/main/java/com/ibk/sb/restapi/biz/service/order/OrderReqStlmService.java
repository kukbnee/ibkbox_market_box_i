package com.ibk.sb.restapi.biz.service.order;

import com.ibk.sb.restapi.app.common.constant.StatusCode;
import com.ibk.sb.restapi.app.common.vo.CustomUser;
import com.ibk.sb.restapi.app.common.vo.ResponseData;
import com.ibk.sb.restapi.biz.service.order.repo.OrderReqStlmRepo;
import com.ibk.sb.restapi.biz.service.order.repo.OrderReqSttsRepo;
import com.ibk.sb.restapi.biz.service.order.vo.req.OrderReqStlmVO;
import com.ibk.sb.restapi.biz.service.order.vo.req.RequestSearchOrdnStlmVO;
import com.ibk.sb.restapi.biz.service.order.vo.req.SummaryOrderResultVO;
import com.ibk.sb.restapi.biz.service.order.vo.req.SummaryOrdnStlmVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.AbstractMap;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderReqStlmService {

    private final OrderReqStlmRepo orderReqStlmRepo;

    private final OrderReqSttsRepo orderReqSttsRepo;

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
     * 결제정보 등록
     * @param orderReqStlmVO
     * @return
     */
    public ResponseData orderSettlementSave(OrderReqStlmVO orderReqStlmVO) {
        /*
         * 결제 정보 없으면 insert, 있으면 update.
         * 결제 정보 이력 insert(infoSqn 증가)
         * 주문 정보 update(stlmInfoId)
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
        orderReqStlmVO.setLoginUsisId(loginUtlinsttId);
        orderReqStlmVO.setLoginUserId(loginUserId);
        orderReqStlmVO.setRgsnUserId(orderReqStlmVO.getLoginUserId()); // 등록 사용자 ID

        // validation(입력값)
        String validateRes = validateReqValForStlmSave(orderReqStlmVO);
        if (!validateRes.equals("")) {
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(validateRes)
                    .build();
        }

        // validation(주문 정보)
        SummaryOrdnStlmVO summaryOrdnStlmVO = orderReqStlmRepo.searchOrderInfo(orderReqStlmVO);
        if (summaryOrdnStlmVO == null) {
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(StatusCode.ORDER0002.getMessage())
                    .build();
        }

        // 공통 처리
        //

        if (summaryOrdnStlmVO.getStlmInfoId() == null) {
            String stlmInfoId = UUID.randomUUID().toString();
            orderReqStlmVO.setStlmInfoId(stlmInfoId);
            // DB 처리(결제 정보)
            orderReqStlmRepo.insertPdfPayL(orderReqStlmVO);

            // DB 처리(주문 정보(결제 정보 ID))
            orderReqStlmRepo.updateOrderInfoForStlm(orderReqStlmVO);
        } else {
            orderReqStlmVO.setStlmInfoId(summaryOrdnStlmVO.getStlmInfoId());
            // DB 처리(결제 정보)
            orderReqStlmRepo.updatePdfPayL(orderReqStlmVO);
        }

        // DB 처리(결제 정보 이력)
        orderReqStlmRepo.insertPdfPayH(orderReqStlmVO);

//        if (requestSearchOrdnStlmVO.getIsStlmInqResOk() != null &&
//                requestSearchOrdnStlmVO.getIsStlmInqResOk().equals("Y")) { // 결제 확인 여부가 'Y' 이면, 주문 상태 ID 도 변경
//            // DB 처리(주문 상품 정보(주문 상태 ID))
//            orderReqStlmRepo.updateOrderPdfSttsForStlm(requestSearchOrdnStlmVO);
//
//            // DB 처리(주문상품정보 이력)
//            orderReqStlmRepo.insertOrderPdfHForStlm(requestSearchOrdnStlmVO);
//        }

        SummaryOrderResultVO summaryOrderResultVO = new SummaryOrderResultVO();
        summaryOrderResultVO.setResultStr(orderReqStlmVO.getStlmInfoId());

        return ResponseData.builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(summaryOrderResultVO)
                .build();
    }

    /**
     * 입력값 검증
     * @param orderReqStlmVO
     * @return
     */
    private String validateReqValForStlmSave(OrderReqStlmVO orderReqStlmVO) {

        // validation(주문 정보 ID)
        if (orderReqStlmVO.getOrdnInfoId() == null || orderReqStlmVO.getOrdnInfoId().equals("")) {
            return StatusCode.ORDER0017.getMessage();
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

}
