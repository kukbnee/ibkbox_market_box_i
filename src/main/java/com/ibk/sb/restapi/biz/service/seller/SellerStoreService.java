package com.ibk.sb.restapi.biz.service.seller;

import com.ibk.sb.restapi.app.common.constant.ComCode;
import com.ibk.sb.restapi.app.common.util.FileUtil;
import com.ibk.sb.restapi.app.common.vo.CustomUser;
import com.ibk.sb.restapi.biz.service.file.repo.FileRepo;
import com.ibk.sb.restapi.biz.service.mainbox.MainBoxService;
import com.ibk.sb.restapi.biz.service.mainbox.vo.MainCompanyVO;
import com.ibk.sb.restapi.biz.service.seller.repo.SellerStoreRepo;
import com.ibk.sb.restapi.biz.service.seller.vo.*;
import com.ibk.sb.restapi.biz.service.user.UserService;
import com.ibk.sb.restapi.biz.service.user.vo.CompanyVO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SellerStoreService {

    private final MainBoxService mainBoxService;

    private final UserService userService;

    private final SellerStoreRepo sellerStoreRepo;

    private final FileUtil fileUtil;

    private final FileRepo fileRepo;


    /*********************************************
     * 판매자 스토어
     *********************************************/
    /**
     * 상품 상세 판매자 헤더 정보 조회
     * @param selrUsisId
     * @return
     */
    public SellerStoreHeaderVO searchSellerHeaderInfo(String selrUsisId) throws Exception{

        SellerStoreHeaderVO sellerStoreHeaderVO = new SellerStoreHeaderVO();

        // 기업사업장 주요 정보 조회
        CompanyVO companyVO = userService.searchUserCompanyInfo(selrUsisId);

        if(companyVO == null) {
            return sellerStoreHeaderVO;
        }

        // 이용기관 ID 셋팅
        sellerStoreHeaderVO.setUtlinsttId(companyVO.getUtlinsttId());
        // 이용기관 명 셋팅
        sellerStoreHeaderVO.setBplcNm(companyVO.getBplcNm());
        // 설립연차 셋팅
        sellerStoreHeaderVO.setYearCnt(companyVO.getYearCnt());
        // 설립일자
        sellerStoreHeaderVO.setFondDe(companyVO.getFondDe());

        // 메인BOX 기업정보 조회(이용기관 원장 조회)
        MainCompanyVO mainCompanyVO = mainBoxService.searchMainCompany(selrUsisId);
        // 이용기관 분류 구분(대기업, 중소기업) 셋팅
        sellerStoreHeaderVO.setUseEntrprsSe(mainCompanyVO.getUseEntrprsSe());
        // 연매출 셋팅
        sellerStoreHeaderVO.setImxprtSctnText(mainCompanyVO.getImxprtSctnText());

        // 회사 소개 셋팅
        SellerInfoVO sellerInfoVO = sellerStoreRepo.selectSellerInfo(selrUsisId);
        sellerStoreHeaderVO.setUserCpCon(sellerInfoVO.getUserCpCon());

        return sellerStoreHeaderVO;
    }

    /**
     * 판매자 스토어 카테고리 정보 조회
     * @param selrUsisId
     * @return
     */
    public List<SellerCategoryVO> searchSellerCategoryList(String selrUsisId) {

        // 판매자 스토어 카테고리 정보 조회
        List<SellerCategoryVO> sellerCategoryVOList = sellerStoreRepo.selectSellerCategoryList(selrUsisId);

        return sellerCategoryVOList;
    }

    /**
     * 판매자 스토어 배너이미지 정보 조회
     * @param selrUsisId
     * @return
     *      판매자 스토어 배경이미지, Key : sellerBgImg, Value : SellerFileVO
     *      판매자 스토어 배너 리스트, Key : sellerBannerList,  Value : List<SellerFileVO>
     */
    public Map<String, Object> searchSellerBannerList(String selrUsisId) {

        Map<String, Object> resultMap = new HashMap<>();

        /*
         * 판매자 이미지 정보
         */
        SellerFileVO sellerBgImg = new SellerFileVO();
        List<SellerFileVO> sellerImgList = sellerStoreRepo.selectSellerFileList(selrUsisId, ComCode.SELLERIMG_BG_IMG.getCode());
        // 판매자 이미지 정보(배경) 이 존재하면서, 리스트 사이즈가 1일때
        if(sellerImgList !=null && sellerImgList.size() == 1) {
            sellerBgImg = sellerImgList.get(0);
            sellerBgImg.setImgFileId(sellerBgImg.getFileId());                   // 배경 이미지 ID
            sellerBgImg.setImgUrl(fileUtil.setImageUrl(sellerBgImg.getFileId()));// 배경 이미지 URL
        }
        resultMap.put("sellerBgImg", sellerBgImg);

        /*
         * 판매자 배너이미지 리스트
         */
        List<SellerFileVO> sellerBannerList = sellerStoreRepo.selectSellerFileList(selrUsisId, ComCode.SELLERIMG_BANNER_IMG.getCode());
        // 이미지 URL 셋팅
        if (sellerBannerList.size() > 0) {
            sellerBannerList.forEach(x -> {
                x.setImgUrl(fileUtil.setImageUrl(x.getFileId()));
            });
        }
        resultMap.put("sellerBannerList", sellerBannerList);

        return resultMap;
    }

    /*********************************************
     * 마이페이지
     *********************************************/
    /**
     * 마이페이지 홈화면 디자인 배경/소개 수정
     * @param requestSellerVO
     */
    public void saveSellerDesign(RequestSellerVO requestSellerVO) {

        // 로그인 체크
        String loginUserId = "";
        String utlinsttId = "";

        // 로그인 정보가 CustomUser.class 라면 jwt 로그인 조회
        // 로그인 상태가 아니라면 String 타입으로 떨이짐
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof CustomUser) {
            CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            loginUserId = user.getLoginUserId();
            utlinsttId = user.getUtlinsttId();
        }

        /*
         * 판매자 정보 수정
         */
        SellerInfoVO sellerInfoVO = new SellerInfoVO();
        sellerInfoVO.setSelrUsisId(utlinsttId);
        sellerInfoVO.setUserCpCon(requestSellerVO.getUserCpCon());
        sellerInfoVO.setAmnnUserId(loginUserId);
        sellerStoreRepo.updateSellerInfo(sellerInfoVO);

        /*
         * 판매자 배경 이미지 수정
         */
        SellerFileVO sellerFileVO = new SellerFileVO();
        sellerFileVO.setSelrUsisId(utlinsttId);
        sellerFileVO.setImgptrnId(ComCode.SELLERIMG_BG_IMG.getCode());

        List<SellerFileVO> oldList = sellerStoreRepo.selectSellerFileList(utlinsttId, ComCode.SELLERIMG_BG_IMG.getCode());
        if(oldList.size() > 0) {
            oldList.forEach(x -> {
                // 기존의 배경 이미지 삭제
                sellerStoreRepo.deleteSellerFile(x);
                // 공통 파일 테이블에서 삭제
                fileRepo.deleteFileInfo(x.getFileId());
            });
        }

        // 파일 ID 셋팅
        sellerFileVO.setFileId(requestSellerVO.getSellerBgImgFileId());
        sellerFileVO.setRgsnUserId(loginUserId);
        sellerFileVO.setAmnnUserId(loginUserId);
        sellerFileVO.setOppbYn("Y");
        // 판매자 배경 이미지 작성
        sellerStoreRepo.insertSellerFile(sellerFileVO);
        // 공통 파일 테이블에서 삭제 플래그를 N으로 바꿈
        fileRepo.updateFileInfo(sellerFileVO.getFileId());
    }

    /**
     * 마이페이지 홈화면 디자인 배너 수정
     * @param requestSellerVO
     */
    public void saveSellerBanner(RequestSellerVO requestSellerVO) {

        // 로그인 체크
        String loginUserId = "";
        String utlinsttId = "";

        // 로그인 정보가 CustomUser.class 라면 jwt 로그인 조회
        // 로그인 상태가 아니라면 String 타입으로 떨이짐
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof CustomUser) {
            CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            loginUserId = user.getLoginUserId();
            utlinsttId = user.getUtlinsttId();
        }

        // 기존의 판매자 배너 이미지 조회
