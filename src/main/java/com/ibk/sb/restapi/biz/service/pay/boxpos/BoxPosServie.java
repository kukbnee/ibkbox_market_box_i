package com.ibk.sb.restapi.biz.service.pay.boxpos;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibk.sb.restapi.app.common.constant.StatusCode;
import com.ibk.sb.restapi.biz.service.pay.boxpos.feign.BoxPosFeign;
import com.ibk.sb.restapi.biz.service.pay.boxpos.vo.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class BoxPosServie {

    private final BoxPosFeign boxPosFeign;

    /**
     * POS 이용신청정보조회 NoAuth
     *
     * @param requestUtlAplcInfoVo
     * @return
     * @throws Exception
     */
    public UtlAplcInfoVo posUtlAplcInfoInqNoAuth(RequestUtlAplcInfoVo requestUtlAplcInfoVo) throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> requestBody = objectMapper.convertValue(requestUtlAplcInfoVo, Map.class);

        // POS 이용신청정보조회 NoAuth
        Map<String, Object> resultMap = boxPosFeign.posUtlAplcInfoInqNoAuth(requestBody);

        // 스테이터스가 올바르지 않을 경우 에러
        if (resultMap.get("STATUS") == null || !resultMap.get("STATUS").equals("0000")) {
            String msg = (String) resultMap.get("RSLT_MSG");
            log.error(msg);
            throw new Exception(msg);
        }

        // Map to Dto
        ObjectMapper mapper = new ObjectMapper();
        UtlAplcInfoVo utlAplcInfoVo = mapper.convertValue((Map<String, Object>) resultMap.get("RSLT_DATA"), UtlAplcInfoVo.class);

        return utlAplcInfoVo;
    }

    /**
     * BOX POS 제휴사 연계결제 등록
     * @param requestLnkStlmRgsnVo
     * @return
     * @throws Exception
     */
    public LnkStlmRgsnVo lnkStlmRgsn(RequestLnkStlmRgsnVo requestLnkStlmRgsnVo) throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> requestBody = objectMapper.convertValue(requestLnkStlmRgsnVo, Map.class);

        // BOX POS 제휴사 연계결제 등록
        Map<String, Object> resultMap = boxPosFeign.lnkStlmRgsn(requestBody);

        // 스테이터스가 올바르지 않을 경우 에러
        if (resultMap.get("STATUS") == null || !resultMap.get("STATUS").equals("0000")) {
            String msg = (String) resultMap.get("RSLT_MSG");
            log.error(msg);
            throw new Exception(msg);
        }

        // Map to Dto
        ObjectMapper mapper = new ObjectMapper();
        LnkStlmRgsnVo lnkStlmRgsnVo = mapper.convertValue((Map<String, Object>) resultMap.get("DATA"), LnkStlmRgsnVo.class);

        return lnkStlmRgsnVo;
    }

    /**
     * BOX POS 제휴사 연계결제 결제 여부 조회
     *
     * @param lnkStlmSrn
     * @return
     * @throws Exception
     */
    public Boolean lnkStlmPgrsInq(String lnkStlmSrn) throws Exception {

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("lnkStlmSrn", lnkStlmSrn);

        // BOX POS 제휴사 연계결제 결제 여부 조회
        Map<String, Object> resultMap = boxPosFeign.lnkStlmPgrsInq(requestBody);

        // 스테이터스가 올바르지 않을 경우 에러
        if (resultMap.get("STATUS") == null || !resultMap.get("STATUS").equals("0000")) {
            String msg = (String) resultMap.get("RSLT_MSG");
            log.error(msg);
            throw new Exception(msg);
        }

        Boolean result = (Boolean) resultMap.get("DATA");

        // 실패인 경우
        if (!result || result == null) {
            String msg = (String) resultMap.get("RSLT_MSG");
            log.error(msg);
        }

        return result;
    }

    /**
     * BOX POS PC원격결제 조회
     *
     * @param lnkStlmSrn
     * @return
     * @throws Exception
     */
    public PcRmteStlmInqVo pcRmteStlmInq(String lnkStlmSrn) throws Exception {

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("lnkStlmSrn", lnkStlmSrn);

        // BOX POS PC원격결제 조회
        Map<String, Object> resultMap = boxPosFeign.pcRmteStlmInq(requestBody);

        // 스테이터스가 올바르지 않을 경우 에러
        if (resultMap.get("STATUS") == null || !resultMap.get("STATUS").equals("0000")) {
            String msg = (String) resultMap.get("RSLT_MSG");
            log.error(msg);
            throw new Exception(msg);
        }

        // Map to Dto
        ObjectMapper mapper = new ObjectMapper();
        PcRmteStlmInqVo pcRmteStlmInqVo = mapper.convertValue((Map<String, Object>) resultMap.get("RSLT_DATA"), PcRmteStlmInqVo.class);

        return pcRmteStlmInqVo;
    }

    /**
     * BOX POS PC원격결제 취소요청 푸시발송
     *
     * @param lnkStlmSrn
     * @return
     * @throws Exception
     */
    public boolean pcRmteStlmCnclRqstPush(String lnkStlmSrn) throws Exception {

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("lnkStlmSrn", lnkStlmSrn);

        // BOX POS PC원격결제 조회
        Map<String, Object> resultMap = boxPosFeign.pcRmteStlmCnclRqstPush(requestBody);

        // 스테이터스가 올바르지 않을 경우 에러
        if (resultMap.get("STATUS") == null || !resultMap.get("STATUS").equals("0000")) {
            String msg = (String) resultMap.get("RSLT_MSG");
            log.error(msg);
            throw new Exception(msg);
        }

        return true;
    }

    /**
     * BOX POS 제휴사 연계결제 취소
     * @param lnkStlmCnclVO
     * @return
     * @throws Exception
     */
    public Map<String, Object> lnkStlmCncl(LnkStlmCnclVO lnkStlmCnclVO) throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();

        Map<String, Object> requestBody = objectMapper.convertValue(lnkStlmCnclVO, Map.class);

        // BOX POS 제휴사 연계결제 취소
        Map<String, Object> resultMap = boxPosFeign.lnkStlmCncl(requestBody);

        // 스테이터스가 올바르지 않을 경우 에러
        if (resultMap.get("STATUS") == null || !resultMap.get("STATUS").equals("0000")) {
            String msg = (String) resultMap.get("RSLT_MSG");
            log.error(msg);
            throw new Exception(msg);
        }

        return resultMap;
    }
}
