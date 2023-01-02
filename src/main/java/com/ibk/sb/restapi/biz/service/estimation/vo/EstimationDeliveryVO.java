package com.ibk.sb.restapi.biz.service.estimation.vo;

import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

import java.util.List;

/**
 * 운송업체 VO
 */
@Getter
@Setter
@Alias("EstimationDeliveryVO")
public class EstimationDeliveryVO {

    // 업체 정보 ID
    private String entpInfoId;

    // 업체명
    private String entpNm;

    //////
    // 조회
    //////

    // 배송비(합계)
    private Integer dvrynone;

    // 공통 가격단위 ID
    private String comPrcUtId;

    // 화물서비스 api 결과 여부
    private String apiResultYn;

    // 화물서비스 api 오류 내용(예시: 수량오류)
    private String apiResultTxt;

    //////////////////
    // 운임체크 결과(상세)
    //////////////////

    // 상품별 배송비
    List<EstimationEsmVO> estimationEsmVOList;

}
