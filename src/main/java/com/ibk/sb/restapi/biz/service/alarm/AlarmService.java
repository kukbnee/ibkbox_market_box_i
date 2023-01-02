package com.ibk.sb.restapi.biz.service.alarm;

import com.ibk.sb.restapi.app.common.constant.AlarmCode;
import com.ibk.sb.restapi.app.common.constant.StatusCode;
import com.ibk.sb.restapi.app.common.util.BizException;
import com.ibk.sb.restapi.app.common.vo.*;
import com.ibk.sb.restapi.biz.service.alarm.feign.BoxAlarmFeign;
import com.ibk.sb.restapi.biz.service.alarm.vo.*;
import com.ibk.sb.restapi.biz.service.mainbox.MainBoxService;
import com.ibk.sb.restapi.biz.service.mainbox.vo.AlarmTargetVO;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AlarmService {

    private final BoxAlarmFeign alarmFeign;

    private final MainBoxService mainBoxService;

    private final MessageSource messageSource;

    /**
     * 알림 발송 Request Set
     * 커머스의 경우 로직에 따라 URL 분기가 추가로 나뉘지 않아 공통화 시킴
     * @param alarmCodeEnum 알림 코드
     * @param addUrlParam 추가 URL 파라미터 AlarmCode.AlarmCodeEnum 주석 참조
     * @param messageParamArr 메시지 파라미터 messages.properties 주석 참조
     * @throws Exception
     */
    public RequestAlarmVO getAlarmRequest(AlarmCode.AlarmCodeEnum alarmCodeEnum, String addUrlParam, Object[] messageParamArr) throws Exception {
        // 알림 요청 인스턴스 생성
        RequestAlarmVO requestAlarmVO = new RequestAlarmVO(alarmCodeEnum);
        // 알림 요청 링크 URL 설정
        requestAlarmVO.setPcLinkUrlCon(alarmCodeEnum.getBaseUrl() + (StringUtils.hasLength(addUrlParam) ? addUrlParam : ""));
        // 알림 메시지 생성
        // messages.properties에 어떤 파라미터가 들어가는지 주석 작성 해놨습니다.
        requestAlarmVO.setAlrtCon(
                messageSource.getMessage(
                        alarmCodeEnum.getTemplateId(),
                        messageParamArr != null ? messageParamArr : new Object[]{},
                        null
                )
        );

        return requestAlarmVO;
    }

    /**
     * 마켓박스 알림 전송
     * @param requestAlarmVO
     * RequestVO : 알림 아이디, 알림 타이틀, 알림 내용, 링크는 각 서비스 단에서 설정
     * @param usisId 대상 이용기관 ID
     * @return
     * @throws Exception
     */
    public void sendMarketAlarm(RequestAlarmVO requestAlarmVO, String usisId) throws Exception {

        // 마켓박스 알림 분류 세팅
        requestAlarmVO.setAlrtLrdvDcd(AlarmCode.ALRT_LRDV_DCD_BOX);
        requestAlarmVO.setAlrtMddvCd(AlarmCode.ALRT_MDDV_CD_MARKET);
        requestAlarmVO.setIbkboxSvcDcd(AlarmCode.IBKBOX_SVC_DCD);

        // 알림여부 Y, 푸시여부 N
        requestAlarmVO.setAlrtYn("Y");
        requestAlarmVO.setPushYn("N");

        // 시스템 아이디 세팅
        requestAlarmVO.setSysLsmdId("admin");

        // 현재시간 세팅
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        requestAlarmVO.setAlrtSndgTs(dateFormat.format(now).toString().substring(0, 12));

        //======================================================================================================//

        // 대상 관리
        // 마켓박스도 로그인 계정이 아닌 이용기관 기준으로 전체 발송
        // 권한별로 3번 알림 전송 API 전송이 필요

        AlarmCode.AuthCodeEnum[] authCodeEnums = {AlarmCode.AuthCodeEnum.AUTH_CODE_C, AlarmCode.AuthCodeEnum.AUTH_CODE_M, AlarmCode.AuthCodeEnum.AUTH_CODE_U};

        for(AlarmCode.AuthCodeEnum authCodeEnum : authCodeEnums) {
            // 수신자 권한 코드
            requestAlarmVO.setRcvrDcd(authCodeEnum.getCode());

            // 대상 리스트 조회
            List<AlarmTargetVO> targetList = mainBoxService.searchAlarmTargetList(usisId, authCodeEnum);

            // 오류 없이 리스트가 올 경우 알림 발송
            if(targetList != null) {
                requestAlarmVO.setArray(targetList != null ? targetList : new ArrayList<>());
                requestAlarmVO.setArrayCnt(Integer.toString(targetList != null ? targetList.size() : 0));

                // 알림 발송
                RequestAlarmResponseVO responseVO = alarmFeign.postSendAlarm(requestAlarmVO);

                // TODO : 발송이력 저장 확인 후 발송번호(alrtSndgNo), 성공여부 저장
                // Caution : 알림 발송 이후이기 때문에 Exception에 의한 Transaction Rollback 주의
                // Exception이 일단 발생할 경우 Transaction이 실패로 인식되어 Rollback이 되므로 BizException 처리 X
                if(responseVO.getRegRslt() != null && responseVO.getRegRslt().equals("00") && responseVO.getRegRsltMsg().equals("success")) {
                } else {
                }
            }
        }
    }


    /**
     * 마켓박스 헤더 알림 리스트 조회 (최신 5개)
     * @return
     * @throws Exception
     */
    public HeaderAlarmListVO searchReceiveMarketHeaderAlarmList() throws Exception {
        // 리스트 조회
        // 최신 5개, 전체 마켓 알림
        BoxListResponseVO<ReceiveAlarmVO> responseVO = searchMarketReceiveAlarmList(0, 5, "Y");

        // result set
        HeaderAlarmListVO resultList = new HeaderAlarmListVO();
        // 알림 리스트 세팅
        resultList.setList(responseVO.getRSLT_DATA() == null ? new ArrayList<>() : responseVO.getRSLT_DATA());
        // 신규 미수신건 확인
        resultList.setUnreadYn(searchUnreadReceiveMarketAlarm() ? "Y" : "N");

        return resultList;
    }

    /**
     * 마켓박스 헤더 알림 페이징 리스트 조회
     * @param page
     * @param record
     * @param pageSize
     * @param alrtAllYn
     * @return
     * @throws Exception
     */
    public PagingVO<ReceiveAlarmVO> searchReceiveMarketAlarmPagingList(Integer page, Integer record, Integer pageSize, String alrtAllYn) throws Exception {

        page = (page != null && page > 0)  ? page : 1;
        record = (record != null && record > 0) ? record : 10;
        pageSize = (pageSize != null && pageSize > 0) ? pageSize : 5;

        /**
         * 현재 BOX 알림은 전체 알림만 total count 조회가 가능
         * -> 페이징 화면 처리를 위한 데이터처리가 되지 않음
         * -> BOX 알림에서 우선 1000만건 정도 조회 후 실제로 리턴되는 리스트 사이즈로 전체 사이즈 측정 (애매하다 싶으면 Integer 범위 내에서 고려)
         */
        /*
        전체 알림 조회
         */

        // inqCnt는 필요에 따라 조정 (현재 1000만)
        BoxListResponseVO<ReceiveAlarmVO> responseVO = searchMarketReceiveAlarmList(0, 10000000, alrtAllYn);


        /*
        페이징 처리
         */

        // 총 리스트 개수
        int totalCnt = responseVO.getRSLT_DATA() != null ? responseVO.getRSLT_DATA().size() : 0;

        // 페이지에 조회되는 리스트 세팅
        int startIdx = (page - 1) * record;
        int endIdx = page * record;

        List<ReceiveAlarmVO> resultList = responseVO.getRSLT_DATA() != null ? responseVO.getRSLT_DATA() : new ArrayList<>();

        // 시작 인덱스가 전체 리스트 사이즈보다 클 경우
        if(startIdx >= totalCnt) return new PagingVO<>(new PageVO(page, record, pageSize), new ArrayList<>(), totalCnt);

        // 마지막 인덱스가 전체 리스트 사이즈보다 클 경우
        if(endIdx > totalCnt - 1) endIdx = totalCnt - 1;

        // subList
        resultList = resultList.subList(startIdx, endIdx + 1);

        // return paging
        return new PagingVO<>(new PageVO(page, record, pageSize), resultList, totalCnt);
    }

    /**
     * 신규 투자박스 알림 수신 유무 확인
     * @return
     * @throws Exception
     */
    public boolean searchUnreadReceiveMarketAlarm() throws Exception {

        // 리스트 조회
        // 신규 읽지 않은 알림만 체크하므로 cnt : 0, inqCnt : 1, alrtAllYn : "N"
        BoxListResponseVO<ReceiveAlarmVO> responseVO = searchMarketReceiveAlarmList(0, 1, "N");

        if(responseVO.getRSLT_DATA().size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 마켓박스 알림 리스트 조회 공통
     * @param cnt
     * @param inqCnt
     * @param alrtAllYn
     * @return
     * @throws Exception
     */
    public BoxListResponseVO<ReceiveAlarmVO> searchMarketReceiveAlarmList(int cnt, int inqCnt, String alrtAllYn) throws Exception {

        // 로그인 유저 조회
        CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Request Setting
        // 빠지는 조건이 있는 경우 body에 해당 필드 자체가 없어야 하므로 VO가 아닌 HashMap 사용
        Map<String, String> requestMap = new HashMap<>();

        // 이용기관 아이디
        requestMap.put("usisId", user.getUtlinsttId());
        // 사용자 아이디
        requestMap.put("idmbId", user.getUsername());
        // 현재 건수
        requestMap.put("cnt", Integer.toString(cnt));
        // 조회 건수 (레코드수)
        requestMap.put("inqCnt", Integer.toString(inqCnt));
        // 확인 미확인 양쪽 포함
        requestMap.put("alrtAllYn", alrtAllYn);

        // 마켓박스는 마켓박스 알림 목록만 조회
        requestMap.put("alrtLrdvDcd", AlarmCode.ALRT_LRDV_DCD_BOX); // 대분류 : BOX
        requestMap.put("alrtMddvCd", AlarmCode.ALRT_MDDV_CD_MARKET); // 중분류 : 생산자네트워크

        // 리스트 조회
        BoxListResponseVO<ReceiveAlarmVO> responseVO = alarmFeign.getInvestBoxReceiveAlarmList(requestMap);
        if(responseVO.getSTATUS() == null || !responseVO.getSTATUS().equals("0000")) {
            throw new BizException(StatusCode.MNB0001);
        }

        return responseVO;
    }

    /**
     * 마켓박스 알림 수신 확인
     * @param alrtSndgNo
     * @return
     * @throws Exception
     */
    public boolean checkReceiveAlarm(String alrtSndgNo) throws Exception {

        // 로그인 유저 정보 조회
        CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // RequestBody 세팅
        RequestCheckAlarmVO requestVO = new RequestCheckAlarmVO();

        requestVO.setIdmbId(user.getUsername());
        requestVO.setUsisId(user.getUtlinsttId());

        // 전체 수신완료 체크 (N)
        // 현재 기타 알림은 보여주지 않으므로
        requestVO.setAlrtAllYn("N");

        // 수신 알림 체크
        if(StringUtils.hasLength(alrtSndgNo)) {
            requestVO.setList(Arrays.asList(alrtSndgNo));
        }

        // api 호출
        BoxMsgResponseVO response = alarmFeign.postCheckAlarm(requestVO);

        if(response.getSTATUS() == null || !response.getSTATUS().equals("0000")) {
            throw new BizException(StatusCode.MNB0001);
        }

        // 마켓박스 알림 수신 유무 체크 (리랜더링 필요할 경우를 위해)
        return searchUnreadReceiveMarketAlarm();
    }

    /**
     * 알림 수신건수 조회
     * 현재 포스트맨 테스트 시 전체 수신건수만 조회됨
     * @return
     * @throws Exception
     */
//    public int searchReceiveAlarmCount(AlarmCode.AlrtLrdvDcdEnum alrtLrdvDcdEnum) throws Exception {
//
//        // 로그인 유저 조회
//        CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//        // 파라미터 세팅
//        Map<String, String> requestMap = new HashMap<>();
//        requestMap.put("usisId", user.getUserGroupId());
//        requestMap.put("idmbId", user.getUsername());
//
//        // 알림 수신건수 API 조회
//        BoxListResponseVO<ReceiveAlarmCountVO> responseVO = alarmFeign.getAlarmCount(requestMap);
//
//        if(!responseVO.getSTATUS().equals("0000")) {
//            throw new BizException(StatusCode.MNB0001);
//        }
//
//        // 수신건수 리스트 확인
//        // ReceiveAlarmCountVO의 cnt 필드 get메서드 수정되어있음
//        if(responseVO.getRSLT_DATA() != null && responseVO.getRSLT_DATA().size() > 0) {
//
//            // 대분류 파라미터가 없을 경우 전체 건에 대해 확인
//            Integer count = 0;
//
//            if(alrtLrdvDcdEnum == null) {
//                count = responseVO.getRSLT_DATA()
//                        .stream()
//                        .filter(x -> x.getAlrtLrdvDcd().equals("ALL") && x.getAlrtMddvCd().equals("ALL"))
//                        .mapToInt(ReceiveAlarmCountVO::getCnt)
//                        .findFirst()
//                        .getAsInt();
//            }
//
//            // 대분류 파라미터가 있을 경우 해당 대분류 건에 대해 취합하여 적용
//            else {
//                count = responseVO.getRSLT_DATA()
//                        .stream()
//                        .filter(x -> x.getAlrtLrdvDcd().equals(alrtLrdvDcdEnum.getCode()))
//                        .mapToInt(ReceiveAlarmCountVO::getCnt)
//                        .sum();
//            }
//
//            return count == null ? 0 : count;
//        } else {
//            return 0;
//        }
//    }

}
