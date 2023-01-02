package com.ibk.sb.restapi.biz.service.file;

import com.ibk.sb.restapi.app.common.constant.FileWhiteList;
import com.ibk.sb.restapi.app.common.constant.StatusCode;
import com.ibk.sb.restapi.app.common.util.BizException;
import com.ibk.sb.restapi.app.common.util.FileUtil;
import com.ibk.sb.restapi.app.common.vo.CustomUser;
import com.ibk.sb.restapi.biz.service.file.repo.FileRepo;
import com.ibk.sb.restapi.biz.service.file.vo.FileInfoVO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FileUtil fileUtil;

    private final FileRepo fileRepo;

    /**
     * 파일 업로드 Service
     * @param file
     * @return 파일 ID
     * @throws Exception
     */
    public FileInfoVO uploadFile(MultipartFile file) throws Exception {

        FileInfoVO fileInfoVO = fileUtil.saveFile(file);

        /*
         * 로그인 체크
         *      로그인 정보가 CustomUser.class 라면 jwt 로그인 조회
         *      로그인 상태가 아니라면 String 타입으로 떨이짐
         */
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof CustomUser) {
            CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            // 등록 사용자 이름 셋팅
            fileInfoVO.setRgsnUserId(user.getUsername());
        }

        fileRepo.insertFileInfo(fileInfoVO);

        if(fileInfoVO.getFilePtrn().startsWith("image")) {
            fileInfoVO.setImgUrl(fileUtil.setImageUrl(fileInfoVO.getFileId()));
        }

        return fileInfoVO;
    }

    /**
     * 파일 다운로드 Service
     * @param fileId
     * @throws Exception
     */
    public void downloadFile(String fileId, HttpServletResponse response) throws Exception {

        // response 버퍼에 남아있는 데이터 삭제
        response.reset();

        // 파일 정보 조회
        FileInfoVO fileInfoVO = fileRepo.selectFileInfo(fileId);

        // 파일아이디 유효성 검증
        if(fileInfoVO == null) {
            throw new BizException(StatusCode.MNB0003);
        }

        // 파일 스트림 다운로드
        fileStreamDownload(fileInfoVO, response);
    }

    /**
     * 파일 이미지 태그 스트림 전송
     * @param fileId
     * @param response
     * @throws Exception
     */
    public void downloadImageFile(String fileId, HttpServletResponse response) throws Exception {

        // response 버퍼에 남아있는 데이터 삭제
        response.reset();

        // 첨부파일 정보 조회
        FileInfoVO fileInfoVO = fileRepo.selectFileInfo(fileId);

        if(fileInfoVO == null) {
            response.getOutputStream().close();
        }

        if(!FileWhiteList.IMAGE.mimeContains(fileInfoVO.getFilePtrn())) {
            response.getOutputStream().close();
        }

        // 파일 스트림 다운로드
        fileStreamDownload(fileInfoVO, response);
    }

    /**
     * 파일 스트림 다운로드
     * @param fileInfoVO
     * @param response
     * @throws Exception
     */
    private void fileStreamDownload(FileInfoVO fileInfoVO, HttpServletResponse response) throws Exception {

        // 줄바꿈 문자 제거 (서비스 취약점 점검 처리)
        String filePattern = fileInfoVO.getFilePtrn();
        filePattern = filePattern.replaceAll("\n", "");
        filePattern = filePattern.replaceAll("\r", "");

        // 파일정보 헤더 세팅
        response.setHeader("Content-Type", filePattern);
        response.setHeader("Content-Disposition", "attachment; filename=\"" + URLEncoder.encode(fileInfoVO.getFileNm(), "UTF-8") + "\"");
        response.setHeader("Content-Transfer-Encoding", "binary");
        response.setHeader("Pragma", "no-cache;");
        response.setHeader("Expires", "-1;");

        // 파일 다운로드 CORS 처리를 위한 헤더 세팅
        response.setHeader("Access-Control-Allow-Origin", "*");

        // 파일 스트림 다운로드
        fileUtil.fileDownload(fileInfoVO, response.getOutputStream());
        response.getOutputStream().close();
    }

    /**
     * 파일 논리 삭제
     */
    @Transactional
    public void deleteFile(String fileId) throws Exception {

        // 파일 아이디가 없는 경우
        if(!StringUtils.hasLength(fileId)) {
            throw new BizException(StatusCode.COM0005);
        }

        // 등록사용자 Id
        String amnnUserId = null;

        /*
         * 로그인 체크
         *      로그인 정보가 CustomUser.class 라면 jwt 로그인 조회
         *      로그인 상태가 아니라면 String 타입으로 떨이짐
         */
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof CustomUser) {
            CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            amnnUserId = user.getUsername();
        }

        fileRepo.deleteFileInfo(fileId);
    }

    /**
     * 파일 정보 조회
     * @param fileId
     * @return
     */
    public FileInfoVO searchFileInfo(String fileId) {
        return fileRepo.selectFileInfo(fileId);
    }
}