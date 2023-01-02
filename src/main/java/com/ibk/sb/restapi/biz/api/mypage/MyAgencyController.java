package com.ibk.sb.restapi.biz.api.mypage;

import com.ibk.sb.restapi.app.common.util.BizException;
import com.ibk.sb.restapi.app.common.vo.PagingVO;
import com.ibk.sb.restapi.app.common.vo.ResponseData;
import com.ibk.sb.restapi.biz.service.agency.AgencyService;
import com.ibk.sb.restapi.biz.service.agency.vo.RequestSearchAgencyVO;
import com.ibk.sb.restapi.biz.service.agency.vo.SummaryAgencyReasonVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"커머스 박스 - 마이페이지 에이전시 API"})
@RestController
@Slf4j
@RequestMapping(path={"/api/my/agency", "/api/mk/v1/my/agency"}, produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
public class MyAgencyController {
    private final AgencyService agencyService;

    /**
     * 에이전시 관리자 승인 요청
     * @return
     */
    @ApiOperation(value = "에이전시 관리자 승인 요청")
    @PostMapping("/apply")
    public ResponseData applyMyAgency() {
        try {

            return agencyService.applyMyAgency();

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
     * 에이전시 관리자 승인 요청취소
     * @return
     */
    @ApiOperation(value = "에이전시 관리자 승인 요청취소")
    @PostMapping("/apply/cancel")
    public ResponseData applyMyAgencyCancel(RequestSearchAgencyVO requestSearchAgencyVO) {
        try {

            return agencyService.applyMyAgencyCancel(requestSearchAgencyVO);

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
     * 에이전시 관리자 승인 상태조회
     * @return
     */
    @ApiOperation(value = "에이전시 관리자 승인 상태조회")
    @GetMapping("/apply/detail")
    public ResponseData applyAgencyMyDetail() {
        try {

            return agencyService.applyAgencyMyDetail();

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
     * 에이전시 보낸요청/받은요청 별 Total 조회
     * @return
     */
    @ApiOperation(value = "에이전시 보낸요청/받은요청 별 Total 조회")
    @GetMapping("/state/total")
    public ResponseData searchReceivedAgencyList() {

        try {

            return agencyService.searchAgenInfMyStateTotal();

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
     * 에이전시 보낸/받은요청 목록 조회
     * agenSearchType = rec(받은요청), sen(보낸요청)
     * agenSearchFilter = COC01001(요청),COC01003(승인),COC01004(반려),COC01005(취소)
     * @param requestSearchAgencyVO
     * @return
     */
    @ApiOperation(value = "에이전시 보낸/받은요청 목록 조회")
    @GetMapping("/list")
    public ResponseData searchAgencyInfMyList(RequestSearchAgencyVO requestSearchAgencyVO) {
        try {

            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(agencyService.searchAgencyInfMyList(requestSearchAgencyVO))
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
     * 에이전시 보낸요청 대기상태 취소
     * agenReqId
     * @param requestSearchAgencyVO
     * @return
     */
    @ApiOperation(value = "에이전시 보낸요청 대기상태 취소")
    @PostMapping("/cancel")
    public ResponseData updateAgencyInfMyCancel(RequestSearchAgencyVO requestSearchAgencyVO) {
        try {

            return agencyService.updateAgencyInfMyCancel(requestSearchAgencyVO);

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
     * 받은요청 대기상태 반려
     * agenReqId
     * @param requestSearchAgencyVO
     * @return
     */
    @ApiOperation(value = "받은요청 대기상태 반려")
    @PostMapping("/reject")
    public ResponseData updateAgencyInfMyReject(RequestSearchAgencyVO requestSearchAgencyVO) {
        try {

            return agencyService.updateAgencyInfMyReject(requestSearchAgencyVO);

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
     * 보낸요청 반려사유 조회
     * agenReqId
     * @param requestSearchAgencyVO
     * @return
     */
    @ApiOperation(value = "보낸요청 반려사유 조회")
    @GetMapping("/reason")
    public ResponseData searchAgencyInfMyReason(RequestSearchAgencyVO requestSearchAgencyVO) {
        try {

            return agencyService.searchAgencyInfMyReason(requestSearchAgencyVO);

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
     * 받은요청 대기상태 승인
     * agenReqId
     * @param requestSearchAgencyVO
     * @return
     */
    @ApiOperation(value = "받은요청 대기상태 승인")
    @ApiImplicitParam(name = "requestSearchAgencyVO", value = "RequestSearchAgencyVO")
    @PostMapping("/approval")
    public ResponseData updateAgencyInfMyApproval(@RequestBody RequestSearchAgencyVO requestSearchAgencyVO) {
        try {

            return agencyService.updateAgencyInfMyApproval(requestSearchAgencyVO);

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
     * 보낸요청 대기상태 취소
     * 마이페이지 > 보낸요청 > 승인상태 > 취소, 마이페이지 > 받은요청 > 승인상태 > 취소.
     * agenReqId
     * @param requestSearchAgencyVO
     * @return
     */
    @ApiOperation(value = "보낸요청 대기상태 취소")
    @PostMapping("/approval/cancel")
    public ResponseData updateAgencyInfMyApprovalCancel(RequestSearchAgencyVO requestSearchAgencyVO) {
        try {

            return agencyService.updateAgencyInfMyApprovalCancel(requestSearchAgencyVO);

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
     * 받은요청 승인취소상태 해제
     * 마이페이지 > 받은요청 > 승인취소 > 취소해제
     * agenReqId
     * @param requestSearchAgencyVO
     * @return
     */
    @ApiOperation(value = "받은요청 승인취소상태 해제")
    @PostMapping("/approval/recovery")
    public ResponseData updateAgencyInfMyApprovalRecovery(RequestSearchAgencyVO requestSearchAgencyVO) {
        try {

            return agencyService.updateAgencyInfMyApprovalRecovery(requestSearchAgencyVO);

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
