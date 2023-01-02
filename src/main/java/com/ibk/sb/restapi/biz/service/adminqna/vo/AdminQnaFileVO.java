package com.ibk.sb.restapi.biz.service.adminqna.vo;

import com.ibk.sb.restapi.app.common.vo.BaseTableVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@NoArgsConstructor
@Alias("AdminQnaFileVO")
public class AdminQnaFileVO extends BaseTableVO {

    // 관리자 문의 정보 ID
    private String admInquInfId;

    // 파일 ID
    private String fileId;

    // 정보 순번
    private String infoSqn;

    // 파일 유형 ID
    private String filePtrnId;

    /*
     * Name : 첨부파일 정보
     * Table : TB_BOX_MKT_FILE_ATCH_M
     */
    // 파일 명
    private String fileNm;
}
