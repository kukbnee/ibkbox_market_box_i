package com.ibk.sb.restapi.biz.service.seal.vo;

import com.ibk.sb.restapi.app.common.vo.BaseTableVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@NoArgsConstructor
@Alias("SealVo")
public class SealVo extends BaseTableVO {

    // 이용기관(회사) ID
    @ApiModelProperty(name = "utlinsttId", value = "이용기관(회사) ID")
    private String utlinsttId;

    // 인감 이미지 파일 ID
    @ApiModelProperty(name = "rgslImgFileId", value = "인감 이미지 파일 ID")
    private String rgslImgFileId;

    // 인감 이미지 base64
    @ApiModelProperty(name = "rgslImgFileId", value = "인감 이미지 파일 ID")
    private String signBase64File;

}
