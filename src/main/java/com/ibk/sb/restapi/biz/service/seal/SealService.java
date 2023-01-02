package com.ibk.sb.restapi.biz.service.seal;

import com.ibk.sb.restapi.app.common.constant.StatusCode;
import com.ibk.sb.restapi.app.common.util.BizException;
import com.ibk.sb.restapi.app.common.util.FileUtil;
import com.ibk.sb.restapi.app.common.vo.CustomUser;
import com.ibk.sb.restapi.biz.service.file.FileService;
import com.ibk.sb.restapi.biz.service.file.vo.FileInfoVO;
import com.ibk.sb.restapi.biz.service.seal.repo.SealRepo;
import com.ibk.sb.restapi.biz.service.seal.vo.SealVo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SealService {

    private final SealRepo sealRepo;

    private final FileService fileService;

    private final FileUtil fileUtil;

    /**
     * 인감정보 조회
     * @return
     * @throws Exception
     */
    public SealVo searchSealInfo(String utlinsttId) throws Exception {

        SealVo sealVo = sealRepo.selectSealInfo(utlinsttId);

        return sealVo;
    }

    /**
     * 인감정보 등록
     * @param sealVo
     * @return
     * @throws Exception
     */
    public SealVo saveSealInfo(SealVo sealVo) throws Exception {

        // 로그인 체크
        // 로그인 정보가 CustomUser.class 라면 jwt 로그인 조회
        // 로그인 상태가 아니라면 String 타입으로 떨이짐
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof CustomUser) {
            CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            sealVo.setRgsnUserId(user.getLoginUserId());
            sealVo.setUtlinsttId(user.getUtlinsttId());
        }

        // 기존의 인감 정보 존재 확인
        SealVo tempSeal = this.searchSealInfo(sealVo.getUtlinsttId());

        if(tempSeal != null) {
//            throw new BizException(StatusCode.COM0005, "이미 존재하는 인감정보입니다");
            sealRepo.deleteSealInfo(sealVo.getUtlinsttId());
        }

        // 인감정보 등록
        sealRepo.insertSealInfo(sealVo);

        // 이미지 URL 셋팅
        sealVo.setImgUrl(fileUtil.setImageUrl(sealVo.getRgslImgFileId()));

        return sealVo;
    }

    /**
     * 인감정보 삭제
     * @param sealVo
     * @return
     * @throws Exception
     */
    public boolean deleteSealInfo(SealVo sealVo) throws Exception {

        // 로그인 체크
        // 로그인 정보가 CustomUser.class 라면 jwt 로그인 조회
        // 로그인 상태가 아니라면 String 타입으로 떨이짐
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof CustomUser) {
            CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            sealVo.setRgsnUserId(user.getLoginUserId());
            sealVo.setUtlinsttId(user.getUtlinsttId());
        }

        // 인감 파일 삭제
        fileService.deleteFile(sealVo.getRgslImgFileId());

        // 인감 정보 삭제
        sealRepo.deleteSealInfo(sealVo.getUtlinsttId());

        return true;
    }

    /**
     * 인감 정보 조회 with base64
     *      인감정보 조회 후, 이미지 파일을 base64 로 인코딩 후 return
     * @param utlinsttId
     * @return
     * @throws Exception
     */
    public SealVo searchSealInfoWithBase64(String utlinsttId) throws Exception {

        // 인감정보 조회
        SealVo sealVo = sealRepo.selectSealInfo(utlinsttId);

        // 인감 정보가 없는 경우, return null
        if(sealVo == null) {
            return null;
        }

        // 인감 파일 정보 조회
        FileInfoVO fileInfoVO = fileService.searchFileInfo(sealVo.getRgslImgFileId());

        // 인감 파일 정보가 없는 경우, return null
        if(fileInfoVO == null) {
            return null;
        }

        // base64 인코딩 후 셋팅
        String base64File = fileUtil.imageFileEncodeBase64(fileInfoVO);
        sealVo.setSignBase64File(base64File);

        return sealVo;
    }
}