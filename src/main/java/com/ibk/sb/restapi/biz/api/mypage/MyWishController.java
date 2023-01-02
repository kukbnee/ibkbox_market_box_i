package com.ibk.sb.restapi.biz.api.mypage;

import com.ibk.sb.restapi.app.common.util.BizException;
import com.ibk.sb.restapi.app.common.vo.ResponseData;
import com.ibk.sb.restapi.biz.service.wishlist.WishListService;
import com.ibk.sb.restapi.biz.service.wishlist.vo.RequestSearchWishListVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * 마이페이지 > 위시리스트 API
 */
@Api(tags = {"커머스 박스 - 마이페이지 위시리스트 API"})
@RestController
@Slf4j
@RequestMapping(path={"/api/my/wish", "/api/mk/v1/my/wish"}, produces = {"application/json"})
@RequiredArgsConstructor
public class MyWishController {

    private final WishListService wishListService;

    /**
     * 마이페이지 위시목록  목록 조회
     * 위시 리스트 조회
     * @return
     */
    @ApiOperation(value = "마이페이지 위시목록  목록 조회")
    @GetMapping("/list")
    public ResponseData selectWishList(RequestSearchWishListVO requestSearchWishListVO) {
        try {

            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(wishListService.selectWishList(requestSearchWishListVO))
                    .build();

        } catch (BizException ex) {
            log.error(ex.getMessage());
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(ex.getMessage())
                    .build();
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(ex.getMessage())
                    .build();
        }
    }

    /**
     * 마이페이지 위시목록 삭제
     * 위시리스트 삭제
     * @param requestSearchWishListVO
     * @return
     */
    @ApiOperation(value = "마이페이지 위시목록 삭제")
    @ApiImplicitParam(name = "requestSearchWishListVO", value = "RequestSearchWishListVO")
    @PostMapping("/delete")
    public ResponseData deleteWish(@RequestBody RequestSearchWishListVO requestSearchWishListVO) {
        try {

            return wishListService.deleteWish(requestSearchWishListVO);

        } catch (BizException ex) {
            log.error(ex.getMessage());
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(ex.getMessage())
                    .build();
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(ex.getMessage())
                    .build();
        }
    }

}
