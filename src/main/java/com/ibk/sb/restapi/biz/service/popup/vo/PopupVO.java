package com.ibk.sb.restapi.biz.service.popup.vo;

import com.ibk.sb.restapi.app.common.vo.BaseTableVO;
import com.ibk.sb.restapi.biz.service.file.vo.FileInfoVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@NoArgsConstructor
@Alias("PopupVO")
public class PopupVO extends BaseTableVO {

    // 팝업 번호 ID
    @ApiModelProperty(name = "popupInfId", value = "팝업 번호 ID")
    private String popupInfId;

    // 제목
    @ApiModelProperty(name = "ttl", value = "제목")
    private String ttl;

    // 링크
    @ApiModelProperty(name = "link", value = "링크")
    private String link;

    // 시작일
    @ApiModelProperty(name = "stdy", value = "시작일")
    private String stdy;

    // 종료일
    @ApiModelProperty(name = "fnda", value = "종료일")
    private String fnda;

    // 상태
    @ApiModelProperty(name = "status", value = "상태")
    private String status;

    // 파일 ID
    @ApiModelProperty(name = "fileId", value = "파일 ID")
    private String fileId;
    @ApiModelProperty(name = "imgFileId", value = "파일 ID")
    private String imgFileId;

    // 이미지 파일 정보
    @ApiModelProperty(name = "imgFileInfo", value = "이미지 파일 정보")
    private FileInfoVO imgFileInfo;

}
