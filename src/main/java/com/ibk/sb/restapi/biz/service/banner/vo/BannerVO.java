package com.ibk.sb.restapi.biz.service.banner.vo;

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
@Alias("BannerVO")
public class BannerVO extends BaseTableVO {

    // 배너 번호 ID
    @ApiModelProperty(name = "banInfId", value = "배너 번호 ID")
    private String banInfId;

    // 배너 배너유형 구분코드 ID
    @ApiModelProperty(name = "banTypeId", value = "배너 배너유형 구분코드 ID")
    private String banTypeId;

    // 제목
    @ApiModelProperty(name = "ttl", value = "제목")
    private String ttl;

    // 내용
    @ApiModelProperty(name = "con", value = "내용")
    private String con;

    // 링크
    @ApiModelProperty(name = "link", value = "링크")
    private String link;

    // 시작일
    @ApiModelProperty(name = "stdy", value = "시작일")
    private String stdy;

    // 종료일
    @ApiModelProperty(name = "fnda", value = "종료일")
    private String fnda;

    // 공개 여부
    @ApiModelProperty(name = "oppbYn", value = "공개 여부")
    private String oppbYn;

    //  상태
    @ApiModelProperty(name = "status", value = "상태")
    private String status;

    // 이미지 파일 id
    @ApiModelProperty(name = "fileId", value = "이미지 파일 id")
    private String fileId;
    @ApiModelProperty(name = "imgFileId", value = "이미지 파일 id")
    private String imgFileId;

    // 이미지 파일 정보
    @ApiModelProperty(name = "imgFileInfo", value = "이미지 파일 정보")
    private FileInfoVO imgFileInfo;

}