//        List<SellerFileVO> oldList = sellerStoreRepo.selectSellerFileList(utlinsttId, ComCode.SELLER_BANNER.getCode());
        List<SellerFileVO> oldList = sellerStoreRepo.selectSellerFileList(utlinsttId, ComCode.SELLERIMG_BANNER_IMG.getCode());
        if(oldList.size() > 0) {
            oldList.forEach(x -> {
                // 기존의 배너 이미지 삭제
                sellerStoreRepo.deleteSellerFile(x);
                // 공통 파일 테이블에서 삭제
                fileRepo.deleteFileInfo(x.getFileId());
            });
        }

        // 배너 이미지 셋팅
        List<SellerFileVO> sellerFileVOList = requestSellerVO.getSellerBannerList();
        String finalUtlinsttId = utlinsttId;
        String finalLoginUserId = loginUserId;
        sellerFileVOList.forEach(x -> {
            x.setSelrUsisId(finalUtlinsttId);
//            x.setImgptrnId(ComCode.SELLER_BANNER.getCode());
            x.setImgptrnId(ComCode.SELLERIMG_BANNER_IMG.getCode());
            x.setRgsnUserId(finalLoginUserId);
            x.setAmnnUserId(finalLoginUserId);
//            x.setOppbYn("Y");
            // 판매자 배경 이미지 작성
            sellerStoreRepo.insertSellerFile(x);
            // 공통 파일 테이블에서 삭제 플래그를 N으로 바꿈
            fileRepo.updateFileInfo(x.getFileId());
        });
    }
}
