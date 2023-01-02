package com.ibk.sb.restapi.biz.api.mypage;

import com.ibk.sb.restapi.app.common.util.BizException;
import com.ibk.sb.restapi.app.common.vo.PageVO;
import com.ibk.sb.restapi.app.common.vo.PagingVO;
import com.ibk.sb.restapi.app.common.vo.ResponseData;
import com.ibk.sb.restapi.biz.service.alarm.AlarmService;
import com.ibk.sb.restapi.biz.service.alarm.vo.HeaderAlarmListVO;
import com.ibk.sb.restapi.biz.service.alarm.vo.ReceiveAlarmVO;
import com.ibk.sb.restapi.biz.service.review.ReviewService;
import com.ibk.sb.restapi.biz.service.seal.SealService;
import com.ibk.sb.restapi.biz.service.seal.vo.SealVo;
import com.ibk.sb.restapi.biz.service.seller.SellerStoreService;
import com.ibk.sb.restapi.biz.service.seller.vo.RequestSellerVO;
import com.ibk.sb.restapi.biz.service.user.UserService;
import com.ibk.sb.restapi.biz.service.user.vo.MyPageUserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 마이페이지 > 내정보 API
 */
@Api(tags = {"커머스 박스 - 마이페이지 내정보 API"})
@RestController
@Slf4j
@RequestMapping(path={"/api/my/info", "/api/mk/v1/my/info"}, produces = {"application/json"})
@RequiredArgsConstructor
public class MyInfoController {

    private final ReviewService reviewService;

    private final UserService userService;

    private final SellerStoreService sellerStoreService;

    private final SealService sealService;

    private final AlarmService alarmService;

    /**
     * 내정보 조회
     * 마이페이지 회원(유저) 정보 조회 (기본정보, 회원구분, 판매자 정보)
     * @param
     * @return
     */
    @ApiOperation(value = "내정보 조회")
    @GetMapping()
    public ResponseData searchMyPageUserInfo() {

        try {

            MyPageUserVO myPageUserVO = userService.searchMyPageUserInfo();

            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(myPageUserVO)
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
     * 내정보 수정
     * 통신판매업신고번호 수정
     * @param requestMap
     *          csbStmtno : 통신판매업신고번호
     * @return
     */
    @ApiOperation(value = "내정보 수정")
    @ApiImplicitParam(name = "requestMap", value = "Map<String, String>", example = "'csbStmtno' : '통신판매업신고번호'")
    @PostMapping("/seller/save")
    public ResponseData saveSellerInfo(@RequestBody Map<String, String> requestMap) {

        try {

            String csbStmtno = requestMap.get("csbStmtno");

            // 마이페이지 통신판매업신고번호 수정
            userService.saveSellerInfo(csbStmtno);

            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
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
     * 내정보 배경/소개 수정
     * 마이페이지 홈화면 디자인 배경/소개 수정
     * @param
     *      "userCpCon" : 회사 소개
     *      "sellerBgImgFileId" : 배경 이미지 파일 ID
     * @return
     */
    @ApiOperation(value = "내정보 배경/소개 수정")
    @ApiImplicitParam(name = "requestSellerVO", value = "RequestSellerVO")
    @PostMapping("/design/save")
    public ResponseData saveSellerDesign(@RequestBody RequestSellerVO requestSellerVO) {

        try {

            // 마이페이지 홈화면 디자인 배경/소개 수정
            sellerStoreService.saveSellerDesign(requestSellerVO);

            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
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
     * 내정보 배너 등록/수정
     * 마이페이지 홈화면 디자인 배너 수정
     * @param
     * @return
     */
    @ApiOperation(value = "내정보 배너 등록/수정")
    @ApiImplicitParam(name = "requestSellerVO", value = "RequestSellerVO")
    @PostMapping("/banner/save")
    public ResponseData saveSellerBanner(@RequestBody RequestSellerVO requestSellerVO) {

        try {

            sellerStoreService.saveSellerBanner(requestSellerVO);

            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
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
     * 내정보 인감 등록
     * 마이페이지 인감정보 등록
     * @param sealVo
     * @return
     */
    @ApiOperation(value = "내정보 인감 등록")
    @ApiImplicitParam(name = "sealVo", value = "SealVo")
    @PostMapping("/seal/save")
    public ResponseData saveSealInfo(@RequestBody SealVo sealVo) {

        try {

            SealVo resultVo = sealService.saveSealInfo(sealVo);

            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .data(resultVo)
                    .message(HttpStatus.OK.getReasonPhrase())
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
     * 내정보 인감 삭제
     * 마이페이지 인감정보 삭제
     * @param sealVo
     * @return
     */
    @ApiOperation(value = "내정보 인감 삭제")
    @ApiImplicitParam(name = "sealVo", value = "SealVo")
    @PostMapping("/seal/delete")
    public ResponseData deleteSealInfo(@RequestBody SealVo sealVo) {

        try {

            sealService.deleteSealInfo(sealVo);

            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
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
     * 내정보 알림 목록 조회
     * 마이페이지 알림 페이징 리스트 조회
     * @param pageVO
     * @return
     */
    @ApiOperation(value = "내정보 알림 목록 조회")
    @GetMapping("/alarm/list")
    public ResponseData searchReceiveMarketHeaderAlarmList(PageVO pageVO) {
        try {
            PagingVO<ReceiveAlarmVO> alarmPagingList = alarmService.searchReceiveMarketAlarmPagingList(pageVO.getPage(), pageVO.getRecord(), pageVO.getPageSize(), "Y");

            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(alarmPagingList)
                    .build();
        } catch (BizException bx) {
            log.error(bx.getMessage());
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(bx.getMessage())
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
     * 내정보 알림 수신 확인
     * @param body
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "내정보 알림 목록 조회")
    @ApiImplicitParam(name = "body", value = "HashMap<String, String>")
    @PostMapping("/alarm/check")
    public ResponseData checkReceiveAlarm(@RequestBody HashMap<String, String> body) throws Exception {

        try {
            boolean isUnread = alarmService.checkReceiveAlarm(body.get("alrtSndgNo"));
            Map<String, String> result = new HashMap<>();
            result.put("unreadYn", isUnread ? "Y" : "N");

            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(result)
                    .build();
        } catch (BizException bx) {
            log.error(bx.getMessage());
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(bx.getMessage())
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
//     * 마이페이지 홈화면 디자인 배경/소개 조회
//     * @param
//     * @return
//     */
//    @GetMapping("/design/detail")
//    @Transactional
//    public ResponseData searchDesignDetail() {
//
//        try {
//
//            SellerFileVO sellerFileVO = sellerStoreService.searchDesignDetail();
//
//            return ResponseData.builder()
//                    .code(HttpStatus.OK.value())
//                    .message(HttpStatus.OK.getReasonPhrase())
//                    .data(sellerFileVO)
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
//    @GetMapping("/banner/list")
//    public ResponseData searchBannerList() {
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
//    @PostMapping("/banner/delete")
//    public ResponseData deleteQna() {
//
//        try {
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

}