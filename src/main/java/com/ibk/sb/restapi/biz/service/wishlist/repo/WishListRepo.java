package com.ibk.sb.restapi.biz.service.wishlist.repo;

import com.ibk.sb.restapi.biz.service.wishlist.vo.RequestSearchWishListVO;
import com.ibk.sb.restapi.biz.service.wishlist.vo.SummaryWishListVO;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface WishListRepo {

    List<SummaryWishListVO> selectWishList(RequestSearchWishListVO requestSearchWishListVO);

    public Integer insertWishList(RequestSearchWishListVO requestSearchWishListVO);

    public Integer deleteWish(RequestSearchWishListVO requestSearchWishListVO);
}
