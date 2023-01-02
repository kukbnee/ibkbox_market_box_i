package com.ibk.sb.restapi.biz.service.user.vo;

import com.ibk.sb.restapi.app.common.vo.BaseTableVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

/**
 * Name : 구매자 정보
 * Table : TB_BOX_MKT_PUCS_INF_M
 */
@Getter
@Setter
@NoArgsConstructor
@Alias("CustomerVO")
public class CustomerVO extends BaseTableVO {

    // 이용기관(회사) ID
    private String utlinsttId;

    // 구매자 ID
    private String pucsId;

    // 수령인
    private String recv;

    // 우편번호
    private String zpcd;

    // 주소
    private String adr;

    // 상세주소
    private String dtlAdr;

    // 연락처1
    private String cnplone;

    // 연락처2
    private String cnpltwo;

    // 배송지 사용 여부
    private String dlplUseYn;

    // 회원상태 ID
    private String mmbrsttsId;
}
