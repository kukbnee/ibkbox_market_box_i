package com.ibk.sb.restapi.biz.service.seller.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ibk.sb.restapi.biz.service.file.vo.FileInfoVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

/**
 * 판매자 이미지 정보
 */
@Getter
@Setter
@NoArgsConstructor
@Alias("SellerFileVO")
@JsonIgnoreProperties({
        "usyn", "delyn", "rgsnUserId", "rgsnTs", "amnnUserId", "amnnTs",
        "totalCnt", "rnum"
})
public class SellerFileVO extends FileInfoVO {

    // 판매자 이용기관 ID
    private String selrUsisId;

    // 파일 ID
    private String fileId;

    // 이미지유형 ID
    private String imgptrnId;

    // 제목
    private String ttl;

    // URL
    private String url;

    // 정보 순번
    private String infoSqn;

    // 공개 여부
    private String oppbYn;

    // 파일 명
    private String fileNm;

    // 등록 일시 문자열
    private String rgsnTsStr;

}
