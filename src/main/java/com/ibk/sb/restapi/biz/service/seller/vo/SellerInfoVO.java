package com.ibk.sb.restapi.biz.service.seller.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ibk.sb.restapi.app.common.constant.ComCode;
import com.ibk.sb.restapi.app.common.vo.BaseTableVO;
import com.ibk.sb.restapi.biz.service.file.vo.FileInfoVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

import java.util.List;

/**
 * Name : 판매자 정보
 * Table : TB_BOX_MKT_SELR_INF_M
 */
@Getter
@Setter
@NoArgsConstructor
@Alias("SellerInfoVO")
@JsonIgnoreProperties({
        "usyn", "delyn", "rgsnUserId", "rgsnTs", "amnnUserId", "amnnTs",
        "totalCnt", "rnum"
})
public class SellerInfoVO extends BaseTableVO {

    // 판매자 이용기관 ID
    private String selrUsisId;

    // 판매자 ID
    private String selrUserId;

    // 회원타입 ID > 디폴트 개인회원
    private String mmbrtypeId = ComCode.PERSONAL_MEMBER.getCode();

    // 회원타입 명
    private String mmbrtypeName = ComCode.PERSONAL_MEMBER.getName();

    // 통신판매업 신고번호
    private String csbStmtno;

    // 회사 소개
    private String userCpCon;

    // 회원상태 ID
    private String mmbrsttsId = ComCode.SELLER_APPROVED.getCode();

    // 회원상태 명
    private String mmbrsttsName = ComCode.SELLER_APPROVED.getName();
}