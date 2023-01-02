package com.ibk.sb.restapi.biz.service.adminqna.vo;

import com.ibk.sb.restapi.app.common.vo.PageVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Alias("RequestSearchAdminQnaVO")
public class RequestSearchAdminQnaVO extends PageVO {

    // 관리자 문의 정보 ID
    @ApiModelProperty(name = "admInquInfId", value = "관리자 문의 정보 ID")
    private String admInquInfId;

    // 관리자문의 문의유형 구분코드 ID
    @ApiModelProperty(name = "inquTypeId", value = "관리자문의 문의유형 구분코드 ID")
    private String inquTypeId;

    // 운영자포털 > 관리자문의 문의유형 구분코드 ID
    @ApiModelProperty(name = "inquTypeIds", value = "운영자포털 > 관리자문의 문의유형 구분코드 ID")
    private List<String> inquTypeIds;

    // 관리자문의 답변유형 구분코드 ID
    @ApiModelProperty(name = "inquSttId", value = "관리자문의 답변유형 구분코드 ID")
    private String inquSttId;

    /** 시스템 내부에서 사용 **/
    // 문의자 사용기관 ID
    @ApiModelProperty(hidden = true)
    private String inquUsisId;

    // 문의자 ID
    @ApiModelProperty(hidden = true)
    private String inquUserId;

}
