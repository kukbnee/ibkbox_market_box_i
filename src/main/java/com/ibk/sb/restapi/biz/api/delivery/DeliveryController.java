package com.ibk.sb.restapi.biz.api.delivery;

import com.ibk.sb.restapi.app.common.util.BizException;
import com.ibk.sb.restapi.app.common.vo.ResponseData;
import com.ibk.sb.restapi.biz.service.delivery.DeliveryService;
import com.ibk.sb.restapi.biz.service.delivery.chunil.fegin.ChunIlFeign;
import com.ibk.sb.restapi.biz.service.delivery.chunil.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

//TODO 천일화물 api URL 은 security config 에서 빼놓기
@Api(tags = {"커머스 박스 - 천일화물 배송 API"})
@RestController
@Slf4j
@RequestMapping(path={"/api/delivery", "/api/mk/v1/delivery"}, produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryService deliveryService;

    /**
     * 천일화물 품목/포장 단위 목록 조회
     * @param type : 코드 타입
     *             "0" : 물품 코드(ITM)
     *             "1" : 포장 단위 코드(UNT)
     **/
    @ApiOperation(value = "천일화물 품목/포장 단위 목록 조회")
    @ApiImplicitParam(name = "type", value = "진행상태", dataType = "String", example = "0")
    @GetMapping("/codelist")
    public ResponseData searchDeliveryCodeList(@RequestParam("type") String type) {

        try {

            Map<String, Object> resultMap = deliveryService.searchDeliveryCodeList(type);

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
     * 천일화물 배송 운임체크
     * @param requestCheckDeliveryCostVO : 배송 운임 체크 VO
     * @return
     */
    @ApiOperation(value = "천일화물 배송 운임체크")
    @ApiImplicitParam(name = "requestCheckDeliveryCostVO", value = "RequestCheckDeliveryCostVO")
    @PostMapping("/check")
    public ResponseData checkDeliveryCost(@RequestBody RequestCheckDeliveryCostVO requestCheckDeliveryCostVO) {

        try {
            Map<String, Object> resultMap = deliveryService.checkDeliveryCost(requestCheckDeliveryCostVO);

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
     * 천일화물 배송 요청 api
     * @param requestDeliveryVO : 배송 요청 VO
     * @return
     */
    @ApiOperation(value = "천일화물 배송 요청 api")
    @ApiImplicitParam(name = "requestDeliveryVO", value = "RequestDeliveryVO")
    @PostMapping("/request")
    public ResponseData requestDelivery(@RequestBody RequestDeliveryVO requestDeliveryVO) {

        try{
            Map<String, Object> resultMap = deliveryService.requestDelivery(requestDeliveryVO);

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
     * 천일화물 배송 취소
     * @param requestCancelDeliveryVO : 배송 취소 API VO
     * @return
     */
    @ApiOperation(value = "천일화물 배송 취소")
    @ApiImplicitParam(name = "requestCancelDeliveryVO", value = "RequestCancelDeliveryVO")
    @PostMapping("/cancel")
    public ResponseData cancelDelivery(@RequestBody RequestCancelDeliveryVO requestCancelDeliveryVO) {

        try{
            Map<String, Object> resultMap = deliveryService.cancelDelivery(requestCancelDeliveryVO);

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
     * 천일화물 배송 스테이터스 변경
     * 배송사에서 커머스 박스 시스템으로 호출
     * @param deliveryStatusVO
     * @return
     */
    @ApiOperation(value = "천일화물 배송 스테이터스 변경")
    @ApiImplicitParam(name = "deliveryStatusVO", value = "DeliveryStatusVO")
    @PostMapping()
    public DeliveryStatusVO updateDeliveryStatus(@RequestBody DeliveryStatusVO deliveryStatusVO) {

        try{
            // 필수 파라메터 체크
            if(!StringUtils.hasLength(deliveryStatusVO.getRTNNUM()) || !StringUtils.hasLength(deliveryStatusVO.getORDNSTTSID()) || !StringUtils.hasLength(deliveryStatusVO.getCallApiBzNm())) {
                throw new Exception("필수입력값을 확인해주세요");
            }

            deliveryService.updateDeliveryStatus(deliveryStatusVO);
            // api 호출 성공
            deliveryStatusVO.setFlg(true);
            return deliveryStatusVO;

        } catch (BizException ex) {
            log.error(ex.getMessage());
            // 실패시 사유 메세지 셋팅
            deliveryStatusVO.setMsg(ex.getMessage());
            // api 호출 실패
            deliveryStatusVO.setFlg(false);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            // 실패시 사유 메세지 셋팅
            deliveryStatusVO.setMsg(ex.getMessage());
            // api 호출 실패
            deliveryStatusVO.setFlg(false);
        }

        return deliveryStatusVO;
    }

    /**
     * 천일화물 배송 이미지 업로드
     * @param fileId
     * @return
     */
    @ApiOperation(value = "천일화물 배송 이미지 업로드")
    @ApiImplicitParam(name = "fileId", value = "파일 ID", dataType = "String")
    @PostMapping("/test")
    public  ResponseData chunilFileUpload(@RequestParam(name = "fileId")String fileId) {

        try {
            String fileNm = deliveryService.DeliveryFileUpload(fileId);

            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(fileNm)
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