package com.ibk.sb.restapi.biz.service.batch;

import com.ibk.sb.restapi.app.common.constant.ComCode;
import com.ibk.sb.restapi.biz.service.batch.repo.BatchRepo;
import com.ibk.sb.restapi.biz.service.order.vo.OrderDeliveryVO;
import com.ibk.sb.restapi.biz.service.seller.vo.SellerInfoVO;
import com.ibk.sb.restapi.biz.service.user.vo.CompanyVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BatchService {

    private final BatchRepo batchRepo;

    public void saveMktFfpcInfoAndSelrInfo(List<CompanyVO> companyVOList) {

        /*
         * 가입 사업자 주요 정보 갱신
         */
        log.info("service start");
        log.info("service Name : 가입 사업자 주요 정보 갱신");
        log.info("update list size : " + companyVOList.size());
        companyVOList.forEach(x -> {
            batchRepo.batchMktFfpcInfo(x);
        });
        log.info("service end");

        /*
         * 판매자 정보 갱신
         */
        log.info("service start");
        log.info("service Name : 판매자 정보 갱신");
        log.info("update list size : " + companyVOList.size());
        companyVOList.forEach(x -> {
            SellerInfoVO sellerInfoVO = new SellerInfoVO();
            sellerInfoVO.setSelrUsisId(x.getUtlinsttId());                      // 판매자 이용기관 ID
            sellerInfoVO.setMmbrtypeId(ComCode.REGULAR_MEMBER.getCode());     // 회원타입 ID - 초기값 '정회원'
            sellerInfoVO.setMmbrsttsId(ComCode.SELLER_APPROVED.getCode());      // 회원 상태 ID - 초기값 '승인'
            batchRepo.batchSelrInfo(sellerInfoVO);
        });
        log.info("service end");

        /*
         * 구매자 정보 갱신
         */
        log.info("service start");
        log.info("service Name : 구매자 정보 갱신");
        log.info("update list size : " + companyVOList.size());
        companyVOList.forEach(x -> {
            // 판매자 이용기관 ID
            // 회원타입 ID - 초기값 '정회원'
            batchRepo.batchOrderInfo(x.getUtlinsttId(), ComCode.SELLER_APPROVED.getCode());
        });
        log.info("service end");
    }
}
