package com.ibk.sb.restapi.biz.service.admin.repo;

import com.ibk.sb.restapi.biz.service.admin.vo.AdminPriceVO;
import com.ibk.sb.restapi.biz.service.admin.vo.RequestPriceSearchVO;
import com.ibk.sb.restapi.biz.service.event.vo.SummaryEventVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdminPriceRepo {

    //판매사별 총 판매 금액
    List<AdminPriceVO> searchSelrList(RequestPriceSearchVO requestPriceSearchVO);

    //에이전시 총 판매 금액
    List<AdminPriceVO> searchAgencyList(RequestPriceSearchVO requestPriceSearchVO);

    //이벤트별 총 판매 금액
    List<SummaryEventVO> searchEventList(RequestPriceSearchVO requestPriceSearchVO);

}
