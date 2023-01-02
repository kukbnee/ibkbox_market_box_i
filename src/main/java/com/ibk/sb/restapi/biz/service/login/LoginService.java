package com.ibk.sb.restapi.biz.service.login;

import com.ibk.sb.restapi.biz.service.mainbox.MainBoxService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.client.HttpClientErrorException;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginService {

    private final MainBoxService mainBoxService;

    /**
     * BOX SESSION 로그인
     * @param reqJson
     * @return
     */
    public Map<String, Object> login(HashMap<String, String> reqJson) {

        Map<String, Object> resultMap = new ModelMap();

        try {
            resultMap = mainBoxService.selectUserSessionInfo(reqJson);

        } catch( HttpClientErrorException e) {
            log.error("ConsultService login HttpClientErrorException >> {}", e.getMessage());
        } catch ( Exception e ) {
            log.error("ConsultService login Exception >> {}", e.getMessage());
        }
        return resultMap;
    }
}
