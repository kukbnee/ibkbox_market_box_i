package com.ibk.sb.restapi.biz.service.delivery.chunil.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@NoArgsConstructor
@Alias("DeliveryProductVO")
public class DeliveryProductVO {

    // 품목 순번
    @JsonProperty("SEQNUM")
    @ApiModelProperty(name = "SEQNUM", value = "품목 순번")
    private String SEQNUM;

    // 원송장번호(반품시 필수항목)
    @JsonProperty("RTNNUM")
    @ApiModelProperty(name = "RTNNUM", value = "원송장번호(반품시 필수항목)")
    private String RTNNUM;

    // 포장단위코드 (포장단위 API정의서의 CODECD값)
    @JsonProperty("UNTCOD")
    @ApiModelProperty(name = "UNTCOD", value = "포장단위코드 (포장단위 API정의서의 CODECD값)")
    private String UNTCOD;

    // 수량
    @JsonProperty("ITMCNT")
    @ApiModelProperty(name = "ITMCNT", value = "수량")
    private String ITMCNT;

    // 부피_가로
    @JsonProperty("WIDTHH")
    @ApiModelProperty(name = "WIDTHH", value = "부피_가로")
    private String WIDTHH;

    // 부피_세로
    @JsonProperty("LENGTT")
    @ApiModelProperty(name = "LENGTT", value = "부피_세로")
    private String LENGTT;

    // 부피_높이
    @JsonProperty("HEIGHT")
    @ApiModelProperty(name = "HEIGHT", value = "부피_높이")
    private String HEIGHT;

    // 중량
    @JsonProperty("WEIGHT")
    @ApiModelProperty(name = "WEIGHT", value = "중량")
    private String WEIGHT;

    // 내품가격
    @JsonProperty("ITMAMT")
    @ApiModelProperty(name = "ITMAMT", value = "내품가격")
    private String ITMAMT;

    // 품목코드 (품목 API정의서의 CODECD값)
    @JsonProperty("ITMGBN")
    @ApiModelProperty(name = "ITMGBN", value = "품목코드 (품목 API정의서의 CODECD값)")
    private String ITMGBN;

    // IBK 제품코드
    @JsonProperty("IBKITM")
    @ApiModelProperty(name = "IBKITM", value = "IBK 제품코드")
    private String IBKITM;

    // 상품명
    @JsonProperty("ITMNAM")
    @ApiModelProperty(name = "ITMNAM", value = "상품명")
    private String ITMNAM;

    // 첨부파일명
    @JsonProperty("FILENM")
    @ApiModelProperty(name = "FILENM", value = "첨부파일명")
    private String FILENM;

    // 배송가능여부(Y/N)
    @JsonProperty("TRNNYN")
    @ApiModelProperty(name = "TRNNYN", value = "배송가능여부(Y/N)")
    private String TRNNYN;

    // 배송불가 메세지
    @JsonProperty("TRNTXT")
    @ApiModelProperty(name = "TRNTXT", value = "배송불가 메세지")
    private String TRNTXT;

    // 총운임(기본운임+할증료+도선료)
    @JsonProperty("ESTAMT")
    @ApiModelProperty(name = "ESTAMT", value = "총운임(기본운임+할증료+도선료)")
    private String ESTAMT;

    // 기본운임
    @JsonProperty("BASAMT")
    @ApiModelProperty(name = "BASAMT", value = "기본운임")
    private String BASAMT;

    // 할증료
    @JsonProperty("ADDAMT")
    @ApiModelProperty(name = "ADDAMT", value = "할증료")
    private String ADDAMT;

    // 도선료
    @JsonProperty("SHIAMT")
    @ApiModelProperty(name = "SHIAMT", value = "도선료")
    private String SHIAMT;

    // 방문예정일
    @JsonProperty("SCHDAT")
    @ApiModelProperty(name = "SCHDAT", value = "방문예정일")
    private String SCHDAT;

}
