package com.ibk.sb.restapi.biz.service.file.vo;

import com.ibk.sb.restapi.app.common.vo.PageVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

import java.util.HashMap;

@Getter
@Setter
@NoArgsConstructor
@Alias("RequestFileInfoSearchVO")
public class RequestFileInfoSearchVO extends PageVO {

    private String fileName;

    private HashMap<String, String> tag;
}
