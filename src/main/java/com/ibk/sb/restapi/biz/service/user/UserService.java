package com.ibk.sb.restapi.biz.service.user;

import com.ibk.sb.restapi.app.common.constant.ComCode;
import com.ibk.sb.restapi.app.common.constant.StatusCode;
import com.ibk.sb.restapi.app.common.util.FileUtil;
import com.ibk.sb.restapi.app.common.vo.CustomUser;
import com.ibk.sb.restapi.biz.service.alarm.AlarmService;
import com.ibk.sb.restapi.biz.service.alarm.vo.HeaderAlarmListVO;
import com.ibk.sb.restapi.biz.service.basket.repo.BasketRepo;
import com.ibk.sb.restapi.biz.service.file.repo.FileRepo;
import com.ibk.sb.restapi.biz.service.mainbox.MainBoxService;
import com.ibk.sb.restapi.biz.service.mainbox.vo.MainCompanyVO;
import com.ibk.sb.restapi.biz.service.mainbox.vo.MainUserVO;
import com.ibk.sb.restapi.biz.service.order.OrderReqService;
import com.ibk.sb.restapi.biz.service.seal.SealService;
import com.ibk.sb.restapi.biz.service.seal.vo.SealVo;
import com.ibk.sb.restapi.biz.service.seller.repo.SellerStoreRepo;
import com.ibk.sb.restapi.biz.service.seller.vo.SellerFileVO;
import com.ibk.sb.restapi.biz.service.seller.vo.SellerInfoVO;
import com.ibk.sb.restapi.biz.service.user.repo.UserRepo;
import com.ibk.sb.restapi.biz.service.user.vo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;

    private final SellerStoreRepo sellerStoreRepo;

    private final MainBoxService mainBoxService;

    private final BasketRepo basketRepo;

    private final FileRepo fileRepo;

    private final FileUtil fileUtil;

    private final SealService sealService;

    private final AlarmService alarmService;


    /**
     * 헤더 기본 개인 정보 조회(로그인 유저)
     * @return
     */
    public UserHeaderVO searchUserHeaderInfo() throws Exception{

        // 로그인 체크
        String loginUserId = null;
        String utlinsttId = null;

        // 로그인 정보가 CustomUser.class 라면 jwt 로그인 조회
        // 로그인 상태가 아니라면 String 타입으로 떨이짐
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof CustomUser) {
            CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            loginUserId = user.getLoginUserId();
            utlinsttId = user.getUtlinsttId();
        }

        UserHeaderVO userHeaderVO = new UserHeaderVO();

        if(StringUtils.hasLength(loginUserId) && StringUtils.hasLength(utlinsttId)) {
            /*
             * 로그인한 유저 정보
             */
            MainUserVO mainUserVO = mainBoxService.searchMainUser(loginUserId, utlinsttId);
            userHeaderVO.setUserId(loginUserId);
            userHeaderVO.setUserNm(mainUserVO.getUserNm());
            userHeaderVO.setUtlinsttId(utlinsttId);
            userHeaderVO.setBplcNm(mainUserVO.getBplcNm());

            // 회원 타입 ID / 상태 ID 조회
            SellerInfoVO sellerInfoVO = new SellerInfoVO();

            // utlinsttId 가 공백문자만 올 경우 > 개인회원
            if(StringUtils.hasLength(utlinsttId) && utlinsttId != null && StringUtils.hasLength(utlinsttId.trim())) {
                sellerInfoVO = sellerStoreRepo.selectSellerInfo(utlinsttId);
            }

            userHeaderVO.setMmbrtypeId(sellerInfoVO.getMmbrtypeId());
            userHeaderVO.setMmbrsttsId(sellerInfoVO.getMmbrsttsId());
            userHeaderVO.setCsbStmtno(sellerInfoVO.getCsbStmtno());

            // 장바구니 정보
            userHeaderVO.setBasketCnt(basketRepo.selectUserBasketCnt(loginUserId, utlinsttId));

            // 알람 정보 조회
            HeaderAlarmListVO headerAlarmListVO = alarmService.searchReceiveMarketHeaderAlarmList();
            // 새로운 알람 유무 셋팅
            userHeaderVO.setAlarmCnt(headerAlarmListVO.getUnreadYn());

            /*
             * 인감 정보
             */
            SealVo sealVo = sealService.searchSealInfo(utlinsttId);
            if(sealVo != null) {
                userHeaderVO.setRgslImgFileId(sealVo.getRgslImgFileId());
                userHeaderVO.setRgslImgFileUrl(fileUtil.setImageUrl(sealVo.getRgslImgFileId()));
                userHeaderVO.setRgslImgAmnnTs(sealVo.getAmnnTs());
            }
        }

        return userHeaderVO;
    }

    /**
     * 마이페이지 회원(유저) 정보 조회 (기본정보, 회원구분, 판매자 정보)
     * @return
     */
    public MyPageUserVO searchMyPageUserInfo() throws Exception {

        // 로그인 체크
        String loginUserId = null;
        String utlinsttId = null;

        // 로그인 정보가 CustomUser.class 라면 jwt 로그인 조회
        // 로그인 상태가 아니라면 String 타입으로 떨이짐
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof CustomUser) {
            CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            loginUserId = user.getLoginUserId();
            utlinsttId = user.getUtlinsttId();
        }

        // 로그인 정보가 존재하지 않을 경우,
        else {
            // Message : 사용자 정보가 존재하지 않습니다
            throw new Exception(StatusCode.LOGIN_NOT_USER_INFO.getMessage());
        }

        MyPageUserVO resultVO = new MyPageUserVO();

        /*
         * 기본정보 셋팅
         */
        MainUserVO mainUserVO = mainBoxService.searchMainUser(loginUserId, utlinsttId);
        resultVO.setUserId(mainUserVO.getUserId());                     // 사용자의ID
        resultVO.setEmail(mainUserVO.getEmail());                       // 사용자대표이메일
        resultVO.setUserNm(mainUserVO.getUserNm());                     // 메인박스사용자명
        resultVO.setMoblphonNo(mainUserVO.getMoblphonNo());             // 사용자휴대전화번호

        /*
         * 회원 구분
         */
        SellerInfoVO sellerInfoVO = new SellerInfoVO();

        // utlinsttId 가 공백문자만 올 경우 > 개인회원
        if(StringUtils.hasLength(utlinsttId) && StringUtils.hasLength(utlinsttId.trim())) {
            sellerInfoVO = sellerStoreRepo.selectSellerInfo(utlinsttId);
        }
        resultVO.setMmbrtypeId(sellerInfoVO.getMmbrtypeId());           // 회원타입 ID
        resultVO.setMmbrtypeName(sellerInfoVO.getMmbrtypeName());       // 회원타입 명
        resultVO.setMmbrsttsId(sellerInfoVO.getMmbrsttsId());           // 회원상태 ID
        resultVO.setMmbrsttsName(sellerInfoVO.getMmbrsttsName());       // 회원상태 명
        resultVO.setUserCpCon(sellerInfoVO.getUserCpCon());             // 회사 소개
        resultVO.setCsbStmtno(sellerInfoVO.getCsbStmtno());             // 통신사업자 신고번호
        /*
         * 판매자 정보
         */
        MainCompanyVO mainCompanyVO = mainBoxService.searchMainCompany(utlinsttId);
        if (mainCompanyVO != null) {
            resultVO.setUtlinsttId(utlinsttId);                             // 이용기관(회사) ID
            resultVO.setBplcNm(mainCompanyVO.getBplcNm());                  // 이용기관의 사업자명
            resultVO.setRprsntvNm(mainCompanyVO.getRprsntvNm());            // 이용기관 대표자명
            resultVO.setReprsntTelno(mainCompanyVO.getReprsntTelno());      // 이용기관 대표 전화번호
            resultVO.setAdres(mainCompanyVO.getAdres());                    // 이용기관 소재 주소
            resultVO.setDetailAdres(mainCompanyVO.getDetailAdres());        // 이용기관 소재 상세주소
            resultVO.setNwAdres(mainCompanyVO.getNwAdres());                // 이용기관 소재 도로명주소
            resultVO.setNwAdresDetail(mainCompanyVO.getNwAdresDetail());    // 이용기관 소재 도로명주소 상세
            resultVO.setPostNo(mainCompanyVO.getPostNo());                  // 이용기관 우편번호
            resultVO.setBizrno(mainCompanyVO.getBizrno());                  // 사업자등록번호
        }

        /*
         * 판매자 이미지 정보
         */
        List<SellerFileVO> sellerImgList = sellerStoreRepo.selectSellerFileList(utlinsttId, ComCode.SELLERIMG_BG_IMG.getCode());
        // 판매자 이미지 정보(배경) 이 존재하면서, 리스트 사이즈가 1일때
        if(sellerImgList !=null && sellerImgList.size() == 1) {
            SellerFileVO bgImg = sellerImgList.get(0);

            resultVO.setImgFileId(bgImg.getFileId());                   // 배경 이미지 ID
            resultVO.setImgUrl(fileUtil.setImageUrl(bgImg.getFileId()));// 배경 이미지 URL
            resultVO.setImgName(bgImg.getFileNm());
        }

        /*
         * 판매자 배너이미지 리스트
         */
        List<SellerFileVO> bannerInfoList = sellerStoreRepo.selectSellerFileList(utlinsttId, ComCode.SELLERIMG_BANNER_IMG.getCode());
        // 이미지 URL 셋팅
        if (bannerInfoList.size() > 0) {
            bannerInfoList.forEach(x -> {
                x.setImgUrl(fileUtil.setImageUrl(x.getImgFileId()));
            });
        }
        resultVO.setBannerInfoList(bannerInfoList);

        /*
         * 명함 정보
         */
        if(mainUserVO != null) {
            resultVO.setAuthorCodeNm(mainUserVO.getAuthorCodeNm());
        }
        if(mainCompanyVO != null) {
            resultVO.setReprsntFxnum(mainCompanyVO.getReprsntFxnum());
            resultVO.setHmpgAdres(mainCompanyVO.getHmpgAdres());
        }

        /*
         * 인감 정보
         */
        SealVo sealVo = sealService.searchSealInfo(utlinsttId);
        if(sealVo != null) {
            resultVO.setRgslImgFileId(sealVo.getRgslImgFileId());
            resultVO.setRgslImgFileUrl(fileUtil.setImageUrl(sealVo.getRgslImgFileId()));
            resultVO.setRgslImgAmnnTs(sealVo.getAmnnTs());
        }

        return resultVO;
    }

    /**
     * 판매자 정보 조회
     * @param userId : 유저 ID
     * @param selrUsisId : 판매자 이용기관 ID
     * @return
     */
    public SellerInfoVO searchSellerInfo(String userId, String selrUsisId) {

        SellerInfoVO sellerInfoVO = sellerStoreRepo.selectSellerInfo(selrUsisId);

        // selrUsisId 가 공백문자만 올 경우 > 개인회원
        if(!(StringUtils.hasLength(selrUsisId) && StringUtils.hasLength(selrUsisId.trim()))) {
            return null;
        }

        // 판매자 정보가 존재하지 않는 경우 디폴트 판매자 정보 등록
        // 판매자 정보는 로그인한 회원의 기본 정보기이 때문에 조회값이 존재하지 않을 경우 기본값으로 등록함
        if(sellerInfoVO == null && StringUtils.hasLength(selrUsisId)) {
            SellerInfoVO tempSellerInfoVO = new SellerInfoVO();
            tempSellerInfoVO.setSelrUsisId(selrUsisId);
            // 정회원
            tempSellerInfoVO.setMmbrtypeId(ComCode.REGULAR_MEMBER.getCode());
            // 회원 상태 - 승인
            tempSellerInfoVO.setMmbrsttsId(ComCode.SELLER_APPROVED.getCode());
            // 사용 여부 - "Y"
            tempSellerInfoVO.setUsyn("Y");
            tempSellerInfoVO.setRgsnUserId(userId);
            tempSellerInfoVO.setAmnnUserId(userId);
            sellerStoreRepo.insertSellerInfo(tempSellerInfoVO);
            return tempSellerInfoVO;
        }

        return sellerInfoVO;
    }

    /**
     * 기업사업장 주요 정보 조회
     * @param utlinsttId
     * @return
     * @throws Exception
     */
    public CompanyVO searchUserCompanyInfo(String utlinsttId) throws Exception {

        CompanyVO companyVO = userRepo.selectUserCompanyInfo(utlinsttId);

        return companyVO;
    }

    /**
     * 마이페이지 통신판매업신고번호 수정
     * @param csbStmtno
     * @throws Exception
     */
    public void saveSellerInfo(String csbStmtno) throws Exception {

        // 로그인 체크
        String loginUserId = " ";
        String utlinsttId = " ";

        // 로그인 정보가 CustomUser.class 라면 jwt 로그인 조회
        // 로그인 상태가 아니라면 String 타입으로 떨이짐
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof CustomUser) {
            CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            loginUserId = user.getLoginUserId();
            utlinsttId = user.getUtlinsttId();
        }
        // 로그인 정보가 존재하지 않을 경우,
        else {
            // Message : 사용자 정보가 존재하지 않습니다
            throw new Exception(StatusCode.LOGIN_NOT_USER_INFO.getMessage());
        }

        SellerInfoVO sellerInfoVO = new SellerInfoVO();
        sellerInfoVO.setSelrUsisId(utlinsttId);                     // 판매자 이용기관 ID
        sellerInfoVO.setCsbStmtno(csbStmtno);     // 통신판매업 신고번호
        sellerInfoVO.setAmnnUserId(loginUserId);                    // 수정 사용자 ID
        // 판매자 통신판매업 신고번호 수정
        sellerStoreRepo.updateCsbStmtno(sellerInfoVO);
    }
}






