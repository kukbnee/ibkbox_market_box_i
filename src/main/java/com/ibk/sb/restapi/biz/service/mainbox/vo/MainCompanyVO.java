package com.ibk.sb.restapi.biz.service.mainbox.vo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

import java.sql.Date;

@Getter
@Setter
public class MainCompanyVO {

    /** 추후 필요한 항목은 추가 */

    // 이용기관 아이디
    private String utlinsttId;

    // 사업자번호
    private String bizrno;

    // 법인번호
    private String jurirno;

    // 이용기관명
    private String bplcNm;

    // 이용기관 대표명
    private String rprsntvNm;

    // 대표 영문명
    private String rprsntvEngNm;

    // 이용기관 대표 전화번호
    private String reprsntTelno;

    // 이용기관 대표이메일
    private String reprsntEmail;

    // 이용기관 대표팩스번호
    private String reprsntFxnum;

    // 이용기관 홈페이지 주소
    private String hmpgAdres;

    // 이용기관의 설립일자
    private String fondDe;

    // 이용기관의 매출금액
    private Long salamt;

    // 활성화 여부
    private String actAt;

    /** 이용기관 주소 */
    // 이용기관 소재 우편번호
    private String postNo;

    // 신주소 사용여부
    private String nwAdresAt;

    // 구주소
    private String adres;

    // 구주소 상세
    private String detailAdres;

    // 신주소
    private String nwAdres;

    // 신주소 상세
    private String nwAdresDetail;

    // 이용기관 분류 구분(대기업, 중소기업)
    private String useEntrprsSe;

    // 연매출
    private String imxprtSctnText;

    // 신주소 전체 정보
//    private String nwAdrAllNm;

    // 구주소 전체 정보
//    private String guAdrAllNm;

    /** 이용기관 로고 파일 정보 */
    // 이용기관 로고파일
    private String cmpnyLogoImageFile;
    private String cmpnyLogoBassImageFile;

    // 이용기관 등록일
    private Date registDt;

    // 이용기관 등록 사용자 ID
    private String registId;

    // 이용기관 수정일
    private Date updtDt;

    /** 실제 사용 로고 파일 설정 */
    private String logoImageFile;

    /** 판매자 상품 판매 총 갯수 */
    private int sellerProductTotalCnt;
}
