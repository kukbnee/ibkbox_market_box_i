package com.ibk.sb.restapi.biz.service.seller.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

/**
 * 판매자 카테고리 정보
 */
@Getter
@Setter
@NoArgsConstructor
@Alias("SellerCategoryVO")
public class SellerCategoryVO {

    // 1차 분류 Id
    private String oneCtgyId;

    // 2차 분류 Id
    private String twoCtgyId;

    // 3차 분류 Id
    private String thrCtgyId;

    // 4차 분류 Id
    private String forCtgyId;

    // 상품 카테고리 명
    private String pdfCtgyName;

    // 카테고리 별 상품 갯수
    private String pdfCnt;

    // 프론트 체크박스 표시용 파라메터
    private String checked = "N";
}
