package com.ibk.sb.restapi.biz.service.admin.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ibk.sb.restapi.app.common.vo.BaseTableVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@NoArgsConstructor
@Alias("AdminSellerVO")
@JsonIgnoreProperties({
        "delYn", "imgUrl", "amnnUserId", "amnnUserName", "amnnTs",
        "totalCnt", "rnum"
})
public class AdminSellerVO extends BaseTableVO {

    private String selrUsisId;      // 회사(이용기관) ID
    private String bplcNm;          // 회사명
    private String rprsntvNm;       // 회사 대표자명
    private String mmbrtypeId;      // 회사유형코드
    private String mmbrtypeNm;      // 회사유형명
    private String mmbrsttsId;      // 회사 상태 코드
    private String mmbrsttsNm;      // 회사 상태명
    private String prdtCnt;         // 판매상품 (수)
    private String agtPrdtCnt;      // 에이전시 판매상품 (수)
    private String avgDate;         // 평균답변시간
}
