package com.ibk.sb.restapi.biz.service.order.mypage;

import com.ibk.sb.restapi.app.common.constant.ComCode;
import com.ibk.sb.restapi.app.common.util.BizException;
import com.ibk.sb.restapi.app.common.util.FileUtil;
import com.ibk.sb.restapi.app.common.vo.CustomUser;
import com.ibk.sb.restapi.app.common.vo.PagingVO;
import com.ibk.sb.restapi.biz.service.common.repo.ComCodeRepo;
import com.ibk.sb.restapi.biz.service.common.vo.SummaryComCodeListVO;
import com.ibk.sb.restapi.biz.service.delivery.repo.DeliveryRepo;
import com.ibk.sb.restapi.biz.service.mainbox.MainBoxService;
import com.ibk.sb.restapi.biz.service.mainbox.vo.MainUserVO;
import com.ibk.sb.restapi.biz.service.order.mypage.repo.MyOrderRepo;
import com.ibk.sb.restapi.biz.service.order.mypage.vo.RequestSearchMyOrderVO;
import com.ibk.sb.restapi.biz.service.order.mypage.vo.SummarySalesBuyVO;
import com.ibk.sb.restapi.biz.service.order.mypage.vo.SummarySalesProductVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class MyOrderService {

    private final FileUtil fileUtil;

    private final MyOrderRepo myOrderRepo;

    private final DeliveryRepo deliveryRepo;

    private final ComCodeRepo comCodeRepo;

    private final MainBoxService mainBoxService;


    /**
     * 마이페이지 > 구매/판매 > 구매/판매 테이블 헤더 정보 조회
     *      buyCnt  : 구매목록 총 수
     *      sellCnt : 판매목록 총 수
     * @return
     */
    public Map<String, String> searchMyOrderHeader() throws Exception {

        Map<String, String> resultMap = new HashMap<>();

        // 로그인 체크
        String utlinsttId = null;

        // 로그인 정보가 CustomUser.class 라면 jwt 로그인 조회
        // 로그인 상태가 아니라면 String 타입으로 떨이짐
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof CustomUser) {
            CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            utlinsttId = user.getUtlinsttId();
        }

        RequestSearchMyOrderVO requestSearchMyOrderVO = new RequestSearchMyOrderVO();

        // 판매자 체크 - 구매목록 인 경우
        requestSearchMyOrderVO.setPucsUsisId(utlinsttId);
        requestSearchMyOrderVO.setSelrUsisId(null);
        List<SummarySalesBuyVO> summarySalesBuyVO = myOrderRepo.selectMyOrderBuyList(requestSearchMyOrderVO);
        resultMap.put("buyCnt", summarySalesBuyVO.size() > 0 ? summarySalesBuyVO.get(0).getTotalCnt().toString() : "0");

        // 판매자 체크 - 판매목록 인 경우
        requestSearchMyOrderVO.setPucsUsisId(null);
        requestSearchMyOrderVO.setSelrUsisId(utlinsttId);
        List<SummarySalesBuyVO> summarySalesSellVO = myOrderRepo.selectMyOrderBuyList(requestSearchMyOrderVO);
        resultMap.put("sellCnt", summarySalesSellVO.size() > 0 ? summarySalesSellVO.get(0).getTotalCnt().toString() : "0");

        return resultMap;
    }


    /**
     * 마이페이지 구매 / 판매 내역 리스트 조회
     * @param requestSearchMyOrderVO
     * @return
     * @throws Exception
     */
    public PagingVO<SummarySalesBuyVO> searchMyBuyList(RequestSearchMyOrderVO requestSearchMyOrderVO) throws Exception{

        // 로그인 체크
        String utlinsttId = null;

        // 로그인 정보가 CustomUser.class 라면 jwt 로그인 조회
        // 로그인 상태가 아니라면 String 타입으로 떨이짐
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof CustomUser) {
            CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            utlinsttId = user.getUtlinsttId();
        }

        if(requestSearchMyOrderVO.isSellerFlg()) { // 판매목록 인 경우
            requestSearchMyOrderVO.setSelrUsisId(utlinsttId);
        } else { // 구매목록 인 경우
            requestSearchMyOrderVO.setPucsUsisId(utlinsttId);
        }

