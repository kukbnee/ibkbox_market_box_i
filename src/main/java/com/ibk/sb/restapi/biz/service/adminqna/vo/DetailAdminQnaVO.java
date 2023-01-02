package com.ibk.sb.restapi.biz.service.adminqna.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Alias("DetailAdminQnaVO")
public class DetailAdminQnaVO {

    // 문의 내용
    private AdminQnaVO adminQnaVO;

    // 답변 내용
    private List<AdminQnaAnswerVO> adminQnaAnswerVOList;
}
