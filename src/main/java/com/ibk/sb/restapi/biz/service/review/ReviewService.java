package com.ibk.sb.restapi.biz.service.review;

import com.ibk.sb.restapi.app.common.constant.ComCode;
import com.ibk.sb.restapi.app.common.constant.StatusCode;
import com.ibk.sb.restapi.app.common.util.BizException;
import com.ibk.sb.restapi.app.common.util.FileUtil;
import com.ibk.sb.restapi.app.common.vo.CustomUser;
import com.ibk.sb.restapi.app.common.vo.PagingVO;
import com.ibk.sb.restapi.biz.service.file.repo.FileRepo;
import com.ibk.sb.restapi.biz.service.file.vo.FileInfoVO;
import com.ibk.sb.restapi.biz.service.mainbox.MainBoxService;
import com.ibk.sb.restapi.biz.service.mainbox.vo.MainUserVO;
import com.ibk.sb.restapi.biz.service.review.repo.ReviewRepo;
import com.ibk.sb.restapi.biz.service.review.vo.RequestSearchReviewVO;
import com.ibk.sb.restapi.biz.service.review.vo.ReviewFileVO;
import com.ibk.sb.restapi.biz.service.review.vo.ReviewHeaderVO;
import com.ibk.sb.restapi.biz.service.review.vo.ReviewVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepo reviewRepo;

    private final FileRepo fileRepo;

    private final FileUtil fileUtil;

    private final MainBoxService mainBoxService;

    /**
     * 상품 상세 구매후기 헤더정보 조회
     * @param requestSearchReviewVO
     * @return
     */
    public ReviewHeaderVO searchProductDetailReviewHeader(RequestSearchReviewVO requestSearchReviewVO) {

        ReviewHeaderVO reviewHeaderVO = reviewRepo.selectProductDetailReviewHeader(requestSearchReviewVO);

        return reviewHeaderVO;
    }

    /**
     * 상품 구매후기 이미지 목록 조회
     * @param requestSearchReviewVO
     * @return
     */
    public List<ReviewVO> searchProductDetailReviewImageList(RequestSearchReviewVO requestSearchReviewVO) {

        // 구매후기 리스트 조회
        List<ReviewVO> reviewVOList = reviewRepo.selectProductDetailReviewImageList(requestSearchReviewVO);

        // 구매후기 첨부 파일 조회
        reviewVOList.forEach(x -> {
            List<ReviewFileVO> reviewFileVOList = reviewRepo.selectReviewFileList(x.getRevInfId());
            // 이미지 URL 셋팅
            reviewFileVOList.forEach(y -> { y.setImgUrl(fileUtil.setImageUrl(y.getFileId())); });
            x.setReviewFileList(reviewFileVOList);
        });

        return reviewVOList;
    }

    /**
     * 상품 구매후기 리스트 조회
     * @param requestSearchReviewVO
     * @return
     */
    public PagingVO<ReviewVO> searchProductDetailReviewList(RequestSearchReviewVO requestSearchReviewVO) throws Exception {

        // 구매후기 리스트 조회
        List<ReviewVO> reviewVOList = reviewRepo.selectReviewList(requestSearchReviewVO);

        reviewVOList.forEach(x -> {
            // 구매후기 첨부 파일 조회
            List<ReviewFileVO> reviewFileVOList = reviewRepo.selectReviewFileList(x.getRevInfId());
            // 이미지 URL 셋팅
            reviewFileVOList.forEach(y -> { y.setImgUrl(fileUtil.setImageUrl(y.getFileId())); });
            x.setReviewFileList(reviewFileVOList);

            // 작성자명 set
            x.setRgsnUserName(getSelrPucsNm(x.getRgsnUserId(), x.getPucsUsisId()));
        });

        return new PagingVO<>(requestSearchReviewVO, reviewVOList);
    }

    private String getSelrPucsNm(String id, String usisId) {
        String name = null;
        try {
            MainUserVO mainUserVO = mainBoxService.searchMainUser(id, usisId);
            name = mainUserVO.getUserNm();
        } catch (BizException bx) {
            log.error("Business Exception ({}) : {}", bx.getErrorCode(), bx.getErrorMsg());
        } catch (Exception e) {
            log.error("mainBoxService.searchMainUser", e);
        }
        return name;
    }

    /**
     * 상품 상세 구매후기 등록
     * @param reviewVO
     */
    public void saveProductDetailReview(ReviewVO reviewVO) {

        // 로그인 체크
        String loginUserId = null;
        String loginUtlinsttId = null;

        // 로그인 정보가 CustomUser.class 라면 jwt 로그인 조회
        // 로그인 상태가 아니라면 String 타입으로 떨이짐
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof CustomUser) {
            CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            loginUserId = user.getLoginUserId();
            loginUtlinsttId = user.getUtlinsttId();
        }

        reviewVO.setPucsUsisId(loginUtlinsttId);
        reviewVO.setRgsnUserId(loginUserId);
        reviewVO.setAmnnUserId(loginUserId);

        String revInfId = UUID.randomUUID().toString();
        reviewVO.setRevInfId(revInfId); // 구매 후기 ID

        // 상품 상세 구매후기 등록
        reviewRepo.insertReview(reviewVO);

        List<ReviewFileVO> reviewFileVOList = reviewVO.getReviewFileList();
        if(reviewFileVOList != null && reviewFileVOList.size() > 0) {
            String finalLoginUserId = loginUserId;
            reviewFileVOList.forEach(x -> {
                if(StringUtils.hasLength(x.getFileId())) {
                    x.setRevInfId(revInfId);
                    x.setRgsnUserId(finalLoginUserId);
                    x.setAmnnUserId(finalLoginUserId);
                    reviewRepo.insertReviewFile(x);
                }
            });
        }
    }

    /**
     * 상품 상세 구매후기 수정
     * @param reviewVO
     */
    public void updateProductDetailReview(ReviewVO reviewVO) throws Exception {

        // 로그인 체크
        String loginUserId = null;
        String loginUtlinsttId = null;

        // 로그인 정보가 CustomUser.class 라면 jwt 로그인 조회
        // 로그인 상태가 아니라면 String 타입으로 떨이짐
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof CustomUser) {
            CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            loginUserId = user.getLoginUserId();
            loginUtlinsttId = user.getUtlinsttId();
        }

        reviewVO.setAmnnUserId(loginUserId);

        // 기존 리뷰 존재 체크
        RequestSearchReviewVO requestSearchReviewVO = new RequestSearchReviewVO();
        requestSearchReviewVO.setRevInfId(reviewVO.getRevInfId());
        List<ReviewVO> reviewVOList = reviewRepo.selectReviewList(requestSearchReviewVO);
        // 해당 리뷰가 존재하지 않는 경우
        if(reviewVOList.size() < 1) {
            throw new Exception(StatusCode.MNB0004.getMessage());
        }

        // 상세 상품 구매후기 수정
        reviewRepo.updateReview(reviewVO);

        // 기존에 존재하던 상품 구매후기 파일정보는 전부 삭제
        List<ReviewFileVO> oldFileList = reviewRepo.selectReviewFileList(reviewVO.getRevInfId());
        // 기존의 상품 구매후기 파일정보 삭제
        reviewRepo.deleteReviewFile(reviewVO.getRevInfId());
        // 기존 구매후기 파일이 존재한다면 공통 파일 테이블에서 삭제
        if(oldFileList != null && oldFileList.size() > 0) {
            oldFileList.forEach(x -> {
                fileRepo.deleteFileInfo(x.getFileId());
            });
        }

        // 상세 상품 구매후기 수정
        List<ReviewFileVO> reviewFileList = reviewVO.getReviewFileList();
        if(reviewFileList != null && reviewFileList.size() > 0) {
            String finalLoginUserId = loginUserId;
            reviewFileList.forEach(x -> {
                x.setRevInfId(reviewVO.getRevInfId());
                x.setRgsnUserId(finalLoginUserId);
                x.setAmnnUserId(finalLoginUserId);
                // 상품 상세 구매후기 파일 등록
                reviewRepo.insertReviewFile(x);
                // 공통 파일 테이블에서 삭제한 내용
                fileRepo.updateFileInfo(x.getFileId());
            });
        }
    }

    /**
     * 상품 상세 구매후기 삭제
     * @param reviewVO
     */
    public void deleteProductDetailReview(ReviewVO reviewVO) throws Exception {

        // 로그인 체크
        String loginUserId = null;
        String loginUtlinsttId = null;

        // 로그인 정보가 CustomUser.class 라면 jwt 로그인 조회
        // 로그인 상태가 아니라면 String 타입으로 떨이짐
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof CustomUser) {
            CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            loginUserId = user.getLoginUserId();
            loginUtlinsttId = user.getUtlinsttId();
        }

        reviewVO.setPucsUsisId(loginUtlinsttId);
        reviewVO.setRgsnUserId(loginUserId);
        reviewVO.setAmnnUserId(loginUserId);

        RequestSearchReviewVO requestSearchReviewVO = new RequestSearchReviewVO();
        requestSearchReviewVO.setRevInfId(reviewVO.getRevInfId());
        List<ReviewVO> reviewVOList = reviewRepo.selectReviewList(requestSearchReviewVO);

        // 리뷰가 존재하지 않거나, 해당 리뷰가 로그인한 사용자의 이용기관에서 작성된 리뷰가 아닌경우 삭제 불가능
        if(reviewVOList == null || reviewVOList.size() == 0 || !reviewVOList.get(0).getPucsUsisId().equals(loginUtlinsttId)) {
            throw new Exception(StatusCode.MNB0007.getMessage());
        }

        // 구매후기 삭제
        reviewRepo.deleteReview(reviewVO);

        // 기존 구매후기 파일 리스트 조회
        List<ReviewFileVO> reviewFileVOList = reviewRepo.selectReviewFileList(reviewVO.getRevInfId());
        if(reviewFileVOList != null && reviewFileVOList.size() > 0) {
            reviewFileVOList.forEach(x -> {
                fileRepo.deleteFileInfo(x.getFileId());
            });
        }

        // 기존 구매후기 파일 리스트 삭제
        reviewRepo.deleteReviewFile(reviewVO.getRevInfId());
    }


