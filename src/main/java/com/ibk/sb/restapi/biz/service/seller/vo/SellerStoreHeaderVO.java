package com.ibk.sb.restapi.biz.service.seller.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;
import org.springframework.util.StringUtils;

/**
 * 판매자 헤더 정보
 */
@Getter
@Setter
@NoArgsConstructor
@Alias("SellerStoreHeaderVO")
public class SellerStoreHeaderVO {

    // 이용기관 ID
    private String utlinsttId;

    // 이용기관 명
    private String bplcNm;

    // 설립연차
    private String yearCnt;

    // 설립일자
    private String fondDe;

    // 이용기관 분류 구분(대기업, 중소기업)
    private String useEntrprsSe;

    // 연매출
    private String imxprtSctnText;

    // 회사 소개
    private String userCpCon;

    /*
     * 이용기관 분류 구분 셋팅
     *      0001 : 대기업
     *      0002 or etc : 즁소기업
     */
    public void setUseEntrprsSe(String useEntrprsSe) {
        if(!StringUtils.hasLength(useEntrprsSe)) {
            this.useEntrprsSe = "대기업";
            return;
        }

        if(useEntrprsSe.equals("0001")) {
            this.useEntrprsSe = "대기업";
        } else {
            this.useEntrprsSe = "중소기업";
        }
    }
}
