package com.ibk.sb.restapi.biz.api.mypage;

import com.ibk.sb.restapi.app.common.util.BizException;
import com.ibk.sb.restapi.app.common.vo.ResponseData;
import com.ibk.sb.restapi.biz.service.basket.BasketService;
import com.ibk.sb.restapi.biz.service.basket.vo.BasketVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api(tags = {"커머스 박스 - 마이페이지 장바구니 API"})
@RestController
@Slf4j
@RequestMapping(path={"/api/my/basket", "/api/mk/v1/my/basket"}, produces = {"application/json"})
@RequiredArgsConstructor
public class MyBasketController {

    private final BasketService basketService;

    /**
     * 장바구니 목록 조회
     * @return
     */
    @ApiOperation(value = "장바구니 목록 조회")
    @GetMapping("/list")
    public ResponseData searchBasketList() {

        try {
            // 장바구니 검색결과
            //      key : 이용기관 사업자명, value : 상품 리스트
            Map<String, Object> result = basketService.searchBasketList();

            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(result)
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
     * 장바구니 삭제
     * @param basketList
     * @return
     */
    @ApiOperation(value = "장바구니 삭제")
    @ApiImplicitParam(name = "basketList", value = "List<BasketVO>")
    @PostMapping("/delete")
    public ResponseData deleteBasket(@RequestBody List<BasketVO> basketList) {

        try {
            basketService.deleteBasket(basketList);

            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
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
}
