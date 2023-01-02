package com.ibk.sb.restapi.biz.api.test;

import com.ibk.sb.restapi.app.common.util.BizException;
import com.ibk.sb.restapi.app.common.vo.ResponseData;
import com.ibk.sb.restapi.biz.service.patent.kipris.*;
import com.ibk.sb.restapi.biz.service.mainbox.MainBoxService;
import com.ibk.sb.restapi.biz.service.patent.kipris.vo.RequestSearchKiprisVO;
import com.ibk.sb.restapi.biz.service.patent.vo.PatentVO;
import com.ibk.sb.restapi.biz.service.test.TestService;
import com.ibk.sb.restapi.biz.service.test.vo.RequestTestPagingVO;
import com.ibk.sb.restapi.biz.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = {"api/test", "/api/mk/v1/test"}, produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
public class TestController {

    private final TestService testService;

    private final UserService userService;

    private final MainBoxService mainBoxService;

    private final KiprisService kiprisService;


    @GetMapping("/version")
    public String getVersion() {
        return "2022-10-04 4:44 배포 버전";
    }

    @PostMapping("/alarm/send")
    public ResponseData testSendAlarm() {
        try {
            testService.testSendAlarm();

            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .build();
        } catch (BizException bx) {
            log.error(bx.getErrorMsg());
            return ResponseData.builder()
                    .code(bx.getErrorCode())
                    .message(bx.getErrorMsg())
                    .build();
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(ex.getMessage())
                    .build();
        }
    }


    @GetMapping("/mainbox/jwt")
    public void testMainJet(@RequestParam(name = "userId")String userId, @RequestParam(name = "utlinsttId") String utlinsttId) throws Exception {
        mainBoxService.checkJwtValidate(userId, utlinsttId, "test");
    }

    /**
     * DB 접속 테스트
     * @return
     */
    @GetMapping("/connect/dual")
    public ResponseData testConnectDual() {
        try {
            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(testService.testConnectDual())
                    .build();
        } catch (BizException bx) {
            log.error(bx.getErrorMsg());
            return ResponseData.builder()
                    .code(bx.getErrorCode())
                    .message(bx.getErrorMsg())
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
     * 페이징 테스트

     * @return
     */
    @GetMapping("/paging")
    public ResponseData testPaging(RequestTestPagingVO requestTestPagingVO) {
        try {
            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(testService.testPaging(requestTestPagingVO))
                    .build();
        } catch (BizException bx) {
            log.error(bx.getErrorMsg());
            return ResponseData.builder()
                    .code(bx.getErrorCode())
                    .message(bx.getErrorMsg())
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
     * 파일 업로드 Test
     * @param file
     * @return
     */
//    @PostMapping(
//            path = {"/file/upload"}
//            , consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}
//    )
//    public ResponseData testUploadFile(@RequestPart(value = "file") MultipartFile file) {
//
//        try {
//            return ResponseData.builder()
//                    .code(HttpStatus.OK.value())
//                    .message(HttpStatus.OK.getReasonPhrase())
//                    .data(testService.testFileUpload(file))
//                    .build();
//        } catch (BizException bx) {
//            log.error(bx.getErrorMsg());
//            return ResponseData.builder()
//                    .code(bx.getErrorCode())
//                    .message(bx.getErrorMsg())
//                    .build();
//        } catch (Exception ex) {
//            log.error(ex.getMessage());
//            return ResponseData.builder()
//                    .code(HttpStatus.BAD_REQUEST.value())
//                    .message(ex.getMessage())
//                    .build();
//        }
//    }
//
//    @GetMapping("/file/download")
//    public void testFileDownload(
//            @RequestParam("date") String date
//            , @RequestParam("fileId") String fileId
//            , @RequestParam("fileName") String fileName
//            , @RequestParam("fileMime") String fileMime
//            , HttpServletResponse response) {
//
//        try {
//            testService.testFileDownload(date, fileId, fileName, fileMime, response);
//        } catch(BizException bx) {
//            log.error(bx.getErrorMsg());
//        } catch(Exception ex) {
//            log.error(ex.getMessage());
//        }
//    }

    /**
     * 특허정보 조회 테스트 api
     * @return
     */
    @GetMapping("/patent/test")
    public ResponseData testPatentList() {

        try {

            List<PatentVO> totalPatentList = kiprisService.searchKiprisPagingList(new RequestSearchKiprisVO());

            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(totalPatentList)
                    .build();
        } catch (BizException bx) {
            log.error(bx.getErrorMsg());
            return ResponseData.builder()
                    .code(bx.getErrorCode())
                    .message(bx.getErrorMsg())
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
