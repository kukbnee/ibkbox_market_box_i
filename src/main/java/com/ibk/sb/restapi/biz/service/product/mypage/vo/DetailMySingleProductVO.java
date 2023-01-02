package com.ibk.sb.restapi.biz.service.product.mypage.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ibk.sb.restapi.biz.service.delivery.vo.*;
import com.ibk.sb.restapi.biz.service.patent.vo.PatentVO;
import com.ibk.sb.restapi.biz.service.product.common.vo.*;
import com.ibk.sb.restapi.biz.service.product.single.vo.SingleProductVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 마이페이지 상품관리 개별상품 등록 VO
 */
@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Alias("DetailMySingleProductVO")
public class DetailMySingleProductVO {

    // 상품 정보 ID
    @ApiModelProperty(name = "pdfInfoId", value = "상품 정보 ID")
    private String pdfInfoId;

    /*
     * 개별상품 VO
     */
    // 수정시, 검색용 VO
    @ApiModelProperty(name = "singleProduct", value = "개별상품 VO")
    private SingleProductVO singleProduct = null;

    // 상품 카테고리 ID
    @ApiModelProperty(name = "pdfCtgyId", value = "상품 카테고리 ID")
    private String pdfCtgyId;

    // 상품 진행 여부
    @ApiModelProperty(name = "pdfPgrsYn", value = "상품 진행 여부")
    private String pdfPgrsYn;

    // 상품 상태 ID
    @ApiModelProperty(name = "pdfSttsId", value = "상품 상태 ID")
    private String pdfSttsId;

    // 상품명
    @ApiModelProperty(name = "pdfNm", value = "상품명")
    private String pdfNm;

    // 모델명
    @ApiModelProperty(name = "ctgyId", value = "카테고리 ID")
    private String mdlnm;

    // 요약 설명
    @ApiModelProperty(name = "brfDesc", value = "요약 설명")
    private String brfDesc;

    // 간략 설명
    @ApiModelProperty(name = "brfSubDesc", value = "간략 설명")
    private String brfSubDesc;

    // 상세 설명
    @ApiModelProperty(name = "dtlDesc", value = "상세 설명")
    private String dtlDesc;

    // 에이전시 접수 정보 ID
    @ApiModelProperty(name = "agenInfId", value = "에이전시 접수 정보 ID")
    private String agenInfId;

    // 개발상품 VO 작성
    public SingleProductVO getSingleProductVO() {
        // 상품명(필수값) 이 존재하지 않는 경우
        if(!StringUtils.hasLength(this.pdfNm)) {
            return null;
        }
        return new SingleProductVO(null, null, this.pdfCtgyId, this.pdfPgrsYn, this.pdfSttsId, this.pdfNm, this.mdlnm, this.brfDesc, this.brfSubDesc, this.dtlDesc, null, this.agenInfId);
    }

    /*
     * 개별상품 키워드 리스트 VO
     */
    @ApiModelProperty(name = "pdfKwrList", value = "개별상품 키워드 리스트 VO")
    private List<ProductKeywordVO> pdfKwrList;

    /*
     * 상품 판매 정보
     */
    // 수정시, 검색용 VO
    @ApiModelProperty(name = "productSale", value = "상품 판매 정보")
    private ProductSaleVO productSale = null;

    // 판매가격
    @ApiModelProperty(name = "pdfPrc", value = "판매가격")
    private String pdfPrc;

    // 공통 가격단위 ID
    @ApiModelProperty(name = "comPrcutId", value = "공통 가격단위 ID")
    private String comPrcutId;

    // 공통 상품단위 ID
    @ApiModelProperty(name = "comPdfutId", value = "공통 상품단위 ID")
    private String comPdfutId;

    // 할인가
    @ApiModelProperty(name = "salePrc", value = "할인가")
    private String salePrc;

    // 가격 협의 여부
    @ApiModelProperty(name = "prcDscsYn", value = "가격 협의 여부")
    private String prcDscsYn;

    // 견적 사용 유무
    @ApiModelProperty(name = "esttUseEn", value = "견적 사용 유무")
    private String esttUseEn;

    // 주문수량 제한 사용여부
    @ApiModelProperty(name = "ordnQtyLmtnUsyn", value = "주문수량 제한 사용여부")
    private String ordnQtyLmtnUsyn;

