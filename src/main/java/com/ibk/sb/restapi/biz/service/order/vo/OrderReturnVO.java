package com.ibk.sb.restapi.biz.service.order.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ibk.sb.restapi.app.common.vo.BaseTableVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

import java.sql.Timestamp;

/**
 * 반품정보 VO
 * */
@Getter
@Setter
@NoArgsConstructor
@Alias("OrderReturnVO")
@JsonIgnoreProperties({
        "usyn", "delyn", "rgsnUserId", "rgsnTs", "amnnUserId", "amnnTs",
        "totalCnt", "rnum", "rgsnUserName", "amnnUserName", "rvsRnum"
})
public class OrderReturnVO extends BaseTableVO {

    // 주문정보ID
    private String ordnInfoId;

    // 주문정보순번
    private Integer infoSqn;

    // 주문 체결번호(주문번호)
    private String cnttNoId;

    // 구매자 ID
    private String pucsId;

    // 구매자 명
    private String pucsNm;

    // 구매자 이용기관 ID
    private String pucsUsisId;

    // 구매자 이용기관 명
    private String pucsBplcNm;

    // 판매자 ID
    private String selrId;

    // 판매자 명
    private String selrNm;

    // 판매자 이용기관 ID
    private String selrUsisId;

    // 판매자 이용기관명
    private String selrBplcNm;

    // 주문 유형 ID
    private String ordnPtrnId;

    // 상품 카테고리 ID
    private String pdfCtgyId;

    // 상품 카테고리 정보
    private String pdfCtgyData;

    // 상품명
    private String pdfNm;

    // 모델명
    private String mdlnm;

    // 에이전시 접수 정보 ID
    private String agenInfId;

    // 에이전시 상품 여부
    private String agenState;

    // 상품 진행 여부(진열 여부)
    private String pdfPgrsYn;

    // 상품 상태 ID(판매중(GDS00001))
    private String pdfSttsId;

    // 수량
    private Integer qty;

    // 판매가격
    private Integer pdfPrc;

    // 총금액
    private Integer ttalAmt;

    // 배송 유형 ID
    private String dvryPtrnId;

    // 배송 정보 ID
    private String dvryInfoId;

    // 주문 상태 ID
    private String ordnSttsId;

    // 상품 정보 ID
    private String pdfInfoId;

    // 등록일 - 주문일로 사용
    private Timestamp rgsnTs;

    // 등록 일시 문자열
    private String rgsnTsStr;

    // 수정일 - 상태변경일로 사용
    private Timestamp amnnTs;

    // 택배사 유형 ID
    private String pcsvcmpPtrnId;

    // 기타 택배사명
    private String pcsvcmpNm;

}
