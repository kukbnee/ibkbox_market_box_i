package com.ibk.sb.restapi.biz.api.main;

import com.ibk.sb.restapi.app.common.constant.ComCode;
import com.ibk.sb.restapi.app.common.util.BizException;
import com.ibk.sb.restapi.app.common.vo.PageVO;
import com.ibk.sb.restapi.app.common.vo.PagingVO;
import com.ibk.sb.restapi.app.common.vo.ResponseData;
import com.ibk.sb.restapi.biz.service.banner.vo.BannerVO;
import com.ibk.sb.restapi.biz.service.basket.BasketService;
import com.ibk.sb.restapi.biz.service.basket.vo.BasketVO;
import com.ibk.sb.restapi.biz.service.banner.BannerService;
import com.ibk.sb.restapi.biz.service.banner.vo.SummaryBannerVO;
import com.ibk.sb.restapi.biz.service.category.CategoryService;
import com.ibk.sb.restapi.biz.service.category.vo.CategoryVO;
import com.ibk.sb.restapi.biz.service.category.vo.ResponseCategoryVO;
import com.ibk.sb.restapi.biz.service.event.EventService;
import com.ibk.sb.restapi.biz.service.event.vo.RequestSearchEventVO;
import com.ibk.sb.restapi.biz.service.popup.PopupService;
import com.ibk.sb.restapi.biz.service.popup.vo.SummaryPopupVO;
import com.ibk.sb.restapi.biz.service.product.bundle.vo.SummaryBundleInfoVO;
import com.ibk.sb.restapi.biz.service.product.single.vo.SummarySingleProductVO;
import com.ibk.sb.restapi.biz.service.wishlist.WishListService;
import com.ibk.sb.restapi.biz.service.wishlist.vo.RequestSearchWishListVO;

