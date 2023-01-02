package com.ibk.sb.restapi.biz.service.adminqna.repo;


import com.ibk.sb.restapi.biz.service.adminqna.vo.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AdminQnaRepo {

    List<SummaryAdminQnaVO> searchAdminQnaList(RequestSearchAdminQnaVO requestSearchAdminQnaVO);

    AdminQnaVO searchAdminQna(RequestSearchAdminQnaVO requestSearchAdminQnaVO);

    List<AdminQnaAnswerVO> searchAdminQnaAnswer(RequestSearchAdminQnaVO requestSearchAdminQnaVO);

    List<AdminQnaFileVO> searchAdminQnaFileList(@Param("admInquInfId") String admInquInfId,
                                                @Param("filePtrnId") String filePtrnId);

    // 관리자 문의 등록
    Integer saveAdminQna(AdminQnaVO adminQnaVO);

    // 관리자 문의 파일 정보 등록
    Integer saveAdminQnaFile(AdminQnaFileVO adminQnaFileVO);

    // 관리자 문의 삭제
    Integer deleteAdminQna(AdminQnaVO adminQnaVO);

    // 운영자포털 > 고객지원관리 > 문의 관리 목록
    List<SummaryAdminQnaVO> searchAdminInquiryList(RequestSearchAdminQnaVO requestSearchAdminQnaVO);

    // 운영자포털 > 고객지원관리 > 문의 관리 > 답변 등록
    Integer inquirySave(AdminQnaAnswerVO adminQnaAnswerVO);

    // 운영자포털 > 고객지원관리 > 문의 관리 > 상태 변경
    Integer inquiryStateUpdate(AdminQnaAnswerVO adminQnaAnswerVO);

    // 운영자포털 > 고객지원관리 > 문의 관리 > 답변 수정
    Integer inquiryUpdate(AdminQnaAnswerVO adminQnaAnswerVO);

}
