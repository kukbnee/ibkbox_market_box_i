package com.ibk.sb.restapi.biz.service.patent.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ibk.sb.restapi.app.common.vo.BaseTableVO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

/**
 * Name : 상품 특허 정보
 * Table : TB_BOX_MKT_PDF_PAT_R
 */
@Getter
@Setter
@Alias("PatentVO")
@Builder
@JsonIgnoreProperties({
        "usyn", "delyn", "rgsnUserId", "rgsnTs", "amnnUserId", "amnnTs",
        "totalCnt", "rnum"
})
public class PatentVO extends BaseTableVO {

    // 상품 정보
    private String pdfInfoId;

    // 정보 순번
    private String infoSqn;

    // 특허 명
    private String ptntNm;

    // 특허 IPC
    private String ptntIpc;

    // 특허 상태
    private String ptntStts;

    // 특허 출원 번호
    private String ptntAlfrNo;

    // 특허 출원 일시
    private String ptntAlfrTs;

    // 특허 등록번호
    private String ptntRtn;

    // 특허 등록일자
    private String ptntTs;

    // 특허 출원인
    private String ptntRlocaus;

    // 프론트 표시용 체크
    private String checked = "N";
}
