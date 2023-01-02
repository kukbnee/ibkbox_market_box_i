package com.ibk.sb.restapi.biz.service.order.mypage.vo;

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
@Alias("RequestSearchMyOrderVO")
public class RequestSearchMyOrderVO extends PageVO {

    // 주문 정보 ID
    @ApiModelProperty(name = "ordnInfoId", value = "주문 정보 ID")
    private String ordnInfoId;

    // 주문 정보 ID 리스트
    @ApiModelProperty(name = "ordnInfoIds", value = "주문 정보 ID 리스트")
    private List<String> ordnInfoIds;

    // 검색 조건 - 시작일(yyyy-MM-dd)
    @ApiModelProperty(name = "stDt", value = "검색 조건 - 시작일(yyyy-MM-dd)")
    private String stDt;

    // 검색 조건 - 종료일(yyyy-MM-dd)
    @ApiModelProperty(name = "edDt", value = "검색 조건 - 종료일(yyyy-MM-dd)")
    private String edDt;

    // 상품명
    @ApiModelProperty(name = "pdfNm", value = "상품명")
    private String pdfNm;

    // 주문 상태 ID
    @ApiModelProperty(name = "ordnSttsId", value = "주문 상태 ID")
    private String ordnSttsId;

    // 판매자 이용기관 명
    @ApiModelProperty(name = "selrUsisNm", value = "판매자 이용기관 명")
    private String selrUsisNm;

    /** 시스템 내부에서 사용**/
    // 구매자 이용기관 ID
    @ApiModelProperty(hidden = true)
    private String pucsUsisId;

    // 판매자 이용기관 ID
    @ApiModelProperty(hidden = true)
    private String selrUsisId;


    // 판매자 체크
    //      true    : 로그인한 유저의 판매목록 조회
    //      false   : 로그인한 유저의 구매목록 조회
    @ApiModelProperty(hidden = true)
    private boolean sellerFlg;

}
