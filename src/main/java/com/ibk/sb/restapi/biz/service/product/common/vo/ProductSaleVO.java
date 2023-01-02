package com.ibk.sb.restapi.biz.service.product.common.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ibk.sb.restapi.app.common.vo.BaseTableVO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Alias("ProductSaleVO")
@JsonIgnoreProperties({
        "usyn", "delyn", "rgsnUserId", "rgsnTs", "amnnUserId", "amnnTs",
        "totalCnt", "rnum"
})
public class ProductSaleVO extends BaseTableVO {

    // 상품 정보 ID
    private String pdfInfoId;

    // 판매가격
    private String pdfPrc;

    // 공통 가격단위 ID
    private String comPrcutId;

    // 공통 상품단위 ID
    private String comPdfutId;

    // 할인가
    private String salePrc;

    // 가격 협의 여부
    private String prcDscsYn;

    // 견적 사용 유무
    private String esttUseEn;

    // 주문수량 제한 사용여부
    private String ordnQtyLmtnUsyn;

    // 주문 최소 수량
    private String ordnMnmmQty;

    // 주문 최대 수량 여부
    private String ordnMxmmQtyYn;

    // 주문 최대 수량
    private String ordnMxmmQty;



}
