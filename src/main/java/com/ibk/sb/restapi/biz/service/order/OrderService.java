package com.ibk.sb.restapi.biz.service.order;

import com.ibk.sb.restapi.app.common.constant.AlarmCode;
import com.ibk.sb.restapi.app.common.constant.ComCode;
import com.ibk.sb.restapi.app.common.constant.StatusCode;
import com.ibk.sb.restapi.app.common.util.BizException;
import com.ibk.sb.restapi.app.common.util.FileUtil;
import com.ibk.sb.restapi.app.common.vo.CustomUser;
import com.ibk.sb.restapi.app.common.vo.PagingVO;
import com.ibk.sb.restapi.app.common.vo.ResponseData;
import com.ibk.sb.restapi.biz.service.alarm.AlarmService;
import com.ibk.sb.restapi.biz.service.alarm.vo.RequestAlarmVO;
import com.ibk.sb.restapi.biz.service.estimation.EstimationService;
import com.ibk.sb.restapi.biz.service.estimation.vo.EstimationProductVO;
import com.ibk.sb.restapi.biz.service.estimation.vo.RequestSearchEstimationVO;
import com.ibk.sb.restapi.biz.service.mainbox.MainBoxService;
import com.ibk.sb.restapi.biz.service.mainbox.vo.MainUserVO;
import com.ibk.sb.restapi.biz.service.order.repo.*;
import com.ibk.sb.restapi.biz.service.order.vo.*;
import com.ibk.sb.restapi.biz.service.order.vo.esm.OrderDeliveryEsmVO;
import com.ibk.sb.restapi.biz.service.order.vo.esm.OrderProductEsmVO;
import com.ibk.sb.restapi.biz.service.order.vo.esm.SummaryPayEsmVO;
import com.ibk.sb.restapi.biz.service.order.vo.req.OrderReqDeliveryVO;
import com.ibk.sb.restapi.biz.service.order.vo.req.OrderReqProductVO;
import com.ibk.sb.restapi.biz.service.order.vo.req.OrderReqStlmVO;
import com.ibk.sb.restapi.biz.service.order.vo.req.SummaryOrderResultVO;
import com.ibk.sb.restapi.biz.service.product.single.vo.SingleProductVO;
import com.ibk.sb.restapi.biz.service.seller.repo.SellerStoreRepo;
import com.ibk.sb.restapi.biz.service.seller.vo.SellerInfoVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final FileUtil fileUtil;

    private final OrderDeliveryRepo orderDeliveryRepo;
    private final OrderProductRepo orderProductRepo;
    private final OrderEsttRepo orderEsttRepo;
    private final OrderReturnRepo orderReturnRepo;
    private final MainBoxService mainBoxService;

    private final SellerStoreRepo sellerStoreRepo;
    private final AlarmService alarmService;

    /**
     * 결제하기
     * @return
     */
    public ResponseData orderPay(){
        return ResponseData.builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .build();
    }

    /**
     * 견적을 제외한 나머지 > 결제하기 > 결제 정보 가져오기
     * param : products(상품아이디 및 구매수량 목록)
     * @return
     */
    public ResponseData getOrderPayList(RequestPayVO requestPayVO){

        SummaryPayVO summaryPayVO = new SummaryPayVO(); // response data
        summaryPayVO.setTotalProductPay(0);
        summaryPayVO.setTotalProductDeliveryPay(0);

        // 로그인 체크
        String loginUserId = null;
        String utlinsttId = null;

        // 로그인 정보가 CustomUser.class 라면 jwt 로그인 조회
        // 로그인 상태가 아니라면 String 타입으로 떨이짐
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof CustomUser) {
            CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            loginUserId = user.getLoginUserId();
            utlinsttId = user.getUtlinsttId();
        }

        // TODO 로그인 정보 필요 : 구매자정보 확인시 필요
        requestPayVO.setPucsUsisId(utlinsttId);
        requestPayVO.setPucsUserId(loginUserId);

        OrderDeliveryVO deliveryData = orderDeliveryRepo.getDeliveryInfo(requestPayVO); // 구매자 정보 가져오기

        // 구매자가 구매가능 권한 체크
        if(deliveryData != null &&
                deliveryData.getMmbrsttsId() != null &&
                deliveryData.getMmbrsttsId().equals(ComCode.SELLER_APPROVED.getCode())){

            String selrUsisId = null; // 판매자 이용기관 ID
            List<OrderProductVO> productData = new ArrayList<>();

            // 결제하려는 상품목록
            for(RequestProductVO item : requestPayVO.getProducts()){

                // 초기 선언
                OrderProductVO orderProduct = new OrderProductVO(); // 검수 상품 목록
                OrderProductSalLVO orderProductSalLVO = new OrderProductSalLVO(); // 상품 판매정보
                OrderProductInfMVO orderProductInfMVO = new OrderProductInfMVO(); // 상품 정보
                OrderProductDelLVO orderProductDelLVO = new OrderProductDelLVO(); // 배송 정보
                OrderProductFieRVO orderProductFieRVO = new OrderProductFieRVO(); // 파일 정보
                OrderProductDvryMVO orderProductDvryMVO = new OrderProductDvryMVO(); // 화물서비스 기본정보

                // 기본값 선언
                orderProduct.setPayUsed("N"); //주문가능 상품여부
                Boolean CheckQtyState = true; // 주문수량체크

                // 상품 공통 가능 조건 설정
                item.setPdfSttsId(ComCode.SELLING_OK.getCode()); //판매중
                item.setPdfPgrsYn("Y"); //진열중

                //
                orderProductSalLVO.setPdfInfoId(item.getPdfInfoId()); //상품아이디
                orderProductSalLVO.setPrcDscsYn("N"); //가격협의여부
                orderProductSalLVO.setEsttUseEn("N"); //견적사용여부
                orderProductSalLVO = orderProductRepo.getProductSalL(orderProductSalLVO); // 상품판매정보 가져오기

                // 주문수량 제한을 사용하는 경우
                if(orderProductSalLVO != null) {

                    selrUsisId = orderProductSalLVO.getSelrUsisId();

                    if (orderProductSalLVO.getSelrUsisId().equals(utlinsttId)) { // 자신의 상품인지 체크
                        return ResponseData.builder()
                                .code(HttpStatus.BAD_REQUEST.value())
                                .message(StatusCode.ORDER0020.getMessage()) // 본인 상품은 주문이 불가능합니다.
                                .build();
                    }

                    if (orderProductSalLVO.getOrdnQtyLmtnUsyn().equals("Y")) {
                        if (
                                (item.getOrderQty() < orderProductSalLVO.getOrdnMnmmQty() && orderProductSalLVO.getOrdnMxmmQtyYn().equals("N")) || // 최소주문수량~제한없음
                                (item.getOrderQty() < orderProductSalLVO.getOrdnMnmmQty() || (orderProductSalLVO.getOrdnMxmmQtyYn().equals("Y") && item.getOrderQty() > orderProductSalLVO.getOrdnMxmmQty())) //최수주문수량~최대주문수량
                        ) {
                            CheckQtyState = false;
                        }
                    }
                } else {
                    return ResponseData.builder()
                            .code(HttpStatus.BAD_REQUEST.value())
                            .message(StatusCode.ORDER0003.getMessage()) // 상품을 확인바랍니다.
                            .build();
                }

                // 상품 기본 정보(판매중, 진열중)+주문수량허용 범위인 경우
                if(orderProductRepo.orderCheckProductInfo(item).equals(1) && CheckQtyState){

                    /**
                     * 상품의 각종 정보 가져오기
                     * */
                    orderProductInfMVO.setPdfInfoId(item.getPdfInfoId());
                    orderProductInfMVO.setPdfPgrsYn(item.getPdfPgrsYn());
                    orderProductInfMVO.setPdfSttsId(item.getPdfSttsId());
                    orderProductInfMVO = orderProductRepo.getProductInfM(orderProductInfMVO);
                    orderProduct.setProductInfo(orderProductInfMVO); // 상품정보

                    orderProductDelLVO.setPdfInfoId(item.getPdfInfoId());
                    orderProductDelLVO = orderProductRepo.getProductDelL(orderProductDelLVO);
                    orderProduct.setProductDelInfo(orderProductDelLVO); // 상품 배송정보

                    orderProduct.setProductSalInfo(orderProductSalLVO); // 판매정보

                    orderProductFieRVO.setPdfInfoId(item.getPdfInfoId());
                    orderProductFieRVO.setFilePtrnId(ComCode.GDS05001.getCode());
                    orderProductFieRVO = orderProductRepo.getProductFieR(orderProductFieRVO);
                    if(orderProductFieRVO != null) {
                        orderProductFieRVO.setImgUrl(fileUtil.setImageUrl(orderProductFieRVO.getFileId()));
                    } else {
                        return ResponseData.builder()
                                .code(HttpStatus.BAD_REQUEST.value())
                                .message(StatusCode.ORDER0003.getMessage()) // 상품을 확인바랍니다.
                                .build();
                    }
                    orderProduct.setProductFieInfo(orderProductFieRVO); // 상품이미지

                    List<OrderProductQtyDvryMVO> orderProductQtyDvryMVO = orderProductRepo.getProductQtyDvryM(item.getPdfInfoId());
                    orderProduct.setProductQtyDvryInfo(orderProductQtyDvryMVO); // 수량별 배송정보

                    List<OrderProductPearDvryMVO> orderProductPearDvryMVO = orderProductRepo.getProductPearDvryM(item.getPdfInfoId());
                    orderProduct.setProductPearDvryInfo(orderProductPearDvryMVO); // 지역별 배송정보

                    orderProductDvryMVO.setPdfInfoId(item.getPdfInfoId());
                    orderProductDvryMVO = orderProductRepo.getProductDvryM(orderProductDvryMVO);
                    orderProduct.setProductDvryInfo(orderProductDvryMVO); // 화물서비스 기본정보

                    List<OrderProductDvryEsttMVO> orderProductDvryEsttMVO = orderProductRepo.getProductDvryEsttM(item.getPdfInfoId());
                    orderProduct.setProductEsttInfo(orderProductDvryEsttMVO); // 화물서비스 견적정보

                    /**
                     * 상품정보 업데이트 - 기본정보
                     * */
                    orderProduct.setPayUsed("Y"); // 주문가능 상품표기
                    orderProduct.setProductQty(item.getOrderQty()); // 상품구매수량

                    /**
                     * 상품정보 업데이트 - 상품결제금액
                     * */
                    // 상품결제금액
                    Integer ProductPrice = 0;
                    if(orderProduct.getProductSalInfo().getSalePrc() > 0){
                        ProductPrice = orderProduct.getProductSalInfo().getSalePrc(); // 할인가가 있는경우 할인가로 주문금액설정
                    }else{
                        ProductPrice = orderProduct.getProductSalInfo().getPdfPrc(); //할인가가 없는경우는 판매가로 주문금액 설정
                    }

                    Integer ProductPriceSum = ProductPrice * item.getOrderQty();
                    orderProduct.setProductPay(ProductPriceSum); // 상품결제금액 설정(금액 * 구매수량)
                    summaryPayVO.setTotalProductPay(summaryPayVO.getTotalProductPay() + ProductPriceSum); // 총 금액

                    /**
                     * 상품정보 업데이트 - 상품배송비
                     * 무료배송비는 자동적으로 0원이며, 화물서비스 이용은 별도의 운임체크를 통해 배송비가 측정되므로, 직접배송에 대해서만 금액을 계산합니다.
                     * */
                    // 직접배송시 로그인 정보의 옵션에 따라 주소 정보 쪼개기
                    String getUserAddr = ""; //주소
                    String getSido = ""; // 시도
                    String getSigungu = ""; //군구

                    getUserAddr = requestPayVO.getAddr(); // 요청주소지를 기본으로 설정

                    if(getUserAddr.equals("")){
                        // 저장된 배송지를 사용하는 경우 기본값으로 설정
                        if(deliveryData.getDlplUseYn() != null && deliveryData.getDlplUseYn().equals("Y")){
                            getUserAddr = deliveryData.getAdr();
                        }
                    }

                    // 주소 정보 기준으로 시,군구 나누기
                    if(!getUserAddr.equals("")){
                        String[] splitData = getUserAddr.split(" ");
                        // 최소 splitData가 2개인경우 0,1번으로 시도, 군구로 설정해준다.
                        if(splitData.length >= 2){
                            getSido = splitData[0];
                            getSigungu = splitData[1];
                            // 최소 splitData가 1개인경우 0번으로 Sido만 설정해준다.
                        }else if(splitData.length == 1){
                            getSido = splitData[0];
                        }
                    }

                    // 배송비설정
                    Integer ProductDeliveryPrice = 0;

                    // 직접배송
                    if(orderProduct.getProductDelInfo().getDvryPtrnId().equals(ComCode.GDS02002.getCode())){

                        ProductDeliveryPrice = orderProduct.getProductDelInfo().getDvryBscprce(); //배송기본가 설정
                        String ptrnId = orderProduct.getProductDelInfo().getDvrynonePtrnId(); // 배송유형 ID

                        if(ptrnId != null) { // 지역별 배송비, 수량별 배송비 정보가 있는 경우

                            //지역별 배송비
                            if(ptrnId.equals(ComCode.GDS04004.getCode()) || ptrnId.equals(ComCode.GDS04002.getCode())){

                                Integer PearDvryPrice = 0;
                                for(OrderProductPearDvryMVO sidoItem : orderProductPearDvryMVO){

                                    // 시도가 같은경우
                                    if(getSido.equals(sidoItem.getTrl())){

                                        // 시도는 맞는데 군구가 없는경우 시도의 마지막 값을 기본값을 시도 값으로 설정한다.
                                        PearDvryPrice = sidoItem.getAmt();

                                        // 군구를 다시 한번 비교
                                        for(OrderProductPearDvryMVO sigunguItem : orderProductPearDvryMVO) {
                                            if(getSido.equals(sidoItem.getTrl()) &&
                                                    getSigungu.equals(sigunguItem.getCtcocrwd())){

                                                PearDvryPrice = sigunguItem.getAmt(); //시,군구까지 같은경우를 최종값으로 업데이트
                                            }
                                        }
                                    }
                                }
                                ProductDeliveryPrice += PearDvryPrice; // 지역별 금액추가
                            }

                            //수량별 배송비
                            if(ptrnId.equals(ComCode.GDS04004.getCode()) || ptrnId.equals(ComCode.GDS04003.getCode()) ){

                                Integer QtyDvryPrice = 0;
                                for(OrderProductQtyDvryMVO qtyItem : orderProductQtyDvryMVO){
                                    // 구매수량이 지정수량 보다 이상 구매시 지정 금액 더하기
                                    if(item.getOrderQty() >= qtyItem.getQty()){
                                        QtyDvryPrice = qtyItem.getAmt();
                                    }
                                }

                                ProductDeliveryPrice += QtyDvryPrice; // 수량별 금액추가
                            }
                        }

                    }

                    orderProduct.setProductDeliveryPay(ProductDeliveryPrice);
                    summaryPayVO.setTotalProductDeliveryPay(summaryPayVO.getTotalProductDeliveryPay() + ProductDeliveryPrice); // 총 금액

                }

                // 배열에 더하기
                productData.add(orderProduct);
            }

            summaryPayVO.setSellerInfoVO(sellerStoreRepo.selectSellerInfo(selrUsisId)); // 판매자 정보
            summaryPayVO.setDeliveryInfo(deliveryData); // 배송정보
            summaryPayVO.setProducts(productData); // 상품정보

            // 결제에 사용할 주문 정보 ID 채번
            summaryPayVO.setOrderReqStlmVO(new OrderReqStlmVO(UUID.randomUUID().toString()));

        }else{
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(StatusCode.ORDER0001.getMessage())
                    .build();
        }

        return ResponseData.builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(summaryPayVO)
                .build();
    }

    /**
     * 견적서 > 결제하기 > 결제 정보 가져오기
     * param : esttInfoId(견적ID)
     * @return
     */
    public ResponseData getOrderPayEsmList(RequestPayVO requestPayVO){

        SummaryPayEsmVO summaryPayEsmVO = new SummaryPayEsmVO();

        /*
        #request 정보
        1. 견적정보ID : esttInfoId

        #조건
        1. 자신이 받은 견적번호가 맞는지 확인

        #기능
        1.배송정보 - 직접배송 ---> 견적서 배송비 더하기 ---> 구매자 배송정보 입력화면 노출 및 구매자 배송정보 내려주기
        2.배송정보 - 배송비없음 ---> 배송비 0원 --->  구매자 배송정보 입력화면 노출 및 구매자 배송정보 내려주기
        3.배송정보 - 구매자가 직접 수령 ---> 배송비 0원 ---> 견적서에 작성된 상품수령위치 정보 내려주기
        4.배송정보 - 화물서비스 이용 ---> 견적서 배송비 더하기 ---> 견적서에 작성된 받는사람주소 정보 내려주기

        #response 정보
        1.배송정보
        2.상품정보
        3.결제정보
        * */

        // 로그인 체크
        String loginUserId = null;
        String utlinsttId = null;

        // 로그인 정보가 CustomUser.class 라면 jwt 로그인 조회
        // 로그인 상태가 아니라면 String 타입으로 떨이짐
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof CustomUser) {
            CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            loginUserId = user.getLoginUserId();
            utlinsttId = user.getUtlinsttId();
        }

        // TODO 로그인 정보 필요 : 구매자정보 확인 및 요청한 견적서가 맞는지 확인
        requestPayVO.setPucsUsisId(utlinsttId);
        requestPayVO.setPucsUserId(loginUserId);

        requestPayVO.setPcsnSttsId(ComCode.ESS02001.getCode()); //발송상태

        OrderDeliveryVO deliveryData = orderDeliveryRepo.getDeliveryInfo(requestPayVO); // 구매자 정보 가져오기

        if(deliveryData != null && deliveryData.getMmbrsttsId().equals(ComCode.SELLER_APPROVED.getCode())) { // 구매자가 구매가능 권한 체크

            if(orderEsttRepo.orderCheckEsmInfo(requestPayVO).equals(1)){ // 주문가능 유무 확인

                OrderDeliveryEsmVO deliveryEsmData = orderEsttRepo.getProductEsmDelivery(requestPayVO); // 견적 배송정보 가져오기

                /**
                 * 견적 배송 정보
                 * */
                summaryPayEsmVO.setDeliveryEsmInfo(deliveryEsmData); // 견적 배송정보

                /**
                 * 견적 상품 정보
                 * */
                //
                List<OrderProductEsmVO> productEsmPdfList = orderEsttRepo.getProductEsmPdfList(requestPayVO); //상품목록 검색

                /**
                 * 상품금액 합계
                 * */
                Integer totalPrice = 0;
                for(OrderProductEsmVO item : productEsmPdfList){
                    totalPrice += (item.getPdfPrc() * item.getOrdnQty());
                }
                // 이미지 URL 셋팅
                if(productEsmPdfList.size() > 0) {
                    productEsmPdfList.forEach(x -> {
                        x.setImgUrl(fileUtil.setImageUrl(x.getImgFileId()));
                    });
                }

                /**
                 * response
                 * */
                summaryPayEsmVO.setTotalProductPay(totalPrice); // 합계 상품가격
                summaryPayEsmVO.setTotalProductDeliveryPay(deliveryEsmData.getDvrynone()); // 합계 배송비
                summaryPayEsmVO.setDvryPtrnId(deliveryEsmData.getDvryPtrnId()); // 견적 배송구분
                summaryPayEsmVO.setEsttInfoId(requestPayVO.getEsttInfoId()); // 견적 정보 ID
                summaryPayEsmVO.setDeliveryInfo(deliveryData); // 구매자 배송정보
                summaryPayEsmVO.setSellerInfoVO(sellerStoreRepo.selectSellerInfo(deliveryEsmData.getSelrUsisId())); // 판매자 정보
                summaryPayEsmVO.setProducts(productEsmPdfList); // 견적 상품정보

                // 결제에 사용할 주문 정보 ID 채번
                summaryPayEsmVO.setOrderReqStlmVO(new OrderReqStlmVO(UUID.randomUUID().toString()));

            }else{
                return ResponseData.builder()
                        .code(HttpStatus.BAD_REQUEST.value())
                        .message(StatusCode.ESTIM0001.getMessage())
                        .build();
            }
        }else{
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(StatusCode.ORDER0001.getMessage())
                    .build();
        }

        return ResponseData.builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(summaryPayEsmVO)
                .build();
    }

    /**
     * 반품상세 정보
     * @return
     */
    public ResponseData orderProductReturnInfo(RequestReturnVO requestReturnVO) throws Exception {

        OrderReturnVO response = orderReturnRepo.orderProductInfo(requestReturnVO);

        // 주문정보 확인
        if(response != null && (requestReturnVO.getReturnType().equals("buyer") || requestReturnVO.getReturnType().equals("selr"))){

            // 로그인 체크
            String loginUserId = null;
            String utlinsttId = null;

            // 로그인 정보가 CustomUser.class 라면 jwt 로그인 조회
            // 로그인 상태가 아니라면 String 타입으로 떨이짐
            if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof CustomUser) {
                CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                loginUserId = user.getLoginUserId();
                utlinsttId = user.getUtlinsttId();
            }

            // TODO 로그인 정보 필요 : 검색기준별 조건 사용
            requestReturnVO.setUsisId(utlinsttId);
            requestReturnVO.setUserId(loginUserId);
            requestReturnVO.setFilePtrnId(ComCode.GDS05001.getCode()); //상품이미지

            response = orderReturnRepo.orderProductReturnInfo(requestReturnVO);

            // 판매자명 셋팅
            if(StringUtils.hasLength(response.getSelrId()) && StringUtils.hasLength(response.getSelrUsisId())) {
                MainUserVO mainUserVO = mainBoxService.searchMainUser(response.getSelrId(), response.getSelrUsisId());
                response.setSelrNm(mainUserVO.getUserNm());
            }

            // 구매자명 셋팅
            if(StringUtils.hasLength(response.getPucsUsisId()) && StringUtils.hasLength(response.getPucsId())) {
                MainUserVO mainUserVO = mainBoxService.searchMainUser(response.getPucsId(), response.getPucsUsisId());
                response.setPucsNm(mainUserVO.getUserNm());
            }

            // 이미지 URL 셋팅
            response.setImgUrl(fileUtil.setImageUrl(response.getImgFileId()));

        }else{
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(StatusCode.ORDER0002.getMessage())
                    .build();
        }

        return ResponseData.builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(response)
                .build();
    }

    /**
     * 반품상세 정보 > 반품요청
     * @return
     */
    public ResponseData orderProductReturnReq(RequestReturnVO requestReturnVO) throws Exception {

        // 로그인 체크
        String loginUserId = null;
        String utlinsttId = null;

        // 로그인 정보가 CustomUser.class 라면 jwt 로그인 조회
        // 로그인 상태가 아니라면 String 타입으로 떨이짐
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof CustomUser) {
            CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            loginUserId = user.getLoginUserId();
            utlinsttId = user.getUtlinsttId();
        }
        // TODO 로그인 정보 필요 : 검색기준별 조건 사용
        requestReturnVO.setUsisId(utlinsttId);
        requestReturnVO.setUserId(loginUserId);
        OrderReturnVO response = orderReturnRepo.orderProductInfo(requestReturnVO);

        // 주문정보의 구매자이면서 주문상태가 배송완료 상태인경우
        if(response.getPucsUsisId().equals(requestReturnVO.getUsisId()) && response.getOrdnSttsId().equals(ComCode.ODS00004.getCode())){

            requestReturnVO.setReturnType("buyer"); //구매자설정
            requestReturnVO.setOrdnSttsId(ComCode.ODS00005.getCode()); //반품요청

            orderReturnRepo.updateReturnState(requestReturnVO); //정보업데이트
            orderReturnRepo.addReturnStateHistory(requestReturnVO); //이력등록

            /*
             * 알림 서비스
             */
            // 알림 요청 인스턴스 생성
            RequestAlarmVO requestAlarmVO = alarmService.getAlarmRequest(AlarmCode.AlarmCodeEnum.AGENCY_REQUEST, null, new Object[]{response.getPdfNm()});
            // 알림 발송
            alarmService.sendMarketAlarm(requestAlarmVO, response.getSelrUsisId());

        }else{
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(StatusCode.ORDER0002.getMessage())
                    .build();
        }

        return ResponseData.builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .build();
    }

    /**
     * 반품상세 정보 > 반품불가
     * @return
     */
    public ResponseData orderProductReturnImpossible(RequestReturnVO requestReturnVO) throws Exception {

        // 로그인 체크
        String loginUserId = null;
        String utlinsttId = null;

        // 로그인 정보가 CustomUser.class 라면 jwt 로그인 조회
        // 로그인 상태가 아니라면 String 타입으로 떨이짐
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof CustomUser) {
            CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            loginUserId = user.getLoginUserId();
            utlinsttId = user.getUtlinsttId();
        }

        // TODO 로그인 정보 필요 : 검색기준별 조건 사용
        requestReturnVO.setUsisId(utlinsttId);
        requestReturnVO.setUserId(loginUserId);
        OrderReturnVO response = orderReturnRepo.orderProductInfo(requestReturnVO);

        // 판매자만 반품불가 가능, 주문이 반품요청 상태에서만 가능
        if(response.getSelrUsisId().equals(requestReturnVO.getUsisId()) && response.getOrdnSttsId().equals(ComCode.ODS00005.getCode())){

            requestReturnVO.setReturnType("selr"); //판매자설정
            requestReturnVO.setOrdnSttsId(ComCode.ODS00006.getCode()); //반품불가

            orderReturnRepo.updateReturnState(requestReturnVO); //정보업데이트
            orderReturnRepo.addReturnStateHistory(requestReturnVO); //이력등록

            /*
             * 알림 서비스
             */
            // 알림 요청 인스턴스 생성
            RequestAlarmVO requestAlarmVO = alarmService.getAlarmRequest(AlarmCode.AlarmCodeEnum.AGENCY_REQUEST, null, new Object[]{response.getPdfNm()});
            // 알림 발송
            alarmService.sendMarketAlarm(requestAlarmVO, response.getPucsUsisId());

        }else{
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(StatusCode.ORDER0002.getMessage())
                    .build();
        }

        return ResponseData.builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .build();
    }

    /**
     * 반품상세 정보 > 반품완료
     * @return
     */
    public ResponseData orderProductReturnCompletion(RequestReturnVO requestReturnVO) throws Exception {

        // 로그인 체크
        String loginUserId = null;
        String utlinsttId = null;

        // 로그인 정보가 CustomUser.class 라면 jwt 로그인 조회
        // 로그인 상태가 아니라면 String 타입으로 떨이짐
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof CustomUser) {
            CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            loginUserId = user.getLoginUserId();
            utlinsttId = user.getUtlinsttId();
        }

        // TODO 로그인 정보 필요 : 검색기준별 조건 사용
        requestReturnVO.setUsisId(utlinsttId);
        requestReturnVO.setUserId(loginUserId);
        OrderReturnVO response = orderReturnRepo.orderProductInfo(requestReturnVO);

        // 판매자만 반품불가 가능, 주문이 반품요청 상태에서만 가능
        if(response.getSelrUsisId().equals(requestReturnVO.getUsisId()) && response.getOrdnSttsId().equals(ComCode.ODS00005.getCode())){

            requestReturnVO.setReturnType("selr"); //판매자설정
            requestReturnVO.setOrdnSttsId(ComCode.ODS00007.getCode()); //반품완료

            orderReturnRepo.updateReturnState(requestReturnVO); //정보업데이트
            orderReturnRepo.addReturnStateHistory(requestReturnVO); //이력등록

            /*
             * 알림 서비스
             */
            // 알림 요청 인스턴스 생성
            RequestAlarmVO requestAlarmVO = alarmService.getAlarmRequest(AlarmCode.AlarmCodeEnum.AGENCY_REQUEST, null, new Object[]{response.getPdfNm()});
            // 알림 발송
            alarmService.sendMarketAlarm(requestAlarmVO, response.getPucsUsisId());

        }else{
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(StatusCode.ORDER0002.getMessage())
                    .build();
        }

        return ResponseData.builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .build();
    }

    /**
     * 반품목록
     * @return
     */
    public ResponseData orderProductReturnList(RequestReturnVO requestReturnVO){

        // 로그인 체크
        String loginUserId = null;
        String utlinsttId = null;

        // 로그인 정보가 CustomUser.class 라면 jwt 로그인 조회
        // 로그인 상태가 아니라면 String 타입으로 떨이짐
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof CustomUser) {
            CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            loginUserId = user.getLoginUserId();
            utlinsttId = user.getUtlinsttId();
        }

        // TODO 로그인 정보 필요 : 검색기준별 조건 사용
        requestReturnVO.setUsisId(utlinsttId);
        requestReturnVO.setUserId(loginUserId);
        requestReturnVO.setFilePtrnId(ComCode.GDS05001.getCode()); //상품이미지

        SummaryReturnVO summaryReturnVO = new SummaryReturnVO();

        String reqReturnType = requestReturnVO.getReturnType();
        requestReturnVO.setReturnType("buyer"); //구매자기준 > 보낸요청
        summaryReturnVO.setSenTotal(orderReturnRepo.returnListsTotal(requestReturnVO));
        requestReturnVO.setReturnType("selr"); //판매자기준 > 받은요청
        summaryReturnVO.setRecTotal(orderReturnRepo.returnListsTotal(requestReturnVO));

        requestReturnVO.setReturnType(reqReturnType); // 요청 reqReturnType 다시 set
        List<OrderReturnVO> lists = orderReturnRepo.orderProductReturnList(requestReturnVO);

        if(lists.size() > 0) {
            lists.forEach(x -> {

                /*
                 * 판매자 / 구매자 명 셋팅
                 */
                try {
                    // 판매자명 셋팅
                    if(StringUtils.hasLength(x.getSelrId()) && StringUtils.hasLength(x.getSelrUsisId())) {
                        MainUserVO mainUserVO = mainBoxService.searchMainUser(x.getSelrId(), x.getSelrUsisId());
                        x.setSelrNm(mainUserVO.getUserNm());
                    }

                    // 구매자명 셋팅
                    if(StringUtils.hasLength(x.getPucsUsisId()) && StringUtils.hasLength(x.getPucsId())) {
                        MainUserVO mainUserVO = mainBoxService.searchMainUser(x.getPucsId(), x.getPucsUsisId());
                        x.setPucsNm(mainUserVO.getUserNm());
                    }

                } catch (BizException bx) {
                    log.error("Business Exception ({}) : {}", bx.getErrorCode(), bx.getErrorMsg());
                } catch (Exception e) {
                    log.error("Fail Trace", e);
                }

                // 이미지 URL 셋팅
                x.setImgUrl(fileUtil.setImageUrl(x.getImgFileId()));
            });
        }
        summaryReturnVO.setLists(new PagingVO<>(requestReturnVO, lists));

        return ResponseData.builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(summaryReturnVO)
                .build();
    }

}
