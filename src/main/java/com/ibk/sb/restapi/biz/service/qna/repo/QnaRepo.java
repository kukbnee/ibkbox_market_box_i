package com.ibk.sb.restapi.biz.service.qna.repo;

import com.ibk.sb.restapi.biz.service.qna.vo.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface QnaRepo {

    // 문의 정보　리스트 조회
    List<SummaryQnainfoVO> selectQnaList(RequestSearchQnaVO requestSearchQnaVO);

    // 문의 정보　조회
    SummaryQnainfoVO selectQnaInfo(RequestSearchQnaVO requestSearchQnaVO);

    List<QnaMessageVO> selectQnaMessageList(RequestSearchQnaVO requestSearchQnaVO);

    Integer insertQnaMessage(QnaMessageVO qnaMessageVO);

    // 문의 등록
    Integer insertQnaInfo(QnaInfoVO qnaInfoVO);

    // 메세지 수신확인 이력 등록
    Integer insertQnaMessageCheckHistoy(QnaMessageCheckHistoyVO qnaMessageCheckHistoyVO);

    // 메세지 수신확인 이력 수정
    Integer updateQnaMessageCheckHistoy(QnaMessageCheckHistoyVO qnaMessageCheckHistoyVO);
//
//    Integer updateQna(QnaVO qnaVO);
//
//    Integer deleteQna(QnaVO qnaVO);

    QnaCntVO selectQnaCnt(RequestSearchQnaVO requestSearchQnaVO);
}
