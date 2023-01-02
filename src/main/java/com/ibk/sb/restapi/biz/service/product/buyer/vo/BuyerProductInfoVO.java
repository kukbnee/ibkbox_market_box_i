package com.ibk.sb.restapi.biz.service.product.buyer.vo;

import com.ibk.sb.restapi.app.common.vo.BaseTableVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

import java.util.List;

/**
 * Table : TB_BOX_MKT_BUYER_INF_R
 * Name : 바이어 상품 정보
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Alias("BuyerProductInfoVO")
public class BuyerProductInfoVO extends BaseTableVO {

    // 바이어 상품 번호 ID
    @ApiModelProperty(name = "buyerInfId", value = "바이어 상품 번호 ID")
    private String buyerInfId;

    // 판매자 이용기관 ID
    @ApiModelProperty(name = "selrUsisId", value = "판매자 이용기관 ID")
    private String selrUsisId;

    // 판매자 이용기관 명
    @ApiModelProperty(name = "selrUsisNm", value = "판매자 이용기관 명")
    private String selrUsisNm;

    // 파일 ID
    @ApiModelProperty(name = "fileId", value = "파일 ID")
    private String fileId;

    // 송신자 이메일
    @ApiModelProperty(name = "senEml", value = "송신자 이메일")
    private String senEml;

    // 수신자 이메일
    @ApiModelProperty(name = "recEml", value = "수신자 이메일")
    private String recEml;

    // 제목
    @ApiModelProperty(name = "ttl", value = "제목")
    private String ttl;

    // 내용
    @ApiModelProperty(name = "con", value = "내용")
    private String con;

    // 파일 명
    @ApiModelProperty(name = "fileNm", value = "파일 명")
    private String fileNm;

}
