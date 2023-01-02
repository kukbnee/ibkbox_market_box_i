package com.ibk.sb.restapi.app.common.util;

import com.ibk.sb.restapi.app.common.constant.FileWhiteList;
import com.ibk.sb.restapi.app.common.constant.StatusCode;
import com.ibk.sb.restapi.app.common.vo.BaseTableVO;
import com.ibk.sb.restapi.biz.service.file.vo.FileInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
@PropertySource("classpath:application.properties")
public class FileUtil {

    private static String imgUploadPath;
    private static String docUploadPath;
    private static String imgContentPath;
    private static String mainboxImgContentPath;

    @Value("${com.ibk.api.upload.img.path}")
    public void setImgUploadPath(String path) { imgUploadPath = path; }

    @Value("${com.ibk.api.upload.doc.path}")
    public void setDocUploadPath(String path) { docUploadPath = path; }

    @Value("${com.ibk.content.img.path}")
    public void setImgContentPath(String path) { imgContentPath = path; }

    @Value("${com.ibk.mainbox.content.img.path}")
    public void setMainboxImgContentPath(String path) { mainboxImgContentPath = path; }

    // 이미지 경로 설정
    public static String setImageUrl(String imgFileId) {
        return StringUtils.hasLength(imgFileId) ? imgContentPath + imgFileId : "";
    }

    // 리스트 이미지 경로 설정
    public List setImageUrlList(List<? extends BaseTableVO> list) throws Exception {

        list = list == null ? new ArrayList<>() : list;
        list.forEach(x -> {
            x.setImgUrl(StringUtils.hasLength(x.getImgFileId())
                    ? imgContentPath + x.getImgFileId()
                    : "");
        });

        return list;
    }

    // 메인 박스 기업 로고 이미지 경로 설정
    public String setMainboxLogoUrl(String logoFileId) throws Exception {
        // MNB의 파일 로고는 디폴트가 ' '임
        // -> null이 뜨는 경우가 생기므로 hasLength 체크 후 trim처리
        if(StringUtils.hasLength(logoFileId)) {
            logoFileId = logoFileId.trim();
        } else {
            logoFileId = "";
        }
        return StringUtils.hasLength(logoFileId) ? mainboxImgContentPath + logoFileId : "";
    }

    /**
     * 파일 저장
     * -> 우선 파일 저장 확인을 위해 multipart 만을 받도록 세팅
     * @return
     * @throws Exception
     */
    public FileInfoVO saveFile(MultipartFile multipartFile) throws Exception {

        FileInfoVO fileInfoVO;

        // 파일정보 추출
        String originalFileName = multipartFile.getOriginalFilename();
        String fileName = originalFileName.substring(originalFileName.lastIndexOf("\\") + 1); // IE, Edge 브라우저 환경에서는 전체 경로가 들어옴

        // 확장자 | mime type
        String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1);
        String mime = multipartFile.getContentType();

        // 파일 헤더정보 파일명에 시큐어 코딩 점검 관련 경로조작 값 체크
        fileName = fileName.substring(0, fileName.lastIndexOf("."));
        fileName = fileName.replace("/", "")
                .replace("\\\\", "")
                .replace(".", "")
                .replace("&", "");

        fileName = fileName + "." + fileExt;

        String path;

        /** 확장자 화이트리스트 체크 **/
        // 이미지 파일 체크
        if(mime.startsWith("image") && FileWhiteList.IMAGE.mimeContains(mime)) {
            path = imgUploadPath;
        }

        // DOC 파일 체크
        else if(FileWhiteList.DOC.mimeContains(mime)) {
            path = docUploadPath;
        }

        // octet-type 으로 오는 경우의 hwp 파일 체크
        else if(fileExt.equals("hwp") && FileWhiteList.DOC.extensionContains(fileExt)) {
            path = docUploadPath;
        }

        else {
            throw new BizException(StatusCode.MNB0002);
        }

        String fileId = UUID.randomUUID().toString();

        // 현재는 uploadPath/날짜/아이디 로
        String folderPath = makeFolder(path, fileId);

        // 저장 파일명 처리
        String savePath = folderPath + File.separator + fileName;

        Path saveFullPath = Paths.get(path + File.separator + savePath);

        // 파일저장 처리 및 fileInfoVO 정보 세팅
        // 유저정보 및 DB 저장은 Service단에서 마저 처리
        try {
            multipartFile.transferTo(saveFullPath);

            fileInfoVO = FileInfoVO.builder()
                    .fileId(fileId)
                    .fileNm(fileName)
                    .filePath(savePath)
                    .filePtrn(multipartFile.getContentType())
                    .fileEtns(fileExt)
                    .fileSize(multipartFile.getSize())
                    .build();

        } catch (IOException ioe) {
            throw new BizException(StatusCode.MNB0002, ioe.getMessage());
        }

