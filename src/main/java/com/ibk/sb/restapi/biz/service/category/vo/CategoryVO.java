package com.ibk.sb.restapi.biz.service.category.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ibk.sb.restapi.app.common.vo.BaseTableVO;
import com.ibk.sb.restapi.app.common.vo.PageVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;

/**
 * Name : 상품 카테고리 정보
 * Table : TB_BOX_MKT_CTGY_INF_C
 * ASC : 상품 카테고리 ID
 */
@Getter
@Setter
@Alias("CategoryVO")
@JsonIgnoreProperties({
        "usyn", "delyn", "rgsnUserId", "rgsnTs", "amnnUserId", "amnnTs",
        "totalCnt", "rgsnUserName", "amnnUserName"
})
@NoArgsConstructor
public class CategoryVO extends BaseTableVO {

    // 카테고리 ID
    @ApiModelProperty(name = "ctgyId", value = "카테고리 ID")
    private String ctgyId;

    // 1차 분류 명
    @ApiModelProperty(hidden = true)
    private String tms1ClsfNm;

    // 1차 분류 코드
    @ApiModelProperty(name = "tms1ClsfCd", value = "이벤트 ID")
    private String tms1ClsfCd;

    // 2차 분류 명
    @ApiModelProperty(hidden = true)
    private String tms2ClsfNm;

    // 2차 분류 코드
    @ApiModelProperty(name = "tms2ClsfCd", value = "이벤트 ID")
    private String tms2ClsfCd;

    // 3차 분류 명
    @ApiModelProperty(hidden = true)
    private String tms3ClsfNm;

    // 3차 분류 코드
    @ApiModelProperty(name = "tms3ClsfCd", value = "이벤트 ID")
    private String tms3ClsfCd;

    // 4차 분류 명
    @ApiModelProperty(hidden = true)
    private String tms4ClsfNm;

    // 4차 분류 코드
    @ApiModelProperty(name = "tms4ClsfCd", value = "이벤트 ID")
    private String tms4ClsfCd;

    // 5차 분류 명
    @ApiModelProperty(hidden = true)
    private String tms5ClsfNm;

    // 5차 분류 코드
    @ApiModelProperty(name = "tms5ClsfCd", value = "이벤트 ID")
    private String tms5ClsfCd;



    /*
     *검색하는 분류 종류
     * 'one'(1차), 'two'(2차), 'thr'(3차), 'for'(4차), 'fiv'(5차), 'six'(5차를 CategoryVO 로 return)
     */
    @ApiModelProperty(name = "searchCategoryNum", value = "검색하는 분류 종류", example = "'one'(1차), 'two'(2차), 'thr'(3차), 'for'(4차), 'fiv'(5차), 'six'(5차를 CategoryVO 로 return)")
    private String searchCategoryNum;

    /*
     * 카테고리 ID setter 생성자
     */
    public CategoryVO(String tms1ClsfCd, String tms2ClsfCd, String tms3ClsfCd, String tms4ClsfCd, String tms5ClsfCd) {
        this.tms1ClsfCd = tms1ClsfCd;
        this.tms2ClsfCd = tms2ClsfCd;
        this.tms3ClsfCd = tms3ClsfCd;
        this.tms4ClsfCd = tms4ClsfCd;
        this.tms5ClsfCd = tms5ClsfCd;

        String searchCategoryNum = "two";
        if(StringUtils.hasLength(tms2ClsfCd)) { searchCategoryNum = "thr"; }
        if(StringUtils.hasLength(tms3ClsfCd)) { searchCategoryNum = "for"; }
        if(StringUtils.hasLength(tms4ClsfCd)) { searchCategoryNum = "fiv"; }
        if(StringUtils.hasLength(tms5ClsfCd)) { searchCategoryNum = "six"; }
        this.searchCategoryNum = searchCategoryNum;
    }
}
