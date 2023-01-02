package com.ibk.sb.restapi.biz.service.popup.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ibk.sb.restapi.app.common.vo.BaseTableVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@NoArgsConstructor
@Alias("SummaryPopupVO")
@JsonIgnoreProperties({
        "usyn", "delyn", "rgsnUserId", "rgsnTs", "amnnUserId", "amnnTs",
        "totalCnt", "rnum"
})
public class SummaryPopupVO extends BaseTableVO {

    // 팝업 번호 ID
    private String popupInfId;

    // 제목
    private String ttl;

    // 링크
    private String link;

    // 시작일
    private String stdy;

    // 종료일
    private String fnda;

    // 파일 ID
    private String fileId;

    // 파일 경로
    private String filePath;
}
