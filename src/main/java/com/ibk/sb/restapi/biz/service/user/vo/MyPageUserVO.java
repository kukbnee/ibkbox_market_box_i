package com.ibk.sb.restapi.biz.service.user.vo;

import com.ibk.sb.restapi.app.common.vo.BaseTableVO;
import com.ibk.sb.restapi.biz.service.seal.vo.SealVo;
import com.ibk.sb.restapi.biz.service.seller.vo.SellerFileVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@Alias("MyPageUserVO")
public class MyPageUserVO extends BaseTableVO {

    /*
     * 기본 정보
     */
    // 메인BOX 사용자의ID
    private String userId;

    // 사용자대표이메일
    private String email;

    // 메인박스사용자명
    private String userNm;

    // 사용자휴대전화번호
    private String moblphonNo;

    /*
     * 회원구분
     */
    // 회원타입 ID
    private String mmbrtypeId;

    // 회원타입 명
    private String mmbrtypeName;

    // 회원상태 ID
    private String mmbrsttsId;

    // 회원상태 명
    private String mmbrsttsName;

    /*
     * 판매자 정보
     */
    // 이용기관(회사) ID
    private String utlinsttId;

    // 이용기관의 사업자명
    private String bplcNm;

    // 이용기관 대표자명
    private String rprsntvNm;

    // 이용기관 대표 전화번호
    private String reprsntTelno;

    // 이용기관 우편번호
    private String postNo;

    // 이용기관 소재 주소
    private String adres;

    // 이용기관 소재 상세주소
    private String detailAdres;

    // 이용기관 소재 도로명주소
    private String nwAdres;

    // 이용기관 소재 도로명주소 상세
    private String nwAdresDetail;

    // 통신판매업신고번호
    private String csbStmtno;

    // 사업자를 식별하는 고유번호(사업자등록번호)
    private String bizrno;

    /*
     * 판매자 이미지 정보
     * BaseTableVO 참고
     */
//    private String imgFileId;
//    private String imgUrl;
    // 판매자 이미지 명
    private String imgName;

    // 회사 소개
    private String userCpCon;

    /*
     * 판매자 홈화면 > 배너이미지
     */
    private List<SellerFileVO> bannerInfoList;

    /*
     * 명함
     */
    // 이용기관 홈페이지 주소
    private String hmpgAdres;

    // 이용기관의 사업자명
    // private String bplcNm;

    // 권한 명
    private String authorCodeNm;

    // 메인박스사용자명
    // private String userNm;

    // 사용자휴대전화번호
    //private String moblphonNo;

    // 이용기관 대표팩스번호
    private String reprsntFxnum;

    // 사용자대표이메일
    // private String email;

    // 이용기관 소재 주소
    // private String adres;

    // 이용기관 소재 상세주소
    // private String detailAdres;

    // 이용기관 소재 도로명주소
    // private String nwAdres;

    // 이용기관 소재 도로명주소 상세
    // private String nwAdresDetail;

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
