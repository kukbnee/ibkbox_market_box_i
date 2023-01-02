package com.ibk.sb.restapi.app.common.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

import java.sql.Timestamp;

@Getter
@Setter
@Alias("BaseVO")
public class BaseTableVO {

    /** 테이블 공통 필드 **/

    // 삭제여부
    private String delyn;

    // 사용여부
    private String usyn;

    // 등록 사용자 ID
    private String rgsnUserId;

    // 등록 사용자 명
    private String rgsnUserName;

    // 등록 일시
    private Timestamp rgsnTs;

    // 수정 사용자 ID
    private String amnnUserId;

    // 수정 사용자 명
    private String amnnUserName;

    // 수정 일시
    private Timestamp amnnTs;


    /** 페이징 조회시 필요한 필드 **/
    // 총 레코드 수
    private Integer totalCnt = 0;

    // 로우넘
    private Integer rnum;

    // 역순 로우넘
    private Integer rvsRnum;

    /** 이미지가 있는 경우 경로 필드 **/
    private String imgFileId;
    private String imgUrl;
}
