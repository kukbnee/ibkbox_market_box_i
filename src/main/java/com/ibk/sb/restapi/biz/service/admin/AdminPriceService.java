package com.ibk.sb.restapi.biz.service.admin;

import com.ibk.sb.restapi.app.common.vo.PagingVO;
import com.ibk.sb.restapi.biz.service.admin.repo.AdminPriceRepo;
import com.ibk.sb.restapi.biz.service.admin.vo.*;
import com.ibk.sb.restapi.biz.service.event.vo.SummaryEventVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminPriceService {

    private final AdminPriceRepo repo;

    /**
     * 판매사별 총 판매 금액
     * @param requestPriceSearchVO
     * @return
     * @throws Exception
     */
    public PagingVO<AdminPriceVO> searchSelrList(RequestPriceSearchVO requestPriceSearchVO) throws Exception {

        List<AdminPriceVO> list = repo.searchSelrList(requestPriceSearchVO);

        return new PagingVO<>(requestPriceSearchVO, list);
    }

    /**
     * 에이전시 총 판매 금액
     * @param requestPriceSearchVO
     * @return
     * @throws Exception
     */
    public PagingVO<AdminPriceVO> searchAgencyList(RequestPriceSearchVO requestPriceSearchVO) throws Exception {

        List<AdminPriceVO> list = repo.searchAgencyList(requestPriceSearchVO);

        return new PagingVO<>(requestPriceSearchVO, list);
    }

    /**
     * 이벤트별 총 판매 금액
     * @param requestPriceSearchVO
     * @return
     * @throws Exception
     */
    public PagingVO<SummaryEventVO> searchEventList(RequestPriceSearchVO requestPriceSearchVO) throws Exception {

        List<SummaryEventVO> list = repo.searchEventList(requestPriceSearchVO);

        return new PagingVO<>(requestPriceSearchVO, list);
    }

}
