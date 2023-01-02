package com.ibk.sb.restapi.biz.service.user.vo;

import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

import java.sql.Timestamp;

@Getter
@Setter
@Alias("UserHeaderVO")
public class UserHeaderVO {

    // 로그인 유저 ID
    private String userId;

    // 로그인 유저 명
    private String userNm;

    // 이용기관 ID
    private String utlinsttId;

    // 이용기관 명
    private String bplcNm;

    // 회원 타입 ID
    private String mmbrtypeId;

    // 회원상태 ID
    private String mmbrsttsId;

    // 새로운 알람 유무
    private String alarmCnt;

    // 장바구니 수
    private int basketCnt;

    // 통신판매업신고번호
    private String csbStmtno;

    /*
     * 인감정보
     */
    // 인감 이미지 파일 ID
    private String rgslImgFileId;

    // 인감 이미지 URL
    private String rgslImgFileUrl;

    // 인감 이미지 수정일
    private Timestamp rgslImgAmnnTs;

}
