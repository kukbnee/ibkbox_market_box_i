package com.ibk.sb.restapi.biz.service.faq;

import com.ibk.sb.restapi.app.common.vo.CustomUser;
import com.ibk.sb.restapi.app.common.vo.PagingVO;
import com.ibk.sb.restapi.app.common.vo.ResponseData;
import com.ibk.sb.restapi.biz.service.faq.repo.FaqRepo;
import com.ibk.sb.restapi.biz.service.faq.vo.FaqVO;
import com.ibk.sb.restapi.biz.service.faq.vo.RequestSearchFaqVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FaqService {

    private final FaqRepo faqRepo;

    /**
     * FAQ 리스트 조회
     *@return
     */
    public PagingVO<FaqVO> searchFaqList(RequestSearchFaqVO requestSearchFaqVO) throws Exception {

        return new PagingVO<>(requestSearchFaqVO, faqRepo.selectFaqList(requestSearchFaqVO));
    }

    /**
     * FAQ 리스트 등록
     *@return
     */
    public ResponseData faqSave(FaqVO faqVO) throws Exception {

        // 로그인
//        CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        faqVO.setFaqInfId(UUID.randomUUID().toString()); //번호
//        faqVO.setRgsnUserId(user.getUsername()); //등록자

        return ResponseData.builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(faqRepo.faqSave(faqVO))
                .build();

    }

    /**
     * FAQ 리스트 상세
     *@return
     */
    public ResponseData faqDetail(RequestSearchFaqVO requestSearchFaqVO) throws Exception {

        return ResponseData.builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(faqRepo.selectFaqDetail(requestSearchFaqVO))
                .build();

    }

    /**
     * FAQ 리스트 수정
     *@return
     */
    public ResponseData faqUpdate(FaqVO faqVO) throws Exception {

        // 로그인
//        CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        faqVO.setAmnnUserId(user.getUsername()); //수정자
        return ResponseData.builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(faqRepo.faqUpdate(faqVO))
                .build();

    }

    /**
     * FAQ 리스트 삭제
     *@return
     */
    public ResponseData faqDelete(FaqVO faqVO) throws Exception {


        // 로그인
//        CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        faqVO.setAmnnUserId(user.getUsername()); //수정자
        return ResponseData.builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(faqRepo.faqDelete(faqVO))
                .build();

    }

}
