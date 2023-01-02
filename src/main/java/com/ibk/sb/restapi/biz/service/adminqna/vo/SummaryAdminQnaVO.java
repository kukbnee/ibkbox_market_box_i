package com.ibk.sb.restapi.biz.service.adminqna.vo;

import com.ibk.sb.restapi.app.common.vo.BaseTableVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@Alias("SummaryAdminQnaVO")
public class SummaryAdminQnaVO extends BaseTableVO {

    // 관리자 문의 정보 ID
    private String admInquInfId;

    // 관리자문의 문의유형 구분코드 ID
    private String inquTypeId;

    // 관리자문의 문의유형 구분코드 명
    private String inquTypeName;

    // 관리자문의 처리유형 구분코드 ID
    private String inquSttId;

    // 관리자문의 처리유형 구분코드 명
    private String inquSttName;

    // 제목
    private String ttl;

    // 이용기관 회사명
    private String bplcNm;

    // 등록일시
    private Timestamp rgsnTs;
}
