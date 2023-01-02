package com.ibk.sb.restapi.app.common.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PageVO {

    private Integer page = 1; // pageNum, 현재 페이지

    private Integer record = 10; // amount, 페이지당 보여줄 데이터

    private Integer pageSize = 10; // 페이지네이션에서 보여주는 페이지 번호 범위

    public PageVO(Integer page, Integer record) {
        this.page = page;
        this.record = record;
    }
}
