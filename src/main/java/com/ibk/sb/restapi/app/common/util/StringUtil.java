package com.ibk.sb.restapi.app.common.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

/**
 * 문자 유틸 클레스
 */
@Slf4j
public class StringUtil {

    /**
     * 문자열을 숫자(Integer)로 변경
     * @param val
     * @return
     */
    public static Integer toInteger(String val) throws Exception {
        if(!StringUtils.hasLength(val)){
            return 0;
        }
        Integer dec = Integer.parseInt(val);
        return dec;
    }
}