    // 주문 최소 수량
    @ApiModelProperty(name = "ordnMnmmQty", value = "주문 최소 수량")
    private String ordnMnmmQty;

    // 주문 최대 수량 여부
    @ApiModelProperty(name = "ordnMxmmQtyYn", value = "주문 최대 수량 여부")
    private String ordnMxmmQtyYn;

    // 주문 최대 수량
    @ApiModelProperty(name = "ordnMxmmQty", value = "주문 최대 수량")
    private String ordnMxmmQty;

    // 상품 판매 정보 VO 작성
    public ProductSaleVO getProductSaleVO() {
        if(!StringUtils.hasLength(this.pdfPrc)) {
            return null;
        }
        return new ProductSaleVO(null, this.pdfPrc, this.comPrcutId, this.comPdfutId, this.salePrc, this.prcDscsYn, this.esttUseEn, this.ordnQtyLmtnUsyn, this.ordnMnmmQty, this.ordnMxmmQtyYn, this.ordnMxmmQty);
    }

    /*
     * 상품 배송 정보
     */
    // 수정시, 검색용 VO
    @ApiModelProperty(name = "deliveryinfo", value = "상품 배송 정보 VO")
    private DeliveryinfoVO deliveryinfo = null;

    // 배송 타입 ID
    @ApiModelProperty(name = "dvryTypeId", value = "배송 타입 ID")
    private String dvryTypeId;

    // 배송 타입 명
    @ApiModelProperty(name = "dvryTypeName", value = "배송 타입 명")
    private String dvryTypeName;

    // 배송 유형 ID
    @ApiModelProperty(name = "dvryPtrnId", value = "배송 유형 ID")
    private String dvryPtrnId;

    // 배송 유형 명
    @ApiModelProperty(name = "dvryPtrnName", value = "배송 유형 명")
    private String dvryPtrnName;

    // 배송비 유형 ID
    @ApiModelProperty(name = "dvrynonePtrnId", value = "배송비 유형 ID")
    private String dvrynonePtrnId;

    // 배송비 유형 명
    @ApiModelProperty(name = "dvrynonePtrnName", value = "배송비 유형 명")
    private String dvrynonePtrnName;

    // 업체 정보 ID
    @ApiModelProperty(name = "entpInfoId", value = "업체 정보 ID")
    private String entpInfoId;

    // 업체 정보 명
    @ApiModelProperty(name = "entpInfoName", value = "업체 정보 명")
    private String entpInfoName;

    // 배송 기본가
    @ApiModelProperty(name = "dvryBscprce", value = "배송 기본가")
    private int dvryBscprce;

    // 상품 배송 정보 VO 작성
    public DeliveryinfoVO getDeliveryinfoVO() {
        if(!StringUtils.hasLength(this.dvryTypeId)) {
            return null;
        }
        return new DeliveryinfoVO(null, this.dvryTypeId, this.dvryTypeName, this.dvryPtrnId, this.dvryPtrnName, this.dvrynonePtrnId, this.dvrynonePtrnName, this.entpInfoId, this.entpInfoName, this.dvryBscprce);
    }

    /*
     * 상품 화물서비스 기본 정보
     */
    // 수정시, 검색용 VO
    @ApiModelProperty(name = "deliveryBaseInfo", value = "상품 화물서비스 기본 정보 VO")
    private DeliveryBaseInfoVO deliveryBaseInfo = null;

    // 파일 ID
    @ApiModelProperty(name = "fileId", value = "파일 ID")
    private String fileId;

    // 출고지 우편번호
    @ApiModelProperty(name = "rlontfZpcd", value = "출고지 우편번호")
    private String rlontfZpcd;

    // 출고지 주소
    @ApiModelProperty(name = "rlontfAdr", value = "출고지 주소")
    private String rlontfAdr;

    // 출고지 상세주소
    @ApiModelProperty(name = "rlontfDtad", value = "출고지 상세주소")
    private String rlontfDtad;

    // 제품포장 단위 ID
    @ApiModelProperty(name = "prdtpcknUtId", value = "제품포장 단위 ID")
    private String prdtpcknUtId;

    // 내품가액(1box당)
    @ApiModelProperty(name = "dchGdsPrc", value = "내품가액(1box당)")
    private String dchGdsPrc;

