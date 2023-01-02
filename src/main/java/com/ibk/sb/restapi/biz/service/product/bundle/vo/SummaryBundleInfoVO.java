package com.ibk.sb.restapi.biz.service.product.bundle.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ibk.sb.restapi.app.common.vo.BaseTableVO;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

import java.util.List;

@Getter
@Setter
@Alias("SummaryBundleInfoVO")
@JsonIgnoreProperties({
        "usyn", "delyn", "rgsnUserId", "rgsnTs", "amnnUserId", "amnnTs"
})
public class SummaryBundleInfoVO extends BaseTableVO {

    // 묶음 상품 번호 ID
    private String bunInfId;

    // 판매자 이용기관 ID
    private String selrUsisId;

    // 판매자 이용기관 ID
    private String selrUsisName;

    // 상품명
    private String pdfNm;

    // 상품 내용
    private String pdfCon;

    // 파일 경로
    private String filePath;

    // 조회 수
    private int viewCnt;

    // 상품 묶음상품 리스트
    List<SummaryBundleProductVO> items;
}
