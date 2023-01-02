package com.ibk.sb.restapi.biz.service.product.mypage.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ibk.sb.restapi.biz.service.product.bundle.vo.BundleProductInfoVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 마이페이지 상품관리 묶음상품 정보 등록 VO
 */
@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Alias("DetailMyBundleProductVO")
public class DetailMyBundleProductVO {

    // 묶음상품 번호 ID
    @ApiModelProperty(name = "bunInfId", value = "묶음상품 번호 ID")
    private String bunInfId;

    /*
     * 묶음 상품 정보 VO
     */
    // 수정시, 검색용 VO
    @ApiModelProperty(name = "bundleProductInfo", value = "묶음 상품 정보 VO")
    private BundleProductInfoVO bundleProductInfo = null;

    // 판매자 이용기관 ID
    @ApiModelProperty(name = "selrUsisId", value = "판매자 이용기관 ID")
    private String selrUsisId;

    // 묶음상품 파일 ID
    @ApiModelProperty(name = "fileId", value = "묶음상품 파일 ID")
    private String fileId;

    // 상품명
    @ApiModelProperty(name = "pdfNm", value = "상품명")
    private String pdfNm;

    // 상품 내용
    @ApiModelProperty(name = "pdfCon", value = "상품 내용")
    private String pdfCon;

    // 메인 노출 여부
    @ApiModelProperty(name = "mainYn", value = "메인 노출 여부")
    private String mainYn;

    // 묶음상품 VO 작성
    public BundleProductInfoVO getBundleProductInfoVO() {
        // 상품명(필수값) 이 존재하지 않는 경우
        if(!StringUtils.hasLength(this.pdfNm)) {
            return null;
        }
        return new BundleProductInfoVO(this.bunInfId, this.selrUsisId, this.fileId, this.pdfNm, this.pdfCon, this.mainYn, null, null);
    }

    // 묶음 상품 목록 VO
    @ApiModelProperty(name = "bunInfId", value = "묶음상품 번호 ID")
    private List<SummaryMyProductVO> bundleProductList;
}
