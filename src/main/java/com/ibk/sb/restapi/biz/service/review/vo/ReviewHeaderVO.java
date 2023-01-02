package com.ibk.sb.restapi.biz.service.review.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@NoArgsConstructor
@Alias("ReviewHeaderVO")
public class ReviewHeaderVO {

    // 총 구매후기 숫자
    private Long totalReviewCnt;

    // 총 구매후기 점수
    private Double totalReviewValue;
}
