package com.ibk.sb.restapi.app.common.util;

import com.ibk.sb.restapi.app.common.constant.StatusCode;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BizException extends RuntimeException {

    private final String ERROR_CODE;
    private final String ERROR_MSG;

    // StatusCode에 정의된 메시지만을 남길 때
    public BizException(StatusCode statusCode) {
        this.ERROR_CODE = statusCode.getCode();
        this.ERROR_MSG = statusCode.getMessage();
    }

    // 추가적으로 에러 메시지를 로그에 남길때
    public BizException(StatusCode statusCode, String trueExceptionLog) {
        this.ERROR_CODE = statusCode.getCode();
        this.ERROR_MSG = statusCode.getMessage();

        log.error(trueExceptionLog);
    }

    public String getErrorCode() {
        return this.ERROR_CODE;
    }

    public String getErrorMsg() {
        return this.ERROR_MSG;
    }
}
