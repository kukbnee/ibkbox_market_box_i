package com.ibk.sb.restapi.biz.api.file;

import com.ibk.sb.restapi.app.common.constant.StatusCode;
import com.ibk.sb.restapi.app.common.util.BizException;
import com.ibk.sb.restapi.app.common.vo.ResponseData;
import com.ibk.sb.restapi.biz.service.file.FileService;
import com.ibk.sb.restapi.biz.service.file.vo.FileInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

@Api(tags = {"커머스 박스 - 파일 업/다운로드 API"})
@RestController
@RequestMapping(value = {"/api/file", "/api/mk/v1/file"})
@Slf4j
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    /**
     * 파일 업로드 API
     * @param file
     * @return
     */
    @ApiOperation(value = "파일 업로드 API")
    @PostMapping(
            path = {"/upload"}
            , consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}
    )
    public ResponseData uploadFile(@RequestPart(value = "file") MultipartFile file) {

        try {

            FileInfoVO fileInfoVO = fileService.uploadFile(file);

            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(fileInfoVO)
                    .build();

        } catch (BizException ex) {
            log.error(ex.getMessage());
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(StatusCode.COM0000.getMessage())
                    .build();
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(StatusCode.COM0000.getMessage())
                    .build();
        }
    }

    /**
     * 파일 다운로드 API
     * @param fileId : 파일 Id
     * @param response
     */
    @ApiOperation(value = "파일 다운로드 API")
    @GetMapping("/download/{fileId}")
    public ResponseData downloadFile(@PathVariable("fileId") String fileId, HttpServletResponse response) {
        try {

            fileService.downloadFile(fileId, response);

            // 파일 다운로드가 정상 진행되는 경우 response 객체를 사용하기 때문에 ResponseData return이 없음
            return null;

        } catch (BizException ex) {
            log.error(ex.getMessage());
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(StatusCode.COM0000.getMessage())
                    .build();
        } catch(Exception ex) {
            log.error(ex.getMessage());
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(StatusCode.COM0000.getMessage())
                    .build();
        }
    }

    /**
     * 이미지 렌더링 API
     * @param fileId
     * @param response
     */
    @ApiOperation(value = "이미지 렌더링 API")
    @GetMapping("/render/image/{fileId}")
    public void downloadImageFile(@PathVariable("fileId") String fileId, HttpServletResponse response) {

        try {
            fileService.downloadImageFile(fileId, response);

        } catch (BizException bx) {
            log.error("Business Exception (code : {}) : {}", bx.getErrorCode(), bx.getErrorMsg());
        } catch(Exception ex) {
            log.error(ex.getMessage());
        }
    }
}
