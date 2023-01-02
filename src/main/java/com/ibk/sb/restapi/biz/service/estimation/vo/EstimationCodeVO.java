package com.ibk.sb.restapi.biz.service.estimation.vo;

import com.ibk.sb.restapi.biz.service.common.vo.SummaryComCodeListVO;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

import java.util.List;

/**
 * 견적 코드 VO
 */
@Getter
@Setter
@Alias("EstimationCodeVO")
public class EstimationCodeVO {

    //////
    // 코드
    //////

    // 공통관련코드(처리 상태)
    private List<SummaryComCodeListVO> pcsnSttsCodes;

    // 공통관련코드(배송 유형)
    private List<SummaryComCodeListVO> dvryPtrnCodes;

    // 공통관련코드(견적 상품 유형)
    private List<SummaryComCodeListVO> esttPdfPtrnCodes;

    // 공통관련코드(공통 상품 단위)
    private List<SummaryComCodeListVO> comPdfUtCodes;

    // 공통관련코드(공통 가격 단위)
    private List<SummaryComCodeListVO> comPrcUtCodes;

}
