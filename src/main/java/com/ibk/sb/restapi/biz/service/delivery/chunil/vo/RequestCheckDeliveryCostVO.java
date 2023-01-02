package com.ibk.sb.restapi.biz.service.delivery.chunil.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.apache.ibatis.type.Alias;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 배송 운임 체크 VO
 */
@Getter
@Setter
@NoArgsConstructor
@Alias("RequestCheckDeliveryCostVO")
public class RequestCheckDeliveryCostVO {

    // 사업자등록번호
    @JsonProperty("SAUPNO")
    @ApiModelProperty(name = "SAUPNO", value = "사업자등록번호")
    private String SAUPNO;

    // 견적일자 (YYYY-MM-DD)
    @JsonProperty("ESTDAT")
    @ApiModelProperty(name = "ESTDAT", value = "견적일자 (YYYY-MM-DD)")
    private String ESTDAT;

    // 발송자 우편번호
    @JsonProperty("BALZIP")
    @ApiModelProperty(name = "BALZIP", value = "발송자 우편번호")
    private String BALZIP;

    // 발송자 주소
    @JsonProperty("BALADD")
    @ApiModelProperty(name = "BALADD", value = "발송자 주소")
    private String BALADD;

    // 발송자 건물관리번호(도로명 주소인 경우)
    @JsonProperty("BALADS")
    @ApiModelProperty(name = "BALADS", value = "발송자 건물관리번호(도로명 주소인 경우)")
    private String BALADS;

    // 수신자 우편번호
    @JsonProperty("DOCZIP")
    @ApiModelProperty(name = "DOCZIP", value = "수신자 우편번호")
    private String DOCZIP;

    // 수신자 주소
    @JsonProperty("DOCADD")
    @ApiModelProperty(name = "DOCADD", value = "수신자 주소")
    private String DOCADD;

    // 수신자 건물관리번호(도로명 주소인 경우)
    @JsonProperty("DOCADS")
    @ApiModelProperty(name = "DOCADS", value = "수신자 건물관리번호(도로명 주소인 경우)")
    private String DOCADS;

    // 총 건수
    @ApiModelProperty(name = "rsCount", value = "총 건수")
    private String rsCount;

    // 요청 품목 정보 리스트
    @ApiModelProperty(name = "rs", value = "요청 품목 정보 리스트")
    private List<DeliveryProductVO> rs;

    public String getParam() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("SAUPNO:").append(setParam(this.SAUPNO));
        sb.append(",").append("ESTDAT:").append(setParam(this.ESTDAT));
        sb.append(",").append("BALZIP:").append(setParam(this.BALZIP));
        sb.append(",").append("BALADD:").append(setParam(this.BALADD));
        sb.append(",").append("BALADS:").append(setParam(this.BALADS));
        sb.append(",").append("DOCZIP:").append(setParam(this.DOCZIP));
        sb.append(",").append("DOCADD:").append(setParam(this.DOCADD));
        sb.append(",").append("DOCADS:").append(setParam(this.DOCADS));
        sb.append(",").append("rsCount:").append(setParam(this.rsCount));
        sb.append(",").append("rs:").append(setParamList(this.rs));
        sb.append("}");
        return sb.toString();
    }

    private String setParam(String param) {

        StringBuilder sb = new StringBuilder();
        sb.append("\"");
        if(StringUtils.hasLength(param)) {
            sb.append(param);
        }
        sb.append("\"");
        return sb.toString();
    }

    private String setParamList(List<DeliveryProductVO> rs) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        if(rs != null) {
            int totalSize = rs.size();
            for(int i = 0 ; i < totalSize; i++ ) {
                DeliveryProductVO vo = rs.get(i);

                sb.append("{");
                sb.append("SEQNUM:").append(setParam(vo.getSEQNUM()));
                sb.append(",").append("ITMCNT:").append(setParam(vo.getITMCNT()));
                sb.append(",").append("WIDTHH:").append(setParam(vo.getWIDTHH()));
                sb.append(",").append("LENGTT:").append(setParam(vo.getLENGTT()));
                sb.append(",").append("HEIGHT:").append(setParam(vo.getHEIGHT()));
                sb.append(",").append("WEIGHT:").append(setParam(vo.getWEIGHT()));
                sb.append(",").append("ITMAMT:").append(setParam(vo.getITMAMT()));
                sb.append("}");
                if(i + 1 != totalSize) {
                    sb.append(",");
                }
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
