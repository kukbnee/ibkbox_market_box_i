package com.ibk.sb.restapi.biz.service.order.vo.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ibk.sb.restapi.app.common.vo.PageVO;
import com.ibk.sb.restapi.biz.service.order.vo.req.OrderReqProductVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

import java.util.List;

@Getter
@Setter
@Alias("RequestSearchOrderVO")
public class RequestSearchOrderVO extends PageVO {

    // 로그인 이용기관 ID
    @ApiModelProperty(name = "loginUsisId", value = "로그인 이용기관 ID")
    private String loginUsisId;

    // 로그인 사용자 ID
    @ApiModelProperty(name = "loginUserId", value = "로그인 사용자 ID")
    private String loginUserId;

    // 등록 사용자 ID
    @ApiModelProperty(name = "rgsnUserId", value = "등록 사용자 ID")
    private String rgsnUserId;

    // 견적 정보 ID
    @ApiModelProperty(name = "esttInfoId", value = "견적 정보 ID")
    private String esttInfoId;

    // 주문 정보 ID
    @ApiModelProperty(name = "ordnInfoId", value = "주문 정보 ID")
    private String ordnInfoId;

    // 체결 번호 ID
    @ApiModelProperty(name = "cnttNoId", value = "체결 번호 ID")
    private String cnttNoId;

    // 구매자 이용기관 ID
    @ApiModelProperty(name = "pucsUsisId", value = "구매자 이용기관 ID")
    private String pucsUsisId;

    // 구매자 ID
    @ApiModelProperty(name = "pucsId", value = "구매자 ID")
    private String pucsId;

    // 판매자 이용기관 ID
    @ApiModelProperty(name = "selrUsisId", value = "판매자 이용기관 ID")
    private String selrUsisId;

    // 판매자 ID
    @ApiModelProperty(name = "selrId", value = "판매자 ID")
    private String selrId;

    // 상품 목록
    @ApiModelProperty(name = "products", value = "상품 목록")
    private List<OrderReqProductVO> products;

    //////
    // 견적
    //////

    // 처리 상태 ID
    @ApiModelProperty(name = "pcsnSttsId", value = "처리 상태 ID")
    private String pcsnSttsId;

    //////
    // 문의
    //////

    // 내용
    @ApiModelProperty(name = "con", value = "내용")
    private String con;

    //////
    // 상품
    //////

    // 정보 순번
    @ApiModelProperty(name = "ordnPdfInfoSqn", value = "정보 순번")
    private Integer ordnPdfInfoSqn;

    // 정보 순번 리스트
    @ApiModelProperty(name = "ordnPdfInfoSqnList", value = "정보 순번 리스트")
    private List<OrderReqProductVO> ordnPdfInfoSqnList;

    // 주문 상태 ID
    @ApiModelProperty(name = "ordnSttsId", value = "주문 상태 ID")
    private String ordnSttsId;

    // 상품 정보 ID
    @ApiModelProperty(name = "pdfInfoId", value = "상품 정보 ID")
    private String pdfInfoId;

    // 상품명
    @ApiModelProperty(name = "pdfNm", value = "상품명")
    private String pdfNm;

    // 수량
    @ApiModelProperty(name = "pdfQty", value = "수량")
    private Integer pdfQty;

    // 배송 유형 ID
    @ApiModelProperty(name = "dvryPtrnId", value = "배송 유형 ID")
    private String dvryPtrnId;

    // 배송 정보 ID
    @ApiModelProperty(name = "dvryInfoId", value = "배송 정보 ID")
    private String dvryInfoId;

    // 업체 정보 ID
    @ApiModelProperty(name = "entpInfoId", value = "업체 정보 ID")
    private String entpInfoId;

    // 배송비
    @ApiModelProperty(name = "dvrynone", value = "배송비")
    private Integer dvrynone;

    ////////
    // 수령지
    ////////

    // 수령지 우편번호
    @ApiModelProperty(name = "recvZpcd", value = "수령지 우편번호")
    private String recvZpcd;

    // 수령지 주소
    @ApiModelProperty(name = "recvAdr", value = "수령지 주소")
    private String recvAdr;

    // 수령지 상세주소
    @ApiModelProperty(name = "recvDtad", value = "수령지 상세주소")
    private String recvDtad;

    // 수령인
    @ApiModelProperty(name = "recv", value = "수령인")
    private String recv;

    // 수령인 연락처1
    @ApiModelProperty(name = "recvCnplone", value = "수령인 연락처1")
    private String recvCnplone;

    // 수령인 연락처2
    @ApiModelProperty(name = "recvCnpltwo", value = "수령인 연락처2")
    private String recvCnpltwo;

    ////////
    // 출고지
    ////////

    // 출고 우편번호
    @ApiModelProperty(name = "rlontfZpcd", value = "출고 우편번호")
    private String rlontfZpcd;

    // 출고 주소
    @ApiModelProperty(name = "rlontfAdr", value = "출고 주소")
    private String rlontfAdr;

    // 출고 상세주소
    @ApiModelProperty(name = "rlontfDtad", value = "출고 상세주소")
    private String rlontfDtad;

    ///////////
    // 구매자 정보
    ///////////

    // 배송지 사용 여부
    @ApiModelProperty(name = "dlplUseYn", value = "배송지 사용 여부")
    private String dlplUseYn;

    //////////
    // 배송 정보
    //////////

    // 택배사 유형 ID
    @ApiModelProperty(name = "pcsvcmpPtrnId", value = "택배사 유형 ID")
    private String pcsvcmpPtrnId;

    // 기타 택배사명
    @ApiModelProperty(name = "pcsvcmpNm", value = "기타 택배사명")
    private String pcsvcmpNm;

    // 운송의뢰 번호
    @ApiModelProperty(name = "trspreqsNo", value = "운송의뢰 번호")
    private String trspreqsNo;

    // 운송장 번호
    @ApiModelProperty(name = "mainnbNo", value = "운송장 번호")
    private String mainnbNo;

    //////////
    // 결제 정보
    //////////

    // 결제 정보 VO
    @ApiModelProperty(name = "orderReqStlmVO", value = "결제 정보 VO")
    @JsonProperty("orderReqStlmVO")
    private OrderReqStlmVO orderReqStlmVO;

}
