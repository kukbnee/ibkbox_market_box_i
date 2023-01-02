package com.ibk.sb.restapi.biz.api.mypage;

import com.ibk.sb.restapi.app.common.util.BizException;
import com.ibk.sb.restapi.app.common.vo.ResponseData;
import com.ibk.sb.restapi.biz.service.estimation.EstimationService;
import com.ibk.sb.restapi.biz.service.estimation.vo.RequestSearchEstimationVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"커머스 박스 - 마이페이지 견적 API"})
@RestController
@Slf4j
@RequestMapping(path = {"/api/my/estimation", "/api/mk/v1/my/estimation"}, produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
public class MyEstimationController {

    private final EstimationService estimationService;

    /**
     * 견적 발송
     * 마이페이지 > 문의 > 견적발송
     * @param requestSearchEstimationVO
     * @return
     */
    @ApiOperation(value = "견적 발송")
    @ApiImplicitParam(name = "requestSearchEstimationVO", value = "RequestSearchEstimationVO")
    @PostMapping("/save")
    public ResponseData saveEstimation(@RequestBody RequestSearchEstimationVO requestSearchEstimationVO) {
        try {
            return estimationService.saveEstimation(requestSearchEstimationVO);
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
     * 견적 취소
     * 마이페이지 > 견적요청 > 견적취소(판매자)
     * @param requestSearchEstimationVO
     * @return
     */
    @ApiOperation(value = "견적 취소")
    @ApiImplicitParam(name = "requestSearchEstimationVO", value = "RequestSearchEstimationVO")
    @PostMapping("/cancel")
    public ResponseData cancelEstimation(@RequestBody RequestSearchEstimationVO requestSearchEstimationVO) {
        try {
            return estimationService.cancelEstimation(requestSearchEstimationVO);
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
     * 견적 조회조건 코드 조회
     * 마이페이지 > 견적요청 > 조회조건 코드(목록(보낸견적/받은견적))
     * @param requestSearchEstimationVO
     * @return
     */
    @ApiOperation(value = "견적 조회조건 코드 조회")
    @GetMapping("/code")
    public ResponseData codeEstimation(RequestSearchEstimationVO requestSearchEstimationVO) {
        try {
            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(estimationService.searchCode(requestSearchEstimationVO))
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
     * 견적 목록 조회
     * 마이페이지 > 견적요청 > 목록(보낸견적/받은견적)
     * @param requestSearchEstimationVO
     * @return
     */
    @ApiOperation(value = "견적 목록 조회")
    @GetMapping("/list")
    public ResponseData listEstimation(RequestSearchEstimationVO requestSearchEstimationVO) {
        try {
            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(estimationService.searchEstimationList(requestSearchEstimationVO))
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
     * 견적 목록 건수 조회
     * 마이페이지 > 견적요청 > 목록건수(보낸견적/받은견적)
     * @param requestSearchEstimationVO
     * @return
     */
    @ApiOperation(value = "견적 목록 건수 조회")
    @GetMapping("/list/cnt")
    public ResponseData listEstimationCnt(RequestSearchEstimationVO requestSearchEstimationVO) {
        try {
            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(estimationService.searchEstimationListCnt(requestSearchEstimationVO))
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
     * 견적 상세
     * 마이페이지 > 문의 > 견적상세
     * @param requestSearchEstimationVO
     * @return
     */
    @ApiOperation(value = "견적 상세")
    @GetMapping("/detail")
    public ResponseData detailEstimation(RequestSearchEstimationVO requestSearchEstimationVO) {
        try {
            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(estimationService.searchEstimation(requestSearchEstimationVO))
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
     * 추가할 상품 검색
     * 마이페이지 > 문의 > 추가할 상품 검색
     * @param requestSearchEstimationVO
     * @return
     */
    @ApiOperation(value = "추가할 상품 검색")
    @GetMapping("/product/add")
    public ResponseData productAddEstimation(RequestSearchEstimationVO requestSearchEstimationVO) {
        try {
            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(estimationService.searchProductAdd(requestSearchEstimationVO))
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
     * 화물서비스 선택
     * 마이페이지 > 문의 > 화물서비스 선택
     * @param requestSearchEstimationVO
     * @return
     */
    @ApiOperation(value = "화물서비스 선택")
    @ApiImplicitParam(name = "requestSearchEstimationVO", value = "RequestSearchEstimationVO")
    @PostMapping("/delivery/list")
    public ResponseData deliveryListEstimation(@RequestBody RequestSearchEstimationVO requestSearchEstimationVO) {
        try {
//            return ResponseData.builder()
//                    .code(HttpStatus.OK.value())
//                    .message(HttpStatus.OK.getReasonPhrase())
//                    .data(estimationService.searchDeliveryList(requestSearchEstimationVO))
//                    .build();
            return estimationService.searchDeliveryList(requestSearchEstimationVO, EstimationService.DeliveryListServiceKind.EstimationService);
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
