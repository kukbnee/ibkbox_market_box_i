package com.ibk.sb.restapi.biz.service.mainbox;

import com.ibk.sb.restapi.app.common.constant.AlarmCode;
import com.ibk.sb.restapi.app.common.constant.StatusCode;
import com.ibk.sb.restapi.app.common.util.BizException;
import com.ibk.sb.restapi.app.common.util.FileUtil;
import com.ibk.sb.restapi.app.common.vo.BoxListResponseVO;
import com.ibk.sb.restapi.biz.service.mainbox.feign.MainBoxByMktKeyFeign;
import com.ibk.sb.restapi.biz.service.mainbox.feign.MainBoxFeign;
import com.ibk.sb.restapi.biz.service.mainbox.vo.*;


import com.ibk.sb.restapi.biz.service.user.vo.MyPageUserVO;
import feign.FeignException;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MainBoxService {

    private final MainBoxFeign mainBoxFeign;
    private final MainBoxByMktKeyFeign mainBoxByMktKeyFeign;

    private final FileUtil fileUtil;

    /**
     * TODO : 메인박스 이용기관 알림 대상 목록 조회 -> 배포 후 수정
     * @param usisId
     * @param authCodeEnum
     * @return
     * @throws Exception
     */
    public List<AlarmTargetVO> searchAlarmTargetList (String usisId, AlarmCode.AuthCodeEnum authCodeEnum) throws Exception {

        // RequestBody 설정
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("UTLINSTT_ID", usisId);
        requestMap.put("AUTHOR_CODE", authCodeEnum.getCode());

        // MNB 알림 대상 리스트 조회 API 호출
        BoxListResponseVO<AlarmTargetResponseVO> responseVO = mainBoxByMktKeyFeign.getMainBoxAlarmTargetList(requestMap);

        // 마켓 박스의 경우 알림 API가 3번 호출되어야 하므로 Transaction을 위해 throw Exception이 아닌 logging 처리 + null 리턴
        if(responseVO.getSTATUS() == null || !responseVO.getSTATUS().equals("0000")) {
            log.error(responseVO.getMESSAGE());
            return null;
        }

        // 데이터가 없는 경우 빈 배열 처리
        if(responseVO.getDATA().size() == 0) {
            return new ArrayList<>();
        }

        // 데이터가 있는 경우 AlarmTargetVO로 매핑처리
        return responseVO.getDATA().stream()
                .filter(x -> StringUtils.hasLength(x.getUTLINSTT_ID()) && StringUtils.hasLength(x.getUSER_ID()))
                .map(x -> new AlarmTargetVO(x.getUTLINSTT_ID(), x.getUSER_ID())).collect(Collectors.toList());
    }

    /**
     * 메인BOX 기업정보 조회(이용기관 원장 조회)
     * @param utlinsttId
     * @return
     * @throws Exception
     */
    public MainCompanyVO searchMainCompany(String utlinsttId) throws Exception {

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("utlinsttId", utlinsttId);

        MainCompanyResponseVO responseVO = mainBoxFeign.getMainBoxUtlinsttInfo(requestBody);

        if(responseVO.getSTATUS() == null || !responseVO.getSTATUS().equals("0000")) {
            throw new Exception(StatusCode.SSO10501.getMessage());
        }

        // 통신은 되었지만 데이터가 없는 경우 null 전송
        // -> Exception 처리로 하는 경우 try-catch 처리를 하더라도 tx에 롤백마크가 터지기에 각 서비스에 대해 null 처리를 하는 것으로
        if(!(responseVO.getRSLT_LIST() != null && responseVO.getRSLT_LIST().size() > 0)) {
            return null;
        }

        // 로고 이미지 파일 세팅
        MainCompanyVO result = responseVO.getRSLT_LIST().get(0);
        String url = StringUtils.hasLength(result.getCmpnyLogoImageFile().trim()) ? result.getCmpnyLogoImageFile() : result.getCmpnyLogoBassImageFile();
        result.setLogoImageFile(fileUtil.setMainboxLogoUrl(url));

        return result;
    }

    /**
     * 메인BOX 사용자 정보 조회
     * @param userId
     * @return
     * @throws Exception
     */
    public MainUserVO searchMainUser(String userId, String utlinsttId) throws Exception {

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("userId", userId);
        requestBody.put("utlinsttId", utlinsttId); // 기업 아이디와 함께 보내는 경우 매핑되는 기업정보 및 권한정보가 함께 옴

        MainUserResponseVO responseVO = new MainUserResponseVO();

        try {

            responseVO = mainBoxFeign.getMainBoxUserInfo(requestBody);

            if(responseVO.getSTATUS() == null || !responseVO.getSTATUS().equals("0000")) {
                throw new Exception(StatusCode.SSO10501.getMessage());
            }

        } catch (FeignException error) {
            responseVO.setSTATUS("9999");
            responseVO.setRSLT_DATA(new MainUserVO());
            log.error("Fail Trace : ", error.getStackTrace());

        }

        // 통신은 되었지만 데이터가 없는 경우 null 전송
        // -> Exception 처리로 하는 경우 try-catch 처리를 하더라도 tx에 롤백마크가 터지기에 각 서비스에 대해 null 처리를 하는 것으로
        return responseVO.getRSLT_DATA();
    }


    /**
     * 로그인한 유저의 jwt 토큰 유효성 검증
     * MainBox DB 에 담겨있는 토큰을 조회해서 요청받은 토큰과 비교
     * @param username
     * @param companyname
     * @param jwtToken
     * @throws RuntimeException
     */
    public void checkJwtValidate(String username, String companyname, String jwtToken) throws Exception {

        // body값 셋팅
        Map<String, String> body = new HashMap<>();
        body.put("USER_ID", username);
        body.put("UTLINSTT_ID", companyname);

        // 메인박스 api 호출
        MainJwtResponseVO mainJwtResponseVO = mainBoxFeign.getJwtTokenCheck(body);

        // 스테이터스가 올바르지 않은 경우
        if(mainJwtResponseVO.getSTATUS() == null || !mainJwtResponseVO.getSTATUS().equals("0000")) {
            throw new Exception(StatusCode.MNB0001.getMessage());
        }

        // DB에 담겨있는 토큰값과 요청받은 토큰값이 다르거나, 유효한 jwt 토큰이 없는 경우, 에러
        if(mainJwtResponseVO.getJWT() == null || !jwtToken.equals(mainJwtResponseVO.getJWT())) {
            throw new Exception(StatusCode.LOGIN0001.getMessage());
        }
    }

    /**
     * jwt 토큰 만료
     * @param jwt
     * @return
     * @throws Exception
     */
    public Map<String, Object> logout(String jwt) throws Exception {

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("JWT", jwt);

        Map<String, Object> resultMap = mainBoxFeign.logout(requestBody);

        // 스테이터스가 올바르지 않을 경우 에러
        if(resultMap.get("STATUS") == null || !resultMap.get("STATUS").equals("0000")) {
            throw new Exception(StatusCode.COM9998.getMessage());
        }

        return resultMap;
    }

    /**
     * 판매자 정보 수정
     * @param myPageUserVO
     * @return
     * @throws Exception
     */
    public Map<String, Object> saveSelrInfo(MyPageUserVO myPageUserVO) throws Exception {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("UTLINSTT_ID", myPageUserVO.getUtlinsttId());           //이용기관 ID
        requestBody.put("REPRSNT_TELNO", myPageUserVO.getReprsntTelno());       //이용기관대표연락처
        requestBody.put("POST_NO", myPageUserVO.getPostNo());                   //이용기관 소재 우편번호
        requestBody.put("ADRES", myPageUserVO.getAdres());                      //이용기관 소재 주소
        requestBody.put("DETAIL_ADRES", myPageUserVO.getDetailAdres());         //이용기관 소재 상세주소
        requestBody.put("NW_ADRES", myPageUserVO.getNwAdres());                 //이용기관 소재 도로명주소
        requestBody.put("NW_ADRES_DETAIL", myPageUserVO.getNwAdresDetail());    //이용기관 소재 도로명주소 상세
        requestBody.put("UPDT_UTLINSTT", myPageUserVO.getUtlinsttId());         //최초의 등록이용기관 이후 수정한 기관
        requestBody.put("UPDT_ID", myPageUserVO.getAmnnUserId());               //등록자 이후 수정시 해당 수정자의 ID

        Map<String, Object> resultMap = mainBoxFeign.saveSelrInfo(requestBody);

        // 스테이터스가 올바르지 않을 경우 에러
        if(resultMap.get("STATUS") == null || !resultMap.get("STATUS").equals("0000")) {
            throw new Exception(StatusCode.COM9998.getMessage());
        }

        return resultMap;
    }

    /**
     * 사용자 세션 정보 확인
     * @param param
     * @return
     * @throws Exception
     */
    public Map<String, Object> selectUserSessionInfo(Map<String, String> param) throws Exception {

        Map<String, Object> resultMap = mainBoxFeign.selectUserSessionInfo(param);

        // 스테이터스가 올바르지 않을 경우 에러
        if(resultMap.get("STATUS") == null || !resultMap.get("STATUS").equals("0000")) {
            log.error(resultMap.get("RSLT_MSG").toString());
            throw new Exception(StatusCode.COM9998.getMessage());
        }

        return (Map<String, Object>) resultMap.get("RSLT_LIST");
    }
}
