package com.ibk.sb.restapi.biz.service.adminqna.vo;

import com.ibk.sb.restapi.app.common.vo.BaseTableVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Alias("AdminQnaVO")
public class AdminQnaVO extends BaseTableVO {

    // 관리자 문의 정보 ID
    @ApiModelProperty(name = "admInquInfId", value = "관리자 문의 정보 ID")
    private String admInquInfId;

    // 문의 이용기관 ID
    @ApiModelProperty(name = "inquUsisId", value = "문의 이용기관 ID")
    private String inquUsisId;

    // 문의 이용기관 명
    @ApiModelProperty(name = "bplcNm", value = "문의 이용기관 명")
    private String bplcNm;

    // 문의 사용자 ID
    @ApiModelProperty(name = "inquUserId", value = "문의 사용자 ID")
    private String inquUserId;


    // 관리자문의 문의유형 구분코드 ID
    @ApiModelProperty(name = "inquTypeId", value = "관리자문의 문의유형 구분코드 ID")
    private String inquTypeId;

    // 관리자문의 문의유형 구분코드 명
    @ApiModelProperty(name = "inquTypeName", value = "관리자문의 문의유형 구분코드 명")
    private String inquTypeName;


    // 관리자문의 처리유형 구분코드 ID
    @ApiModelProperty(name = "inquSttId", value = "관리자문의 처리유형 구분코드 ID")
    private String inquSttId;

    // 관리자문의 처리유형 구분코드 명
    @ApiModelProperty(name = "inquSttName", value = "관리자문의 처리유형 구분코드 명")
    private String inquSttName;


    // 제목
    @ApiModelProperty(name = "ttl", value = "제목")
    private String ttl;

    // 내용
    @ApiModelProperty(name = "con", value = "내용")
    private String con;

    // 파일 ID 리스트
    @ApiModelProperty(name = "adminQnaFileVOList", value = "파일 ID 리스트")
    private List<AdminQnaFileVO> adminQnaFileVOList;
}
