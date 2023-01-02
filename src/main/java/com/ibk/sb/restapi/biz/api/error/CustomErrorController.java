package com.ibk.sb.restapi.biz.api.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
public class CustomErrorController implements ErrorController {

    /**
     *  로컬이 아닌 개발, 운영에서 서버 설정으로
     *  정적 리소스 영역의 에러페이지가 아닌 에러 jsp로 바로 빠지므로
     *  Controller로 매핑을 잡음
     *
     *  현재 404 페이지는 css 파일도 같은 파일 안에 작성되어 있으므로
     *  Controller, Views 만 처리
     *  static 영역에 css, js를 분리할 경우 resource handler 설정을 java config 등으로 잡아야함 (투자박스 MvcConfig.java 참조)
     */

    @GetMapping("/error")
    public String handleError(HttpServletRequest request) {

        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if(status != null) {
            log.error("Server Error StatusCode : {}", status);
            int statusCode = Integer.valueOf(status.toString());
            if(statusCode == HttpStatus.NOT_FOUND.value()) return "404";
            if(statusCode == HttpStatus.FORBIDDEN.value()) return "404";
        } else {
            log.error("Server Extra Error");
        }
        return "error";
    }
}
