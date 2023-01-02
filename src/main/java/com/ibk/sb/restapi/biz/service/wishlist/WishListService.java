package com.ibk.sb.restapi.biz.service.wishlist;

import com.ibk.sb.restapi.app.common.constant.ComCode;
import com.ibk.sb.restapi.app.common.constant.StatusCode;
import com.ibk.sb.restapi.app.common.util.FileUtil;
import com.ibk.sb.restapi.app.common.vo.CustomUser;
import com.ibk.sb.restapi.app.common.vo.PagingVO;
import com.ibk.sb.restapi.app.common.vo.ResponseData;
import com.ibk.sb.restapi.biz.service.product.single.repo.SingleProductRepo;
import com.ibk.sb.restapi.biz.service.product.single.vo.SingleProductVO;
import com.ibk.sb.restapi.biz.service.wishlist.repo.WishListRepo;
import com.ibk.sb.restapi.biz.service.wishlist.vo.RequestSearchWishListVO;
import com.ibk.sb.restapi.biz.service.wishlist.vo.SummaryWishListVO;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WishListService {

    private final WishListRepo wishListRepo;

    private final SingleProductRepo singleProductRepo;

    private final FileUtil fileUtil;

    /**
     * 위시리스트 조회
     */
    public PagingVO<SummaryWishListVO> selectWishList(RequestSearchWishListVO requestSearchWishListVO) {

        requestSearchWishListVO.setFilePtrnId(ComCode.GDS05001.getCode()); //상품이미지
        requestSearchWishListVO.setPdfSttsId(ComCode.SELLING_OK.getCode()); //판매중
        requestSearchWishListVO.setPdfPgrsYn("Y"); //진행

        /*
         * 로그인 체크
         *      로그인 정보가 CustomUser.class 라면 jwt 로그인 조회
         *      로그인 상태가 아니라면 String 타입으로 떨이짐
         */
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof CustomUser) {
            CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            requestSearchWishListVO.setLoginUtlinsttId(user.getUtlinsttId());
            requestSearchWishListVO.setLoginUserId(user.getLoginUserId());
        }

        // 위시 리스트 조회
        List<SummaryWishListVO> wishListVOList = wishListRepo.selectWishList(requestSearchWishListVO);
        // 위시 리스트 이미지 URL 셋팅
        if(wishListVOList.size() > 0) {
            wishListVOList.forEach(x -> {
                x.setImgUrl(fileUtil.setImageUrl(x.getImgFileId()));
            });
        }

        return new PagingVO<>(requestSearchWishListVO, wishListVOList);
    }

    /**
     * 위시리스트 등록
     * @param requestSearchWishListVO
     */
    public ResponseData saveWish(RequestSearchWishListVO requestSearchWishListVO) throws Exception {

        requestSearchWishListVO.setFilePtrnId(ComCode.GDS05001.getCode()); //상품이미지
        requestSearchWishListVO.setPdfSttsId(ComCode.SELLING_OK.getCode()); //판매중
        requestSearchWishListVO.setPdfPgrsYn("Y"); //진행

        /*
         * 로그인 체크
         *      로그인 정보가 CustomUser.class 라면 jwt 로그인 조회
         *      로그인 상태가 아니라면 String 타입으로 떨이짐
         */
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof CustomUser) {
            CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            requestSearchWishListVO.setLoginUtlinsttId(user.getUtlinsttId());
            requestSearchWishListVO.setLoginUserId(user.getLoginUserId());
        }

        // 판매 중지인 경우. 판매 중지(GDS00002), 관리자 판매 중지(GDS00005)
        SingleProductVO singleProductVO = singleProductRepo.selectPdfStts(requestSearchWishListVO.getPdfInfoId());
        if(singleProductVO.getPdfSttsId().equals(ComCode.STOP_SALES.getCode()) ||
            singleProductVO.getPdfSttsId().equals(ComCode.STOP_SALES_BY_MANAGER.getCode())) {
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(StatusCode.WISH0004.getMessage()) // 판매중지된 상품은 위시리스트에 추가 불가합니다.
                    .build();
        }

        // 위시리스트 등록여부 체크
        if(wishListRepo.selectWishList(requestSearchWishListVO).size() == 0) {

            // 위시리스트 등록
            if(!wishListRepo.insertWishList(requestSearchWishListVO).equals(1)){
                return ResponseData.builder()
                        .code(HttpStatus.BAD_REQUEST.value())
                        .message(HttpStatus.OK.getReasonPhrase())
                        .data(StatusCode.WISH0002.getMessage())
                        .build();
            }

        }else{
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(StatusCode.WISH0001.getMessage())
                    .build();
        }

        return ResponseData.builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .build();
    }

    /**
     * 위시리스트 삭제
     * @param requestSearchWishListVO
     */
    public ResponseData deleteWish(RequestSearchWishListVO requestSearchWishListVO) throws Exception {

        /*
         * 로그인 체크
         *      로그인 정보가 CustomUser.class 라면 jwt 로그인 조회
         *      로그인 상태가 아니라면 String 타입으로 떨이짐
         */
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof CustomUser) {
            CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            requestSearchWishListVO.setLoginUtlinsttId(user.getUtlinsttId());
            requestSearchWishListVO.setLoginUserId(user.getLoginUserId());
        }

        //삭제
        if(wishListRepo.deleteWish(requestSearchWishListVO).equals(0)){
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(StatusCode.WISH0003.getMessage())
                    .build();
        }

        return ResponseData.builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .build();
    }

}
