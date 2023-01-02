package com.ibk.sb.restapi.biz.service.qna;

import com.ibk.sb.restapi.app.common.constant.AlarmCode;
import com.ibk.sb.restapi.app.common.constant.ComCode;
import com.ibk.sb.restapi.app.common.constant.StatusCode;
import com.ibk.sb.restapi.app.common.util.BizException;
import com.ibk.sb.restapi.app.common.util.FileUtil;
import com.ibk.sb.restapi.app.common.vo.CustomUser;
import com.ibk.sb.restapi.app.common.vo.PagingVO;
import com.ibk.sb.restapi.biz.service.alarm.AlarmService;
import com.ibk.sb.restapi.biz.service.alarm.vo.RequestAlarmVO;
import com.ibk.sb.restapi.biz.service.mainbox.MainBoxService;
import com.ibk.sb.restapi.biz.service.product.single.repo.SingleProductRepo;
import com.ibk.sb.restapi.biz.service.product.single.vo.SingleProductVO;
import com.ibk.sb.restapi.biz.service.qna.repo.QnaRepo;
import com.ibk.sb.restapi.biz.service.qna.vo.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class QnaService {

    private final QnaRepo qnaRepo;

    private final MainBoxService mainBoxService;

    private final FileUtil fileUtil;

    private final AlarmService alarmService;

    private final SingleProductRepo singleProductRepo;

    /**
     * 문의 리스트 조회
     * @param requestSearchQnaVO
     * @return
     * @throws Exception
     */
    public PagingVO<SummaryQnainfoVO> searchQnaList(RequestSearchQnaVO requestSearchQnaVO) throws Exception {

        // 로그인 체크
        String loginUserId = "";
        String utlinsttId = "";

        // 로그인 정보가 CustomUser.class 라면 jwt 로그인 조회
        // 로그인 상태가 아니라면 String 타입으로 떨이짐
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof CustomUser) {
            CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            loginUserId = user.getLoginUserId();
            utlinsttId = user.getUtlinsttId();
        }

        requestSearchQnaVO.setUtlinsttId(utlinsttId);
        requestSearchQnaVO.setLoginUserId(loginUserId);


        // 상품 공통 파일 정보 구분 코드 ID 셋팅 - 상품 이미지
        requestSearchQnaVO.setFilePtrnId(ComCode.GDS05001.getCode());

        List<SummaryQnainfoVO> summaryQnainfoVOList = qnaRepo.selectQnaList(requestSearchQnaVO);

        if(summaryQnainfoVOList.size() > 0) {
            String finalUtlinsttId = utlinsttId;
            summaryQnainfoVOList.forEach(x -> {
                // 문의자 여부 셋팅
                // 로그인한 유저가 문의한 사람(구매자)일 경우, Y
                x.setInquYn(x.getInquUsisId().equals(finalUtlinsttId) ? "Y" : "N");

                // 문의 상품 이미지 URL 셋팅
                x.setImgUrl(fileUtil.setImageUrl(x.getImgFileId()));
            });
        }

        return new PagingVO<>(requestSearchQnaVO, summaryQnainfoVOList);
    }

    public QnaCntVO searchQnaListCnt(RequestSearchQnaVO requestSearchQnaVO) throws Exception {

        // 로그인 체크
        String loginUserId = "";
        String utlinsttId = "";

        // 로그인 정보가 CustomUser.class 라면 jwt 로그인 조회
        // 로그인 상태가 아니라면 String 타입으로 떨이짐
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof CustomUser) {
            CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            loginUserId = user.getLoginUserId();
            utlinsttId = user.getUtlinsttId();
        }

        requestSearchQnaVO.setUtlinsttId(utlinsttId);
        requestSearchQnaVO.setLoginUserId(loginUserId);

        // DB 처리(문의 건수)
        QnaCntVO qnaCntVO = qnaRepo.selectQnaCnt(requestSearchQnaVO);
        return qnaCntVO;
    }

    /**
     * 문의 상세 조회
     * @param requestSearchQnaVO
     * @return
     * @throws Exception
     */
    public SummaryQnainfoVO searchQna(RequestSearchQnaVO requestSearchQnaVO) throws Exception {

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

        requestSearchQnaVO.setUtlinsttId(loginUtlinsttId);
        requestSearchQnaVO.setLoginUserId(loginUserId);

        SummaryQnainfoVO summaryQnainfoVO = qnaRepo.selectQnaInfo(requestSearchQnaVO);
        // 문의 상세 이미지 URL 셋팅
        summaryQnainfoVO.setImgUrl(fileUtil.setImageUrl(summaryQnainfoVO.getImgFileId()));

        return summaryQnainfoVO;
    }

    /**
     * 문의 상세 메세지 리스트 조회
     * @param requestSearchQnaVO
     * @return
     * @throws Exception
     */
    public List<QnaMessageVO> searchQnaMessageList(RequestSearchQnaVO requestSearchQnaVO) throws Exception {



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

        requestSearchQnaVO.setUtlinsttId(utlinsttId);
        requestSearchQnaVO.setLoginUserId(loginUserId);

        // 문의 상세 메세지 리스트 조회
        List<QnaMessageVO> qnaMessageVOList = qnaRepo.selectQnaMessageList(requestSearchQnaVO);
        // 메세지 내역이 존재하는 경우, 수신자 & 발신자 유저정보 조회
        if(qnaMessageVOList != null && qnaMessageVOList.size() > 0) {
            String finalUtlinsttId = utlinsttId;
            String finalLoginUserId = loginUserId;
            qnaMessageVOList.forEach(x -> {

                /*
                 * 수신 메세지 수신확인 이력 수정
                 */
                // 수신자의 이용기관ID 가 로그인한 유저의 이용기관ID인 메세지에 한에서 수신확인 이력 수정
                if(x.getRcvrUsisId().equals(finalUtlinsttId)) {
                    // 메세지 수신확인 이력 등록
                    QnaMessageCheckHistoyVO qnaMessageCheckHistoyVO = new QnaMessageCheckHistoyVO();
                    qnaMessageCheckHistoyVO.setInqrInfoId(x.getInqrInfoId());
                    qnaMessageCheckHistoyVO.setInfoSqn(x.getInfoSqn());
                    qnaMessageCheckHistoyVO.setAmnnUserId(finalLoginUserId);
                    qnaRepo.updateQnaMessageCheckHistoy(qnaMessageCheckHistoyVO);
                }

                /*
                 * 메세지 사용자 정보 등록
                 */
                try {
                    if(StringUtils.hasLength(x.getRcvrUserId()) && StringUtils.hasLength(x.getRcvrUsisId())) {
                        // 수신자 사용자 정보 등록
                        x.setRcvrUserInfo(mainBoxService.searchMainUser(x.getRcvrUserId(), x.getRcvrUsisId()));
                    }
                    if(StringUtils.hasLength(x.getDpmpUserId()) && StringUtils.hasLength(x.getDpmpUsisId())) {
                        // 발신자 사용자 정보 등록
                        x.setDpmpUserInfo(mainBoxService.searchMainUser(x.getDpmpUserId(), x.getDpmpUsisId()));
                    }
                } catch (BizException bx) {
                    log.error("Business Exception ({}) : {}", bx.getErrorCode(), bx.getErrorMsg());
                } catch (Exception e) {
                    log.error("Fail Trace", e);
                }
            });
        }
        return qnaMessageVOList;
    }

    /**
     * 상품 상세 문의하기 버튼 클릭(문의 정보 등록)
     * @param qnaInfoVO
     * @return
     */
    public String saveQnaInfo(QnaInfoVO qnaInfoVO) throws Exception {

        String loginUserId = null;
        String loginUtlinsttId = null;

        /*
         * 로그인 체크
         *      로그인 정보가 CustomUser.class 라면 jwt 로그인 조회
         *      로그인 상태가 아니라면 String 타입으로 떨이짐
         */
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof CustomUser) {
            CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            loginUserId = user.getLoginUserId();
            loginUtlinsttId = user.getUtlinsttId();
        } else {
            throw new Exception(StatusCode.LOGIN_NOT_USER_INFO.getMessage());
        }

        // 문의 정보 ID
        String inqrInfoId = qnaInfoVO.getInqrInfoId();

        RequestSearchQnaVO requestSearchQnaVO = new RequestSearchQnaVO();
        boolean searchFlg = false;
        // 문의 정보 ID 가 존재하는 경우, 문의 정보 ID 만 셋팅
        if(StringUtils.hasLength(inqrInfoId)) {
            requestSearchQnaVO.setInqrInfoId(inqrInfoId);
            searchFlg = true;
        }

        // 문의 정보 ID 는 없지만 상품 ID 가 있는 경우
        else if(StringUtils.hasLength(qnaInfoVO.getPdfInfoId())) {
            requestSearchQnaVO.setUtlinsttId(loginUtlinsttId);
            requestSearchQnaVO.setLoginUserId(loginUserId);
            requestSearchQnaVO.setPdfInfoId(qnaInfoVO.getPdfInfoId());
            searchFlg = true;
        }

        SummaryQnainfoVO summaryQnainfoVO = new SummaryQnainfoVO();

        // 프론트로부터 문의를 검색할 수 있는 파라메터를 받은 경우 문의 정보 조회
        if(searchFlg) {
            requestSearchQnaVO.setSendFlg("sen");
            summaryQnainfoVO = qnaRepo.selectQnaInfo(requestSearchQnaVO);
        }

        // 문의 상세 조회가 존재하지 않는 경우
        if(summaryQnainfoVO == null || !StringUtils.hasLength(summaryQnainfoVO.getInqrInfoId())) {
            QnaInfoVO newQnaVO = new QnaInfoVO();
            inqrInfoId = UUID.randomUUID().toString();
            newQnaVO.setInqrInfoId(inqrInfoId);
            newQnaVO.setPdfInfoId(requestSearchQnaVO.getPdfInfoId());
            newQnaVO.setInquUserId(loginUserId);
            newQnaVO.setInquUsisId(loginUtlinsttId);
            newQnaVO.setRgsnUserId(loginUserId);
            newQnaVO.setAmnnUserId(loginUserId);

            // 문의 등록
            qnaRepo.insertQnaInfo(newQnaVO);
        } else {
            inqrInfoId = summaryQnainfoVO.getInqrInfoId();
        }

        return inqrInfoId;
    }

    /**
     * 문의 상세 메세지 등록 / 수정
     * @param qnaMessageVO
     * @throws Exception
     */
    public void saveQnaMessage(QnaMessageVO qnaMessageVO) throws Exception {

        String loginUserId = null;
        String loginUtlinsttId = null;

        /*
         * 로그인 체크
         *      로그인 정보가 CustomUser.class 라면 jwt 로그인 조회
         *      로그인 상태가 아니라면 String 타입으로 떨이짐
         */
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof CustomUser) {
            CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            loginUserId = user.getLoginUserId();
            loginUtlinsttId = user.getUtlinsttId();
        } else {
            throw new Exception(StatusCode.LOGIN_NOT_USER_INFO.getMessage());
        }

        // 등록자 / 수정자 ID 셋팅
        qnaMessageVO.setRgsnUserId(loginUserId);
        qnaMessageVO.setAmnnUserId(loginUserId);

        // 발신자 이용기관 / 사용자 ID 셋팅
        qnaMessageVO.setDpmpUsisId(loginUtlinsttId);
        qnaMessageVO.setDpmpUserId(loginUserId);

        // 문의 유형 - 상품 문의
        qnaMessageVO.setInqrptrnId(ComCode.IQS00002.getCode());

        // 존재하는 Qna
        RequestSearchQnaVO requestSearchQnaVO = new RequestSearchQnaVO();
        requestSearchQnaVO.setInqrInfoId(qnaMessageVO.getInqrInfoId());
        requestSearchQnaVO.setUtlinsttId(loginUtlinsttId);
        SummaryQnainfoVO summaryQnainfoVO = qnaRepo.selectQnaInfo(requestSearchQnaVO);

        // 참조 코드 - 셋팅 안함
        // qnaMessageVO.setRfcdId(summaryQnainfoVO.getPdfInfoId());

        // 수신자, 수신자 이용기관 ID 셋팅
        // 발신자(메세지 작성자)가 문의자인 경우 - 수신자에 상품판매자 ID 셋팅
        if(loginUtlinsttId.equals(summaryQnainfoVO.getInquUsisId())) {
            qnaMessageVO.setRcvrUsisId(summaryQnainfoVO.getSelrUsisId());
        }

        // 발신자가(메세지 작성자) 문의자가 아닌 경우 - 수신사에 문의자 ID 셋팅
        else {
            qnaMessageVO.setRcvrUserId(summaryQnainfoVO.getInquUserId());
            qnaMessageVO.setRcvrUsisId(summaryQnainfoVO.getInquUsisId());
        }

        // 문의 메세지 등록
        qnaRepo.insertQnaMessage(qnaMessageVO);

        // 메세지 수신확인 이력 등록
        QnaMessageCheckHistoyVO qnaMessageCheckHistoyVO = new QnaMessageCheckHistoyVO();
        qnaMessageCheckHistoyVO.setInqrInfoId(qnaMessageVO.getInqrInfoId());
        qnaMessageCheckHistoyVO.setInfoSqn(qnaMessageVO.getInfoSqn());
        qnaMessageCheckHistoyVO.setRgsnUserId(loginUserId);
        qnaMessageCheckHistoyVO.setAmnnUserId(loginUserId);
        qnaRepo.insertQnaMessageCheckHistoy(qnaMessageCheckHistoyVO);

        /*
         * 알림 서비스
         */
        // 알림 요청 인스턴스 생성
        RequestAlarmVO requestAlarmVO = alarmService.getAlarmRequest(AlarmCode.AlarmCodeEnum.AGENCY_PRODUCT_QNA, null, new Object[]{summaryQnainfoVO.getPdfNm()});
        // 알림 발송
        alarmService.sendMarketAlarm(requestAlarmVO, qnaMessageVO.getRcvrUsisId());
    }
}
