package com.ibk.sb.restapi.biz.service.admin.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ibk.sb.restapi.app.common.vo.BaseTableVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@Alias("AdminEventVO")
@JsonIgnoreProperties({
        "delYn", "imgUrl", "rgsnTs", "rgsnUserId", "rgsnUserName", "amnnUserId", "amnnUserName", "amnnTs",
        "totalCnt", "rnum", "rvsRnum", "usyn", "delyn"
})
public class AdminEventVO extends BaseTableVO {

    @ApiModelProperty(name = "evntInfId", value = "이벤트 정보 ID", example = "cb00de32-5d38-42ec-b7f5-7cb0d970b113")
    private String evntInfId;

    @ApiModelProperty(name = "fileId", value = "파일 ID")
    private String fileId;

    @ApiModelProperty(name = "evntTtl", value = "이벤트 제목", example = "신년 스토어 이벤트")
    private String evntTtl;

    @ApiModelProperty(name = "evntCon", value = "이벤트 내용", example = "이벤트 모집 중 입니다.")
    private String evntCon;

    @ApiModelProperty(name = "enlsSldyTs", value = "모집 시작일", example = "2022-07-01")
    private Timestamp enlsSldyTs;

    @ApiModelProperty(name = "enlsCldyTs", value = "모집 마감일", example = "2022-07-10")
    private Timestamp enlsCldyTs;

    @ApiModelProperty(name = "evntStdyTs", value = "이벤트 시작일", example = "2022-07-15")
    private Timestamp evntStdyTs;

    @ApiModelProperty(name = "evntFndaTs", value = "이벤트 종료일", example = "2022-07-31")
    private Timestamp evntFndaTs;

    @ApiModelProperty(name = "useYn", value = "사용 여부", example = "Y")
    private String useYn;

    @ApiModelProperty(name = "pgstId", value = "이벤트 상태", example = "ETS00002")
    private String pgstId;

    @ApiModelProperty(name = "rgsnUserId", value = "등록 사용자 ID", example = "test99")
    private String rgsnUserId;

    @ApiModelProperty(name = "amnnUserId", value = "수정 사용자 ID", example = "test99")
    private String amnnUserId;

}
