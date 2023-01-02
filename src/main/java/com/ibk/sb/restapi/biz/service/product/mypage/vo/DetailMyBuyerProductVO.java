package com.ibk.sb.restapi.biz.service.product.mypage.vo;

import com.ibk.sb.restapi.biz.service.product.buyer.vo.BuyerProductInfoVO;
import com.ibk.sb.restapi.biz.service.product.buyer.vo.BuyerProductVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 마이페이지 상품관리 바이어 상품 정보 등록 VO
 */
@Getter
@Setter
@NoArgsConstructor
@Alias("DetailMyBuyerProductVO")
public class DetailMyBuyerProductVO {

    // 바이어 상품 번호 ID
    @ApiModelProperty(name = "buyerInfId", value = "바이어 상품 번호 ID")
    private String buyerInfId;

    /*
     * 바이어 상품 정보 VO
     */
    // 수정시, 검색용 VO
    @ApiModelProperty(name = "buyerProductInfo", value = "바이어 상품 정보 VO")
    private BuyerProductInfoVO buyerProductInfo;

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

    // 바이어 상품 VO 작성
    public BuyerProductInfoVO getBuyerProductInfoVO() {
        // 상품명(필수값) 이 존재하지 않는 경우
        if(!StringUtils.hasLength(this.ttl)) {
            return null;
        }
        return new BuyerProductInfoVO(this.buyerInfId, this.selrUsisId, this.selrUsisNm, this.fileId, this.senEml, this.recEml, this.ttl, this.con, null);
    }

    // 바이어 상품 목록 VO
    @ApiModelProperty(name = "buyerProductList", value = "바이어 상품 목록 VO")
    private List<SummaryMyProductVO> buyerProductList;
}