//
//    /**
//     * 구매후기 상세 조회
//     * @param requestSearchReviewVO
//     * @return
//     */
//    public ReviewVO searchReviewDetail(RequestSearchReviewVO requestSearchReviewVO) throws Exception {
//
//        List<ReviewVO> reviewVOList = reviewRepo.selectReviewList(requestSearchReviewVO);
//
//        // 데이터가 존재하지 않는 경우 & 데이터가 한 개 이상 존재하는 경우
//        if(reviewVOList.size() == 0 || reviewVOList.size() > 1) {
//            throw new BizException(StatusCode.COM0005);
//        }
//
//        // 검색 결과 1개 항목 조회
//        ReviewVO reviewVO = reviewVOList.get(0);
//
//        // 구매후기 파일 리스트 조회
//        reviewVO.setReviewFileList(reviewRepo.selectReviewFileList(reviewVO.getUtlinsttId(), reviewVO.getGdsId(), reviewVO.getRevInfId()));
//
//        return reviewVO;
//    }
//
//    /**
//     * 구매후기 등록 / 수정
//     * @param reviewVO
//     * @param request
//     * @param reponse
//     */
//    public void saveReview(ReviewVO reviewVO, HttpServletRequest request, HttpServletResponse reponse) throws Exception {
//
//        // 로그인 정보 조회
//        // User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        String userId = "seonggil121";
//        reviewVO.setAmnnUserId(userId);
//
//        // 구매후기 ID 가 존재하지 않는 경우, 등록
//        if(!StringUtils.hasLength(reviewVO.getRevInfId())) {
//            reviewVO.setRevInfId(UUID.randomUUID().toString());
//            reviewVO.setRgsnUserId(userId);
//            reviewRepo.insertReview(reviewVO);
//        }
//
//        // 리스트가 존재하지 않는 경우,
//        List<ReviewVO> reviewVOList = null; //reviewRepo.selectReviewList(new RequestSearchReviewVO(reviewVO.getUtlinsttId(), reviewVO.getGdsId(), reviewVO.getRevInfId()));
//        if(reviewVOList.size() == 0) {
//            throw new BizException(StatusCode.COM0005);
//        }
//
//        // 수정
//        reviewRepo.updateReview(reviewVO);
//    }
//
//    /**
//     * 구매후기 삭제
//     * @param reviewVO
//     */
//    public void deleteReview(ReviewVO reviewVO) {
//        reviewRepo.deleteReview(reviewVO);
//    }
}
