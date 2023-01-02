package com.ibk.sb.restapi.biz.service.banner.vo;

import com.ibk.sb.restapi.app.common.vo.BaseTableVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

/**
 * 리스트 조회용 베너 VO
 */
@Getter
@Setter
@NoArgsConstructor
@Alias("SummaryBannerVO")
public class SummaryBannerVO extends BaseTableVO {

    // 배너 번호 ID
    private String banInfId;

    // 제목
    private String ttl;

    // 내용
    private String con;

    // 링크
    private String link;

    // 배너 이미지 파일 ID
    private String fileId;

    // 배너 이미지 url
    private String filePath;
}
