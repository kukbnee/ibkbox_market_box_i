package com.ibk.sb.restapi.biz.service.admin.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@NoArgsConstructor
@Alias("AdminSellerTypeVO")
public class AdminSellerTypeVO {

    private String mmbrsttsId;
    private String mmbrsttsNm;

}
