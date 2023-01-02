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
@Alias("AdminPriceVO")
@JsonIgnoreProperties({
        "delYn", "imgUrl", "amnnUserId", "amnnUserName", "amnnTs",
        "totalCnt", "rnum"
})
public class AdminPriceVO extends BaseTableVO {

    //이용기관 ID
    private String selrUsisId;

    //회원 타입 명
    private String mmbrtypeNm;

    //판매자 승인 명
    private String mmbrsttsNm;

    //회사명
    private String bplcNm;

    //대표자명
    private String rprsntvNm;

    //총 상품 수
    private String totalPdfCnt;

    //총 금액
    private String totalPrc;

    //승인 에이전시 상품 수
    private String agenArlCnt;

}
