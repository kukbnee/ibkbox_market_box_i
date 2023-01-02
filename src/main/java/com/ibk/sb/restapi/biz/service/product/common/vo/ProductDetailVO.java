package com.ibk.sb.restapi.biz.service.product.common.vo;

import com.ibk.sb.restapi.biz.service.delivery.vo.DeliveryCntInfoVO;
import com.ibk.sb.restapi.biz.service.delivery.vo.DeliveryLocalInfoVO;
import com.ibk.sb.restapi.biz.service.delivery.vo.DeliveryProductServiceInfoVO;
import com.ibk.sb.restapi.biz.service.delivery.vo.DeliveryinfoVO;
import com.ibk.sb.restapi.biz.service.mainbox.vo.MainCompanyVO;
import com.ibk.sb.restapi.biz.service.order.vo.common.OrderPdfDtlVO;
import com.ibk.sb.restapi.biz.service.patent.vo.PatentVO;
import com.ibk.sb.restapi.biz.service.product.single.vo.SummarySingleProductVO;
import com.ibk.sb.restapi.biz.service.seller.vo.SellerStoreHeaderVO;
import com.ibk.sb.restapi.biz.service.user.vo.MyPageUserVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

import java.util.List;

/**
 * 상품 상세 페이지 통합 VO
 */
@Getter
@Setter
@NoArgsConstructor
@Alias("ProductDetailVO")
public class ProductDetailVO {

    // 판매자 헤더 정보
    private SellerStoreHeaderVO sellerStoreHeader;

    // 상품 파일 정보
    private List<ProductFileVO> productFileList;

    // 상품 영상 정보
    private List<ProductVideoVO> productVideoList;

    // 상품 링크 정보
    private List<ProductLinkVO> productLinkList;

    /*
     * 상품 배송정보
     */
    // 배송 정보
    private DeliveryinfoVO deliveryinfo;

    // 화물 서비스 견적 정보 리스트
    private List<DeliveryProductServiceInfoVO> deliveryProductServiceInfoList;

    // 상품 지역별 배송 정보 리스트
    private List<DeliveryLocalInfoVO> deliveryLocalInfoList;

    // 상품 수량별 배송 정보 리스트
    private List<DeliveryCntInfoVO> deliveryCntInfoList;

    // 상품 정보
    private SummarySingleProductVO productInfo;

    // 특허 정보 리스트
    private List<PatentVO> patentList;

    // 판매자의 다른 상품
    private List<SummarySingleProductVO> sellerProductList;

    // 관련상품 리스트
    private List<SummarySingleProductVO> relatedProductList;

    // 판매자 정보
    private MainCompanyVO sellerInfo;

    // 판매자 정보(회원타입, 회원상태, 통신판매업 신고번호)
    private MyPageUserVO sellerPageUserVO;

    // 반품/교환 정보
    private ProductReturnVO productReturnInfo;

    // 주문 상품 조회
    private OrderPdfDtlVO orderPdfDtlVO;

}
