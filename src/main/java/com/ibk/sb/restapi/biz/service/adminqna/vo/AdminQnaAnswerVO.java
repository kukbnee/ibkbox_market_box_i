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
@Alias("AdminQnaAnswerVO")
public class AdminQnaAnswerVO extends BaseTableVO {

    // 관리자 문의 정보 ID
    @ApiModelProperty(name = "admInquInfId", value = "관리자 문의 정보 ID")
    private String admInquInfId;

    // 답변 상태
    @ApiModelProperty(name = "inquSttId", value = "답변 상태")
    private String inquSttId;

    // 답변 내용
    @ApiModelProperty(name = "admCon", value = "답변 내용")
    private String admCon;

    // 첨부파일 라스트
    @ApiModelProperty(name = "AdminQnaAnswerFileVOList", value = "첨부파일 라스트")
    private List<AdminQnaFileVO> AdminQnaAnswerFileVOList;
}
