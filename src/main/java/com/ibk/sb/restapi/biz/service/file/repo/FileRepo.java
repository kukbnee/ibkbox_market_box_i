package com.ibk.sb.restapi.biz.service.file.repo;

import com.ibk.sb.restapi.biz.service.file.vo.FileInfoVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface FileRepo {

    FileInfoVO selectFileInfo(@Param("fileId") String fileId);
//
//    List<FileInfoVO> selectFileInfoList(RequestFileInfoSearchVO requestFileInfoSearchVO);

    // 첨부파일 정보 등록
    Integer insertFileInfo(FileInfoVO fileInfoVO);

    // 첨부파일 정보 삭제
    Integer deleteFileInfo(@Param("fileId") String fileId);

    // 첨부파일 정보 수정
    Integer updateFileInfo(@Param("fileId") String fileId);
}
