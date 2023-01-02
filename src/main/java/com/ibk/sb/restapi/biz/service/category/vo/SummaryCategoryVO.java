package com.ibk.sb.restapi.biz.service.category.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Alias("SummaryCategoryVO")
public class SummaryCategoryVO {

    // 1차정보
    List<CategoryActiveVO> depthOne;

    // 2차정보
    List<CategoryActiveVO> depthTwo;

    // 3차정보
    List<CategoryActiveVO> depthThr;

    // 4차정보
    List<CategoryActiveVO> depthFor;

    // 5차정보
    List<CategoryActiveVO> depthFiv;

}