//        // 판매 / 구매내역 리스트 조회
//        List<SummarySalesBuyVO> summarySalesBuyVO = myOrderRepo.selectMyOrderBuyList(requestSearchMyOrderVO);
//
//        // 주문 상품 정보 리스트 조회
//        if(summarySalesBuyVO.size() > 0) {
//            summarySalesBuyVO.forEach(x -> {
//                // 주문 상품 정보 리스트 조회 - 주문정보ID, 상품이미지 코드
//                List<SummarySalesProductVO> summarySalesProductVOList = myOrderRepo.selectMyOrderProduct(x.getOrdnInfoId(), ComCode.GDS05001.getCode());
//
//                // 주문 상품 대표 이미지 URL 설정
//                summarySalesProductVOList.forEach(y -> {
//
//                    /*
//                     * 판매자 / 구매자 명 셋팅
//                     */
//                    try {
//                        // 판매자명 셋팅
//                        if(StringUtils.hasLength(y.getSelrId()) && StringUtils.hasLength(y.getSelrUsisId())) {
//                            MainUserVO mainUserVO = mainBoxService.searchMainUser(y.getSelrId(), y.getSelrUsisId());
//                            y.setSelrNm(mainUserVO.getUserNm());
//                        }
//
//                        // 구매자명 셋팅
//                        if(StringUtils.hasLength(y.getPucsUsisId()) && StringUtils.hasLength(y.getPucsId())) {
//                            MainUserVO mainUserVO = mainBoxService.searchMainUser(y.getPucsId(), y.getPucsUsisId());
//                            y.setPucsNm(mainUserVO.getUserNm());
//                        }
//
//                    } catch (Exception e) {
//                        log.error("Fail Trace", e);
//                    }
//
//                    /*
//                     * 배송 주소 셋팅
//                     */
////                    // 배송정보 ID - 화물 서비스
////                    if(y.getDvryInfoId().equals(ComCode.DELIVERY_INFO_DVRY_R.getCode())) {
////                        DeliveryOrderProductServiceInfoVO deliveryinfoVO = deliveryRepo.selectDeliveryOrderProductServiceInfo(y.getOrdnInfoId(), y.getInfoSqn());
////                        y.setRlontfZpcd(deliveryinfoVO.getRlontfZpcd());            // 출고 우편번호
////                        y.setRlontfAdr(deliveryinfoVO.getRlontfAdr());              // 출고 주소
////                        y.setRlontfDtad(deliveryinfoVO.getRlontfDtad());            // 출고 상세주소
////                        y.setRecvZpcd(deliveryinfoVO.getRecvZpcd());                // 수령인 우편번호
////                        y.setRecvAdr(deliveryinfoVO.getRecvAdr());                  // 수령인 주소
////                        y.setRecvDtad(deliveryinfoVO.getRecvDtad());                // 수령인 상세주소
////                        y.setMainnbNo(deliveryinfoVO.getMainnbNo());                // 운송장 번호
////                        y.setEntpInfoId(deliveryinfoVO.getEntpInfoId());            // 업체 정보 ID
////                        y.setEntpNm(deliveryinfoVO.getEntpNm());                    // 업체명
////                        y.setDvrynone(deliveryinfoVO.getDvrynone());                // 배송비
////                    }
////                    // 배송정보 ID - 직접수령 // 구매자수령
////                    else if(y.getDvryInfoId().equals(ComCode.DELIVERY_INFO_UDVR_R.getCode())) {
////                        DeliveryOrderDirectPickUpInfoVO deliveryinfoVO = deliveryRepo.selectDeliveryOrderDirectPickUpInfo(y.getOrdnInfoId(), y.getInfoSqn());
////                        y.setRecvZpcd(deliveryinfoVO.getRecvZpcd());                // 수령인 우편번호
////                        y.setRecvAdr(deliveryinfoVO.getRecvAdr());                  // 수령인 주소
////                        y.setRecvDtad(deliveryinfoVO.getRecvDtad());                // 수령인 상세주소
////                    }
////                    // 배송정보 ID - 구매자배송정보 // 직접배송, 무료배송
////                    else if(y.getDvryInfoId().equals(ComCode.DELIVERY_INFO_PDVRY_R.getCode())) {
////                        DeliveryOrderBuyerDeliveryInfoVO deliveryinfoVO = deliveryRepo.selectDeliveryOrderBuyerDeliveryInfo(y.getOrdnInfoId(), y.getInfoSqn());
////                        y.setRecvZpcd(deliveryinfoVO.getRecvZpcd());                // 수령인 우편번호
////                        y.setRecvAdr(deliveryinfoVO.getRecvAdr());                  // 수령인 주소
////                        y.setRecvDtad(deliveryinfoVO.getRecvDtad());                // 수령인 상세주소
////                        y.setMainnbNo(deliveryinfoVO.getMainnbNo());                // 운송장 번호
////                        y.setPcsvcmpPtrnId(deliveryinfoVO.getPcsvcmpPtrnId());      // 택배사 유형 ID
////                        y.setPcsvcmpNm(deliveryinfoVO.getPcsvcmpNm());              // 택배사 명
////                        y.setDvrynone(deliveryinfoVO.getDvrynone());                // 배송비
////                    }
//                    // 배송정보 ID - 화물 서비스
//                    if(y.getDvryInfoId().equals(ComCode.DELIVERY_INFO_DVRY_R.getCode())) {
//                    }
//                    // 배송정보 ID - 직접수령 // 구매자수령
//                    else if(y.getDvryInfoId().equals(ComCode.DELIVERY_INFO_UDVR_R.getCode())) {
//                    }
//                    // 배송정보 ID - 구매자배송정보 // 직접배송, 무료배송
//                    else if(y.getDvryInfoId().equals(ComCode.DELIVERY_INFO_PDVRY_R.getCode())) {
//                    }
//
//                    // 상품 이미지 URL 셋팅
//                    y.setImgUrl(fileUtil.setImageUrl(y.getImgFileId()));
//                });
//
//                x.setItems(summarySalesProductVOList);
//            });
//        }

        // 판매 / 구매내역 리스트 조회
        List<SummarySalesBuyVO> buyVOList = myOrderRepo.selectMyOrderBuyList(requestSearchMyOrderVO);

        // 주문 상품 정보 리스트 조회
        if(buyVOList.size() > 0) {
            List<String> ordnInfoIds = new ArrayList<>();
            buyVOList.forEach(x -> {
                ordnInfoIds.add(x.getOrdnInfoId());
            });
            requestSearchMyOrderVO.setOrdnInfoIds(ordnInfoIds);

            // 주문 상품 정보 리스트 조회(한꺼번에 조회. in 절 사용) - 주문정보ID, 상품이미지 코드
            List<SummarySalesProductVO> summarySalesProductVOList = myOrderRepo.selectMyOrderProductList(requestSearchMyOrderVO);

            buyVOList.forEach(x -> {

                List<SummarySalesProductVO> pdfList = new ArrayList<>();

                // 주문 상품 대표 이미지 URL 설정
                summarySalesProductVOList.forEach(y -> {
                    if (x.getOrdnInfoId().equals(y.getOrdnInfoId())) { // 한꺼번에 조회했기 때문에 주문번호로 필터링
                        pdfList.add(y);
                    }
                });

                if(pdfList.size() > 0) {

                    String selrNm = null;
                    String pucsNm = null;
                    if (requestSearchMyOrderVO.isSellerFlg()) { // 판매목록 인 경우
                        if (StringUtils.hasLength(pdfList.get(0).getSelrId()) && StringUtils.hasLength(pdfList.get(0).getSelrUsisId())) {
                            selrNm = getSelrPucsNm(pdfList.get(0).getSelrId(), pdfList.get(0).getSelrUsisId()); // 판매자명 셋팅
                        }
                    } else {
                        if (StringUtils.hasLength(pdfList.get(0).getPucsId()) && StringUtils.hasLength(pdfList.get(0).getPucsUsisId())) {
                            pucsNm = getSelrPucsNm(pdfList.get(0).getPucsId(), pdfList.get(0).getPucsUsisId()); // 구매자명 셋팅
                        }
                    }

                    String finalSelrNm = selrNm;
                    String finalPucsNm = pucsNm;
                    pdfList.forEach(z -> {
                        if(requestSearchMyOrderVO.isSellerFlg()) { // 판매목록 인 경우
                            if(StringUtils.hasLength(z.getSelrId()) && StringUtils.hasLength(z.getSelrUsisId())) {
                                z.setSelrNm(finalSelrNm); // 판매자명 셋팅
                            }
                            if(StringUtils.hasLength(z.getPucsId()) && StringUtils.hasLength(z.getPucsUsisId())) {
                                z.setPucsNm(getSelrPucsNm(z.getPucsId(), z.getPucsUsisId())); // 구매자명 셋팅
                            }
                        } else { // 구매목록 인 경우
                            if(StringUtils.hasLength(z.getSelrId()) && StringUtils.hasLength(z.getSelrUsisId())) {
                                z.setSelrNm(getSelrPucsNm(z.getSelrId(), z.getSelrUsisId())); // 판매자명 셋팅
                            }
                            if(StringUtils.hasLength(z.getPucsId()) && StringUtils.hasLength(z.getPucsUsisId())) {
                                z.setPucsNm(finalPucsNm); // 구매자명 셋팅
                            }
                        }

                        // 상품 이미지 URL 셋팅
                        z.setImgUrl(fileUtil.setImageUrl(z.getImgFileId()));

                        // 운송장 번호 URL 세팅
                        z.setMainnbNoUrl(z.getMainnbNo() == null || z.getMainnbNo().equals("") ? null : "https://www.chunil.co.kr/HTrace/HTrace_mob.jsp?transNo=" + z.getMainnbNo()); // TODO 주소 하드코딩 개선하기
                    });

                    x.setItems(pdfList);
                }
            });
        }

        // DB 처리(공통코드)
        List<SummaryComCodeListVO> pcsvcmpPtrnCodes = comCodeRepo.searchComCodeList("ODS01"); // 공통관련코드(택배업체)
        if (pcsvcmpPtrnCodes.size() > 0) {
            for (SummarySalesBuyVO vo : buyVOList) {
                vo.setPcsvcmpPtrnCodes(pcsvcmpPtrnCodes);
            }
        }

        return new PagingVO<>(requestSearchMyOrderVO, buyVOList);
    }

    private String getSelrPucsNm(String id, String usisId) {
        String name = null;
        try {
            MainUserVO mainUserVO = mainBoxService.searchMainUser(id, usisId);
            name = mainUserVO.getUserNm();
        } catch (BizException bx) {
            log.error("Business Exception ({}) : {}", bx.getErrorCode(), bx.getErrorMsg());
        } catch (Exception e) {
            log.error("mainBoxService.searchMainUser", e);
        }
        return name;
    }

}