package com.ibk.sb.restapi.biz.service.patent.kipris;

import com.ibk.sb.restapi.app.common.constant.StatusCode;
import com.ibk.sb.restapi.app.common.vo.CustomUser;
import com.ibk.sb.restapi.biz.service.patent.kipris.feign.KiprisFeign;
import com.ibk.sb.restapi.biz.service.patent.kipris.vo.RequestSearchKiprisVO;
import com.ibk.sb.restapi.biz.service.patent.kipris.vo.response.*;
import com.ibk.sb.restapi.biz.service.patent.vo.PatentVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class KiprisService {

    private final KiprisFeign kiprisFeign;

    @Value("${feign.kipris-api.key}")
    private String kiprisApiServiceKey;

    /**
     * Kipris 리스트 조회
     * @param requestSearchKiprisVO
     * @return PatentVO
     * @throws Exception
     */
    public List<PatentVO> searchKiprisPagingList(RequestSearchKiprisVO requestSearchKiprisVO) throws Exception {

        // 리턴할 리스트
        List<PatentVO> resultList = new ArrayList<>();

        // 출원인 번호 세팅
        String applicantNum = "";
        if (StringUtils.hasLength(requestSearchKiprisVO.getApplicantNumber())) {
            applicantNum = requestSearchKiprisVO.getApplicantNumber();
        } else {

            String bizNum = null;

            // 로그인 정보가 CustomUser.class 라면 jwt 로그인 조회
            // 로그인 상태가 아니라면 String 타입으로 떨이짐
            if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof CustomUser) {
                CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                // 사업자 번호 셋팅
                bizNum = user.getBizrno();
            }

            // 사업자 번호가 없다면 빈 리스트 리턴
            if (!StringUtils.hasLength(bizNum)) {
                return resultList;
            }

            // 사업장번호 하이픈(-) 처리
            bizNum = bizNum != null ? bizNum.replaceAll("[^0-9]", "") : "";
            if (bizNum.length() != 10) {
                throw new Exception(StatusCode.COM0005.getMessage());
            }
            bizNum = bizNum.substring(0, 3) + "-" + bizNum.substring(3, 5) + "-" + bizNum.substring(5, 10);

            // 출원인번호 조회
            KiprisApplicantResponseVO applicantResponse = kiprisFeign.getApplicant(bizNum, kiprisApiServiceKey);

            // 조회값이 있는 경우 출원인번호 세팅
            if (applicantResponse.getBody().getItems().getCorpBsApplicantInfo() != null && applicantResponse.getBody().getItems().getCorpBsApplicantInfo().size() > 0) {
                applicantNum = applicantResponse.getBody().getItems().getCorpBsApplicantInfo().get(0).getApplicantNumber();
            } else {
                return resultList;
            }
        }

        // 리스트 세팅 및 리턴
        resultList = this.searchKiprisIpList(requestSearchKiprisVO, applicantNum);

        return resultList;
    }

    /**
     * Kipris IP 리스트 조회
     * @param requestSearchKiprisVO
     * @param applicantNum
     * @return
     * @throws Exception
     */
    private List<PatentVO> searchKiprisIpList(RequestSearchKiprisVO requestSearchKiprisVO, String applicantNum) throws Exception {

        // List<KiprisSummaryVO> resultList = new ArrayList<>();
        List<PatentVO> resultList = new ArrayList<>();

        KiprisIpResponseVO countResponseVO  = kiprisFeign.getIpListResponse(applicantNum, requestSearchKiprisVO.getPage(), 1, kiprisApiServiceKey);

        KiprisIpResponseVO ipResponseVO = kiprisFeign.getIpListResponse(applicantNum, requestSearchKiprisVO.getPage(), countResponseVO.getCount().getTotalCount(), kiprisApiServiceKey);

        // 송신 확인
        if(!(ipResponseVO != null && ipResponseVO.getHeader() != null && ipResponseVO.getHeader().getSuccessYN().equals("Y"))) {
            return resultList;
        }

        if (ipResponseVO.getBody().getItems().getItem() != null && ipResponseVO.getBody().getItems().getItem().size() > 0) {
            for (KiprisIpResponseVO.Items.Item item : ipResponseVO.getBody().getItems().getItem()) {
//                KiprisSummaryVO summaryVO = KiprisSummaryVO.builder()
//                        .inventionName(item.getInventionTitle())            // 발명명(특허명)
//                        .ipcNumber(item.getIpcNumber())                     // IPC 번호
//                        .registerStatus(item.getRegisterStatus())           // 등록상태
//                        .applicationNumber(item.getApplicationNumber())     // 출원번호
//                        .applicationDate(item.getApplicationDate())         // 출원일자
//                        .registrationNumber(item.getRegisterNumber())       // 등록번호
//                        .registrationDate(item.getRegisterDate())           // 등록일자
//                        .applicantName(item.getApplicantName())             // 출원인
//                        .build();
                PatentVO summaryVO = PatentVO.builder()
                        .ptntNm(item.getInventionTitle())                     // 발명명(특허명)
                        .ptntIpc(item.getIpcNumber())                         // IPC 번호
                        .ptntStts(item.getRegisterStatus())                   // 등록상태
                        .ptntAlfrNo(item.getApplicationNumber())              // 출원번호
                        .ptntAlfrTs(item.getApplicationDate())                // 출원일자
                        .ptntRtn(item.getRegisterNumber())                    // 등록번호
                        .ptntTs(item.getRegisterDate())                       // 등록일자
                        .ptntRlocaus(item.getApplicantName())                 // 출원인
                        .checked("N")
                        .build();

                resultList.add(summaryVO);
            }

            // 총 개수 처리
            resultList.get(0).setTotalCnt(ipResponseVO.getCount().getTotalCount());
        }

        return resultList;
    }
}
