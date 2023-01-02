package com.ibk.sb.restapi.biz.service.event.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ibk.sb.restapi.app.common.vo.BaseTableVO;
import com.ibk.sb.restapi.biz.service.file.vo.FileInfoVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

import java.sql.Timestamp;
import java.util.List;

/**
 * 리스트 조회용 이벤트 VO
 */
@Getter
@Setter
@NoArgsConstructor
@Alias("SummaryEventVO")
@JsonIgnoreProperties({
        "usyn", "delyn", "rgsnUserId", "rgsnTs", "amnnUserId", "amnnTs",
        "totalCnt", "rnum", "rgsnUserName", "amnnUserName"
})
public class SummaryEventVO extends BaseTableVO {

    // 이벤트 정보 ID
    private String evntInfId;

    // 이벤트 제목
    private String evntTtl;

    // 이벤트 설명
    private String evntCon;

    // 모집 시작일
    private Timestamp enlsSldyTs;

    // 모집 마감일
    private Timestamp enlsCldyTs;

    // 이벤트 시작일
    private Timestamp evntStdyTs;

    // 이벤트 종료일
    private Timestamp evntFndaTs;

    // 이벤트 상태
    private String pgstId;

    // 이벤트 남은일
    private String days;

    // 이벤트 모집일 남은 일수
    private String enlsDays;

    // 파일 ID
    private String fileId;

    private String imgFileId;

    // 이벤트 상태명
    private String comCdNm;

    // 이벤트 참여가능 여부
    private String evntUsedState;

    // 이벤트 선정 여부(선정 상품이 1개 이상이면, 이벤트는 선정)
    private String evntPickedState;

    // 이벤트 신청일
    private Timestamp receiptDate;

    // 이벤트 상품 리스트
    private List<SummaryEventProductVO> items;

    // 이미지 파일 정보
    private FileInfoVO imgFileInfo;

    // 참여 일자(상품 신청 일자)
    private String reqDate;

    // 이벤트 총 금액
    private String totalPrc;
}
