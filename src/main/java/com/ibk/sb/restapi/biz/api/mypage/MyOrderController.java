package com.ibk.sb.restapi.biz.api.mypage;


import com.ibk.sb.restapi.app.common.util.BizException;
import com.ibk.sb.restapi.app.common.vo.PagingVO;
import com.ibk.sb.restapi.app.common.vo.ResponseData;
import com.ibk.sb.restapi.biz.service.order.mypage.MyOrderService;
import com.ibk.sb.restapi.biz.service.order.mypage.vo.RequestSearchMyOrderVO;
import com.ibk.sb.restapi.biz.service.order.mypage.vo.SummarySalesBuyVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api(tags = {"커머스 박스 - 마이페이지 구매/판매 API"})
@RestController
@Slf4j
@RequestMapping(path={"/api/my/sales"}, produces = {"application/json"})
@RequiredArgsConstructor
public class MyOrderController {

    private final MyOrderService myOrderService;

    /**
     * 마이페이지 구매/판매 헤더 정보 조회
     * 마이페이지 > 구매/판매 > 구매/판매 테이블 헤더 정보 조회
     *      buyCnt  : 구매목록 총 수
     *      sellCnt : 판매목록 총 수
     * @return
     */
    @ApiOperation(value = "마이페이지 구매/판매 헤더 정보 조회")
    @GetMapping("/header")
    public ResponseData searchMyOrderHeader() {

        try {

            Map<String, String> resultMap = myOrderService.searchMyOrderHeader();

            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(resultMap)
                    .build();

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
     * 구매내역 목록 조회
     * 마이페이지 구매내역 리스트 조회
     * @param
     * @return
     */
    @ApiOperation(value = "구매내역 목록 조회")
    @GetMapping("/buy/list")
    public ResponseData searchMyBuyList(RequestSearchMyOrderVO requestSearchMyOrderVO) {

        try {
            // 판매자 체크 - 구매목록
            requestSearchMyOrderVO.setSellerFlg(false);

            // 마이페이지 구매내역 리스트 조회
            PagingVO<SummarySalesBuyVO> summarySalesBuyVOList = myOrderService.searchMyBuyList(requestSearchMyOrderVO);

            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(summarySalesBuyVOList)
                    .build();

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
     * 판매내역 목록 조회
     * 마이페이지 판매내역 리스트 조회
     * @param
     * @return
     */
    @ApiOperation(value = "판매내역 목록 조회")
    @GetMapping("/sell/list")
    public ResponseData searchMySellList(RequestSearchMyOrderVO requestSearchMyOrderVO) {

        try {
            // 판매자 체크 - 판매목록
            requestSearchMyOrderVO.setSellerFlg(true);

            // 마이페이지 구매 / 판매 내역 리스트 조회
            PagingVO<SummarySalesBuyVO> summarySalesBuyVOList = myOrderService.searchMyBuyList(requestSearchMyOrderVO);

            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(summarySalesBuyVOList)
                    .build();

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

//
//    /**
//     *
//     * @param
//     * @return
//     */
//    @GetMapping("/buy/delivery/detail")
//    public ResponseData searchBuyDetail() {
//
//        try {
//
//            return ResponseData.builder()
//                    .code(HttpStatus.OK.value())
//                    .message(HttpStatus.OK.getReasonPhrase())
//                    .data("data")
//                    .build();
//
//        } catch (Exception ex) {
//            log.error(ex.getMessage());
//            return ResponseData.builder()
//                    .code(HttpStatus.BAD_REQUEST.value())
//                    .message(ex.getMessage())
//                    .build();
//        }
//    }
//
//    /**
//     *
//     * @param
//     * @return
//     */
//    @GetMapping("/sell/list")
//    public ResponseData searchSellList() {
//
//        try {
//
//            return ResponseData.builder()
//                    .code(HttpStatus.OK.value())
//                    .message(HttpStatus.OK.getReasonPhrase())
//                    .data("data")
//                    .build();
//
//        } catch (Exception ex) {
//            log.error(ex.getMessage());
//            return ResponseData.builder()
//                    .code(HttpStatus.BAD_REQUEST.value())
//                    .message(ex.getMessage())
//                    .build();
//        }
//    }
//
//    /**
//     *
//     * @param
//     * @return
//     */
//    @GetMapping("/sell/order/list")
//    public ResponseData searchSellOrderList() {
//
//        try {
//
//            return ResponseData.builder()
//                    .code(HttpStatus.OK.value())
//                    .message(HttpStatus.OK.getReasonPhrase())
//                    .data("data")
//                    .build();
//
//        } catch (Exception ex) {
//            log.error(ex.getMessage());
//            return ResponseData.builder()
//                    .code(HttpStatus.BAD_REQUEST.value())
//                    .message(ex.getMessage())
//                    .build();
//        }
//    }
//
//    /**
//     * 리뷰 등록 / 수정
//     * @param reviewVO
//     * @return
//     */
//    @PostMapping("/review/save")
//    public ResponseData saveReview(@RequestBody ReviewVO reviewVO, HttpServletRequest request, HttpServletResponse reponse) {
//
//        try {
//            reviewService.saveReview(reviewVO, request, reponse);
//
//            return ResponseData.builder()
//                    .code(HttpStatus.OK.value())
//                    .message(HttpStatus.OK.getReasonPhrase())
//                    .build();
//
//        } catch (Exception ex) {
//            log.error(ex.getMessage());
//            return ResponseData.builder()
//                    .code(HttpStatus.BAD_REQUEST.value())
//                    .message(ex.getMessage())
//                    .build();
//        }
//    }
//
//    /**
//     * 리뷰 삭제
//     * @param reviewVO
//     * @return
//     */
//    @PostMapping("/delete")
//    public ResponseData deleteReview(@RequestBody ReviewVO reviewVO) {
//
//        try {
//            reviewService.deleteReview(reviewVO);
//
//            return ResponseData.builder()
//                    .code(HttpStatus.OK.value())
//                    .message(HttpStatus.OK.getReasonPhrase())
//                    .build();
//
//        } catch (Exception ex) {
//            log.error(ex.getMessage());
//            return ResponseData.builder()
//                    .code(HttpStatus.BAD_REQUEST.value())
//                    .message(ex.getMessage())
//                    .build();
//        }
//    }
//
//}