package com.ibk.sb.restapi.biz.service.basket.repo;

import com.ibk.sb.restapi.biz.service.basket.vo.BasketVO;
import com.ibk.sb.restapi.biz.service.basket.vo.SummaryBasketVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BasketRepo {

    // 장바구니 리스트 조회
    List<SummaryBasketVO> selectBasketList(@Param("pucsId") String pucsId,
                                           @Param("pucsUsisId") String pucsUsisId,
                                           @Param("filePtrnId")String filePtrnId,
                                           @Param("pdfInfoId")String pdfInfoId);
    // 장바구니 등록
    Integer insertBasket(BasketVO basketVO);

    // 장바구니 수정
    Integer updateBasket(BasketVO basketVO);

    // 장바구니 삭제
    Integer deleteBasket(BasketVO basketVO);

    int selectUserBasketCnt(@Param("loginUserId") String loginUserId,
                            @Param("utlinsttId") String utlinsttId);
}
