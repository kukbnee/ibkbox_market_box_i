package com.ibk.sb.restapi.biz.service.mainbox.feign;

import com.ibk.sb.restapi.app.common.vo.BoxListResponseVO;
import com.ibk.sb.restapi.app.config.MainBoxFeignConfig;
import com.ibk.sb.restapi.biz.service.mainbox.vo.AlarmTargetResponseVO;
import com.ibk.sb.restapi.biz.service.mainbox.vo.MainCompanyResponseVO;
import com.ibk.sb.restapi.biz.service.mainbox.vo.MainJwtResponseVO;
import com.ibk.sb.restapi.biz.service.mainbox.vo.MainUserResponseVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "box-open-api-mnb", url = "${feign.box-open-api.url}", configuration = MainBoxFeignConfig.class)
public interface MainBoxFeign {

    /**
     * 메인BOX jwt 토큰 체크
     * @param body
     * @return
     */
    @PostMapping("/api/mb/v1/checkjwt.do")
    MainJwtResponseVO getJwtTokenCheck(@RequestBody Map<String, String> body);

    /**
     * jwt 토큰 만료
     * 메인BOX seesion 에 있는 jwt 토큰 만료
     * @return
     */
    @PostMapping("/api/mb/v1/expirejwt.do")
    Map<String, Object> logout(@RequestBody Map<String, String> body);

    /**
     * 이용기관 원장 조회
     * @param body
     * @return
     */
    @PostMapping("/api/mb/v1/mnbcmpy/CmpyInq")
    MainCompanyResponseVO getMainBoxUtlinsttInfo(@RequestBody Map<String, String> body);

    /**
     * 메인BOX 사용자 상제정보 조회
     * @param body
     * @return
     */
    @PostMapping("/api/mb/v1/mnbusr/plfUserLedgrDtlInq")
    MainUserResponseVO getMainBoxUserInfo(@RequestBody Map<String, String> body);

    /**
     * 메인BOX 커머스박스 판매자 정보 수정
     * @param body
     * @return
     */
    @PostMapping("/api/mb/v1/mkt/selr/info/save")
    Map<String, Object> saveSelrInfo(@RequestBody Map<String, String> body);

    /**
     * 메인BOX 사용자 세션 정보 확인
     * @param body
     * @return
     */
    @PostMapping("api/mb/v1/com001/selectUserSessionInfo")
    Map<String, Object> selectUserSessionInfo(@RequestBody Map<String, String> body);

}
