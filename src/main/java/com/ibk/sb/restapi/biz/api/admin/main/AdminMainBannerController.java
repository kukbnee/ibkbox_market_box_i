package com.ibk.sb.restapi.biz.api.admin.main;

import com.ibk.sb.restapi.app.common.constant.ComCode;
import com.ibk.sb.restapi.app.common.util.BizException;
import com.ibk.sb.restapi.app.common.vo.PagingVO;
import com.ibk.sb.restapi.app.common.vo.ResponseData;
import com.ibk.sb.restapi.biz.service.banner.BannerService;
import com.ibk.sb.restapi.biz.service.banner.vo.BannerStatusVO;
import com.ibk.sb.restapi.biz.service.banner.vo.BannerVO;
import com.ibk.sb.restapi.biz.service.banner.vo.RequestBannerSearchVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"운영자 포탈 - 메인 관리 / 배너 관리 API"})
@RestController
@Slf4j
@RequestMapping(path = {"/api/admin/banner", "/api/mk/v1/admin/banner"}, produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
public class AdminMainBannerController {

    private final BannerService service;

    @ApiOperation(value = "(관리자) 배너별 등록 데이터 수 조회")
    @GetMapping("/reg/count")
    public ResponseData bannerCurrentStatus() {

        try {
            BannerStatusVO result = service.searchBannerCurrentStatus();
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

    @ApiOperation(value = "(관리자) 메인배너 목록 조회")
    @GetMapping("/main/list")
    public ResponseData mainBannerList(RequestBannerSearchVO searchParams) {

        try {
            searchParams.setTypeId(ComCode.BANNER_MAIN.getCode());
            PagingVO<BannerVO> list = service.searchBannerPaging(searchParams);
            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(list)
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

    @ApiOperation(value = "(관리자) 메인배너 상세 조회")
    @ApiImplicitParam(name = "banInfId", value = "배너 ID", dataType = "String")
    @GetMapping("/main/detail")
    public ResponseData mainDetail(@RequestParam("banInfId") String banInfId) {

        try {
            BannerVO detail = service.searchBannerInfo(banInfId);
            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(detail)
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

    @ApiOperation(value = "(관리자) 메인배너 삭제")
    @ApiImplicitParam(name = "list", value = "List<String> ids")
    @PostMapping("/main/delete")
    public ResponseData mainDelete(@RequestBody List<String> list) {

        try {
            boolean result = service.deleteBanner(list);
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

    @ApiOperation(value = "(관리자) 메인배너 등록")
    @ApiImplicitParam(name = "params", value = "BannerVO")
    @PostMapping("/main/save")
    public ResponseData mainSave(@RequestBody BannerVO params){

        try {
            boolean result = service.saveBanner(ComCode.BANNER_MAIN, params);
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

    @ApiOperation(value = "(관리자) 서브배너 목록 조회")
    @GetMapping("/sub/list")
    public ResponseData subBannerList(RequestBannerSearchVO searchParams) {

        try {
            searchParams.setTypeId(ComCode.BANNER_SUB.getCode());
            PagingVO<BannerVO> list = service.searchBannerPaging(searchParams);
            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(list)
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

    @ApiOperation(value = "(관리자) 서브배너 상세 조회")
    @ApiImplicitParam(name = "banInfId", value = "배너 ID", dataType = "String")
    @GetMapping("/sub/detail")
    public ResponseData subDetail(@RequestParam("banInfId") String banInfId) {

        try {
            BannerVO detail = service.searchBannerInfo(banInfId);
            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(detail)
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

    @ApiOperation(value = "(관리자) 서브배너 등록")
    @ApiImplicitParam(name = "params", value = "BannerVO")
    @PostMapping("/sub/save")
    public ResponseData subSave(@RequestBody BannerVO params) {

        try {
            boolean result = service.saveBanner(ComCode.BANNER_SUB, params);
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

    @ApiOperation(value = "(관리자) 서브배너 삭제")
    @ApiImplicitParam(name = "list", value = "List<String> ids")
    @PostMapping("/sub/delete")
    public ResponseData subDelete(@RequestBody List<String> list) {

        try {
            boolean result = service.deleteBanner(list);
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

    @ApiOperation(value = "(관리자) 상품배너 목록 조회")
    @GetMapping("/product/list")
    public ResponseData productBannerList(RequestBannerSearchVO searchParams) {

        try {
            searchParams.setTypeId(ComCode.BANNER_PRODUCT.getCode());
            PagingVO<BannerVO> list = service.searchBannerPaging(searchParams);
            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(list)
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

    @ApiOperation(value = "(관리자) 상품배너 상세 조회")
    @ApiImplicitParam(name = "banInfId", value = "배너 ID", dataType = "String")
    @GetMapping("/product/detail")
    public ResponseData productDetail(@RequestParam("banInfId") String banInfId) {

        try {
            BannerVO detail = service.searchBannerInfo(banInfId);
            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(detail)
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

    @ApiOperation(value = "(관리자) 상품배너 등록")
    @ApiImplicitParam(name = "params", value = "BannerVO")
    @PostMapping("/product/save")
    public ResponseData productSave(@RequestBody BannerVO params) {

        try {
            boolean result = service.saveBanner(ComCode.BANNER_PRODUCT, params);
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

    @ApiOperation(value = "(관리자) 상품배너 삭제")
    @ApiImplicitParam(name = "list", value = "List<String> ids")
    @PostMapping("/product/delete")
    public ResponseData productDelete(@RequestBody List<String> list) {

        try {
            boolean result = service.deleteBanner(list);
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

    @ApiOperation(value = "(관리자) 이벤트배너 목록 조회")
    @GetMapping("/event/list")
    public ResponseData eventBannerList(RequestBannerSearchVO searchParams) {

        try {
            searchParams.setTypeId(ComCode.BANNER_EVENT.getCode());
            PagingVO<BannerVO> list = service.searchBannerPaging(searchParams);
            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(list)
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

    @ApiOperation(value = "(관리자) 이벤트배너 상세 조회")
    @ApiImplicitParam(name = "banInfId", value = "배너 ID", dataType = "String")
    @GetMapping("/event/detail")
    public ResponseData eventDetail(@RequestParam("banInfId") String banInfId) {

        try {
            BannerVO detail = service.searchBannerInfo(banInfId);
            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(detail)
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

    @ApiOperation(value = "(관리자) 이벤트배너 등록")
    @ApiImplicitParam(name = "params", value = "BannerVO")
    @PostMapping("/event/save")
    public ResponseData eventSave(@RequestBody BannerVO params) {

        try {
            boolean result = service.saveBanner(ComCode.BANNER_EVENT, params);
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

    @ApiOperation(value = "(관리자) 이벤트배너 삭제")
    @ApiImplicitParam(name = "list", value = "List<String> ids")
    @PostMapping("/event/delete")
    public ResponseData eventDelete(@RequestBody List<String> list) {

        try {
            boolean result = service.deleteBanner(list);
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

}
