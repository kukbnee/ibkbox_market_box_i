package com.ibk.sb.restapi.biz.api.batch;

import com.ibk.sb.restapi.app.common.util.BizException;
import com.ibk.sb.restapi.biz.service.batch.BatchService;
import com.ibk.sb.restapi.biz.service.user.vo.CompanyVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = {"배치 - 운영기관 기본 정보 및 판매자 정보 등록"})
@RestController
@Slf4j
@RequestMapping(path = {"/api/batch", "/api/mk/v1/batch"}, produces={"application/json"})
@RequiredArgsConstructor
public class batchController {

    private final BatchService service;

    /**
     * 운영기관 기본 정보 및 판매자 정보 등록
     * @param companyVOList
     * @throws Exception
     */
    @ApiOperation(value = "운영기관 기본 정보 및 판매자 정보 등록")
    @ApiImplicitParam(name = "companyVOList", value = "CompanyVO")
    @PostMapping("/utlinstt/save")
    public void saveMktFfpcInfoAndSelrInfo(@RequestBody List<CompanyVO> companyVOList) throws Exception {

        try {
            log.info("===================");
            log.info("batch service start");
            log.info("service Name", "운영기관 기본 정보 및 판매자 정보 등록");

            service.saveMktFfpcInfoAndSelrInfo(companyVOList);

            log.info("batch service end");
            log.info("===================");

        } catch (BizException ex) {
            log.error(ex.getMessage());
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
    }
}
