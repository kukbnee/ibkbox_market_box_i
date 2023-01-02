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
 * 배송 요청 API VO
 */
@Getter
@Setter
@NoArgsConstructor
@Alias("RequestDeliveryVO")
public class RequestDeliveryVO {

    // 거래번호(BOX 발급번호)
    @JsonProperty("IBKNUM")
    @ApiModelProperty(name = "IBKNUM", value = "거래번호(BOX 발급번호)")
    private String IBKNUM;

    // 요청자ID(“IBKAPI” 고정값)
    @JsonProperty("USERID")
    @ApiModelProperty(name = "USERID", value = "요청자ID(“IBKAPI” 고정값)")
    private String USERID = "IBKAPI";

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

    // 발송자 이름
    @JsonProperty("BALNAM")
    @ApiModelProperty(name = "BALNAM", value = "발송자 이름")
    private String BALNAM;

    // 발송자 전화번호
    @JsonProperty("BALTEL")
    @ApiModelProperty(name = "BALTEL", value = "발송자 전화번호")
    private String BALTEL;

    // 수신자 이름
    @JsonProperty("DOCNAM")
    @ApiModelProperty(name = "DOCNAM", value = "수신자 이름")
    private String DOCNAM;

    // 수신자 전화번호
    @JsonProperty("DOCTEL")
    @ApiModelProperty(name = "DOCTEL", value = "수신자 전화번호")
    private String DOCTEL;

    // 선불/착불구분(선불:“1”, 착불:“2”) : “1”값 고정
    @JsonProperty("PAYGBN")
    @ApiModelProperty(name = "PAYGBN", value = "선불/착불구분(선불:“1”, 착불:“2”) : “1”값 고정")
    private String PAYGBN = "1";

    // 결제방법(현금:”CASH”, POS:”POS”) : “”값 고정
    @JsonProperty("PAYTYP")
    @ApiModelProperty(name = "PAYTYP", value = "결제방법(현금:”CASH”, POS:”POS”) : “”값 고정")
    private String PAYTYP = "";

    // 총 건수
    @ApiModelProperty(name = "rsCount", value = "총 건수")
    private String rsCount;

    // 요청 품목 정보 리스트
    @ApiModelProperty(name = "rs", value = "요청 품목 정보 리스트")
    private List<DeliveryProductVO> rs;

    public String getParam() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("IBKNUM:").append(setParam(this.IBKNUM, true));
        sb.append(",").append("USERID:").append(setParam(this.USERID, true));
        sb.append(",").append("SAUPNO:").append(setParam(this.SAUPNO, true));
        sb.append(",").append("ESTDAT:").append(setParam(this.ESTDAT, true));
        sb.append(",").append("BALZIP:").append(setParam(this.BALZIP, true));
        sb.append(",").append("BALADD:").append(setParam(this.BALADD, true));
        sb.append(",").append("BALADS:").append(setParam(this.BALADS, true));
        sb.append(",").append("DOCZIP:").append(setParam(this.DOCZIP, true));
        sb.append(",").append("DOCADD:").append(setParam(this.DOCADD, true));
        sb.append(",").append("DOCADS:").append(setParam(this.DOCADS, true));
        sb.append(",").append("BALNAM:").append(setParam(this.BALNAM, true));
        sb.append(",").append("BALTEL:").append(setParam(this.BALTEL, true));
        sb.append(",").append("DOCNAM:").append(setParam(this.DOCNAM, true));
        sb.append(",").append("DOCTEL:").append(setParam(this.DOCTEL, true));
        sb.append(",").append("PAYGBN:").append(setParam(this.PAYGBN, true));
        sb.append(",").append("PAYTYP:").append(setParam(this.PAYTYP, true));
        sb.append(",").append("rsCount:").append(setParam(this.rsCount, false));
        sb.append(",").append("rs:").append(setParamList(this.rs));
        sb.append("}");
        return sb.toString();
    }

    private String setParam(String param, boolean comma) {
        StringBuilder sb = new StringBuilder();
        if(comma) sb.append("\"");
        sb.append(param);
        if(comma) sb.append("\"");
        return sb.toString();
    }

    private String setParamList(List<DeliveryProductVO> rs) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        int totalSize = rs.size();
        for(int i = 0 ; i < totalSize; i++ ) {
            DeliveryProductVO vo = rs.get(i);

            sb.append("{");
            sb.append("SEQNUM:").append(setParam(vo.getSEQNUM(), false));
            sb.append(",").append("RTNNUM:").append(setParam(vo.getRTNNUM(), true));
            sb.append(",").append("UNTCOD:").append(setParam(vo.getUNTCOD(), true));
            sb.append(",").append("ITMCNT:").append(setParam(vo.getITMCNT(), false));
            sb.append(",").append("WIDTHH:").append(setParam(vo.getWIDTHH(), false));
            sb.append(",").append("LENGTT:").append(setParam(vo.getLENGTT(), false));
            sb.append(",").append("HEIGHT:").append(setParam(vo.getHEIGHT(), false));
            sb.append(",").append("WEIGHT:").append(setParam(vo.getWEIGHT(), false));
            sb.append(",").append("ITMAMT:").append(setParam(vo.getITMAMT(), false));
            sb.append(",").append("ITMGBN:").append(setParam(vo.getITMGBN(), true));
            sb.append(",").append("IBKITM:").append(setParam(vo.getIBKITM(), true));
            sb.append(",").append("ITMNAM:").append(setParam(vo.getITMNAM(), true));
            sb.append(",").append("FILENM:").append(setParam(vo.getFILENM(), true));
            sb.append(",").append("TRNNYN:").append(setParam(vo.getTRNNYN(), true));
            sb.append(",").append("TRNTXT:").append(setParam(vo.getTRNTXT(), true));
            sb.append(",").append("ESTAMT:").append(setParam(vo.getESTAMT(), true));
            sb.append(",").append("BASAMT:").append(setParam(vo.getBASAMT(), true));
            sb.append(",").append("ADDAMT:").append(setParam(vo.getADDAMT(), true));
            sb.append(",").append("SHIAMT:").append(setParam(vo.getSHIAMT(), true));
            sb.append(",").append("SCHDAT:").append(setParam(vo.getSCHDAT(), true));

            sb.append("}");
            if(i + 1 != totalSize) {
                sb.append(",");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
