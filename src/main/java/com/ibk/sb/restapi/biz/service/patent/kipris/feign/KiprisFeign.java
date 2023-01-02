package com.ibk.sb.restapi.biz.service.patent.kipris.feign;

import com.ibk.sb.restapi.app.config.MarketBoxFeignConfig;
import com.ibk.sb.restapi.biz.service.patent.kipris.vo.response.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "kipris-api", url = "${feign.box-open-api.url}", configuration = MarketBoxFeignConfig.class)
public interface KiprisFeign {

    /**
     * 출원인번호(특허고객번호) 조회 (by 사업자 번호) 조회
     *
     * ->   특허 실용 항목별 검색(전체검색) by 출원인번호
     *      + 상표 항목별검색(전체검색) by 출원인번호
     *      + 디자인 항목별 검색(전체검색) by 출원인번호
     *
     * -> 자바 페이징 처리
     *
     * -> 디자인 항목의 경우 상세가 가야할 경우  서지상세정보 필요
     *
     */


    /**
     * 출원인 법인
     * 출원인 법인 및 사업자 번호(사업자번호)
     * 사업자번호 -> 출원인번호 조회
     *
     * @param BusinessRegistrationNumber
     * @param accessKey
     * @return
     */
    @GetMapping(value = "/api/kipris/v1/openapi/rest/CorpBsApplicantService/corpBsApplicantInfoV3")
    KiprisApplicantResponseVO getApplicant(@RequestParam("BusinessRegistrationNumber") String BusinessRegistrationNumber,
                                           @RequestParam("accessKey") String accessKey);


    /**
     * 특허 실용 공개 등록 공보
     * 항목별검색 -> 전체검색 -> 출원인명 :출원인번호로 조회
     *
     * @param applicant
     * @param ServiceKey
     * @return
     */
    @GetMapping(value = "/api/kipris/v1/kipo-api/kipi/patUtiModInfoSearchSevice/getAdvancedSearch")
    KiprisIpResponseVO getIpListResponse(@RequestParam("applicant") String applicant,
                                         @RequestParam("pageNo") Integer pageNo,
                                         @RequestParam("numOfRows") Integer numOfRows,
                                         @RequestParam("ServiceKey") String ServiceKey);
}