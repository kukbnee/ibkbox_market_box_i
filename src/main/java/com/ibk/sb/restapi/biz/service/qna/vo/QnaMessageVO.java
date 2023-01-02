package com.ibk.sb.restapi.biz.service.qna.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ibk.sb.restapi.app.common.vo.BaseTableVO;
import com.ibk.sb.restapi.biz.service.mainbox.vo.MainUserVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@Alias("QnaMessageVO")
@JsonIgnoreProperties({
        "usyn", "delyn", "rgsnUserId", "amnnUserId", "amnnTs"
})
public class QnaMessageVO extends BaseTableVO {

    // 문의 정보 ID
    @ApiModelProperty(name = "inqrInfoId", value = "문의 정보 ID")
    private String inqrInfoId;

    // 정보 순번
    @ApiModelProperty(name = "infoSqn", value = "정보 순번")
    private int infoSqn;

    // 발신자 이용기관 ID
    @ApiModelProperty(name = "dpmpUsisId", value = "발신자 이용기관 ID")
    private String dpmpUsisId;

    // 발신자 사용자 ID
    @ApiModelProperty(name = "dpmpUserId", value = "발신자 사용자 ID")
    private String dpmpUserId;

    // 발신자 사용자 정보
    @ApiModelProperty(name = "dpmpUserInfo", value = "발신자 사용자 정보")
    private MainUserVO dpmpUserInfo;

    // 수신자 이용기관
    @ApiModelProperty(name = "rcvrUsisId", value = "수신자 이용기관")
    private String rcvrUsisId;

    // 수신자 사용자 ID
    @ApiModelProperty(name = "rcvrUserId", value = "수신자 사용자 ID")
    private String rcvrUserId;

    // 수신자 사용자 정보
    @ApiModelProperty(name = "rcvrUserInfo", value = "수신자 사용자 정보")
    private MainUserVO rcvrUserInfo;

    // 문의유형 ID
    @ApiModelProperty(name = "inqrptrnId", value = "문의유형 ID")
    private String inqrptrnId;

    // 참조코드 ID
    @ApiModelProperty(name = "rfcdId", value = "참조코드 ID")
    private String rfcdId;

    // 내용
    @ApiModelProperty(name = "con", value = "내용")
    private String con;

    // 메제시 플래그
    //      Y : 일반 메세지
    //      N : 견적 메세지
    @ApiModelProperty(name = "utlinsttId", value = "로그인 유저 이용기관 ID")
    private String messageFlg;

    // 발신 메세지 플래그
    //      Y : 발신 메세지
    //      N : 수신 메세지
    @ApiModelProperty(name = "sentMessageFlg", value = "발신 메세지 플래그", example = "Y : 발신 메세지, N : 수신 메세지")
    private String sentMessageFlg;


    /*
     * Name : 문의 견적 연관정보
     * Table : TB_BOX_MKT_INES_R
     */
    // 견적 연관정보 순번
    @ApiModelProperty(name = "inesSqn", value = "견적 연관정보 순번")
    private String inesSqn;

    // 견적 연관정보 처리상태 ID
    @ApiModelProperty(name = "pcsnSttsId", value = "견적 연관정보 처리상태 ID")
    private String pcsnSttsId;

    /*
     * 조회
     */
    // 주문 정보 ID
    @ApiModelProperty(name = "ordnInfoId", value = "주문 정보 ID")
    private String ordnInfoId;

    // 주문 상태 ID
    @ApiModelProperty(name = "ordnSttsId", value = "주문 상태 ID")
    private String ordnSttsId;

}
