package com.ibk.sb.restapi.biz.service.event.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ibk.sb.restapi.app.common.vo.BaseTableVO;
import com.ibk.sb.restapi.biz.service.file.vo.FileInfoVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

/**
 * 검색용 이벤트상품 VO
 */
@Getter
@Setter
@NoArgsConstructor
@Alias("EventProductVO")
@JsonIgnoreProperties({
        "usyn", "delyn", "rgsnUserId", "rgsnTs", "amnnUserId", "amnnTs",
        "totalCnt", "rnum", "rgsnUserName", "amnnUserName"
})
public class EventProductVO extends BaseTableVO {

    // 이벤트 ID
    private String evntInfId;

    // 상품 정보 ID
    private String pdfInfoId;

    // 판매자 이용기관(회사) ID
    private String selrUsisId;

    // 회사명
    private String bplcNm;

    // 상품 카테고리ID
    private String pdfCtgyId;

    // 상품 카테고리 데이타
    private String ctgyData;

    // 상품명
    private String pdfNm;

    // 모델명
    private String mdlnm;

    // 요약설명
    private String brfDesc;

    // 간략설명
    private String brfSubDesc;

    // 에이전시 유무
    private String pdfAgenState;

    // 에이전시 원판매사
    private String pdfAgenOrgBplcNm;

    // 상품 가격
    private String pdfPrc;

    // 상품 가격단위ID
    private String comPrcutId;

    // 상품 가격단위 변환
    private String comPrcutNm;

    // 상품 수량단위ID
    private String comPdfutId;

    // 상품 수량단위 변환
    private String comPdfutNm;

    // 상품 할인가
    private String salePrc;

    // 가격협의 여부
    private String prcDscsYn;

    // 견적 여부
    private String esttUseEn;

    // 주문수량제한 사용여부
    private String ordnQtyLmtnUsyn;

    // 주문최소수량
    private String ordnMnmmQty;

    // 주문최대수량여부
    private String ordnMxmmQtyYn;

    // 주문최대수량
    private String ordnMxmmQty;

    // 처리상태 ID
    private String pcsnsttsId;

    // 접수유형 ID(구매자, 관리자)
    private String rcipptrnId;

    // 상품 상태 ID(판매중(GDS00001))
    private String pdfSttsId;

    // 상품 진행 여부(진열 여부)
    private String pdfPgrsYn;

    // 이미지 파일 정보
    private String imgFileId;
    private FileInfoVO imgFileInfo;

}
