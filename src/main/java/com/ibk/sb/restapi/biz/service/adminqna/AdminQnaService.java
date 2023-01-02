package com.ibk.sb.restapi.biz.service.adminqna;

import com.ibk.sb.restapi.app.common.constant.AlarmCode;
import com.ibk.sb.restapi.app.common.constant.ComCode;
import com.ibk.sb.restapi.app.common.vo.CustomUser;
import com.ibk.sb.restapi.app.common.vo.PagingVO;
import com.ibk.sb.restapi.app.common.vo.ResponseData;
import com.ibk.sb.restapi.biz.service.adminqna.repo.AdminQnaRepo;
import com.ibk.sb.restapi.biz.service.adminqna.vo.*;
import com.ibk.sb.restapi.biz.service.alarm.AlarmService;
import com.ibk.sb.restapi.biz.service.alarm.vo.RequestAlarmVO;
import com.ibk.sb.restapi.biz.service.file.repo.FileRepo;
import com.ibk.sb.restapi.biz.service.product.bundle.vo.SummaryBundleInfoVO;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdminQnaService {

    private final AdminQnaRepo adminQnaRepo;

    private final FileRepo fileRepo;

    // 알림 관련 의존성 주입
    private final AlarmService alarmService;


    /**
     * 관리자 문의 조회
     * @param requestSearchAdminQnaVO
     * @return
     * @throws Exception
     */
    public PagingVO<SummaryAdminQnaVO> searchAdminQnaList(RequestSearchAdminQnaVO requestSearchAdminQnaVO) throws Exception {

        /*
         * 로그인 체크
         *      로그인 정보가 CustomUser.class 라면 jwt 로그인 조회
         *      로그인 상태가 아니라면 String 타입으로 떨이짐
         */
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof CustomUser) {
            CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            requestSearchAdminQnaVO.setInquUserId(user.getLoginUserId());
            requestSearchAdminQnaVO.setInquUsisId(user.getUtlinsttId());
        }

        List<SummaryAdminQnaVO> searchAdminQnaList = adminQnaRepo.searchAdminQnaList(requestSearchAdminQnaVO);

        return new PagingVO<>(requestSearchAdminQnaVO, searchAdminQnaList);
    }

    /**
     * 관리자 문의 상세조회
     * @param requestSearchAdminQnaVO
     * @return
     * @throws Exception
     */
    public DetailAdminQnaVO searchAdminQnaDetail(RequestSearchAdminQnaVO requestSearchAdminQnaVO) throws Exception {

        /*
         * 로그인 체크
         *      로그인 정보가 CustomUser.class 라면 jwt 로그인 조회
         *      로그인 상태가 아니라면 String 타입으로 떨이짐
         */
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof CustomUser) {
            CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            requestSearchAdminQnaVO.setInquUsisId(user.getUtlinsttId());
            requestSearchAdminQnaVO.setInquUserId(user.getLoginUserId());
        }

        DetailAdminQnaVO result = new DetailAdminQnaVO();

        // 문의 내용 조회
        AdminQnaVO adminQnaVO = adminQnaRepo.searchAdminQna(requestSearchAdminQnaVO);
        // 문의 첨부파일 조회 및 셋팅
        adminQnaVO.setAdminQnaFileVOList(adminQnaRepo.searchAdminQnaFileList(requestSearchAdminQnaVO.getAdmInquInfId(), ComCode.AIS02001.getCode()));
        // 문의 내용 셋팅
        result.setAdminQnaVO(adminQnaVO);

        // 답변 내용 조회
        List<AdminQnaAnswerVO> adminQnaAnswerVOList = adminQnaRepo.searchAdminQnaAnswer(requestSearchAdminQnaVO);
        // 답변 내용 셋팅
        result.setAdminQnaAnswerVOList(adminQnaAnswerVOList);

        return result;
    }


    /**
     * 관리자 문의 등록
     * @param adminQnaVO
     * @return
     */
    public String saveAdminQna(AdminQnaVO adminQnaVO) {

        // 로그인 체크
        String loginUserId = null;
        String utlinsttId = null;

        // 로그인 정보가 CustomUser.class 라면 jwt 로그인 조회
        // 로그인 상태가 아니라면 String 타입으로 떨이짐
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof CustomUser) {
            CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            loginUserId = user.getLoginUserId();
            utlinsttId = user.getUtlinsttId();
        }

        // 문의 사용자 정보 셋팅
        adminQnaVO.setInquUserId(loginUserId);
        adminQnaVO.setInquUsisId(utlinsttId);

        adminQnaVO.setRgsnUserId(loginUserId);
        adminQnaVO.setAmnnUserId(loginUserId);

        // 디폴트 로직 : 수정(update)
        // update flg = true
        boolean updateFlg = true;

        // 관리자 문의 정보 ID
        String admInquInfId = adminQnaVO.getAdmInquInfId();

        // ID 가 존재하지 않을 경우, 새로 생성하고 등록 로직
        if(!StringUtils.hasLength(admInquInfId)) {
            admInquInfId = UUID.randomUUID().toString();
            updateFlg = false;
        }

        // 수정
        if(updateFlg) {

        }

        // 등록
        else {
            // 관리자 문의 정보 ID 셋팅
            adminQnaVO.setAdmInquInfId(admInquInfId);
            // 처리유형 쿠분 코드 셋팅 - 답변대기
            adminQnaVO.setInquSttId(ComCode.AIS01001.getCode());
            // 관리자 문의 등록
            adminQnaRepo.saveAdminQna(adminQnaVO);

            // 관리자 문의 첨부 파일리스트
            List<AdminQnaFileVO> adminQnaFileVOList = adminQnaVO.getAdminQnaFileVOList();
            // 관리자 문의 첨부 파일리스트가 존재하는 경우, 관리자 문의 파일 정보 등록
            if(adminQnaFileVOList != null && adminQnaFileVOList.size() > 0) {
                String finalAdmInquInfId = admInquInfId;
                String finalLoginUserId = loginUserId;
                adminQnaFileVOList.forEach(x -> {
                    x.setAdmInquInfId(finalAdmInquInfId);
                    x.setFilePtrnId(ComCode.AIS02001.getCode());
                    x.setRgsnUserId(finalLoginUserId);
                    x.setAmnnUserId(finalLoginUserId);
                    // 관리자 문의 파일 정보 등록
                    adminQnaRepo.saveAdminQnaFile(x);
                });
            }
        }

        return admInquInfId;
    }

    /**
     * 관리자 문의 삭제
     * @param adminQnaVO
     */
    public void deleteAdminQna(AdminQnaVO adminQnaVO) {

        // 로그인 체크
        String loginUserId = null;

        // 로그인 정보가 CustomUser.class 라면 jwt 로그인 조회
        // 로그인 상태가 아니라면 String 타입으로 떨이짐
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof CustomUser) {
            CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            loginUserId = user.getLoginUserId();
        }

        adminQnaVO.setRgsnUserId(loginUserId);
        adminQnaVO.setAmnnUserId(loginUserId);

        // 검색조건 셋팅
        RequestSearchAdminQnaVO requestSearchAdminQnaVO = new RequestSearchAdminQnaVO();
        requestSearchAdminQnaVO.setAdmInquInfId(adminQnaVO.getAdmInquInfId());
        // 문의 내용 조회
        AdminQnaVO oldAdminQnaVO = adminQnaRepo.searchAdminQna(requestSearchAdminQnaVO);

        // 기존의 문의가 존재하는 경우,
        if(oldAdminQnaVO != null) {
            List<AdminQnaFileVO> fileList = adminQnaRepo.searchAdminQnaFileList(oldAdminQnaVO.getAdmInquInfId(), ComCode.AIS02001.getCode());
            // 첨부한 파일이 존재하는 경우, 첨부파일 삭제
            if(fileList != null && fileList.size() > 0) {
                fileList.forEach(x -> {
                    fileRepo.deleteFileInfo(x.getFileId());
                });
            }
        }

        // 관리자 문의 삭제
        adminQnaRepo.deleteAdminQna(adminQnaVO);
    }

    /**
     * 운영자포털 > 고객지원관리 > 문의관리
     * @param requestSearchAdminQnaVO
     * @return
     * @throws Exception
     */
    public PagingVO<SummaryAdminQnaVO> searchAdminInquiryList(RequestSearchAdminQnaVO requestSearchAdminQnaVO) throws Exception {

        String[] ids = requestSearchAdminQnaVO.getInquTypeId().split(",");
        requestSearchAdminQnaVO.setInquTypeIds(Arrays.asList(ids));

        List<SummaryAdminQnaVO> searchAdminQnaList = adminQnaRepo.searchAdminInquiryList(requestSearchAdminQnaVO);

        return new PagingVO<>(requestSearchAdminQnaVO, searchAdminQnaList);
    }

    /**
     * 운영자포털 > 고객지원관리 > 관리자 문의 상세조회
     * @param requestSearchAdminQnaVO
     * @return
     * @throws Exception
     */
    public DetailAdminQnaVO searchAdminInquiryDetail(RequestSearchAdminQnaVO requestSearchAdminQnaVO) throws Exception {

        DetailAdminQnaVO result = new DetailAdminQnaVO();

        // 문의 내용 조회
        AdminQnaVO adminQnaVO = adminQnaRepo.searchAdminQna(requestSearchAdminQnaVO);
        // 문의 첨부파일 조회 및 셋팅
        adminQnaVO.setAdminQnaFileVOList(adminQnaRepo.searchAdminQnaFileList(requestSearchAdminQnaVO.getAdmInquInfId(), ComCode.AIS02001.getCode()));
        // 문의 내용 셋팅
        result.setAdminQnaVO(adminQnaVO);

        // 답변 내용 조회
        List<AdminQnaAnswerVO> adminQnaAnswerVOList = adminQnaRepo.searchAdminQnaAnswer(requestSearchAdminQnaVO);
        // 답변 내용 셋팅
        result.setAdminQnaAnswerVOList(adminQnaAnswerVOList);

        return result;
    }

    /**
     * 운영자포털 > 고객지원관리 > 관리자 문의 상세조회 > 답변 등록
     * @param adminQnaAnswerVO
     * @return
     * @throws Exception
     */
    public void inquirySave(AdminQnaAnswerVO adminQnaAnswerVO) throws Exception {

        // 로그인
//        CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        adminQnaAnswerVO.setRgsnUserId(user.getUsername()); //등록자
//        adminQnaAnswerVO.setAmnnUserId(user.getUsername()); //수정자

        adminQnaAnswerVO.setInquSttId(ComCode.AIS01002.getCode()); //답변완료

        adminQnaRepo.inquirySave(adminQnaAnswerVO); //문의 등록
        adminQnaRepo.inquiryStateUpdate(adminQnaAnswerVO); //문의 내용 상태 업데이트

        /*
         * 알림 서비스
         */
        RequestSearchAdminQnaVO requestQna = new RequestSearchAdminQnaVO();
        requestQna.setAdmInquInfId(adminQnaAnswerVO.getAdmInquInfId());
        AdminQnaVO adminQnaVO = adminQnaRepo.searchAdminQna(requestQna);

        // 알림 요청 인스턴스 생성
        RequestAlarmVO requestAlarmVO = alarmService.getAlarmRequest(AlarmCode.AlarmCodeEnum.ADMIN_QNA, null, null);
        // 알림 발송
        alarmService.sendMarketAlarm(requestAlarmVO, adminQnaVO.getInquUsisId());
    }

    /**
     * 운영자포털 > 고객지원관리 > 관리자 문의 상세조회 > 답변 수정
     * @param adminQnaAnswerVO
     * @return
     * @throws Exception
     */
    public void inquiryUpdate(AdminQnaAnswerVO adminQnaAnswerVO) throws Exception {

        // 로그인
//        CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        adminQnaAnswerVO.setRgsnUserId(user.getUsername()); //등록자
//        adminQnaAnswerVO.setAmnnUserId(user.getUsername()); //수정자

        adminQnaRepo.inquiryUpdate(adminQnaAnswerVO); //문의 등록

    }

    /**
     * 운영자포털 > 고객지원관리 > 관리자 문의 상세조회 > 삭제
     * @param adminQnaVO
     * @return
     * @throws Exception
     */
    public void inquiryDel(AdminQnaVO adminQnaVO) throws Exception {

        // 로그인
//        CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        adminQnaVO.setAmnnUserId(user.getUsername()) //수정자


        // 검색조건 셋팅
        RequestSearchAdminQnaVO requestSearchAdminQnaVO = new RequestSearchAdminQnaVO();
        requestSearchAdminQnaVO.setAdmInquInfId(adminQnaVO.getAdmInquInfId());

        // 문의 내용 조회
        AdminQnaVO oldAdminQnaVO = adminQnaRepo.searchAdminQna(requestSearchAdminQnaVO);

        // 기존의 문의가 존재하는 경우,
        if(oldAdminQnaVO != null) {
            List<AdminQnaFileVO> fileList = adminQnaRepo.searchAdminQnaFileList(oldAdminQnaVO.getAdmInquInfId(), ComCode.AIS02001.getCode());
            // 첨부한 파일이 존재하는 경우, 첨부파일 삭제
            if(fileList != null && fileList.size() > 0) {
                fileList.forEach(x -> {
                    fileRepo.deleteFileInfo(x.getFileId());
                });
            }
        }

        // 관리자 문의 삭제
        adminQnaRepo.deleteAdminQna(adminQnaVO);

    }
}
