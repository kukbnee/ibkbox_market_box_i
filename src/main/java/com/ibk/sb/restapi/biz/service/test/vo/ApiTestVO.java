package com.ibk.sb.restapi.biz.service.test.vo;

import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@Alias("TestApiVO")
public class ApiTestVO {
    private String id;

    private String name;
}
