package com.ibk.sb.restapi.biz.api.seal;

import com.ibk.sb.restapi.app.common.util.BizException;
import com.ibk.sb.restapi.app.common.vo.ResponseData;
import com.ibk.sb.restapi.biz.service.seal.SealService;
import com.ibk.sb.restapi.biz.service.seal.vo.SealVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * 인감 정보 API
 */
@Api(tags = {"커머스 박스 - 인감정보 API"})
@RestController
@Slf4j
@RequestMapping(path={"/api/seal", "/api/mk/v1/seal"}, produces = {"application/json"})
@RequiredArgsConstructor
public class SealController {

    private final SealService sealService;

    /**
     * 인감 조회
     * @param
     * @return
     */
    @ApiOperation(value = "인감 조회")
    @GetMapping()
    public ResponseData searchSealInfo(@RequestParam("utlinsttId") String utlinsttId) {

        try {

            SealVo sealVo = sealService.searchSealInfoWithBase64(utlinsttId);

            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(sealVo)
                    .build();

        } catch (BizException ex) {
            log.error(ex.getMessage());
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(ex.getMessage())
                    .build();
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(ex.getMessage())
                    .build();
        }
    }

    /**
     * 인감 등록
     * @param sealVo
     * @return
     */
    @ApiOperation(value = "인감 등록")
    @ApiImplicitParam(name = "sealVo", value = "SealVo")
    @PostMapping("/save")
    public ResponseData saveSealInfo(@RequestBody SealVo sealVo) {

        try {

            SealVo resultVo = sealService.saveSealInfo(sealVo);

            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .data(resultVo)
                    .message(HttpStatus.OK.getReasonPhrase())
                    .build();

        } catch (BizException ex) {
            log.error(ex.getMessage());
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(ex.getMessage())
                    .build();
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(ex.getMessage())
                    .build();
        }
    }

    /**
     * 인감 삭제
     *      SealVo.rgslImgFileId 필요
     * @param sealVo
     * @return
     */
    @ApiOperation(value = "인감 삭제")
    @ApiImplicitParam(name = "sealVo", value = "SealVo")
    @PostMapping("/delete")
    public ResponseData deleteSealInfo(@RequestBody SealVo sealVo) {

        try {

            sealService.deleteSealInfo(sealVo);

            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .build();

        } catch (BizException ex) {
            log.error(ex.getMessage());
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(ex.getMessage())
                    .build();
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(ex.getMessage())
                    .build();
        }
    }
}
