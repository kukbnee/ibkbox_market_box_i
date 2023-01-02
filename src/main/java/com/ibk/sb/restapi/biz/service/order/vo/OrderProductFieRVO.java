package com.ibk.sb.restapi.biz.service.order.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ibk.sb.restapi.app.common.vo.BaseTableVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

/**
 * 상품파일정보 VO
 * */
@Getter
@Setter
@NoArgsConstructor
@Alias("OrderProductFieRVO")
@JsonIgnoreProperties({
        "pdfInfoId", "filePtrnId",
        "usyn", "delyn", "rgsnUserId", "rgsnTs", "amnnUserId", "amnnTs",
        "totalCnt", "rgsnUserName", "amnnUserName","rnum", "rvsRnum"
})
public class OrderProductFieRVO extends BaseTableVO {

    // 상품정보ID
    private String pdfInfoId;

    // 상품파일타입
    private String filePtrnId;

    // 파일ID
    private String fileId;

    // 파일명
    private String fileNm;

    // 파일 경로
    private String filePath;

    // 파일 확장자
    private String fileEtns;

    // 파일 유형
    private String filePtrn;

    // 파일 크기
    private String fileSize;

}
