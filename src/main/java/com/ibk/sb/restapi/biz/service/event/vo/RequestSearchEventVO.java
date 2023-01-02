package com.ibk.sb.restapi.biz.service.event.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ibk.sb.restapi.app.common.vo.PageVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

/**
 * 검색용 VO
 */
@Getter
@Setter
@NoArgsConstructor
@Alias("RequestSearchEventVO")
@JsonIgnoreProperties({
        "usyn", "delyn", "rgsnUserId", "rgsnTs", "amnnUserId", "amnnTs",
        "totalCnt", "rnum"
})
public class RequestSearchEventVO extends PageVO {

    // 이벤트 ID
    @ApiModelProperty(name = "evntInfId", value = "이벤트 ID")
    private String evntInfId;

    // 이벤트 제목
    @ApiModelProperty(name = "evntTtl", value = "이벤트 제목")
    private String evntTtl;

    // 이벤트 정렬
    @ApiModelProperty(name = "evntSort", value = "이벤트 정렬")
    private String evntSort;

    // 이벤트 진행상태 ID
    @ApiModelProperty(name = "pgstId", value = "이벤트 진행상태 ID")
    private String pgstId;

    // 이벤트 사용여부
    @ApiModelProperty(name = "evntUsed", value = "이벤트 사용여부")
    private String evntUsed;

    // 로그인 유저 이용기관(회사)ID
    @ApiModelProperty(name = "loginUsisId", value = "로그인 유저 이용기관(회사)ID", hidden = true)
    private String loginUsisId;

    // 로그인 유저 아이디
    @ApiModelProperty(name = "loginUserId", value = "로그인 유저 아이디", hidden = true)
    private String loginUserId;

    // 이벤트 상품 선정여부
    @ApiModelProperty(name = "pcsnsttsId", value = "이벤트 상품 선정여부", hidden = true)
    private String pcsnsttsId;

    // 상품 진열여부
    @ApiModelProperty(name = "pdfPgrsYn", value = "상품 진열여부")
    private String pdfPgrsYn;

    // 상품 판매중여부
    @ApiModelProperty(name = "pdfSttsId", value = "상품 판매중여부")
    private String pdfSttsId;

    // 상품 공통 파일 정보 구분 코드 ID
    @ApiModelProperty(name = "filePtrnId", value = "상품 진열여부", hidden = true)
    private String filePtrnId;

    // 상품명
    @ApiModelProperty(name = "pdfNm", value = "상품 진열여부")
    private String pdfNm;

    // 메인화면 플래그
    // 메인 화면 리스트 조회 시, 이벤트 상품이 없는 진행중 이벤트는 제외
    @ApiModelProperty(name = "mainEventFlg", value = "상품 진열여부", hidden = true)
    private String mainEventFlg;

}
