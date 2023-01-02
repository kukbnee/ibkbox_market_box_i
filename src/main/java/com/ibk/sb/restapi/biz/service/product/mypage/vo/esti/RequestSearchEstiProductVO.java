package com.ibk.sb.restapi.biz.service.product.mypage.vo.esti;

import com.ibk.sb.restapi.biz.service.file.vo.FileInfoVO;
import com.ibk.sb.restapi.biz.service.product.common.vo.ProductFileVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

import java.util.List;

/**
 * 마이페이지 상품관리 개별상품 화물서비스 선택 VO
 */
@Getter
@Setter
@Alias("RequestSearchEstiProductVO")
public class RequestSearchEstiProductVO {

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

    // 내품가액
    @ApiModelProperty(name = "dchGdsPrc", value = "내품가액")
    private Integer dchGdsPrc;

    // 최대상품수
    @ApiModelProperty(name = "mxmmGdsCnt", value = "최대상품수")
    private String mxmmGdsCnt;

    // 제품 가로
    @ApiModelProperty(name = "prdtBrdh", value = "제품 가로")
    private String prdtBrdh;

    // 제품 세로
    @ApiModelProperty(name = "prdtVrtc", value = "제품 세로")
    private String prdtVrtc;

    // 제품 높이
    @ApiModelProperty(name = "prdtAhgd", value = "제품 높이")
    private String prdtAhgd;

    // 제품 무게 (kg)
    @ApiModelProperty(name = "prdtWgt", value = "제품 무게 (kg)")
    private String prdtWgt;

    // 견적 수량
    @ApiModelProperty(name = "estiQty", value = "견적 수량")
    private Integer estiQty;

    // 상품명
    @ApiModelProperty(name = "pdfNm", value = "상품명")
    private String pdfNm;

    // 상품 이미지 파일 정보 리스트
    @ApiModelProperty(name = "productFileList", value = "상품 이미지 파일 정보 리스트")
    private List<FileInfoVO> productFileList;

}
