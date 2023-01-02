package com.ibk.sb.restapi.biz.service.category.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@NoArgsConstructor
@Alias("CategoryActiveVO")
public class CategoryActiveVO {

    // 카테고리명
    private String ctgyNm;

    // 카테고리 부모코드
    private String ctgyParentCd;

    // 카레고리 번호
    private String ctgyCd;

    // 카테고리 사용여부
    private String useYn;

}
