package com.ibk.sb.restapi.biz.service.mainbox.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MainUserVO {

    /** 추후 필요한 항목은 추가 */
    // 회원 ID
    private String userId = "";

    // 회원 명
    private String userNm = "";

    // 회원 타입
    private String mmbrtypeId = "";

    // 회원 상태
    private String mmbrsttsId = "";

    // 이메일
    private String email = "";

    //사용자휴대전화번호
    private String moblphonNo = "";


    /** 권한 정보 */

    // 권한 코드
    private String authorCode = "";
    // 권한 명
    private String authorCodeNm = "";

    /** 함께 받아오는 기업 정보 -> UserDetail에 필요한 부분 세팅 */

    // 이용기관 아이디
    private String utlinsttId = "";

    // 사업자번호
    private String bizrno = "";

    // 이용기관명
    private String bplcNm = "";

    // 이용기관 로고파일
    private String cmpnyLogoImageFile = "";
    private String cmpnyLogoBassImageFile = "";

}
