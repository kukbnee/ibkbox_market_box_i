package com.ibk.sb.restapi.biz.service.product.mypage.vo;

import com.ibk.sb.restapi.app.common.constant.ComCode;
import com.ibk.sb.restapi.app.common.vo.PageVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@NoArgsConstructor
@Alias("RequestSearchMyProductVO")
public class RequestSearchMyProductVO extends PageVO {

    // 상품 정보 ID
    @ApiModelProperty(name = "pdfInfoId", value = "상품 정보 ID")
    private String pdfInfoId;

    // 묶음상품 번호 ID
    @ApiModelProperty(name = "bunInfId", value = "묶음상품 번호 ID")
    private String bunInfId;

    // 바이어 상품 번호 ID
    @ApiModelProperty(name = "buyerInfId", value = "바이어 상품 번호 ID")
    private String buyerInfId;

    // 상품 검색 조건
    @ApiModelProperty(name = "pdfInfoCon", value = "상품 검색 조건")
    private String pdfInfoCon;

    /*
     * 플래그
     */
    // 정렬 플래그
    //      '0' : 최신순
    //      '1' : 가격 낮은 순
    //      '2' : 가격 높은 순
    @ApiModelProperty(name = "orderByFlg", value = "정렬 플래그", example = "'0' : 최신순, '1' : 가격 낮은 순, '2' : 가격 높은 순")
    private String orderByFlg;

    // 상품 진행 여부(진열 여부)
    @ApiModelProperty(name = "pdfPgrsYnFlg", value = "상품 진행 여부(진열 여부)")
    private String pdfPgrsYnFlg;

    // 바이어 여부('selr': 판매자, '': 구매자)
    @ApiModelProperty(name = "buyerFlg", value = "바이어 여부('selr': 판매자, '': 구매자)")
    private String buyerFlg;

    /** 시스템 내부에서 사용**/
    // 좋아요 플래그 용 로그인 유저 아이디
    @ApiModelProperty(hidden = true)
    private String loginUserId;

    // 좋아요 플래그 용 로그인 유저 이용기관(회사)ID
    @ApiModelProperty(hidden = true)
    private String loginUtlinsttId;

    // 파일정보 구분 코드 ID - 상품 이미지
    @ApiModelProperty(hidden = true)
    private String filePtrnId = ComCode.GDS05001.getCode();
}
