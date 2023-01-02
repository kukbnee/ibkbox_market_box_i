package com.ibk.sb.restapi.biz.service.test;

import com.ibk.sb.restapi.app.common.constant.AlarmCode;
import com.ibk.sb.restapi.app.common.util.FileUtil;
import com.ibk.sb.restapi.app.common.vo.CustomUser;
import com.ibk.sb.restapi.app.common.vo.PagingVO;
import com.ibk.sb.restapi.biz.service.alarm.AlarmService;
import com.ibk.sb.restapi.biz.service.alarm.vo.RequestAlarmVO;
import com.ibk.sb.restapi.biz.service.test.repo.TestRepo;
import com.ibk.sb.restapi.biz.service.test.vo.RequestTestPagingVO;
import com.ibk.sb.restapi.biz.service.test.vo.TestConnectDualVO;
import com.ibk.sb.restapi.biz.service.test.vo.TestDataVO;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TestService {

    private final TestRepo testRepo;

    private final FileUtil fileUtil;

    private final MessageSource messageSource;

    private final AlarmService alarmService;


    /**
     * 알림 발송 테스트
     * 테스트해볼 알림은 견적 발송
     * @throws Exception
     */
    public void testSendAlarm() throws Exception {

        // 로그인 정보 조회
        CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // 테스트에 사용할 알림 코드
        AlarmCode.AlarmCodeEnum alarmCodeEnum = AlarmCode.AlarmCodeEnum.ESTIMATE_REQUEST;

        // 알림 요청 인스턴스 생성
        RequestAlarmVO requestAlarmVO = alarmService.getAlarmRequest(alarmCodeEnum, null, new Object[]{"테스트발송업체"});

        // 알림 발송
        alarmService.sendMarketAlarm(requestAlarmVO, user.getUtlinsttId());
    }

    /**
     * DB 접속 테스트
     * @return
     * @throws Exception
     */
    public TestConnectDualVO testConnectDual() throws Exception {
        return testRepo.testConnectDual();
    }

    /**
     * 페이징 테스트
     * @param requestTestPagingVO
     * @return
     * @throws Exception
     */
    public PagingVO<TestDataVO> testPaging(RequestTestPagingVO requestTestPagingVO) throws Exception {
        List<TestDataVO> testDataList = testRepo.testPaging(requestTestPagingVO);

        PagingVO<TestDataVO> testPaging = new PagingVO<>(requestTestPagingVO, testDataList, testDataList.get(0).getTotalCnt());

        return testPaging;
    }

//    /**
//     * 파일 업로드 테스트
//     * @param multipartFile
//     * @return
//     * @throws Exception
//     */
//    public FileInfoVO testFileUpload(MultipartFile multipartFile) throws Exception {
//
//        FileInfoVO fileInfoVO = fileUtil.saveFile(multipartFile);
//
//        return fileInfoVO;
//    }
//
//    /**
//     * 파일 다운로드 테스트
//     * @param date
//     * @param fileId
//     * @throws Exception
//     */
//    public void testFileDownload(String date, String fileId, String fileName, String fileMime, HttpServletResponse response) throws Exception {
//
//        // response 버퍼에 남아있는 데이터 삭제
//        response.reset();
//
//        // DB 입력이 안된 정보이므로 파라미터로 저장소 경로 세팅
//        String filePath = date + File.separator + fileId + File.separator + fileName;
//
//        // 파일정보 헤더 세팅
//        response.setHeader("Content-Type", fileMime);
//        response.setHeader("Content-Disposition", "attachment; filename=\"" + URLEncoder.encode(fileName, "UTF-8") + "\"");
//        response.setHeader("Content-Transfer-Encoding", "binary");
//        response.setHeader("Pragma", "no-cache;");
//        response.setHeader("Expires", "-1;");
//
//        // 파일 스트림 다운로드
//        fileUtil.fileDownload(filePath, response.getOutputStream());
//        response.getOutputStream().close();
//    }


}
