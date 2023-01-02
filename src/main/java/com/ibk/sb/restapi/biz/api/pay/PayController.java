package com.ibk.sb.restapi.biz.api.pay;

import com.ibk.sb.restapi.app.common.util.BizException;
import com.ibk.sb.restapi.app.common.vo.ResponseData;
import com.ibk.sb.restapi.biz.service.pay.boxpos.BoxPosServie;
import com.ibk.sb.restapi.biz.service.pay.boxpos.vo.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Slf4j
@RequestMapping(path={"/api/pay", "/api/mk/v1/pay"}, produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
public class PayController {

    private final BoxPosServie boxPosServie;

    /**
     * (커머스) BOX POS 이용신청정보조회 NoAuth
     * @param requestUtlAplcInfoVo
     * @return
     * @throws Exception
     */
    @PostMapping("/utlaplcinfo")
    public ResponseData posUtlAplcInfoInqNoAuth(@RequestBody RequestUtlAplcInfoVo requestUtlAplcInfoVo) throws Exception {

        try {
            // POS 이용신청정보조회 NoAuth
            UtlAplcInfoVo resultVo = boxPosServie.posUtlAplcInfoInqNoAuth(requestUtlAplcInfoVo);

            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(resultVo)
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
     * (커머스) BOX POS 제휴사 연계결제 등록
     * @param requestLnkStlmRgsnVo
     * @return
     * @throws Exception
     */
    @PostMapping("/lnkStlmRgsn")
    public ResponseData lnkStlmRgsn(@RequestBody RequestLnkStlmRgsnVo requestLnkStlmRgsnVo) throws Exception {

        try {
            // BOX POS 제휴사 연계결제 등록
            LnkStlmRgsnVo resultVo = boxPosServie.lnkStlmRgsn(requestLnkStlmRgsnVo);

            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(resultVo)
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
     * (커머스) BOX POS 제휴사 연계결제 결제 여부 조회
     * @param lnkStlmSrn
     * @return
     * @throws Exception
     */
    @PostMapping("/lnkStlmPgrsInq")
    public ResponseData lnkStlmPgrsInq(@RequestBody Map<String, String> lnkStlmSrn) throws Exception {

        try {
            // BOX POS 제휴사 연계결제 결제 여부 조회
            Boolean result = boxPosServie.lnkStlmPgrsInq(lnkStlmSrn.get("lnkStlmSrn"));

            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(result)
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
     * (커머스) BOX POS PC원격결제 조회
     * @param lnkStlmSrn
     * @return
     * @throws Exception
     */
    @PostMapping("/pcRmteStlmInq")
    public ResponseData pcRmteStlmInq(@RequestBody Map<String, String> lnkStlmSrn) throws Exception {

        try {
            // BOX POS PC원격결제 조회
            PcRmteStlmInqVo resultVo = boxPosServie.pcRmteStlmInq(lnkStlmSrn.get("lnkStlmSrn"));

            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(resultVo)
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
     * (커머스) BOX POS PC원격결제 취소요청 푸시발송
     * @param lnkStlmSrn
     * @return
     * @throws Exception
     */
    @PostMapping("/pcRmteStlmCnclRqstPush")
    public ResponseData pcRmteStlmCnclRqstPush(@RequestBody Map<String, String> lnkStlmSrn) throws Exception {

        try {
            // BOX POS PC원격결제 취소요청 푸시발송
            boolean result = boxPosServie.pcRmteStlmCnclRqstPush(lnkStlmSrn.get("lnkStlmSrn"));

            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(result)
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
     * (커머스) BOX POS 제휴사 연계결제 취소
     * @param lnkStlmCnclVO
     * @return
     * @throws Exception
     */
    @PostMapping("/lnkStlmCncl")
    public ResponseData lnkStlmCncl(@RequestBody LnkStlmCnclVO lnkStlmCnclVO) throws Exception {

        try {
            // BOX POS 제휴사 연계결제 취소
            Map<String, Object> resultMap = boxPosServie.lnkStlmCncl(lnkStlmCnclVO);

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
}
