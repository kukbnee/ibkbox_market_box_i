package com.ibk.sb.restapi.biz.service.admin.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ibk.sb.restapi.app.common.vo.BaseTableVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Alias("AdminOrderVO")
@JsonIgnoreProperties({
        "delYn", "imgUrl", "amnnUserId", "amnnUserName", "amnnTs",
        "totalCnt", "rnum"
})
public class AdminOrderVO extends BaseTableVO {

    /*주문정보(1)*/
    private String ordnInfoId; //주문번호
    private String cnttNoId; //체결번호
    private Timestamp orderRgsnTs; //주문일시

    private String pucsUsisId; //구매자 이용기관 ID
    private String pucsBplcNm; //구매자 회사명
    private String pucsRprsntvNm; //구매자 대표자명
    private String pucsMmbrtypeId; //구매자 회원타입 ID
    private String pucsMmbrtypeNm; //구매자 회원타입 명

    private String selrUsisId; //판매자 이용기관 ID
    private String selrBplcNm; //판매자 회사명
    private String selrRprsntvNm; //판매자 대표자명
    private String selrMmbrtypeId; //판매자 회원타입 ID
    private String selrMmbrtypeNm; //판매자 회원타입 명

    private String stlmInfoId; //결제정보 ID
    private String stlmptrnId; //결제타입 코드
    private String stlmptrnNm; //결제타입 명
    private String stlmsttsId; //결제상태 코드
    private String stlmsttsNm; //결제상태 명
    private String payAmt; //결제금액
    private String stlmRsltId; //결제 연계번호 ID

    /*주문상품정보(n)*/
    List<AdminOrderProductVO> products;

}
