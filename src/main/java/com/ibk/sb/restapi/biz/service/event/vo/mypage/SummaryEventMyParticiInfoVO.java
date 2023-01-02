package com.ibk.sb.restapi.biz.service.event.vo.mypage;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ibk.sb.restapi.app.common.vo.BaseTableVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

import java.sql.Timestamp;

/**
 * 마이페이지 > 기업 참여 이벤트상세정보 리스트 조회용 이벤트 VO
 */
@Getter
@Setter
@NoArgsConstructor
@Alias("SummaryEventMyParticiInfoVO")
@JsonIgnoreProperties({
        "usyn", "delyn", "rgsnUserId", "rgsnTs", "amnnUserId", "amnnTs",
        "totalCnt", "rnum"
})
public class SummaryEventMyParticiInfoVO extends BaseTableVO {

    // 이벤트 정보 ID
    private String evntInfId;

    // 이벤트 제목
    private String evntTtl;

    // 이벤트 설명
    private String evntCon;

    // 이벤트 시작일
    private Timestamp evntStdyTs;

    // 이벤트 종료일
    private Timestamp evntFndaTs;

    // 이벤트 상태
    private String pgstId;

    // 이벤트 파일명
    private String fileNm;

    // 이벤트 파일경로
    private String filePath;

    // 이벤트 남은 일수
    private String days;

    // 이벤트 모집일 남은 일수
    private String enlsDays;

    // 종합상태 코드
    private String stateCode;
}
