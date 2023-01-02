package com.ibk.sb.restapi.biz.service.qna.vo;

import com.ibk.sb.restapi.app.common.constant.ComCode;
import com.ibk.sb.restapi.app.common.vo.PageVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@Alias("RequestSearchQnaVO")
public class RequestSearchQnaVO extends PageVO {

    // 로그인 유저 이용기관 ID
    @ApiModelProperty(name = "utlinsttId", value = "로그인 유저 이용기관 ID")
    private String utlinsttId;

    // 로그인 유저 ID
    @ApiModelProperty(name = "loginUserId", value = "로그인 유저 ID")
    private String loginUserId;

    // 파일 유형 ID - 파일이미지
    @ApiModelProperty(hidden = true)
    private String filePtrnId = ComCode.GDS05001.getCode();

    /*
     * 검색 조건
     */
    // 문의 정보 ID
    @ApiModelProperty(name = "inqrInfoId", value = "문의 정보 ID")
    private String inqrInfoId;

    // 상품 정보 ID
    @ApiModelProperty(name = "pdfInfoId", value = "상품 정보 ID")
    private String pdfInfoId;

    // 보낸 문의 플래그
    //      sen : 보낸 문의 검색
    //      rec : 받은 문의 검색
    @ApiModelProperty(name = "sendFlg", value = "보낸 문의 플래그", example = "sen : 보낸 문의 검색, rec : 받은 문의 검색")
    private String sendFlg;

    // 상품명
    @ApiModelProperty(name = "pdfNm", value = "상품명")
    private String pdfNm;

    // 회사 명, 회사 대표 명
    @ApiModelProperty(name = "comCon", value = "회사 명, 회사 대표 명")
    private String comCon;

    // 시작일
    @ApiModelProperty(name = "stdt", value = "시작일")
    private String stdt;

    // 종료일
    @ApiModelProperty(name = "eddt", value = "종료일")
    private String eddt;

    // 보낸문의(sen), 받은문의(rec)
    @ApiModelProperty(name = "qnaSearchType", value = "보낸문의(sen), 받은문의(rec)")
    private String qnaSearchType;
}
