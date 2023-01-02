package com.ibk.sb.restapi.biz.service.banner;

import com.ibk.sb.restapi.app.common.constant.ComCode;
import com.ibk.sb.restapi.app.common.constant.StatusCode;
import com.ibk.sb.restapi.app.common.util.BizException;
import com.ibk.sb.restapi.app.common.util.FileUtil;
import com.ibk.sb.restapi.app.common.vo.PagingVO;
import com.ibk.sb.restapi.biz.service.banner.repo.BannerRepo;
import com.ibk.sb.restapi.biz.service.banner.vo.BannerStatusVO;
import com.ibk.sb.restapi.biz.service.banner.vo.BannerVO;
import com.ibk.sb.restapi.biz.service.banner.vo.RequestBannerSearchVO;
import com.ibk.sb.restapi.biz.service.file.FileService;
import com.ibk.sb.restapi.biz.service.file.repo.FileRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BannerService {

    private final BannerRepo repo;

    private final FileRepo fileRepo;
    private final FileService fileService;

    private final FileUtil fileUtil;

    /**
     * 베너 리스트 조회
     * @param typeId
     * @return
     */
    public List<BannerVO> searchBannerList(String typeId) throws Exception {
        // 베너 리스트 조회
        List<BannerVO> list = repo.selectBannerList(typeId);
        // 베너 이미지 url 셋팅
        if(list.size() > 0) {
            list.forEach(x -> {
                x.setImgUrl(fileUtil.setImageUrl(x.getImgFileId()));
            });
        }
        return list;
    }

    public PagingVO<BannerVO> searchBannerPaging(RequestBannerSearchVO params) throws Exception {
        // 베너 리스트 조회
        List<BannerVO> list = repo.selectBannerPaging(params);
        list = list == null ? new ArrayList<>() : list;
        // 베너 이미지 url 셋팅
        if(list.size() > 0) {
            list.forEach(x -> {
                x.setImgUrl(fileUtil.setImageUrl(x.getImgFileId()));
                x.setImgFileInfo(fileService.searchFileInfo(x.getImgFileId()));
            });
        }
        return new PagingVO<>(params, list);
    }

    /**
     * 배너 저장
     * @param bannerType
     * @param params
     * @return
     * @throws Exception
     */
    public boolean saveBanner(ComCode bannerType, BannerVO params) throws Exception {

        // 로그인 정보 조회
//            CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

//            params.setRgsnUserId(user.getUsername());
//            params.setAmnnUserId(user.getUsername());

        // 이미지 파일 정보입력
        if(params.getImgFileInfo() != null) params.setFileId(params.getImgFileInfo().getFileId());

        BannerVO info = repo.selectBannerInfo(params.getBanInfId());
        if(info != null) {
            if (repo.updateBannerInfo(bannerType.getCode(), params) < 1) {
                throw new Exception(bannerType.getName() + bannerType.getName() + " 정보 수정 오류 발생");
            }
        } else {
            params.setBanInfId(UUID.randomUUID().toString());
            if (repo.insertBannerInfo(bannerType.getCode(), params) < 1) {
                throw new Exception("배너 정보 입력 오류 발생");
            }
        }
        return true;

        // Controller Exception 처리
//        try {
//
//            // 로그인 정보 조회
////            CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
////            params.setRgsnUserId(user.getUsername());
////            params.setAmnnUserId(user.getUsername());
//
//            // 이미지 파일 정보입력
//            if(params.getImgFileInfo() != null) params.setFileId(params.getImgFileInfo().getFileId());
//
//            BannerVO info = repo.selectBannerInfo(params.getBanInfId());
//            if(info != null) {
//                if (repo.updateBannerInfo(bannerType.getCode(), params) < 1) {
//                    throw new Exception(bannerType.getName() + bannerType.getName() + " 정보 수정 오류 발생");
//                }
//            } else {
//                params.setBanInfId(UUID.randomUUID().toString());
//                if (repo.insertBannerInfo(bannerType.getCode(), params) < 1) {
//                    throw new Exception("배너 정보 입력 오류 발생");
//                }
//            }
//            return true;
//
//
//        } catch (Exception e) {
//            log.error("Fail Trace", e);
//            throw e;
//        }
    }

    /**
     * 배너 삭제
     * @param list
     * @return
     */
    public boolean deleteBanner(List<String> list) throws Exception {

        // CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        BannerVO params = new BannerVO();
        list.forEach(id -> {
            // 로그인 정보 조회
            params.setBanInfId(id);
            // params.setAmnnUserId(user.getUsername());
            int delCnt = repo.deleteBannerInfo(params);
            if(delCnt < 1) throw new BizException(StatusCode.MNB0007);
        });
        return true;
    }

    public BannerVO searchBannerInfo(String banInfId) throws Exception {
        // 상세 정보 조회
        BannerVO detail = repo.selectBannerInfo(banInfId);

        // 파일 상세 정보 조히
        if(detail != null && StringUtils.hasLength(detail.getFileId())) {
            detail.setImgFileInfo(fileRepo.selectFileInfo(detail.getFileId()));
            detail.setImgUrl(fileUtil.setImageUrl(detail.getImgFileId()));
        }
        return detail;
    }

    public BannerStatusVO searchBannerCurrentStatus() {
        return repo.selectBannerCurrentStatus();
    }
}
