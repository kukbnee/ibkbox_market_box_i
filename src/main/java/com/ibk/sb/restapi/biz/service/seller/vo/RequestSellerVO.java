package com.ibk.sb.restapi.biz.service.seller.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Alias("RequestSellerInfoVO")
public class RequestSellerVO {

    // 판매자 운영기관 ID
    @ApiModelProperty(name = "selrUsisId", value = "판매자 운영기관 ID")
    private String selrUsisId;

    // 회사 소개
    @ApiModelProperty(name = "userCpCon", value = "회사 소개")
    private String userCpCon;

    // 배경 이미지 파일 ID
    @ApiModelProperty(name = "sellerBgImgFileId", value = "배경 이미지 파일 ID")
    private String sellerBgImgFileId;

    // 배너 이지미 리스트
    @ApiModelProperty(name = "sellerBannerList", value = "배너 이지미 리스트(List<SellerFileVO>)")
    private List<SellerFileVO> sellerBannerList;
}
