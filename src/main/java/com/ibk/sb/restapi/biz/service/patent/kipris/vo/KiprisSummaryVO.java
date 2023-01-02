package com.ibk.sb.restapi.biz.service.patent.kipris.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ibk.sb.restapi.app.common.vo.BaseTableVO;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties({
        "delYn", "imgUrl", "rgsnUserId", "rgsnTs", "amnnUserId", "amnnTs",
        "totalCnt", "rnum", "rvsRnum"
})
public class KiprisSummaryVO extends BaseTableVO {

    // 발명명
    private String inventionName;

    // IPC 번호
    private String ipcNumber;

    // 등록상태
    private String registerStatus;

    // 출원번호
    private String applicationNumber;

    // 출원일자
    private String applicationDate;

    // 등록번호
    private String registrationNumber;

    // 등록일자
    private String registrationDate;

    // 출원인
    private String applicantName;
}
