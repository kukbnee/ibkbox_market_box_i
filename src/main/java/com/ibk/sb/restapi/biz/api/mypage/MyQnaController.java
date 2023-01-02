package com.ibk.sb.restapi.biz.api.mypage;

import com.ibk.sb.restapi.app.common.util.BizException;
import com.ibk.sb.restapi.app.common.vo.PagingVO;
import com.ibk.sb.restapi.app.common.vo.ResponseData;
import com.ibk.sb.restapi.biz.service.estimation.EstimationService;
import com.ibk.sb.restapi.biz.service.qna.QnaService;
import com.ibk.sb.restapi.biz.service.qna.vo.QnaMessageVO;
import com.ibk.sb.restapi.biz.service.qna.vo.SummaryQnainfoVO;
import com.ibk.sb.restapi.biz.service.qna.vo.RequestSearchQnaVO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping(path={"/api/my/qna", "/api/mk/v1/my/qna"}, produces = {"application/json"})
@RequiredArgsConstructor
public class MyQnaController {

    private final QnaService qnaService;

    private final EstimationService estimationService;

    /**
     * Q&A 문의 목록 조회
     * @param requestSearchQnaVO
     * @return
     */
    @ApiOperation(value = "Q&A 문의 목록 조회")
    @GetMapping("/list")
    public ResponseData searchQnaList(RequestSearchQnaVO requestSearchQnaVO) {

        try {

            PagingVO<SummaryQnainfoVO> qnaVOList = qnaService.searchQnaList(requestSearchQnaVO);

            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(qnaVOList)
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
     * Q&A 문의 목록 건수 조회
     * @param requestSearchQnaVO
     * @return
     */
    @ApiOperation(value = "Q&A 문의 목록 건수 조회")
    @GetMapping("/list/cnt")
    public ResponseData searchQnaListCnt(RequestSearchQnaVO requestSearchQnaVO) {
        try {
            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(qnaService.searchQnaListCnt(requestSearchQnaVO))
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
     * Q&A 문의 상세 조회
     * @param requestSearchQnaVO
     * @return
     */
    @ApiOperation(value = "Q&A 문의 상세 조회")
    @GetMapping("/detail")
    public ResponseData searchQnaDetail(RequestSearchQnaVO requestSearchQnaVO) {

        try {
            SummaryQnainfoVO summaryQnainfoVO = qnaService.searchQna(requestSearchQnaVO);

            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(summaryQnainfoVO)
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
     * Q&A 문의 상세 메세지 목록 조회
     * @param requestSearchQnaVO
     * @return
     */
    @ApiOperation(value = "Q&A 문의 상세 메세지 목록 조회")
    @GetMapping("/detail/list")
    public ResponseData searchQnaMessageList(RequestSearchQnaVO requestSearchQnaVO) {

        try {
            List<QnaMessageVO> qnaMessageVOList = qnaService.searchQnaMessageList(requestSearchQnaVO);

            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(qnaMessageVOList)
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
     * Q&A 문의 등록
     * 문의 상세 메세지 등록 / 수정
     * @param qnaMessageVO
     * @return
     */
    @ApiOperation(value = "Q&A 문의 등록")
    @ApiImplicitParam(name = "qnaMessageVO", value = "QnaMessageVO")
    @PostMapping("/save")
    public ResponseData saveQnaMessage(@RequestBody QnaMessageVO qnaMessageVO) {

        try {
            qnaService.saveQnaMessage(qnaMessageVO);

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

//    /**
//     * 문의 삭제
//     * @param qnaVO
//     * @return
//     */
//    @PostMapping("/delete")
//    public ResponseData deleteQna(@RequestBody QnaVO qnaVO) {
//
//        try {
//            qnaService.deleteQna(qnaVO);
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
//     * 견적 승인/반려 요청
//     * @param estimationVO
//     * @param request
//     * @param reponse
//     * @return
//     */
//    @PostMapping("/estimation/request")
//    public ResponseData requestEstimation(@RequestBody EstimationVO estimationVO, HttpServletRequest request, HttpServletResponse reponse) {
//
//        try {
//            estimationService.requestEstimation(estimationVO, request, reponse);
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
