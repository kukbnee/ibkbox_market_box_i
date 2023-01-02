package com.ibk.sb.restapi.biz.api.mypage;

import com.ibk.sb.restapi.app.common.util.BizException;
import com.ibk.sb.restapi.app.common.vo.PagingVO;
import com.ibk.sb.restapi.app.common.vo.ResponseData;
import com.ibk.sb.restapi.biz.service.adminqna.AdminQnaService;
import com.ibk.sb.restapi.biz.service.adminqna.vo.AdminQnaVO;
import com.ibk.sb.restapi.biz.service.adminqna.vo.DetailAdminQnaVO;
import com.ibk.sb.restapi.biz.service.adminqna.vo.RequestSearchAdminQnaVO;
import com.ibk.sb.restapi.biz.service.adminqna.vo.SummaryAdminQnaVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"커머스 박스 - 마이페이지 관리자 문의 API"})
@RestController
@Slf4j
@RequestMapping(path={"/api/my/qna/admin", "/api/mk/v1/my/qna/admin"}, produces = {"application/json"})
@RequiredArgsConstructor
public class MyAdminQnaController {

    private final AdminQnaService adminQnaService;

    /**
     * 관리자 문의 조회
     * @param requestSearchAdminQnaVO
     * @return
     */
    @ApiOperation(value = "관리자 문의 조회")
    @GetMapping("/list")
    public ResponseData searchAdminQnaList(RequestSearchAdminQnaVO requestSearchAdminQnaVO) {

        try {

            PagingVO<SummaryAdminQnaVO> searchAdminQnaList = adminQnaService.searchAdminQnaList(requestSearchAdminQnaVO);

            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(searchAdminQnaList)
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
     * 관리자 문의 상세
     * @param requestSearchAdminQnaVO
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "관리자 문의 상세")
    @GetMapping("/detail")
    public ResponseData searchAdminQnaDetail(RequestSearchAdminQnaVO requestSearchAdminQnaVO) throws Exception {

        try {

            DetailAdminQnaVO searchAdminQnaDetail = adminQnaService.searchAdminQnaDetail(requestSearchAdminQnaVO);

            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(searchAdminQnaDetail)
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
     * 관리자 문의 등록
     * @param adminQnaVO
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "관리자 문의 등록")
    @ApiImplicitParam(name = "adminQnaVO", value = "AdminQnaVO")
    @PostMapping("/save")
    public ResponseData saveAdminQna(@RequestBody AdminQnaVO adminQnaVO) throws Exception {

        try {
            // 관리자 문의 등록
            String resultId = adminQnaService.saveAdminQna(adminQnaVO);

            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(resultId)
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
     * 관리자 문의 삭제
     * @param adminQnaVO
     * @return
     */
    @ApiOperation(value = "관리자 문의 삭제")
    @ApiImplicitParam(name = "adminQnaVO", value = "AdminQnaVO")
    @PostMapping("/delete")
    public ResponseData deleteAdminQna(@RequestBody AdminQnaVO adminQnaVO) {
        try {
            // 관리자 문의 삭제
            adminQnaService.deleteAdminQna(adminQnaVO);

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
}