        return fileInfoVO;
    }

    /**
     * 파일 다운로드 (스트림)
     *
     * @param fileInfoVO
     * @param os
     * @throws Exception
     */
    public void fileDownload(FileInfoVO fileInfoVO, OutputStream os) throws Exception {

        FileInputStream fis = null;

        String fileFullPath;

        if(fileInfoVO.getFilePtrn().startsWith("image")) {
            fileFullPath = imgUploadPath + File.separator + fileInfoVO.getFilePath();
        } else {
            fileFullPath = docUploadPath + File.separator + fileInfoVO.getFilePath();
        }

        try {
            fis = new FileInputStream(fileFullPath);
            byte[] data = new byte[8096]; //버퍼 크기 설정
            int len = -1;
            while ((len = fis.read(data)) != -1) {
                os.write(data, 0, len);
            } // 파일이 남아 있면 읽어서(read) data에 저장(write)

        } catch (Exception e) {
            throw new BizException(StatusCode.COM0000, e.getMessage());
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException ioe) {
                    throw new BizException(StatusCode.COM0000, ioe.getMessage());
                }
            }
            if (os != null) {
                try {
                    os.close();
                } catch (IOException oe) {
                    throw new BizException(StatusCode.COM0000, oe.getMessage());
                }
            }
        }
    }

    /**
     * 파일 to 멀티파트파일 컨버트
     * @param fileInfoVO
     * @return
     * @throws Exception
     */
    public MultipartFile convertFileToMultipartFile(FileInfoVO fileInfoVO) throws Exception {

        MultipartFile mFile = null;
        InputStream input = null;
        OutputStream os = null;


        String fileFullPath;

        if(fileInfoVO.getFilePtrn().startsWith("image")) {
            fileFullPath = imgUploadPath + File.separator + fileInfoVO.getFilePath();
        } else {
            fileFullPath = docUploadPath + File.separator + fileInfoVO.getFilePath();
        }

        try {
            File file = new File(fileFullPath);

            // 파일이 존재하지 않는 경우
            if(file == null) {
                file.delete();
                return null;
            }

            FileItem fileItem = new DiskFileItem(fileInfoVO.getFileNm(), Files.probeContentType(file.toPath()), false, file.getName(), (int) file.length(), file.getParentFile());

            input = new FileInputStream(file);
            os = fileItem.getOutputStream();
            IOUtils.copy(input, os);
            mFile = new CommonsMultipartFile(fileItem);

            input.close();
            os.close();

        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BizException(StatusCode.COM0000, e.getMessage());
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException ioe) {
                    throw new BizException(StatusCode.COM0000, ioe.getMessage());
                }
            }
            if (os != null) {
                try {
                    os.close();
                } catch (IOException oe) {
                    throw new BizException(StatusCode.COM0000, oe.getMessage());
                }
            }
        }

        return mFile;
    }

    /**
     * filePath base64 인코딩 메서드
     * @param fileInfoVO
     * @return
     * @throws Exception
     */
    public String imageFileEncodeBase64(FileInfoVO fileInfoVO) throws Exception {

        if(!FileWhiteList.IMAGE.mimeContains(fileInfoVO.getFilePtrn())) {
            return null;
        }

        String fullPath = imgUploadPath + File.separator + fileInfoVO.getFilePath();
        fullPath = fullPath.replace(File.separator, "/");

        Path path = Paths.get(fullPath);

        // 파일 읽기
        Resource resource = new UrlResource(path.toUri());
        File file = ResourceUtils.getFile(resource.getURI().toString());

        // 인코딩
        String encodedPath = Base64.getEncoder().encodeToString(Files.readAllBytes(file.toPath()));

        encodedPath = "data:" + fileInfoVO.getFilePtrn() + ";base64, " + encodedPath;

        return encodedPath;
    }

    /**
     * 폴더경로 생성
     * TODO : 경로 기준 협의 및 세팅 필요
     * @param uuid
     * @return
     */
    private String makeFolder(String path, String uuid) {
        String folderPath = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        folderPath = folderPath + File.separator + uuid;

        // @Value 경로
        File uploadPathFolder = new File(path, folderPath);

        //기존 경로 같은 폴더 파일이 없을 때만 mkdirs()로 위 폴더들을 생성
        if (!uploadPathFolder.exists()) {
            uploadPathFolder.mkdirs();
        }

        return folderPath;
    }

    /**
     * 파일 패스 작성
     * @param fileInfoVO
     * @return
     * @throws Exception
     */
    public String getFileFullPath(FileInfoVO fileInfoVO) throws Exception {

        String fileFullPath;

        if(fileInfoVO.getFilePtrn().startsWith("image")) {
            fileFullPath = imgUploadPath + File.separator + fileInfoVO.getFilePath();
        } else {
            fileFullPath = docUploadPath + File.separator + fileInfoVO.getFilePath();
        }

        return fileFullPath;
    }
}
