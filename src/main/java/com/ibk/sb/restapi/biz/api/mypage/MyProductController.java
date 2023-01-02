package com.ibk.sb.restapi.biz.api.mypage;


import com.ibk.sb.restapi.app.common.util.BizException;
import com.ibk.sb.restapi.app.common.vo.PagingVO;
import com.ibk.sb.restapi.app.common.vo.ResponseData;
import com.ibk.sb.restapi.biz.service.category.CategoryService;
import com.ibk.sb.restapi.biz.service.category.vo.CategoryVO;
import com.ibk.sb.restapi.biz.service.category.vo.ResponseCategoryVO;
import com.ibk.sb.restapi.biz.service.patent.kipris.*;
import com.ibk.sb.restapi.biz.service.patent.kipris.vo.RequestSearchKiprisVO;
import com.ibk.sb.restapi.biz.service.patent.vo.PatentVO;
import com.ibk.sb.restapi.biz.service.product.bundle.vo.BundleProductInfoVO;
import com.ibk.sb.restapi.biz.service.product.buyer.vo.BuyerProductInfoVO;
import com.ibk.sb.restapi.biz.service.product.common.vo.ProductReturnVO;
import com.ibk.sb.restapi.biz.service.product.mypage.MyProductService;
import com.ibk.sb.restapi.biz.service.product.mypage.vo.*;
import com.ibk.sb.restapi.biz.service.product.mypage.vo.esti.RequestSearchEstiProductVO;
import com.ibk.sb.restapi.biz.service.product.single.vo.SingleProductVO;
import com.ibk.sb.restapi.biz.service.user.vo.MyPageUserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = {"커머스 박스 - 마이페이지 상품관리 API"})
@RestController
@Slf4j
@RequestMapping(path={"/api/my/product"}, produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
public class MyProductController {

    private final MyProductService myProductService;

    private final CategoryService categoryService;

    private final KiprisService kiprisService;

    /*********************************************
     * 공통
     *********************************************/

    /**
     * 헤더 정보
     * 마이페이지 상품관리 헤더 정보 조회
     * @return
     */
    @ApiOperation(value = "헤더 정보")
    @GetMapping("/header")
    public ResponseData searchMyProductHeaderInfo() {

        try {
            MyProductHeaderVO myProductHeaderVO = myProductService.searchMyProductHeaderInfo();

            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(myProductHeaderVO)
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

    /*********************************************
     * 개별상품
     *********************************************/

    /**
     * 카테고리 조회
     * 마이페이지 상품관리 개별상품 등록 카테고리 리스트 조회
     * @param categoryVO
     * @return
     */
    @ApiOperation(value = "카테고리 조회")
    @GetMapping("/category/list")
    public ResponseData searchMyProductCategoryList(CategoryVO categoryVO) {

        try {

            CategoryVO requsetCategoryVO = new CategoryVO(categoryVO.getTms1ClsfCd(), categoryVO.getTms2ClsfCd(), categoryVO.getTms3ClsfCd(), categoryVO.getTms4ClsfCd(), categoryVO.getTms5ClsfCd());

            List<ResponseCategoryVO> categoryVOList = categoryService.searchCategoryList(requsetCategoryVO);

            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("list", categoryVOList);
            resultMap.put("dept", requsetCategoryVO.getSearchCategoryNum());

            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(resultMap)
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
     * 마이페이지 상품 카테고리 전체목록 조회
     * @param parentCode
     * @return
     */
    @ApiOperation(value = "마이페이지 상품 카테고리 전체목록 조회")
    @ApiImplicitParam(name = "parentCode", value = "카테고리 부모 코드")
    @GetMapping("/category/depth/list")
    public ResponseData searchCategoryDepthList(@RequestParam("parentCode") String parentCode) {
        try {

            return categoryService.searchCategoryDepthList(parentCode);

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
     * 판매자 정보 조회
     * 마이페이지 상품관리 개별상품 등록 판매자 정보 조회
     * @param
     * @return
     */
    @ApiOperation(value = "판매자 정보 조회")
    @GetMapping("/seller")
    public ResponseData searchMyProductSellerInfo() {

        try {

            MyPageUserVO myPageUserVO = myProductService.searchMyProductSellerInfo();


            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(myPageUserVO)
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
     * 마이페이지 반품/교환 정보 조회
     * 마이페이지 상품관리 개별상품 등록 반품/교환 정보 조회
     * @return
     */
    @ApiOperation(value = "마이페이지 반품/교환 정보 조회")
    @GetMapping("/seller/rtin")
    public ResponseData searchMyProductSellerLastRtin() {
        try {
            ProductReturnVO productReturnVO = myProductService.searchMyProductSellerLastRtin();

            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(productReturnVO)
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
     * 개별상품 목록 조회
     * 마이페이지 상품관리 개별상품 리스트 조회
     * @param requestSearchMyProductVO
     * @return
     */
    @ApiOperation(value = "개별상품 목록 조회")
    @GetMapping("/single/list")
    public ResponseData searchMySingleProductList(RequestSearchMyProductVO requestSearchMyProductVO) {

        try {
            PagingVO<SummaryMySingleProductVO> summaryMySingleProductList  = myProductService.searchMySingleProductList(requestSearchMyProductVO);

            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(summaryMySingleProductList)
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
     * 개별상품 상세
     * 마이페이지 상품관리 개별상품 상세 조회
     * 수정 페이지에서 정보 불러올때 사용
     * @param requestSearchMyProductVO
     * @return
     */
    @ApiOperation(value = "개별상품 상세")
    @GetMapping("/single/detail")
    public ResponseData searchMySingleProduct(RequestSearchMyProductVO requestSearchMyProductVO) {

        try {
            DetailMySingleProductVO detailMySingleProductVO  = myProductService.searchMySingleProduct(requestSearchMyProductVO);

            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(detailMySingleProductVO)
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
     * 특허정보검색
     * 마이페이지 상품관리 개별상품 등록용 특허 리스트 조회
     * 로그인한 유저의 특허 정보 조회
     * @return
     */
    @ApiOperation(value = "특허정보검색")
    @GetMapping("/single/save/patent")
    public ResponseData searchMySingleProductPatentList() {

        RequestSearchKiprisVO requestSearchKiprisVO = new RequestSearchKiprisVO();

        try {
            List<PatentVO> patentVOList = kiprisService.searchKiprisPagingList(requestSearchKiprisVO);

            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(patentVOList)
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
     * 개별상품 등록
     * 마이페이지 상품관리 개별상품 등록 / 수정
     * @param detailMySingleProductVO
     * @return
     */
    @ApiOperation(value = "개별상품 등록")
    @ApiImplicitParam(name = "detailMySingleProductVO", value = "DetailMySingleProductVO")
    @PostMapping("/single/save")
    public ResponseData saveMySingleProduct(@RequestBody DetailMySingleProductVO detailMySingleProductVO) {

        try {
            String resultId = myProductService.saveMySingleProduct(detailMySingleProductVO);

            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(resultId)
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
     * 개별상품 삭제
     * 마이페이지 상품관리 개별상품 삭제
     * @param singleProductVOList
     *          SingleProductVO.pdfInfoId : 상품 ID
     * @return
     */
    @ApiOperation(value = "개별상품 삭제")
    @ApiImplicitParam(name = "singleProductVOList", value = "List<SingleProductVO>", example = "[{'pdfInfoId' : '상품 ID'}]")
    @PostMapping("/single/delete")
    public ResponseData deleteSingleProduct(@RequestBody List<SingleProductVO> singleProductVOList) {
        try {

            // 마이페이지 상품관리 개별상품 삭제
            myProductService.deleteSingleProduct(singleProductVOList);

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

    /**
     * 마이페이지 개별상품 화물서비스 선택
     * 마이페이지 상품관리 개별상품 화물서비스 선택
     * @param requestSearchEstiProductVO
     * @return
     */
    @ApiOperation(value = "마이페이지 개별상품 화물서비스 선택")
    @ApiImplicitParam(name = "requestSearchEstiProductVO", value = "RequestSearchEstiProductVO")
    @PostMapping("/single/delivery/list")
    public ResponseData searchMySingleProductDeliveryList(@RequestBody RequestSearchEstiProductVO requestSearchEstiProductVO) {
        try {
            return myProductService.searchMySingleProductDeliveryList(requestSearchEstiProductVO);
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

    /*********************************************
     * 묶음 상품
     *********************************************/

    /**
     * 묶음상품 목록 조회
     * 마이페이지 상품관리 묶음상품 리스트 조회
     * @param requestSearchMyProductVO
     * @return
     */
    @ApiOperation(value = "묶음상품 목록 조회")
    @GetMapping("/bundle/list")
    public ResponseData searchMyBundleProductInfoList(RequestSearchMyProductVO requestSearchMyProductVO) {

        try {
            PagingVO<SummaryMyBundleProductInfoVO> summaryMyBundleProductList  = myProductService.searchMyBundleProductInfoList(requestSearchMyProductVO);

            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(summaryMyBundleProductList)
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
     * 묶음상품 상품 목록 조회
     * 마이페이지 상품관리 상품 리스트 조회
     * 묶음 상품 과 바이어 상품 같이 사용
     * @param requestSearchMyProductVO
     * @return
     */
    @ApiOperation(value = "묶음상품 상품 목록 조회")
    @GetMapping("/bundle/save/list")
    public ResponseData searchMyProductList(RequestSearchMyProductVO requestSearchMyProductVO) {
        try {
            List<SummaryMyProductVO> resultList = myProductService.searchMyProductList(requestSearchMyProductVO);

            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(resultList)
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
     * 묶음상품 상세
     * 마이페이지 상품관리 묶음상품 상세 조회
     * 수정 페이지에서 정보 불러올때 사용
     * @param requestSearchMyProductVO
     * @return
     */
    @ApiOperation(value = "묶음상품 상세")
    @GetMapping("/bundle/detail")
    public ResponseData searchMyBundleProduct(RequestSearchMyProductVO requestSearchMyProductVO) {

        try {
            DetailMyBundleProductVO detailMyBundleProductVO = myProductService.searchMyBundleProduct(requestSearchMyProductVO);

            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(detailMyBundleProductVO)
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
     * 묶음상품 등록
     * 마이페이지 상품관리 묶음상품 등록 / 수정
     * @param detailMyBundleProductVO
     * @return
     */
    @ApiOperation(value = "묶음상품 등록")
    @ApiImplicitParam(name = "detailMyBundleProductVO", value = "DetailMyBundleProductVO")
    @PostMapping("/bundle/save")
    public ResponseData saveMyBundleProduct(@RequestBody DetailMyBundleProductVO detailMyBundleProductVO) {

        try {
            String resultId = myProductService.saveMyBundleProduct(detailMyBundleProductVO);

            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(resultId)
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
     * 묶음상품 삭제
     * 마이페이지 상품관리 묶음상품 삭제
     * @param bundleProductInfoVOList
     *          bunInfId : 묶음상품 번호 ID
     * @return
     */
    @ApiOperation(value = "묶음상품 삭제")
    @ApiImplicitParam(name = "bundleProductInfoVOList", value = "List<BundleProductInfoVO>", example = "[{'bunInfId' : '묶음상품 번호 ID'}]")
    @PostMapping("/bundle/delete")
    public ResponseData deleteMyBundleProductInfo(@RequestBody List<BundleProductInfoVO> bundleProductInfoVOList) {

        try {
            myProductService.deleteMyBundleProductInfo(bundleProductInfoVOList);

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

    /*********************************************
     * 바이어 상품
     *********************************************/

    /**
     * 바이어상품 목록 조회
     * 마이페이지 상품관리 바이어 상품 정보 리스트 조회
     * @param requestSearchMyProductVO
     * @return
     */
    @ApiOperation(value = "바이어상품 목록 조회")
    @GetMapping("/buyer/list")
    public ResponseData searchMyBuyerProductInfoList(RequestSearchMyProductVO requestSearchMyProductVO) {

        try {
            PagingVO<SummaryMyBuyerProductInfoVO> summaryMyBuyerProductInfoList  = myProductService.searchMyBuyerProductInfoList(requestSearchMyProductVO);

            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(summaryMyBuyerProductInfoList)
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
     * 바이어상품 등록
     * 마이페이지 상품관리 바이어 상품 등록 / 수정
     * @param detailMyBuyerProductVO
     * @return
     */
    @ApiOperation(value = "바이어상품 등록")
    @ApiImplicitParam(name = "detailMyBuyerProductVO", value = "DetailMyBuyerProductVO")
    @PostMapping("/buyer/save")
    public ResponseData saveMyBuyerProduct(@RequestBody DetailMyBuyerProductVO detailMyBuyerProductVO) {

        try {
            String resultId = myProductService.saveMyBuyerProduct(detailMyBuyerProductVO);

            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(resultId)
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
     * 바이어상품 삭제
     * 마이페이지 상품관리 바이어상품 삭제
     * @param buyerProductInfoVOList
     *          buyerInfId : 바이어 상품 번호 ID
     *          rgsnUserId : 바이어 상품 등록사용자 ID
     * @return
     */
    @ApiOperation(value = "바이어상품 등록")
    @ApiImplicitParam(name = "buyerProductInfoVOList", value = "List<BuyerProductInfoVO>")
    @PostMapping("/buyer/delete")
    public ResponseData deleteMyBuyerProductInfo(@RequestBody List<BuyerProductInfoVO> buyerProductInfoVOList) {

        try {
            myProductService.deleteMyBuyerProductInfo(buyerProductInfoVOList);

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

    /**
     * 바이어상품 상세
     * 마이페이지 상품관리 바이어상품 상세 조회
     * 수정 페이지에서 정보 불러올때 사용
     * 바이어 상품 URL 에 들어왔을때 표시용으로 사용
     * @param requestSearchMyProductVO
     * @return
     */
    @ApiOperation(value = "바이어상품 상세")
    @GetMapping("/buyer/detail")
    public ResponseData searchMyBuyerProduct(RequestSearchMyProductVO requestSearchMyProductVO) {

        try {

            DetailMyBuyerProductVO detailMyBuyerProductVO = myProductService.searchMyBuyerProduct(requestSearchMyProductVO);

            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(detailMyBuyerProductVO)
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