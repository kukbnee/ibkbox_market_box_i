package com.ibk.sb.restapi.biz.api.order;

import com.ibk.sb.restapi.app.common.util.BizException;
import com.ibk.sb.restapi.app.common.vo.ResponseData;
import com.ibk.sb.restapi.biz.service.order.*;
import com.ibk.sb.restapi.biz.service.order.vo.*;
import com.ibk.sb.restapi.biz.service.order.vo.req.RequestSearchOrderVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"커머스 박스 - 주문 API"})
@RestController
@Slf4j
@RequestMapping(path={"/api/order", "/api/mk/v1/order"}, produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final OrderReqService orderReqService;

    private final OrderReqSttsService orderReqSttsService;

    private final OrderReqStlmService orderReqStlmService;

    private final OrderDvryService orderDvryService;

//    /**
//     * 결제하기
//     * @return
//     */
//    @PostMapping("/pay")
//    public ResponseData orderPay() {
//        try {
//
//            return orderService.orderPay();
//
//        } catch (BizException ex) {
//            log.error(ex.getMessage());
//            return ResponseData.builder()
//                    .code(HttpStatus.BAD_REQUEST.value())
//                    .message(ex.getMessage())
//                    .build();
//        } catch (Exception ex) {
//            log.error(ex.getMessage());
//            return ResponseData.builder()
//                    .code(HttpStatus.BAD_REQUEST.value())
//                    .message(ex.getMessage())
//                    .build();
//        }
//    }

    /**
     * 상품/장바구니 결제 배송정보 및 결제상품 조회
     * 견적을 제외한 나머지 > 결제하기 > 결제 정보 가져오기
     * param : products(상품아이디 및 구매수량 목록)
     * @return
     */
    @ApiOperation(value = "상품/장바구니 결제상품 조회")
    @ApiImplicitParam(name = "requestPayVO", value = "RequestPayVO")
    @PostMapping("/pay/product")
    public ResponseData getOrderPayList(@RequestBody RequestPayVO requestPayVO) {
        try {

            return orderService.getOrderPayList(requestPayVO);

        } catch (BizException ex) {
            log.error(ex.getMessage());
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(ex.getMessage())
                    .build();
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(ex.getMessage())
                    .build();
        }
    }

    /**
     * 견적서 결제 배송정보 및 결제상품 조회
     * 견적서 > 결제하기 > 결제 정보 가져오기
     * param : esttInfoId(견적ID)
     * @return
     */
    @ApiOperation(value = "견적 결제상품 조회")
    @ApiImplicitParam(name = "requestPayVO", value = "RequestPayVO")
    @PostMapping("/pay/esm")
    public ResponseData getOrderPayEsmList(@RequestBody RequestPayVO requestPayVO) {
        try {

            return orderService.getOrderPayEsmList(requestPayVO);

        } catch (BizException ex) {
            log.error(ex.getMessage());
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(ex.getMessage())
                    .build();
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(ex.getMessage())
                    .build();
        }
    }

    /**
     * 결제 주문 등록
     * @return
     */
    @ApiOperation(value = "결제 주문 등록")
    @ApiImplicitParam(name = "requestSearchOrderVO", value = "RequestSearchOrderVO")
    @PostMapping("/pay/product/order")
    public ResponseData orderProductPay(@RequestBody RequestSearchOrderVO requestSearchOrderVO) {
        try {
            return orderReqService.orderProductPay(requestSearchOrderVO);
        } catch (BizException ex) {
            log.error(ex.getMessage());
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(ex.getMessage())
                    .build();
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(ex.getMessage())
                    .build();
        }
    }

    /**
     * 견적서 결제 주문 등록
     * @return
     */
    @ApiOperation(value = "견적 결제 주문 등록")
    @ApiImplicitParam(name = "requestSearchOrderVO", value = "RequestSearchOrderVO")
    @PostMapping("/pay/esm/order")
    public ResponseData orderProductEsmPay(@RequestBody RequestSearchOrderVO requestSearchOrderVO) {
        try {
            return orderReqService.orderProductEsmPay(requestSearchOrderVO);
        } catch (BizException ex) {
            log.error(ex.getMessage());
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(ex.getMessage())
                    .build();
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(ex.getMessage())
                    .build();
        }
    }

//    /**
//     * 결제정보 등록
//     * @param requestSearchOrdnStlmVO
//     * @return
//     */
//    @PostMapping("/stlm/save")
//    public ResponseData orderSettlementSave(@RequestBody OrderReqStlmVO requestSearchOrdnStlmVO) {
//        try {
//            return orderReqStlmService.orderSettlementSave(requestSearchOrdnStlmVO);
//        } catch (BizException ex) {
//            log.error(ex.getMessage());
//            return ResponseData.builder()
//                    .code(HttpStatus.BAD_REQUEST.value())
//                    .message(ex.getMessage())
//                    .build();
//        } catch (Exception ex) {
//            log.error(ex.getMessage());
//            return ResponseData.builder()
//                    .code(HttpStatus.BAD_REQUEST.value())
//                    .message(ex.getMessage())
//                    .build();
//        }
//    }

    /**
     * 주문상세 정보 조회
     * @param requestSearchOrderVO
     * @return
     */
    @ApiOperation(value = "주문상세 조회")
    @GetMapping("/detail")
    public ResponseData orderDetail(RequestSearchOrderVO requestSearchOrderVO) {
        try {
            return orderReqService.searchOrder(requestSearchOrderVO);
        } catch (BizException ex) {
            log.error(ex.getMessage());
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(ex.getMessage())
                    .build();
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(ex.getMessage())
                    .build();
        }
    }

    /**
     * 배송 정보(운송장번호) 입력
     * @param requestSearchOrderVO
     * @return
     */
    @ApiOperation(value = "배송정보입력(운송장번호) 처리")
    @PostMapping("/delivery/info/update")
    public ResponseData orderProductDeliveryUpdate(@RequestBody RequestSearchOrderVO requestSearchOrderVO) {
        try {
            return orderReqSttsService.orderProductDeliveryUpdate(requestSearchOrderVO);
        } catch (BizException ex) {
            log.error(ex.getMessage());
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(ex.getMessage())
                    .build();
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(ex.getMessage())
                    .build();
        }
    }

    /**
     * 상품 배송비 목록 조회
     * 배송정보(화물서비스) 입력
     * @param requestSearchOrderVO
     * @return
     */
    @ApiOperation(value = "배송정보입력(화물서비스) 상품배송비목록 조회")
    @GetMapping("/delivery/info/list")
    public ResponseData orderProductDeliveryList(RequestSearchOrderVO requestSearchOrderVO) {
        try {
            return orderReqService.searchOrderProductDeliveryList(requestSearchOrderVO, OrderReqService.OrderApiKind.OrderApiProductDeliveryList);
        } catch (BizException ex) {
            log.error(ex.getMessage());
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(ex.getMessage())
                    .build();
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(ex.getMessage())
                    .build();
        }
    }

    /**
     * 주문 운임체크(화물서비스)
     * @param requestSearchOrderVO
     * @return
     */
    @ApiOperation(value = "운임체크(화물서비스)")
    @PostMapping("/delivery/amt")
    public ResponseData orderProductDeliveryAmt(@RequestBody RequestSearchOrderVO requestSearchOrderVO) {
        try {
            return orderReqService.searchOrderProductDeliveryList(requestSearchOrderVO, OrderReqService.OrderApiKind.OrderApiProductDeliveryAmt);
        } catch (BizException ex) {
            log.error(ex.getMessage());
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(ex.getMessage())
                    .build();
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message((ex.getMessage()))
                    .build();
        }
    }

    /**
     * 반품요청 상세 조회
     * @return
     */
    @ApiOperation(value = "반품요청 상세 조회")
    @GetMapping("/return/detail")
    public ResponseData orderProductReturnInfo(RequestReturnVO requestReturnVO){
        try {

            return orderService.orderProductReturnInfo(requestReturnVO);

        } catch (BizException ex) {
            log.error(ex.getMessage());
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(ex.getMessage())
                    .build();
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(ex.getMessage())
                    .build();
        }
    }

    /**
     * 반품 요청
     * @return
     */
    @ApiOperation(value = "반품요청 처리")
    @PostMapping("/return/request")
    public ResponseData orderProductReturnReq(RequestReturnVO requestReturnVO){
        try {

            return orderService.orderProductReturnReq(requestReturnVO);

        } catch (BizException ex) {
            log.error(ex.getMessage());
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(ex.getMessage())
                    .build();
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(ex.getMessage())
                    .build();
        }
    }

    /**
     * 반품요청 반품 불가
     * @return
     */
    @ApiOperation(value = "반품요청 반품불가 처리")
    @PostMapping("/return/impossible")
    public ResponseData orderProductReturnImpossible(RequestReturnVO requestReturnVO){
        try {

            return orderService.orderProductReturnImpossible(requestReturnVO);

        } catch (BizException ex) {
            log.error(ex.getMessage());
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(ex.getMessage())
                    .build();
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(ex.getMessage())
                    .build();
        }
    }

    /**
     * 반품요청 반품 완료
     * @return
     */
    @ApiOperation(value = "반품요청 반품완료 처리")
    @PostMapping("/return/completion")
    public ResponseData orderProductReturnCompletion(RequestReturnVO requestReturnVO){
        try {

            return orderService.orderProductReturnCompletion(requestReturnVO);

        } catch (BizException ex) {
            log.error(ex.getMessage());
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(ex.getMessage())
                    .build();
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(ex.getMessage())
                    .build();
        }
    }

    /**
     * 반품요청 목록 조회
     * @return
     */
    @ApiOperation(value = "반품요청 목록 조회")
    @GetMapping("/return/list")
    public ResponseData orderProductReturnList(RequestReturnVO requestReturnVO){
        try {

            return orderService.orderProductReturnList(requestReturnVO);

        } catch (BizException ex) {
            log.error(ex.getMessage());
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(ex.getMessage())
                    .build();
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(ex.getMessage())
                    .build();
        }
    }

    /**
     * 주문 취소(주문취소 승인)
     * 판매자 > 주문취소
     * @return
     */
    @ApiOperation(value = "주문취소(주문취소)")
    @PostMapping("/cancel")
    public ResponseData orderProductCancel(@RequestBody RequestSearchOrderVO requestSearchOrderVO){
        try {
            return orderReqSttsService.orderProductCancel(requestSearchOrderVO);
        } catch (BizException ex) {
            log.error(ex.getMessage());
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(ex.getMessage())
                    .build();
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(ex.getMessage())
                    .build();
        }
    }

    /**
     * 주문 취소(주문취소 완료)
     * 구매자 > 결제취소
     * @param requestSearchOrderVO
     * @return
     */
    @ApiOperation(value = "주문취소(주문취소 완료)")
    @PostMapping("/pay/cancel")
    public ResponseData orderProductPayCancel(@RequestBody RequestSearchOrderVO requestSearchOrderVO) {
        try {
            return orderReqSttsService.orderProductPayCancel(requestSearchOrderVO);
        } catch (BizException ex) {
            log.error(ex.getMessage());
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(ex.getMessage())
                    .build();
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(ex.getMessage())
                    .build();
        }
    }

    /**
     * 운송취소
     * @param requestSearchOrderVO
     * @return
     */
    @ApiOperation(value = "운송취소 처리")
    @PostMapping("/dvry/cancel")
    public ResponseData orderDvryCancel(@RequestBody RequestSearchOrderVO requestSearchOrderVO) {
        try {
            return orderDvryService.requestDvryCancelChunil(requestSearchOrderVO);
        } catch (BizException ex) {
            log.error(ex.getMessage());
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(ex.getMessage())
                    .build();
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(ex.getMessage())
                    .build();
        }
    }

    /**
     * 운송의뢰
     * @param requestSearchOrderVO
     * @return
     */
    @ApiOperation(value = "운송의뢰 처리")
    @PostMapping("/dvry/request")
    public ResponseData orderDvryRequest(@RequestBody RequestSearchOrderVO requestSearchOrderVO) {
        try {
            return orderDvryService.requestDvryDvry(requestSearchOrderVO);
        } catch (BizException ex) {
            log.error(ex.getMessage());
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(ex.getMessage())
                    .build();
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(ex.getMessage())
                    .build();
        }
    }

    /**
     * 배송 요청
     * 판매자 > 배송요청
     * @return
     */
    @ApiOperation(value = "배송정보입력에서의 배송요청 처리")
    @PostMapping("/delivery/request")
    public ResponseData orderProductDeliveryRequest(@RequestBody RequestSearchOrderVO requestSearchOrderVO){
        try {
            return orderReqSttsService.orderProductDeliveryRequest(requestSearchOrderVO);
        } catch (BizException ex) {
            log.error(ex.getMessage());
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(ex.getMessage())
                    .build();
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(ex.getMessage())
                    .build();
        }
    }

    /**
     * 배송 수령 확인
     * 구매자 > 수령확인
     * @return
     */
    @ApiOperation(value = "수령확인 처리")
    @PostMapping("/receipt/check")
    public ResponseData orderProductReceiptCheck(@RequestBody RequestSearchOrderVO requestSearchOrderVO){
        try {
            return orderReqSttsService.orderProductReceiptCheck(requestSearchOrderVO);
        } catch (BizException ex) {
            log.error(ex.getMessage());
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(ex.getMessage())
                    .build();
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(ex.getMessage())
                    .build();
        }
    }

}