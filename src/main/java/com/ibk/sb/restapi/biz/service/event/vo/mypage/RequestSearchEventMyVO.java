package com.ibk.sb.restapi.biz.service.event.vo.mypage;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ibk.sb.restapi.app.common.vo.PageVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Alias("RequestSearchEventMyVO")
@JsonIgnoreProperties({
        "usyn", "delyn", "rgsnUserId", "rgsnTs", "amnnUserId", "amnnTs",
        "totalCnt", "rnum"
})
public class RequestSearchEventMyVO extends PageVO {

    // 검색 탭 코드
    @ApiModelProperty(name = "tabCode", value = "검색 탭 코드", example = "'' : 전체, ETS01001 : 접수, ETS01002 : 선정, ETS01003 : 미선정")
    private String tabCode;

    // 이벤트 검색 조건
    @ApiModelProperty(name = "pgstId", value = "이벤트 검색 조건", example = "이벤트상태(ETS00001(진행중), ETS00002(준비중), ETS00003(마감)), ''(전체)")
    private String pgstId;

    // 단일 이벤트 ID
    @ApiModelProperty(name = "evntInfId", value = "단일 이벤트 ID")
    private String evntInfId;

    // 이벤트 상품 선정여부
    @ApiModelProperty(name = "pcsnsttsId", value = "이벤트 상품 선정여부")
    private String pcsnsttsId;

    // 상품 진열여부
    @ApiModelProperty(name = "pdfPgrsYn", value = "상품 진열여부")
    private String pdfPgrsYn;

    // 상품 판매중여부
    @ApiModelProperty(name = "pdfSttsId", value = "상품 판매중여부")
    private String pdfSttsId;

    // 상품 이미지 파일유형
    @ApiModelProperty(name = "filePtrnId", value = "상품 이미지 파일유형")
    private String filePtrnId;

    // 검색 이벤트 ID
    @ApiModelProperty(name = "evntIds", value = "검색 이벤트 ID(List<String>)")
    private List<String> evntIds;

    // 로그인 유저 이용기관(회사)ID
    @ApiModelProperty(hidden = true)
    private String loginUsisId;

    // 로그인 유저 유저ID
    @ApiModelProperty(hidden = true)
    private String loginUserId;

}
