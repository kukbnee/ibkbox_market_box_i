package com.ibk.sb.restapi.biz.service.review.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ibk.sb.restapi.app.common.vo.BaseTableVO;
import com.ibk.sb.restapi.biz.service.file.vo.FileInfoVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

import java.sql.Timestamp;
import java.util.List;

/**
 * Name : 상품 구매후기 정보
 * Table : TB_BOX_MKT_PDF_REV_R
 */
@Getter
@Setter
@Alias("ReviewVO")
@JsonIgnoreProperties({
        "usyn", "delyn", "rgsnUserId", "rgsnTs", "amnnUserId", "amnnTs",
        "totalCnt", "rnum"
})
public class ReviewVO extends BaseTableVO {

    // 구매 후기 ID
    @ApiModelProperty(name = "revInfId", value = "구매 후기 ID")
    private String revInfId;

    // 상품 정보 ID
    @ApiModelProperty(name = "pdfInfoId", value = "상품 정보 ID")
    private String pdfInfoId;

    // 구매자 이용기관 ID
    @ApiModelProperty(name = "pucsUsisId", value = "구매자 이용기관 ID")
    private String pucsUsisId;

    // 구매자 이용기관 명
    @ApiModelProperty(name = "pucsUsisName", value = "구매자 이용기관 명")
    private String pucsUsisName;

    // 주문 정보 ID
    @ApiModelProperty(name = "ordnInfoId", value = "주문 정보 ID")
    private String ordnInfoId;

    // 구매 후기 내용
    @ApiModelProperty(name = "revCon", value = "구매 후기 내용")
    private String revCon;

    // 구매 후기 평점
    @ApiModelProperty(name = "revVal", value = "구매 후기 평점")
    private double revVal;

    // 등록 일시 문자열
    @ApiModelProperty(name = "rgsnTsStr", value = "등록 일시 문자열")
    private String rgsnTsStr;

    // 수량
    @ApiModelProperty(name = "qty", value = "수량")
    private Integer qty;

    // 총 금액
    @ApiModelProperty(name = "ttalAmt", value = "총 금액")
    private Integer ttalAmt;

    // 주문 일시 문자열
    @ApiModelProperty(name = "ordnTsStr", value = "주문 일시 문자열")
    private String ordnTsStr;

    // 상품 구매후기 파일 정보
    @ApiModelProperty(name = "reviewFileList", value = "상품 구매후기 파일 정보(List<ReviewFileVO>)")
    private List<ReviewFileVO> reviewFileList;

}
