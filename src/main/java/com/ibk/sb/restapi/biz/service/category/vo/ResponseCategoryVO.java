package com.ibk.sb.restapi.biz.service.category.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Alias("ResponseCategoryVO")
public class ResponseCategoryVO extends CategoryVO {

    // 선택용 카테고리 id
    private String clsfCd;

    // 선택용 카테고리 명
    private String clsfNm;

    // 3차분류, 4차분류 트리 구조용 리스트
    List<ResponseCategoryVO> items;
}
