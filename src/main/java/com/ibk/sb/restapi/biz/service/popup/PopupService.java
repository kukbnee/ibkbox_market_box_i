package com.ibk.sb.restapi.biz.service.popup;

import com.ibk.sb.restapi.app.common.constant.StatusCode;
import com.ibk.sb.restapi.app.common.util.BizException;
import com.ibk.sb.restapi.app.common.util.FileUtil;
import com.ibk.sb.restapi.app.common.vo.PagingVO;
import com.ibk.sb.restapi.biz.service.file.repo.FileRepo;
import com.ibk.sb.restapi.biz.service.popup.repo.PopupRepo;
import com.ibk.sb.restapi.biz.service.popup.vo.PopupVO;
import com.ibk.sb.restapi.biz.service.popup.vo.RequestPopupSearchVO;
import com.ibk.sb.restapi.biz.service.popup.vo.SummaryPopupVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PopupService {

    private final PopupRepo repo;

    private final FileRepo fileRepo;

    private final FileUtil fileUtil;

    /**
     * 팝업 리스트 조회
     * @return
     */
    public List<SummaryPopupVO> searchPopupList() {

        // 팝업 리스트 조회
        List<SummaryPopupVO> popupVOList = repo.selectPopupList();

        // 팝업 이미지 URL 셋팅
        if(popupVOList.size() > 0) {
            popupVOList.forEach(x -> {
                x.setImgUrl(fileUtil.setImageUrl(x.getImgFileId()));
            });
        }

        return popupVOList;
    }

    public PagingVO<PopupVO> searchPopupPaging(RequestPopupSearchVO params) {
        // 팝업 리스트 조회
        List<PopupVO> list = repo.selectPopupPaging(params);
        list = list == null ? new ArrayList<>() : list;
        // 팝업 이미지 URL 셋팅
        if(list.size() > 0) {
            list.forEach(x -> {
                x.setImgUrl(fileUtil.setImageUrl(x.getImgFileId()));
            });
        }
        return new PagingVO<>(params, list);
    }

    public PopupVO searchPopupInfo(String popupInfId) {
        // 상세 정보 조회
        PopupVO detail = repo.selectPopupInfo(popupInfId);

        // 파일 상세 정보 조히
        if(detail != null && StringUtils.hasLength(detail.getFileId())) {
            detail.setImgFileInfo(fileRepo.selectFileInfo(detail.getFileId()));
            detail.setImgUrl(fileUtil.setImageUrl(detail.getImgFileId()));
        }
        return detail;
    }

    public boolean savePopup(PopupVO params) throws Exception {

        try {

            // 로그인 정보 조회
//            CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

//            params.setRgsnUserId(user.getUsername());
//            params.setAmnnUserId(user.getUsername());

            // 이미지 파일 정보입력
            if(params.getImgFileInfo() != null) params.setFileId(params.getImgFileInfo().getFileId());

            PopupVO info = repo.selectPopupInfo(params.getPopupInfId());
            if(info != null) {
                if (repo.updatePopupInfo(params) < 1) {
                    throw new Exception("정보 수정 오류 발생");
                }
            } else {
                params.setPopupInfId(UUID.randomUUID().toString());
                if (repo.insertPopupInfo(params) < 1) {
                    throw new Exception("배너 정보 입력 오류 발생");
                }
            }
            return true;

        } catch (Exception e) {
            log.error("Fail Trace", e);
            throw e;
        }
    }

    public boolean deletePopup(List<String> list) {

        // CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        PopupVO params = new PopupVO();
        list.forEach(id -> {
            // 로그인 정보 조회
            params.setPopupInfId(id);
            // params.setAmnnUserId(user.getUsername());
            int delCnt = repo.deletePopupInfo(params);
            if(delCnt < 1) throw new BizException(StatusCode.MNB0007);
        });
        return true;
    }
}