import com.ibk.sb.restapi.biz.service.product.bundle.BundleProductService;
import com.ibk.sb.restapi.biz.service.product.single.SingleProductService;
import com.ibk.sb.restapi.biz.service.product.common.vo.RequestSearchProductVO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping(path = {"/api/main", "/api/mk/v1/main"}, produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
public class MainController {

    private final BannerService bannerService;

    private final SingleProductService singleProductService;

    private final BundleProductService bundleProductService;

    private final EventService eventService;

    private final WishListService wishListService;

    private final CategoryService categoryService;

    private final BasketService basketService;

    private final PopupService popupService;


    /**
     * 메인화면 메인배너(상단) 목록 조회
     * @return
     */
    @ApiOperation(value = "메인화면 메인배너(상단) 목록 조회")
    @GetMapping("/banner/main/list")
    public ResponseData searchMainBannerList() {

        try {
            // 메인 베너 코드
            String typeId = ComCode.BANNER_MAIN.getCode();

            // 베너 리스트 조회
            List<BannerVO> bannerVOList = bannerService.searchBannerList(typeId);

            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(bannerVOList)
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
     * 메인화면 서브배너(중간) 목록 조회
     * @return
     */
    @ApiOperation(value = "메인화면 서브배너(중간) 목록 조회")
    @GetMapping("/banner/list")
    public ResponseData searchSubBannerList() {

        try {
            // 메인 베너 코드
            String banTypeId = ComCode.BANNER_SUB.getCode();

            // 베너 리스트 조회
            List<BannerVO> bannerVOList = bannerService.searchBannerList(banTypeId);

            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(bannerVOList)
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
     * 메인화면 카테고리 목록 조회
     * @param categoryVO
     * @return
     */
    @ApiOperation(value = "메인화면 카테고리 목록 조회")
    @GetMapping("/category")
    public ResponseData searchCategoryList(CategoryVO categoryVO) {
        try {

            CategoryVO requsetCategoryVO = new CategoryVO(categoryVO.getTms1ClsfCd(), categoryVO.getTms2ClsfCd(), categoryVO.getTms3ClsfCd(), categoryVO.getTms4ClsfCd(), categoryVO.getTms5ClsfCd());

            List<ResponseCategoryVO> responseCategoryList = categoryService.searchCategoryList(requsetCategoryVO);

            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(responseCategoryList)
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
     * 메인화면 이벤트 목록 조회
     * @return
     */
    @ApiOperation(value = "메인화면 이벤트 목록 조회")
    @GetMapping("/event/list")
    public ResponseData searchEventMainList(RequestSearchEventVO requestSearchEventVO) {
        try {

            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(eventService.searchEventMainList(requestSearchEventVO))
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
     * 메인화면 인기 상품 목록 조회
     * @return
     */
    @ApiOperation(value = "메인화면 인기 상품 목록 조회")
    @GetMapping("/single/popular/list")
    public ResponseData searchPopularProductList(RequestSearchProductVO requestSearchProductVO) {

        try {
            // 인기상품 플래그 셋팅
            requestSearchProductVO.setPopularFlg("Y");
            // 상품 공통 파일 정보 구분 코드 ID 셋팅 - 상품 이미지
            requestSearchProductVO.setFilePtrnId(ComCode.GDS05001.getCode());
            // 상품 조회 이력 유형 ID 셋팅 - 상품 상세 조회
            requestSearchProductVO.setPrhsPtrnId(ComCode.HTS00001.getCode());

            PagingVO<SummarySingleProductVO> productVOList = singleProductService.searchSingleProductList(requestSearchProductVO);

            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(productVOList)
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
     * 메인화면 묶음 상품 목록 조회
     * @param
     * @return
     */
    @ApiOperation(value = "메인화면 묶음 상품 목록 조회")
    @GetMapping("/bundle/list")
    public ResponseData searchBundleProductList(RequestSearchProductVO requestSearchProductVO) {

        try {

            // 상품 묶음상품 리스트 페이징 셋팅
            requestSearchProductVO.setItemPage(new PageVO(requestSearchProductVO.getPage(), requestSearchProductVO.getRecord()));

            PagingVO<SummaryBundleInfoVO> bundleProductInfoList = bundleProductService.searchBundleProductInfoList(requestSearchProductVO);

            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(bundleProductInfoList)
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
     * 메인화면 셀럽쵸이스 목록 조회
     * @return
     */
    @ApiOperation(value = "메인화면 셀럽쵸이스 목록 조회")
    @GetMapping("/celeb/list")
    public ResponseData searchCelebProductList(RequestSearchProductVO requestProductInfoSearchVO) {

        try {
            requestProductInfoSearchVO.setCelebrityFlg("Y");
            PagingVO<SummarySingleProductVO> productVOList = singleProductService.searchSingleProductList(requestProductInfoSearchVO);

            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(productVOList)
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
     * 메인화면 위시목록 추가
     * @param requestSearchWishListVO
     * @return
     */
    @ApiOperation(value = "메인화면 위시목록 추가")
    @ApiImplicitParam(name = "requestSearchWishListVO", value = "RequestSearchWishListVO")
    @PostMapping("/wish/save")
    public ResponseData saveWish(@RequestBody RequestSearchWishListVO requestSearchWishListVO) {
        try {

            return wishListService.saveWish(requestSearchWishListVO);

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
     * 메인화면 위시목록 삭제
     * @param requestSearchWishListVO
     * @return
     */
    @ApiOperation(value = "메인화면 위시목록 삭제")
    @ApiImplicitParam(name = "requestSearchWishListVO", value = "RequestSearchWishListVO")
    @PostMapping("/wish/delete")
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

    /**
     * 메인화면 장바구니 등록
     * @param basketVO
     * @return
     */
    @ApiOperation(value = "메인화면 장바구니 등록")
    @ApiImplicitParam(name = "basketVO", value = "BasketVO")
    @PostMapping("/basket/save")
    public ResponseData saveBasket(@RequestBody BasketVO basketVO) {

        try {
            return basketService.saveBasket(basketVO);
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
     * 메인화면 팝업 목록 조회
     * @return
     */
    @ApiOperation(value = "메인화면 팝업 목록 조회")
    @GetMapping("/popup/list")
    public ResponseData searchPopupList() {
        try {
            List<SummaryPopupVO> summaryPopupVOList = popupService.searchPopupList();

            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .data(summaryPopupVOList)
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
