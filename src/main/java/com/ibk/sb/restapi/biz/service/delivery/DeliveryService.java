package com.ibk.sb.restapi.biz.service.delivery;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibk.sb.restapi.app.common.constant.AlarmCode;
import com.ibk.sb.restapi.app.common.constant.ComCode;
import com.ibk.sb.restapi.app.common.constant.StatusCode;
import com.ibk.sb.restapi.app.common.util.FileUtil;
import com.ibk.sb.restapi.biz.service.alarm.AlarmService;
import com.ibk.sb.restapi.biz.service.alarm.vo.RequestAlarmVO;
import com.ibk.sb.restapi.biz.service.delivery.chunil.fegin.ChunIlFeign;
import com.ibk.sb.restapi.biz.service.delivery.chunil.vo.*;
import com.ibk.sb.restapi.biz.service.delivery.repo.DeliveryRepo;
import com.ibk.sb.restapi.biz.service.delivery.vo.DeliveryOrderProductServiceInfoVO;
import com.ibk.sb.restapi.biz.service.delivery.vo.DeliveryOrderProductVO;
import com.ibk.sb.restapi.biz.service.file.repo.FileRepo;
import com.ibk.sb.restapi.biz.service.file.vo.FileInfoVO;
import com.ibk.sb.restapi.biz.service.order.repo.OrderReqDetailRepo;
import com.ibk.sb.restapi.biz.service.order.vo.req.RequestSearchOrderVO;
import com.ibk.sb.restapi.biz.service.order.vo.req.SummaryOrderInfoVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DeliveryService {

    private final ChunIlFeign chunIlFeign;

    private final DeliveryRepo deliveryRepo;

    private final FileRepo fileRepo;

    private final FileUtil fileUtil;

    private final AlarmService alarmService;

    private final OrderReqDetailRepo orderReqDetailRepo;

    /**
     * 품목 / 포장 단위 리스트 조회
     * @param type
     * @return
     */
    public Map<String, Object> searchDeliveryCodeList(String type) throws Exception {

        StringBuilder sb = new StringBuilder();
        String typeCode = type.equals("0") ? "ITM" : "UNT";
        sb.append("{").append("TYPECD:").append("\"").append(typeCode).append("\"").append("}");

        // 천일화물 api 호출
        String objectMap = chunIlFeign.searchDeliveryCodeList(sb.toString());

        return new ObjectMapper().readValue(objectMap, Map.class);
    }

    /**
     * 배송 운임 체크 API
     * @param requestCheckDeliveryCostVO
     * @return
     * @throws Exception
     */
    public Map<String, Object> checkDeliveryCost(RequestCheckDeliveryCostVO requestCheckDeliveryCostVO) throws Exception{

        // 천일화물 api 호출
        String objectMap = chunIlFeign.checkDeliveryCost(requestCheckDeliveryCostVO.getParam());

        // 천일화물 배송 취소 api return 값이 null 일 경우
        if (!StringUtils.hasLength(objectMap)) {
            // 서버와의 통신이 원활하지 않습니다
            throw new Exception(StatusCode.MNB0001.getMessage());
        }

        return new ObjectMapper().readValue(objectMap, Map.class);
    }

    /**
     * 배송 요청 API
     * @param requestDeliveryVO
     * @return
     * @throws Exception
     */
    public Map<String, Object> requestDelivery(RequestDeliveryVO requestDeliveryVO) throws Exception {

        // 천일화물 api 호출
        String objectMap = chunIlFeign.requestDelivery(requestDeliveryVO.getParam());

        // 천일화물 배송 취소 api return 값이 null 일 경우
        if(!StringUtils.hasLength(objectMap)) {
            // 서버와의 통신이 원활하지 않습니다
            throw new Exception(StatusCode.MNB0001.getMessage());
        }

        return new ObjectMapper().readValue(objectMap, Map.class);
    }

    /**
     * 배송 취소 API
     * @param requestCancelDeliveryVO
     * @return
     * @throws Exception
     */
    public Map<String, Object> cancelDelivery(RequestCancelDeliveryVO requestCancelDeliveryVO) throws Exception {

        // 천일화물 api 호출
        String objectMap = chunIlFeign.cancelDelivery(requestCancelDeliveryVO.getParam());

        // 천일화물 배송 취소 api return 값이 null 일 경우
        if(!StringUtils.hasLength(objectMap)) {
            // 서버와의 통신이 원활하지 않습니다
            throw new Exception(StatusCode.MNB0001.getMessage());
        }

        return new ObjectMapper().readValue(objectMap, Map.class);
    }

    /**
     * 배송 스테이터스 변경
     * @param deliveryStatusVO
     * @return
     */
    public void updateDeliveryStatus(DeliveryStatusVO deliveryStatusVO) throws Exception {

        // 주문 화물서비스 배송정보 조회 by 원송장(운송장) 번호
        DeliveryOrderProductServiceInfoVO productServiceInfoVO = deliveryRepo.selectDeliveryOrderProductServiceInfoByMainnbNo(deliveryStatusVO.getRTNNUM());
        if(productServiceInfoVO == null) {
            throw new Exception("해당 운송장 번호와 일치하는 화물 서비스 배송정보가 존재하지 않습니다.");
        }

        // 주문 상품 정보 조회 by 주문정보ID, 정보 순번, 등록사용자 ID
        DeliveryOrderProductVO deliveryOrderProductVO = deliveryRepo.selectDeliveryOrderProductById(productServiceInfoVO.getOrdnInfoId(), productServiceInfoVO.getInfoSqn().toString(), productServiceInfoVO.getRgsnUserId());

        if(deliveryOrderProductVO == null) {
            throw new Exception("해당 주문 정보ID에 대한 주문상품정보가 존재하지 않습니다.");
        }

        // 주문상태 ID 셋팅
        deliveryOrderProductVO.setOrdnSttsId(deliveryStatusVO.getORDNSTTSID());
        // API 호출 사업자 명 셋팅
        deliveryOrderProductVO.setAmnnUserId(deliveryStatusVO.getCallApiBzNm());

        // 주문 상품 정보 스테이터스 수정
        deliveryRepo.updateDeliveryStatus(deliveryOrderProductVO);

        // 쿼리 실행 및 데이터 수정 날짜 셋팅
        deliveryStatusVO.setRsDate(deliveryOrderProductVO.getResultAmnnTs());

        // 배송완료일 경우 알람 송신
        if(deliveryOrderProductVO.getOrdnSttsId().equals(ComCode.ODS00004.getCode())) {
            /*
             * 알림 서비스
             */
            // 주문정보 조회
            RequestSearchOrderVO requestSearchOrderVO = new RequestSearchOrderVO();
            requestSearchOrderVO.setOrdnInfoId(deliveryOrderProductVO.getOrdnInfoId());
            SummaryOrderInfoVO summaryOrderInfoVO = orderReqDetailRepo.searchOrderInfo(requestSearchOrderVO);

            // 알림 요청 인스턴스 생성
            RequestAlarmVO requestAlarmVO = alarmService.getAlarmRequest(AlarmCode.AlarmCodeEnum.DELIVERY_SHIPPED, null, new Object[]{deliveryOrderProductVO.getPdfNm()});
            // 알림 발송
            alarmService.sendMarketAlarm(requestAlarmVO, summaryOrderInfoVO.getPucsUsisId());
        }

        return;
    }

    /**
     * 배송 이미지 업로드 API
     * @param fileId
     * @return
     * @throws Exception
     */
    public String DeliveryFileUpload(String fileId) throws Exception {

        // 파일 정보 조회
        FileInfoVO fileInfoVO = fileRepo.selectFileInfo(fileId);

        // 파일 패스 작성
        String fileFullPath = fileUtil.getFileFullPath(fileInfoVO);


        LinkedMultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

        Resource resource = new FileSystemResource(fileFullPath);
        body.add("file", resource);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.add("Content-Type", "application/json; charset=utf-8");
        headers.add("Media-Type", "application/json; charset=utf-8");
        headers.add("appKey", this.appKey);

        HttpEntity<LinkedMultiValueMap<String, Object>> httpEntity = new HttpEntity<>(body, headers);

        String url = this.baseUrl + "/api/chunil/v1/chunilEsti/upload_ibk";

        ResponseEntity<String> responseEntity = REST_TEMPLATE.postForEntity(url, httpEntity, String.class);

        String resultbody = "";
        Map<String, Object> result = null;

        if(responseEntity != null && responseEntity.getBody() != null && StringUtils.hasLength(responseEntity.getBody())) {
            resultbody = responseEntity.getBody();
        }

        result = new ObjectMapper().readValue(resultbody, Map.class);

        // 천일화물 배송 취소 api return 값이 null 일 경우
        if(result == null) {
            // 서버와의 통신이 원활하지 않습니다
            throw new Exception(StatusCode.MNB0001.getMessage());
        }

        // 천일화물 파일 업로드가 안될 경우, 파일 이름 return
        if(result.size() == 0) {
            return fileInfoVO.getFileNm();
        }

        if(result.get("flag").equals("false")) {
            // 서버와의 통신이 원활하지 않습니다
            throw new Exception(StatusCode.MNB0001.getMessage());
        }

        return (String) result.get("SAVENM");
    }

    /*
     * IBK G/W 통신을 위한 appKey
     */
    @Value("${feign.mk-api.key}")
    private String appKey;

    /*
     * IBK G/W 통신을 위한 baseUrl
     */
    @Value("${feign.box-open-api.url}")
    private String baseUrl;

    /*
     * http 통신을 위한 RestTemplate 셋팅
     */
    private static final RestTemplate REST_TEMPLATE;

    static {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(3000);
        factory.setReadTimeout(3000);
        factory.setBufferRequestBody(false);

        REST_TEMPLATE = new RestTemplate(factory);
    }
}
