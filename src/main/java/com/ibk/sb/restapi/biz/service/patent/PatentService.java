package com.ibk.sb.restapi.biz.service.patent;

import com.ibk.sb.restapi.biz.service.patent.repo.PatentRepo;
import com.ibk.sb.restapi.biz.service.patent.vo.PatentVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatentService {

    private final PatentRepo patentRepo;

    /**
     * 상품 특허 정보 리스트
     * @param pdfInfoId
     * @return
     */
    public List<PatentVO> searchPatentList(String pdfInfoId) {

        List<PatentVO> patentVOList = patentRepo.searchPatentList(pdfInfoId);

        return patentVOList;
    }
}
