package com.ibk.sb.restapi.biz.service.product.buyer;

import com.ibk.sb.restapi.biz.service.product.buyer.repo.BuyerProductRepo;
import com.ibk.sb.restapi.biz.service.product.buyer.vo.BuyerProductInfoVO;
import com.ibk.sb.restapi.biz.service.product.buyer.vo.BuyerProductVO;
import com.ibk.sb.restapi.biz.service.product.buyer.vo.RequestBuyerProductInfoSearchVO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BuyerProductService {

    private final BuyerProductRepo buyerProductRepo;

    /**
     * 번들 제품 정보 리스트 조회
     */
    public List<BuyerProductInfoVO> searchBuyerProductInfoList(RequestBuyerProductInfoSearchVO requestBuyerProductInfoSearchVO) throws Exception {

        List<BuyerProductInfoVO> buyerProductInfoVOList = null;

        buyerProductInfoVOList = buyerProductRepo.selectBuyerProductInfoList(requestBuyerProductInfoSearchVO);

        // throws 처리되어 Controller에서 캐치됨
//        try {
//            buyerProductInfoVOList = buyerProductRepo.selectBuyerProductInfoList(requestBuyerProductInfoSearchVO);
//
//        } catch (Exception e) {
//
//        }

        return buyerProductInfoVOList;

    }

    /**
     * 번들 제품 정보 상세 조회
     * @param bunInfId
     * @return
     * @throws Exception
     */
    public BuyerProductInfoVO searchBuyerProductInfo(String bunInfId) throws Exception {

        // 로그인 정보 조회
//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        BuyerProductInfoVO buyerProductInfoVO = null;

        // get BuyerProductInfo
        RequestBuyerProductInfoSearchVO requestBuyerProductInfoSearchVO = new RequestBuyerProductInfoSearchVO();
        requestBuyerProductInfoSearchVO.setBuyerInfId(bunInfId);
        buyerProductInfoVO = buyerProductRepo.selectBuyerProductInfo(requestBuyerProductInfoSearchVO);

        // get BuyerProductInfo Id
        String BuyerProductId = buyerProductInfoVO.getBuyerInfId();

        // insert into request VO
//        RequestBuyerProductInfoSearchVO requestBuyerProductInfoSearchVO = null;
//        requestBuyerProductInfoSearchVO.setBuyerInfId(BuyerProductId);

        // get BuyerProduct List
        //List<BuyerProductVO> buyerProductVOList = buyerProductRepo.selectBuyerProductList(requestBuyerProductInfoSearchVO);

        // insert into BuyerProductInfo VO
        // buyerProductInfoVO.setBuyerProducts(buyerProductVOList);


        return buyerProductInfoVO;
    }
}
