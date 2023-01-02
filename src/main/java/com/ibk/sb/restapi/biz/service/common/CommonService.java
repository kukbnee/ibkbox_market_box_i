package com.ibk.sb.restapi.biz.service.common;

import com.ibk.sb.restapi.app.common.vo.ResponseData;
import com.ibk.sb.restapi.biz.service.common.repo.ComCodeRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommonService {

    private final ComCodeRepo comCodeRepo;

    /**
     * 공통 코드 그룹 목록
     * @return
     */
    public ResponseData searchComCodeGroupList(String grpCdTag){

        return ResponseData.builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(comCodeRepo.searchComCodeGroupList(grpCdTag))
                .build();
    }

    /**
     * 공통 코드 그룹 상세 목록
     * @return
     */
    public ResponseData searchComCodeList(String grpCdId){

        return ResponseData.builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(comCodeRepo.searchComCodeList(grpCdId))
                .build();
    }

}
