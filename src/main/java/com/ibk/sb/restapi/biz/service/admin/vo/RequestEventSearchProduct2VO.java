package com.ibk.sb.restapi.biz.service.admin.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ibk.sb.restapi.biz.service.event.vo.RequestSearchEventVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@NoArgsConstructor
@Alias("RequestEventSearchProduct2VO")
@JsonIgnoreProperties({
        "usyn", "delyn", "rgsnUserId", "rgsnTs", "amnnUserId", "amnnTs", "totalCnt", "rnum"
})
public class RequestEventSearchProduct2VO extends RequestSearchEventVO {

//    @ApiModelProperty(name = "이벤트 ID", example = "3a63d8c2-d3c1-428a-a205-408f2e63f572")
//    private String evntInfId;
//
//    @ApiModelProperty(name = "이벤트 제목", hidden = true)
//    private String evntTtl;
//
//    @ApiModelProperty(name = "이벤트 정렬", hidden = true)
//    private String evntSort;
//
//    @ApiModelProperty(name = "이벤트 진행상태 ID", hidden = true)
//    private String pgstId;
//
//    @ApiModelProperty(name = "이벤트 사용여부", hidden = true)
//    private String evntUsed;
//
//    @ApiModelProperty(name = "로그인 유저 이용기관(회사)ID", hidden = true)
//    private String loginUsisId;
//
//    @ApiModelProperty(name = "로그인 유저 아이디", hidden = true)
//    private String loginUserId;
//
//    @ApiModelProperty(name = "이벤트 상품 선정여부", hidden = true)
//    private String pcsnsttsId;
//
//    @ApiModelProperty(name = "상품 진열여부", hidden = true)
//    private String pdfPgrsYn;
//
//    @ApiModelProperty(name = "상품 판매중여부", hidden = true)
//    private String pdfSttsId;
//
//    @ApiModelProperty(name = "상품 이미지 파일유형", hidden = true)
//    private String filePtrnId;
//
//    @ApiModelProperty(name = "상품명", hidden = true)
//    private String pdfNm;
//
//    @ApiModelProperty(name = "상품 이벤트 신청 대상 여부", hidden = true)
//    private boolean everyEventProductYn;

}
