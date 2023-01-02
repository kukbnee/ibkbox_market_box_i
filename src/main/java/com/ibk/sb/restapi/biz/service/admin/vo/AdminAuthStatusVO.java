package com.ibk.sb.restapi.biz.service.admin.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@NoArgsConstructor
@Alias("AdminAuthStatusVO")
public class AdminAuthStatusVO {

    private String pcsnsttsId;
    private String pcsnsttsNm;

}
