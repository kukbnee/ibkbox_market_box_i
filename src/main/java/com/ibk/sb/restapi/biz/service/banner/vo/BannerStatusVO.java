package com.ibk.sb.restapi.biz.service.banner.vo;

import com.ibk.sb.restapi.app.common.vo.BaseTableVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@NoArgsConstructor
@Alias("BannerStatusVO")
public class BannerStatusVO {

    private int mainBanCnt;
    private int subBanCnt;
    private int prdtBanCnt;
    private int eventBanCnt;

}
