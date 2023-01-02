package com.ibk.sb.restapi.biz.service.product.mypage.vo;

import com.ibk.sb.restapi.app.common.vo.BaseTableVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@NoArgsConstructor
@Alias("SummaryMyBuyerProductInfoVO")
public class SummaryMyBuyerProductInfoVO extends BaseTableVO {

    // 바이어 상품 번호 ID
    private String buyerInfId;

    // 판매자 이용기관 ID
    private String selrUsisId;

    // 파일 ID
    private String fileId;

    // 송신자 이메일
    private String senEml;

    // 수신자 이메일
    private String recEml;

    // 제목
    private String ttl;

    // 내용
    private String con;

    // 바이서 상품 갯수
    private String pdfCnt;
}