    // 최대상품수(1box당)
    @ApiModelProperty(name = "mxmmGdsCnt", value = "최대상품수(1box당)")
    private String mxmmGdsCnt;

    // 박스 가로
    @ApiModelProperty(name = "prdtBrdh", value = "박스 가로")
    private String prdtBrdh;

    // 박스 세로
    @ApiModelProperty(name = "prdtVrtc", value = "박스 세로")
    private String prdtVrtc;

    // 박스 높이
    @ApiModelProperty(name = "prdtAhgd", value = "박스 높이")
    private String prdtAhgd;

    // 박스 무게 (kg)
    @ApiModelProperty(name = "prdtWgt", value = "박스 무게 (kg)")
    private String prdtWgt;

    // 상품 화물서비스 기본 정보 VO 작성
    public DeliveryBaseInfoVO getDeliveryBaseInfoVO() {
        if(!StringUtils.hasLength(this.rlontfZpcd)) {
            return null;
        }
        return new DeliveryBaseInfoVO(null, this.fileId, null, this.rlontfZpcd, this.rlontfAdr, this.rlontfDtad, this.prdtpcknUtId, null, this.dchGdsPrc, this.mxmmGdsCnt, this.prdtBrdh, this.prdtVrtc, this.prdtAhgd, this.prdtWgt);
    }

    // 상품 화물서비스 견적 정보 리스트
    @ApiModelProperty(name = "deliveryProductServiceInfoList", value = "상품 화물서비스 견적 정보 리스트(List<DeliveryProductServiceInfoVO>)")
    private List<DeliveryProductServiceInfoVO> deliveryProductServiceInfoList;

    /*
     * 지역별 배송비 리스트
     */
    @ApiModelProperty(name = "deliveryLocalInfoList", value = "지역별 배송비 리스트(List<DeliveryLocalInfoVO>)")
    private List<DeliveryLocalInfoVO> deliveryLocalInfoList;

    /*
     * 수량별 배송비 리스트
     */
    @ApiModelProperty(name = "deliveryCntInfoList", value = "수량별 배송비 리스트(List<DeliveryCntInfoVO>)")
    private List<DeliveryCntInfoVO> deliveryCntInfoList;

    /*
     * 상품 이미지 파일 정보 리스트
     */
    @ApiModelProperty(name = "productFileList", value = "상품 이미지 파일 정보 리스트(List<ProductFileVO>)")
    private List<ProductFileVO> productFileList;

    /*
     * 상품 제품 영상 정보 리스트
     */
    @ApiModelProperty(name = "productVideoList", value = "상품 제품 영상 정보 리스트(List<ProductVideoVO>)")
    private List<ProductVideoVO> productVideoList;

    /*
     * 상품 제품 링크 정보 리스트
     */
    @ApiModelProperty(name = "productLinkList", value = "상품 제품 링크 정보 리스트(List<ProductLinkVO>)")
    private List<ProductLinkVO> productLinkList;

    /*
     * 상품 특허 정보 리스트
     */
    @ApiModelProperty(name = "patentList", value = "상품 특허 정보 리스트(List<PatentVO>)")
    private List<PatentVO> patentList;

    /*
     * 상품 반품/교환 정보
     */
    // 수정시, 검색용 VO
    @ApiModelProperty(name = "productReturn", value = "상품 반품/교환 정보 VO")
    private ProductReturnVO productReturn = null;

    // 반품 교환 기간
    @ApiModelProperty(name = "rtgdInrcTrm", value = "반품 교환 기간")
    private String rtgdInrcTrm;

    // 반품 비용
    @ApiModelProperty(name = "rtgdExp", value = "반품 비용")
    private String rtgdExp;

    // 반품 교환 절차
    @ApiModelProperty(name = "rtgdInrcPrcd", value = "반품 교환 절차")
    private String rtgdInrcPrcd;

    // 반품 교환 불가
    @ApiModelProperty(name = "rtgdInrcDsln", value = "반품 교환 불가")
    private String rtgdInrcDsln;

    // 상품 반품/교환 정보 VO 작성
    public ProductReturnVO getProductReturnVO() {
        if(!StringUtils.hasLength(this.rtgdInrcTrm)) {
            return null;
        }
        return new ProductReturnVO(null, this.rtgdInrcTrm, this.rtgdExp, this.rtgdInrcPrcd, this.rtgdInrcDsln);
    }
}
