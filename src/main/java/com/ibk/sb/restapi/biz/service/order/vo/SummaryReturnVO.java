package com.ibk.sb.restapi.biz.service.order.vo;

import com.ibk.sb.restapi.app.common.vo.BaseTableVO;
import com.ibk.sb.restapi.app.common.vo.PageVO;
import com.ibk.sb.restapi.app.common.vo.PagingVO;
import lombok.*;
import org.apache.ibatis.type.Alias;

import java.util.List;

/**
 * 반품 결과 VO
 * */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Alias("SummaryReturnVO")
public class SummaryReturnVO {

    // 받은요청 합계
    private Integer recTotal;

    // 보낸요청 합계
    private Integer senTotal;

    // 반품 목록
    PagingVO<OrderReturnVO> lists;
}
