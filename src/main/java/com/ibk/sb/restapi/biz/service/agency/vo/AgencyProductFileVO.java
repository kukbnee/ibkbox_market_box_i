package com.ibk.sb.restapi.biz.service.agency.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

import java.sql.Timestamp;

/**
 * 첨부파일 정보 VO
***/
@Getter
@Setter
@NoArgsConstructor
@Alias("AgencyProductFileVO")
public class AgencyProductFileVO {

    //파일ID
    private String fileId;

    // 신규 파일정보 ID
    private String newFileId;

    //파일명
    private String fileNm;

    //파일 경로
    private String filePath;

    //파일 확장자
    private String fileEtns;

    //파일 유형
    private String filePtrn;

    //파일 크기
    private String fileSize;

    //삭제 여부
    private String delYn;

    //등록 사용자ID
    private String rgsnUserId;

    //등록일시
    private Timestamp rgsnTs;

}
