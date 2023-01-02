package com.ibk.sb.restapi.biz.service.product.mypage.vo;

import com.ibk.sb.restapi.app.common.vo.BaseTableVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@NoArgsConstructor
@Alias("SummaryMyBundleProductInfoVO")
public class SummaryMyBundleProductInfoVO extends BaseTableVO {

    // 묶음 상품 번호 ID
    private String bunInfId;

    // 판매자 이용기관 ID
    private String selrUsisId;

    // 상품 명
    private String pdfNm;

    // 상품 내용
    private String pdfCon;

    // 메인 플래그
    private String mainYn;

    // 묶음 상품 갯수
    private String pdfCnt;

    // 카테고리 명
    private String pdfCtgyName;
}
