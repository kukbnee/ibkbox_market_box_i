package com.ibk.sb.restapi.biz.service.basket;

import com.ibk.sb.restapi.app.common.constant.ComCode;
import com.ibk.sb.restapi.app.common.constant.StatusCode;
import com.ibk.sb.restapi.app.common.util.FileUtil;
import com.ibk.sb.restapi.app.common.vo.CustomUser;
import com.ibk.sb.restapi.app.common.vo.ResponseData;
import com.ibk.sb.restapi.biz.service.basket.repo.BasketRepo;
import com.ibk.sb.restapi.biz.service.basket.vo.BasketVO;
import com.ibk.sb.restapi.biz.service.basket.vo.SummaryBasketVO;
import com.ibk.sb.restapi.biz.service.delivery.repo.DeliveryRepo;
import com.ibk.sb.restapi.biz.service.delivery.vo.DeliveryinfoVO;
import com.ibk.sb.restapi.biz.service.product.single.repo.SingleProductRepo;
import com.ibk.sb.restapi.biz.service.product.single.vo.SingleProductVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class BasketService {

    private final BasketRepo basketRepo;

    private final DeliveryRepo deliveryRepo;

    private final SingleProductRepo singleProductRepo;

    private final FileUtil fileUtil;

    /**
     * 장바구니 리스트 조회
     * @return
     * @throws Exception
     */
    public Map<String, Object> searchBasketList() throws Exception {

        // 로그인 체크
        String loginUserId = null;
        String loginUtlinsttId = null;

        // 로그인 정보가 CustomUser.class 라면 jwt 로그인 조회
        // 로그인 상태가 아니라면 String 타입으로 떨이짐
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof CustomUser) {
            CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            loginUserId = user.getLoginUserId();
            loginUtlinsttId = user.getUtlinsttId();
        }

        // 장바구니 리스트 조회
        List<SummaryBasketVO> basketProductVOList = basketRepo.selectBasketList(loginUserId, loginUtlinsttId, ComCode.GDS05001.getCode(), null);

        // 장바구니 페이지 표시용 result map
        Map<String, Object> result = new HashMap<>();

        basketProductVOList.forEach(x -> {
            // 장바구니 상품 이미지 URL 셋팅
            x.setImgUrl(fileUtil.setImageUrl(x.getImgFileId()));

            /*
             * 판매자 이용기관 명, 상품 상태 명 셋팅
             */
            // 상품 상태 명 셋팅
            x.setPdfSttsName(ComCode.of(x.getPdfSttsId()).getName());

            /*
             * 상품별 배송정보 셋팅
             */
            // 배송 기본 정보 조회
            DeliveryinfoVO deliveryinfoVO = deliveryRepo.selectDeliveryInfo(x.getPdfInfoId());
            x.setDeliveryinfo(deliveryinfoVO);

            // 무료 배송이 아닐 경우,
            if(!deliveryinfoVO.getDvryTypeId().equals(ComCode.GDS02003.getCode())) {

                // 배송 유형이 화물 서비스 일 경우,
                if(deliveryinfoVO.getDvryTypeId().equals(ComCode.GDS02001.getCode())) {
                    // 화물서비스 견적 정보 리스트 조회
                    x.setDeliveryProductServiceInfoList(deliveryRepo.selectDeliveryProductInfo(x.getPdfInfoId()));
                }

                // 배송비 유형이 지역별 배송비 혹은 지역/수량별 배송비일 경우
                String dvrynonePtrnId = deliveryinfoVO.getDvrynonePtrnId();
                if (dvrynonePtrnId != null) {
                    if (dvrynonePtrnId.equals(ComCode.GDS04002.getCode()) || dvrynonePtrnId.equals(ComCode.GDS04004.getCode())) {
                        // 상품 지역별 배송 정보 리스트 조회
                        x.setDeliveryLocalInfoList(deliveryRepo.selectDeliveryLocalInfo(x.getPdfInfoId()));
                    }

                    // 배송비 유형이 수량별 배송비 혹은 지역/수량별 배송비일 경우
                    if (dvrynonePtrnId.equals(ComCode.GDS04003.getCode()) || dvrynonePtrnId.equals(ComCode.GDS04004.getCode())) {
                        // 상품 수량별 배송 정보 리스트 조화
                        x.setDeliveryCntInfoList(deliveryRepo.selectDeliveryCntInfo(x.getPdfInfoId()));
                    }
                }
            }

            /*
             * 장바구니 페이지 표시용 result map 셋팅
             */
            // key 값으로 회사 명이 등록되어있는 경우
            if(result.containsKey(x.getSelrUsisName())) {
                List<SummaryBasketVO> basketList = (List<SummaryBasketVO>) result.get(x.getSelrUsisName());
                basketList.add(x);
                result.put(x.getSelrUsisName(), basketList);
            }
            // key 값으로 회사 명이 등록되어있지 않은 경우
            else {
                List<SummaryBasketVO> basketList = new ArrayList<>();
                basketList.add(x);
                result.put(x.getSelrUsisName(), basketList);
            }
        });

        return result;
    }

    /**
     * 장바구니 등록
     * @param basketVO
     * @throws Exception
     */
    public ResponseData saveBasket(BasketVO basketVO) throws Exception {

        // 로그인 체크
        String loginUserId = null;
        String loginUtlinsttId = null;

        // 로그인 정보가 CustomUser.class 라면 jwt 로그인 조회
        // 로그인 상태가 아니라면 String 타입으로 떨이짐
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof CustomUser) {
            CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            loginUserId = user.getLoginUserId();
            loginUtlinsttId = user.getUtlinsttId();
        }

        // validation(상품 상태 ID)
        SingleProductVO singleProductVO = singleProductRepo.selectPdfStts(basketVO.getPdfInfoId());
        if (singleProductVO == null) {
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(StatusCode.BASKET0001.getMessage()) // 존재하지 않는 상품입니다.
                    .build();
        } else {
            if (Arrays.asList(ComCode.STOP_SALES.getCode(), ComCode.STOP_SALES_BY_MANAGER.getCode())
                    .contains(singleProductVO.getPdfSttsId())) {
                return ResponseData.builder()
                        .code(HttpStatus.BAD_REQUEST.value())
                        .message(StatusCode.BASKET0002.getMessage()) // 판매 중지 상품입니다.
                        .build();
            }
            if (singleProductVO.getPdfSttsId().equals(ComCode.POSTPONE_SALES.getCode())) {
                return ResponseData.builder()
                        .code(HttpStatus.BAD_REQUEST.value())
                        .message(StatusCode.BASKET0003.getMessage()) // 판매 보류 상품입니다.
                        .build();
            }
        }

        // 장바구니 리스트 조회
        List<SummaryBasketVO> basketProductVOList = basketRepo.selectBasketList(loginUserId, loginUtlinsttId, ComCode.GDS05001.getCode(), basketVO.getPdfInfoId());

        boolean updateFlg = false;

        if(basketProductVOList != null && basketProductVOList.size() != 0) {
            updateFlg = true;
        }

        basketVO.setPucsUsisId(loginUtlinsttId);            // 구매자 이용기관 ID
        basketVO.setPucsId(loginUserId);                    // 구매자 ID
        basketVO.setUtId(ComCode.COC02001.getCode());       // 단위 ID
        basketVO.setRgsnUserId(loginUserId);
        basketVO.setAmnnUserId(loginUserId);

        // 장바구니 수정
        if(updateFlg) {
            basketRepo.updateBasket(basketVO);
        }

        // 장바구니 등록
        else {
            basketRepo.insertBasket(basketVO);
        }

        return ResponseData.builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .build();
    }

    /**
     * 장바구니 삭제
     * @param basketList
     * @throws Exception
     */
    public void deleteBasket(List<BasketVO> basketList) throws Exception {

        // 로그인 체크
        String loginUserId = null;
        String loginUtlinsttId = null;

        // 로그인 정보가 CustomUser.class 라면 jwt 로그인 조회
        // 로그인 상태가 아니라면 String 타입으로 떨이짐
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof CustomUser) {
            CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            loginUserId = user.getLoginUserId();
            loginUtlinsttId = user.getUtlinsttId();
        }

        if(basketList.size() > 0) {
            String finalLoginUtlinsttId = loginUtlinsttId;
            String finalLoginUserId = loginUserId;
            basketList.forEach(x -> {
                x.setPucsUsisId(finalLoginUtlinsttId);            // 구매자 이용기관 ID
                x.setPucsId(finalLoginUserId);                    // 구매자 ID
                basketRepo.deleteBasket(x);
            });
        }
    }
}