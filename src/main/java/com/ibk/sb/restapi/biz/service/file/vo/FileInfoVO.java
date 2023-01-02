package com.ibk.sb.restapi.biz.service.file.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ibk.sb.restapi.app.common.vo.BaseTableVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@Alias("FileInfoVO")
@JsonIgnoreProperties({
        "usyn", "delyn", "rgsnUserId", "rgsnTs", "amnnUserId", "amnnTs",
        "totalCnt", "rnum"
})
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileInfoVO extends BaseTableVO {

    // 파일 ID
    @ApiModelProperty(name = "fileId", value = "파일 ID")
    private String fileId;

    // 파일명
    @ApiModelProperty(name = "fileNm", value = "파일명")
    private String fileNm;

    // 파일 경로
    @ApiModelProperty(name = "filePath", value = "파일 경로")
    private String filePath;

    // 파일 확장자
    @ApiModelProperty(name = "fileEtns", value = "파일 확장자")
    private String fileEtns;

    // 파일 유형
    @ApiModelProperty(name = "filePtrn", value = "파일 유형")
    private String filePtrn;

    // 파일 크기
    @ApiModelProperty(name = "fileSize", value = "파일 크기")
    private Long fileSize;
}
