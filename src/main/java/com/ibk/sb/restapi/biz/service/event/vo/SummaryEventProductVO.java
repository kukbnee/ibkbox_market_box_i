package com.ibk.sb.restapi.biz.service.event.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ibk.sb.restapi.app.common.constant.ComCode;
import com.ibk.sb.restapi.app.common.vo.BaseTableVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;
import org.springframework.util.StringUtils;

/**
 * 리스트 조회용 이벤트 상품 VO
 */
@Getter
@Setter
@Alias("SummaryEventProductVO")
@JsonIgnoreProperties({
        "usyn", "delyn", "rgsnUserId", "rgsnTs", "amnnUserId", "amnnTs",
        "totalCnt", "rnum"
})
public class SummaryEventProductVO extends BaseTableVO {

    @ApiModelProperty(name = "이벤트 정보 ID")
    private String evntInfId;

    @ApiModelProperty(name = "상품 정보 ID")
    private String pdfInfoId;

    @ApiModelProperty(name = "상품 이미지 파일명")
    private String fileNm;

    @ApiModelProperty(name = "상품 파일 경로")
    private String filePath;

    @ApiModelProperty(name = "상품명")
    private String pdfNm;

    @ApiModelProperty(name = "상품 판매자 이용기관 ID")
    private String selrUsisId;

    @ApiModelProperty(name = "상품 판매자 아이디")
    private String rgsnUserId;

    @ApiModelProperty(name = "상품 판매자 회사명")
    private String selrUsisName;

    @ApiModelProperty(name = "상품 판매자 회사명")
    private String usisName;

    @ApiModelProperty(name = "판매자 타입 ID")
    private String mmbrtypeId;

    @ApiModelProperty(name = "판매자 인증여부 플래그")
    private String userAuthYn;

    @ApiModelProperty(name = "상품 판매가")
    private String pdfPrc;

    @ApiModelProperty(name = "상품 판매가 단위")
    private String pdfPrcType;

    @ApiModelProperty(name = "상품 할인가")
    private String salePrc;

    @ApiModelProperty(name = "상품 가격협의여부")
    private String prcDscsYn;

    @ApiModelProperty(name = "상품 견적사용유무")
    private String esttUseEn;

    @ApiModelProperty(name = "상품 위시리스트 추가여부")
    private String wishFlg = "N";

    @ApiModelProperty(name = "에이전시 상품여부")
    private String pdfAgenState;

    @ApiModelProperty(name = "상품 진행 여부(진열 여부)")
    private String pdfPgrsYn;

    @ApiModelProperty(name = "상품 상태 ID", example = "GDS00001")
    private String pdfSttsId;

    public void setMmbrtypeId(String mmbrtypeId) {
        this.mmbrtypeId = mmbrtypeId;
        this.userAuthYn = "N";

        // 판매자 타입 ID가 존재하고, 해당 판매자 타입 ID가 정회원 or 에이전시일 경우 userAuthYn = Y
        if(StringUtils.hasLength(mmbrtypeId) && (mmbrtypeId.equals(ComCode.REGULAR_MEMBER.getCode()) || mmbrtypeId.equals(ComCode.AGENCY_MEMBER.getCode())) ) {
            this.userAuthYn = "Y";
        }
    }
}
