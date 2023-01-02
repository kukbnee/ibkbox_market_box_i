package com.ibk.sb.restapi.biz.service.estimation;

import com.ibk.sb.restapi.app.common.constant.AlarmCode;
import com.ibk.sb.restapi.app.common.constant.ComCode;
import com.ibk.sb.restapi.app.common.constant.StatusCode;
import com.ibk.sb.restapi.app.common.util.BizException;
import com.ibk.sb.restapi.app.common.util.FileUtil;
import com.ibk.sb.restapi.app.common.vo.CustomUser;
import com.ibk.sb.restapi.app.common.vo.PagingVO;
import com.ibk.sb.restapi.app.common.vo.ResponseData;
import com.ibk.sb.restapi.biz.service.alarm.AlarmService;
import com.ibk.sb.restapi.biz.service.alarm.vo.RequestAlarmVO;
import com.ibk.sb.restapi.biz.service.common.repo.ComCodeRepo;
import com.ibk.sb.restapi.biz.service.common.vo.SummaryComCodeListVO;
import com.ibk.sb.restapi.biz.service.delivery.DeliveryService;
import com.ibk.sb.restapi.biz.service.delivery.chunil.vo.DeliveryProductVO;
import com.ibk.sb.restapi.biz.service.delivery.chunil.vo.RequestCheckDeliveryCostVO;
import com.ibk.sb.restapi.biz.service.estimation.repo.EstimationDeliveryRepo;
import com.ibk.sb.restapi.biz.service.estimation.repo.EstimationProductRepo;
import com.ibk.sb.restapi.biz.service.estimation.repo.EstimationRepo;
import com.ibk.sb.restapi.biz.service.estimation.vo.*;
import com.ibk.sb.restapi.biz.service.file.vo.FileInfoVO;
import com.ibk.sb.restapi.biz.service.qna.repo.QnaRepo;
import com.ibk.sb.restapi.biz.service.qna.vo.QnaMessageCheckHistoyVO;
import com.ibk.sb.restapi.biz.service.qna.vo.QnaMessageVO;
import com.ibk.sb.restapi.biz.service.seller.repo.SellerStoreRepo;
import com.ibk.sb.restapi.biz.service.user.UserService;
import com.ibk.sb.restapi.biz.service.user.repo.UserRepo;
import com.ibk.sb.restapi.biz.service.user.vo.CompanyVO;
import com.ibk.sb.restapi.biz.service.user.vo.MyPageUserVO;
import com.ibk.sb.restapi.biz.service.seller.vo.SellerInfoVO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static com.ibk.sb.restapi.biz.service.estimation.EstimationService.DeliveryListServiceKind.*;

@Service
@RequiredArgsConstructor
public class EstimationService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private final EstimationRepo estimationRepo;

    private final EstimationProductRepo estimationProductRepo;

    private final EstimationDeliveryRepo estimationDeliveryRepo;

//    private final CommonService commonService;
    private final ComCodeRepo comCodeRepo;

    private final UserService userService;

    private final SellerStoreRepo sellerStoreRepo;

    private final UserRepo userRepo;

    private final QnaRepo qnaRepo;

    private final FileUtil fileUtil;

    private final DeliveryService deliveryService;

    // 알람서비스
    private final AlarmService alarmService;

    private enum EstimationApiKind {
        EstimationApiSave, // 견적발송
        EstimationApiDeliveryList // 화물서비스 선택
    }

    public enum DeliveryListServiceKind {
        None,
        EstimationService, // 견적 서비스 // 견적 발송 시
        OrderReqService_ProductDeliveryAmt, // 주문 서비스(운임체크(화물서비스)) // 주문 전
        OrderReqService_ProductDeliveryList, // 주문 서비스(배송정보입력 > 화물서비스) // 주문 후 // 직접배송, 무료배송에서 화물서비스로 변경하는 케이스
        MyProductService_SingleProductDeliveryList // 상품 서비스 // 상품 등록 시
    }

    public enum DeliveryEntpKind {
        Chunil // 천일화물
    }

    private AbstractMap.SimpleImmutableEntry<String, String> authInSvc() {
        // 로그인 정보
        String loginUserId = null;
        String loginUtlinsttId = null;
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof CustomUser) {
            CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            loginUserId = user.getLoginUserId();
            loginUtlinsttId = user.getUtlinsttId();
        }
//        loginUserId = "box14601";
//        loginUtlinsttId = "C0000151";
        return new AbstractMap.SimpleImmutableEntry<>(loginUserId, loginUtlinsttId);
    }

    /**
     * 마이페이지 > 문의 > 견적발송
     * @param requestSearchEstimationVO
     * @throws Exception
     */
    public ResponseData saveEstimation(RequestSearchEstimationVO requestSearchEstimationVO) throws Exception {
        /*
         * 프런트가 보내온 견적내용을 저장한다.
         * 프런트는 상품정보, 배송정보(배송비 등), 문의정보(견적관련 문의정보ID 등) 를 보내온다.
         * api 는 권한검증, 입력값검증, DB처리 순으로 작업한다.
         * api 는 각 검증에 실패하면, 적절한 메시지를 프런트에 돌려준다.
         * DB처리는 각 정보테이블에 견적내용을 넣고, 이력테이블에 상태값을 포함해 이력을 만든다.
         */

        // 로그인 정보
        String loginUserId = null;
        String loginUtlinsttId = null;
        AbstractMap.SimpleImmutableEntry<String, String> auth = authInSvc();
        if (auth.getKey() != null && auth.getValue() != null) {
            loginUserId = auth.getKey();
            loginUtlinsttId = auth.getValue();
        } else {
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(StatusCode.COM0005.getMessage()) // COM0005(잘못된 접근입니다.)
                    .build();
        }
        requestSearchEstimationVO.setLoginUsisId(loginUtlinsttId);
        requestSearchEstimationVO.setLoginUserId(loginUserId);

        // validation(연결된 문의)
        requestSearchEstimationVO.setDpmpUsisId(requestSearchEstimationVO.getLoginUsisId()); // 발신자 이용기관 ID
        EstimationVO estimationVO = estimationRepo.searchEstimationInqrRcvrUsisId(requestSearchEstimationVO);
        if (estimationVO == null) {
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(StatusCode.ESTIMATION0011.getMessage()) // 연결된 문의를 찾을 수 없습니다.
                    .build();
        }

        requestSearchEstimationVO.setDpmpUserId(requestSearchEstimationVO.getLoginUserId()); // 발신자 사용자 ID
        requestSearchEstimationVO.setRcvrUsisId(estimationVO.getRcvrUsisId()); // 수신자 이용기관 ID

        requestSearchEstimationVO.setRgsnUserId(requestSearchEstimationVO.getLoginUserId()); // 등록 사용자 ID

        // validation(견적작성가능권한)
        // 견적작성가능권한(정회원, 에이전시), 견적결제가능권한(정회원, 에이전시, 준회원)
        SellerInfoVO sellerInfoVO = sellerStoreRepo.selectSellerInfo(requestSearchEstimationVO.getLoginUsisId());
        if (!Arrays.asList(ComCode.REGULAR_MEMBER.getCode(), ComCode.AGENCY_MEMBER.getCode())
                .contains(sellerInfoVO.getMmbrtypeId())) { // 견적작성가능권한 검증
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(StatusCode.ESTIMATION0001.getMessage())
                    .build();
        }

        // validation(입력값)
        String validateRes = validateReqValForSave(requestSearchEstimationVO, EstimationApiKind.EstimationApiSave);
        if (!validateRes.equals("")) {
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(validateRes)
                    .build();
        }

        // 공통 처리
        String esttInfoId = UUID.randomUUID().toString();
//        String esttInfoId = "test0705-" + UUID.randomUUID().toString();
        requestSearchEstimationVO.setEsttInfoId(esttInfoId); // 견적 정보 ID
        requestSearchEstimationVO.setPcsnSttsId("ESS02001"); // 처리 상태 ID // 발송(ESS02001)

        // DB 처리(견적 정보)
        requestSearchEstimationVO.setOrdnInfoId(""); // 주문 정보 ID
        estimationRepo.insertEstimationInfo(requestSearchEstimationVO);

        // DB 처리(견적 정보 변경 이력)
        requestSearchEstimationVO.setPcsnCon(StatusCode.ESTIMATION1001.getMessage()); // 처리 내용 // '견적이 발송되었습니다.'
        estimationRepo.insertEstimationInfoHistory(requestSearchEstimationVO);

        // DB 처리(견적 배송 정보)
        requestSearchEstimationVO.setSelrUsisId(requestSearchEstimationVO.getLoginUsisId()); // 판매자 이용기관 ID
        estimationRepo.insertEstimationDelivery(requestSearchEstimationVO);

        // DB 처리(견적 배송 출고지 정보)
        estimationRepo.insertEstimationRlon(requestSearchEstimationVO);

        // DB 처리(견적 배송 수령지 정보)
        estimationRepo.insertEstimationRcar(requestSearchEstimationVO);

        List<EstimationProductVO> estimationProductVOList = requestSearchEstimationVO.getItems();
        AtomicInteger indexHolder = new AtomicInteger();
        estimationProductVOList.forEach(x -> {
            x.setRgsnUserId(requestSearchEstimationVO.getLoginUserId()); // 등록 사용자 ID
            x.setEsttInfoId(esttInfoId); // 견적 정보 ID
            x.setSelrUsisId(requestSearchEstimationVO.getLoginUsisId()); // 판매자 이용기관 ID
//            x.setInfoSqn(indexHolder.getAndIncrement()); // 0 부터 시작
            if(indexHolder.get() < 0 || indexHolder.get() > Integer.MAX_VALUE - 1) {
                throw new BizException(StatusCode.COM0000);
            }
            x.setInfoSqn(indexHolder.incrementAndGet()); // 1 부터 시작

            // DB 처리(견적 상품 정보)
            estimationProductRepo.insertEstimationProduct(x);
        });

        ////////////
        // 문의 쪽 처리
        ////////////

        // 공통 처리

        // DB 처리(문의 정보)
        // 문의 정보 ID
        // 상품 정보 ID
        // 문의자 이용기관 ID
        // 문의자 ID

        // DB 처리(문의 상세 정보)
        // 문의 정보 ID
        // 정보 순번
        // 발신자 이용기관 ID
        // 발신자 사용자 ID
        // 수신자 이용기관 ID
        // 수신자 사용자 ID
        // 문의유형 ID // 견적 문의(IQS00004)
        // 참조코드 ID // 견적 정보 ID
        // 내용
        // 삭제 여부 // N
        QnaMessageVO qnaMessageVO = new QnaMessageVO();
        qnaMessageVO.setInqrInfoId(requestSearchEstimationVO.getInqrInfoId()); // 문의 정보 ID
        qnaMessageVO.setDpmpUsisId(requestSearchEstimationVO.getDpmpUsisId()); // 발신자 이용기관 ID
        qnaMessageVO.setDpmpUserId(requestSearchEstimationVO.getDpmpUserId()); // 발신자 사용자 ID
        qnaMessageVO.setRcvrUsisId(requestSearchEstimationVO.getRcvrUsisId()); // 수신자 이용기관 ID
        qnaMessageVO.setRcvrUserId(requestSearchEstimationVO.getRcvrUserId()); // 수신자 사용자 ID
        qnaMessageVO.setInqrptrnId("IQS00004"); // 문의유형 ID // 견적 문의(IQS00004)
        qnaMessageVO.setRfcdId(esttInfoId); // 참조코드 ID // 견적 정보 ID
        qnaMessageVO.setCon(StatusCode.ESTIMATION1002.getMessage()); // 내용 // '견적서 전달드립니다.'
        qnaMessageVO.setDelyn("N"); // 삭제 여부
        qnaMessageVO.setRgsnUserId(requestSearchEstimationVO.getRgsnUserId()); // 등록 사용자 ID
        qnaMessageVO.setAmnnUserId(""); // 수정 사용자 ID
        qnaRepo.insertQnaMessage(qnaMessageVO);

        // DB 처리(문의 수신확인 이력)
        // 문의 정보 ID
        // 정보 순번
        // 확인 여부 // N
        requestSearchEstimationVO.setDpmpUsisId(requestSearchEstimationVO.getLoginUsisId()); // 발신자 이용기관 ID
        EstimationVO estimationVOForNoti = estimationRepo.searchEstimationInqrDpmpUsisId(requestSearchEstimationVO);
        QnaMessageCheckHistoyVO qnaMessageCheckHistoyVO = new QnaMessageCheckHistoyVO();
        qnaMessageCheckHistoyVO.setInqrInfoId(estimationVOForNoti.getInqrInfoId());
        qnaMessageCheckHistoyVO.setInfoSqn(estimationVOForNoti.getInqrInfoSqn());
        qnaMessageCheckHistoyVO.setRgsnUserId(requestSearchEstimationVO.getRgsnUserId());
        qnaMessageCheckHistoyVO.setAmnnUserId("");
        qnaRepo.insertQnaMessageCheckHistoy(qnaMessageCheckHistoyVO);

        // DB 처리(문의 견적 연관정보)
        // 견적 정보 ID
        // 문의 정보 ID
        // 정보 순번
        // 연관 순번
        // 처리 상태
        estimationRepo.insertEstimationInes(requestSearchEstimationVO);

        SummaryEstimationResultVO summaryEstimationResultVO = new SummaryEstimationResultVO();
        summaryEstimationResultVO.setResultStr(esttInfoId); // 결과 문자열. 추후 프런트 요구 존재시 보완 예정

        /*
         * 알림 서비스
         */
        // 알림 발송 기업사업장 주요 정보 조회
        CompanyVO companyVO = userService.searchUserCompanyInfo(requestSearchEstimationVO.getDpmpUsisId());
        // 알림 요청 인스턴스 생성
        RequestAlarmVO requestAlarmVO = alarmService.getAlarmRequest(AlarmCode.AlarmCodeEnum.ESTIMATE_REQUEST, null, new Object[]{companyVO.getBplcNm()});
        // 알림 발송
        alarmService.sendMarketAlarm(requestAlarmVO, requestSearchEstimationVO.getRcvrUsisId());

        return ResponseData.builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(summaryEstimationResultVO)
                .build();
    }

    /**
     * 마이페이지 > 견적요청 > 견적취소(판매자)
     * @param requestSearchEstimationVO
     * @throws Exception
     */
    public ResponseData cancelEstimation(RequestSearchEstimationVO requestSearchEstimationVO) throws Exception {
        /*
         * 견적 취소를 한다.
         * api 는 입력값에 대해, 취소 가능한 견적인지 검증한다.
         * 정보테이블에 상태값을 변경하고, 이력테이블에 상태값을 포함해 이력을 만든다.
         * 취소상세(ESS02004)로 새 문의를 만든다(프런트 요청 사항).
         * 현재, 취소상세(ESS02004)는 문의 견적 연관정보 테이블(TB_BOX_MKT_INES_R)에만 히스토리성으로 insert 된다. 견적 정보 변경이력 테이블(TB_BOX_MKT_ESM_INF_H)에는 insert 되지 않고 있다.
         */

        // 로그인 정보
        String loginUserId = null;
        String loginUtlinsttId = null;
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof CustomUser) {
            CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            loginUserId = user.getLoginUserId();
            loginUtlinsttId = user.getUtlinsttId();
        } else {
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(StatusCode.COM0005.getMessage()) // COM0005(잘못된 접근입니다.)
                    .build();
        }
        requestSearchEstimationVO.setLoginUsisId(loginUtlinsttId);
        requestSearchEstimationVO.setLoginUserId(loginUserId);

        // validation(입력값)
        String validateRes = validateReqValForCancel(requestSearchEstimationVO);
        if (!validateRes.equals("")) {
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(validateRes)
                    .build();
        }

        // validation(연결된 문의)
        EstimationVO estimationVO = estimationRepo.searchEstimationInqrInfoId(requestSearchEstimationVO);
        if (estimationVO == null) {
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(StatusCode.ESTIMATION0011.getMessage())
                    .build();
        }

        // 공통 처리
        requestSearchEstimationVO.setPcsnSttsId("ESS02003"); // 처리 상태 ID // 취소(ESS02003)

        // DB 처리(견적 정보)
        requestSearchEstimationVO.setAmnnUserId(requestSearchEstimationVO.getLoginUserId());
        estimationRepo.updateEstimationInfo(requestSearchEstimationVO);

        // DB 처리(견적 정보 변경 이력)
        requestSearchEstimationVO.setRgsnUserId(requestSearchEstimationVO.getLoginUserId());
        requestSearchEstimationVO.setAmnnUserId(""); // 수정 사용자 ID
        requestSearchEstimationVO.setPcsnCon(StatusCode.ESTIMATION1003.getMessage()); // 처리 내용
        estimationRepo.insertEstimationInfoHistory(requestSearchEstimationVO);

        /////////////////////////////
        // 프런트 요청으로, 견적 취소 update
        /////////////////////////////
        requestSearchEstimationVO.setInqrInfoId(estimationVO.getInqrInfoId());
        requestSearchEstimationVO.setInqrInfoSqn(estimationVO.getInqrInfoSqn());

        // DB 처리(문의 상세 정보)
        requestSearchEstimationVO.setInqrCon(StatusCode.ESTIMATION1003.getMessage()); // 내용 // '견적이 취소되었습니다.'
        estimationRepo.updateEstimationInquR(requestSearchEstimationVO);

        // DB 처리(문의 견적 연관정보)
        requestSearchEstimationVO.setInesSqn(estimationVO.getInesSqn());
        estimationRepo.updateEstimationInes(requestSearchEstimationVO);

        /////////////////////////////
        // 프런트 요청으로, 견적 취소상세 insert
        /////////////////////////////

        // DB 처리(문의 상세 정보)
        // 문의 정보 ID
        // 정보 순번
        // 발신자 이용기관 ID
        // 발신자 사용자 ID
        // 수신자 이용기관 ID
        // 수신자 사용자 ID
        // 문의유형 ID // 견적 문의(IQS00004)
        // 참조코드 ID // 견적 정보 ID
        // 내용
        // 삭제 여부 // N
        requestSearchEstimationVO.setDpmpUsisId(requestSearchEstimationVO.getLoginUsisId()); // 발신자 이용기관 ID
        requestSearchEstimationVO.setDpmpUserId(requestSearchEstimationVO.getLoginUserId()); // 발신자 사용자 ID
        requestSearchEstimationVO.setRcvrUsisId(estimationVO.getRcvrUsisId());
        requestSearchEstimationVO.setRcvrUserId("");

        QnaMessageVO qnaMessageVOCancelDetail = new QnaMessageVO();
        qnaMessageVOCancelDetail.setInqrInfoId(requestSearchEstimationVO.getInqrInfoId()); // 문의 정보 ID
        qnaMessageVOCancelDetail.setDpmpUsisId(requestSearchEstimationVO.getDpmpUsisId()); // 발신자 이용기관 ID
        qnaMessageVOCancelDetail.setDpmpUserId(requestSearchEstimationVO.getDpmpUserId()); // 발신자 사용자 ID
        qnaMessageVOCancelDetail.setRcvrUsisId(requestSearchEstimationVO.getRcvrUsisId()); // 수신자 이용기관 ID
        qnaMessageVOCancelDetail.setRcvrUserId(requestSearchEstimationVO.getRcvrUserId()); // 수신자 사용자 ID
        qnaMessageVOCancelDetail.setInqrptrnId("IQS00004"); // 문의유형 ID // 견적 문의(IQS00004)
        qnaMessageVOCancelDetail.setRfcdId(requestSearchEstimationVO.getEsttInfoId()); // 참조코드 ID // 견적 정보 ID
        qnaMessageVOCancelDetail.setCon(StatusCode.ESTIMATION1004.getMessage()); // 내용 // '견적 취소상세 보기'
        qnaMessageVOCancelDetail.setDelyn("N"); // 삭제 여부
        qnaMessageVOCancelDetail.setRgsnUserId(requestSearchEstimationVO.getRgsnUserId()); // 등록 사용자 ID
        qnaMessageVOCancelDetail.setAmnnUserId(""); // 수정 사용자 ID
        qnaRepo.insertQnaMessage(qnaMessageVOCancelDetail);

        // DB 처리(문의 수신확인 이력)
        // 문의 정보 ID
        // 정보 순번
        // 확인 여부 // N
        EstimationVO estimationVOForNoti = estimationRepo.searchEstimationInqrDpmpUsisId(requestSearchEstimationVO);
        QnaMessageCheckHistoyVO qnaMessageCheckHistoyVO = new QnaMessageCheckHistoyVO();
        qnaMessageCheckHistoyVO.setInqrInfoId(estimationVOForNoti.getInqrInfoId());
        qnaMessageCheckHistoyVO.setInfoSqn(estimationVOForNoti.getInqrInfoSqn());
        qnaMessageCheckHistoyVO.setRgsnUserId(requestSearchEstimationVO.getRgsnUserId());
        qnaMessageCheckHistoyVO.setAmnnUserId("");
        qnaRepo.insertQnaMessageCheckHistoy(qnaMessageCheckHistoyVO);

        // DB 처리(문의 견적 연관정보)
        // 견적 정보 ID
        // 문의 정보 ID
        // 정보 순번
        // 연관 순번
        // 처리 상태
        requestSearchEstimationVO.setPcsnSttsId("ESS02004"); // 처리 상태 ID // 취소상세(ESS02004) // 프런트 요청으로, 취소상세 insert
//        requestSearchEstimationVO.setInqrInfoSqn(requestSearchEstimationVO.getInqrInfoSqn() + 1); // infoSqn 1 증가시킨다
        estimationRepo.insertEstimationInesForCancel(requestSearchEstimationVO);

        SummaryEstimationResultVO summaryEstimationResultVO = new SummaryEstimationResultVO();
        summaryEstimationResultVO.setResultStr(requestSearchEstimationVO.getEsttInfoId()); // 결과 문자열. 추후 프런트 요구 존재시 보완 예정

        /*
         * 알림 서비스
         */
        // 알림 발송 기업사업장 주요 정보 조회
        CompanyVO companyVO = userService.searchUserCompanyInfo(requestSearchEstimationVO.getDpmpUsisId());
        // 알림 요청 인스턴스 생성
        RequestAlarmVO requestAlarmVO = alarmService.getAlarmRequest(AlarmCode.AlarmCodeEnum.ESTIMATE_CANCEL, null, new Object[]{companyVO.getBplcNm()});
        // 알림 발송
        alarmService.sendMarketAlarm(requestAlarmVO, requestSearchEstimationVO.getRcvrUsisId());

        return ResponseData.builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(summaryEstimationResultVO)
                .build();
    }

    /**
     * 마이페이지 > 견적요청 > 조회조건 코드(목록(보낸견적/받은견적))
     * @param requestSearchEstimationVO
     * @return
     * @throws Exception
     */
    public EstimationCodeVO searchCode(RequestSearchEstimationVO requestSearchEstimationVO) throws Exception {
        /*
         * 목록화면 조회조건에 사용하는 코드, 코드값을 전달한다.
         */

        EstimationCodeVO estimationCodeVO = new EstimationCodeVO();

        // DB 처리(공통코드)
        List<SummaryComCodeListVO> pcsnSttsCodes = comCodeRepo.searchComCodeList("ESS02"); // 공통관련코드(처리 상태)
        if (pcsnSttsCodes.size() > 0) {
            estimationCodeVO.setPcsnSttsCodes(pcsnSttsCodes);
        }
        List<SummaryComCodeListVO> dvryPtrnCodes = comCodeRepo.searchComCodeList("GDS02"); // 공통관련코드(배송 유형)
        if (dvryPtrnCodes.size() > 0) {
            estimationCodeVO.setDvryPtrnCodes(dvryPtrnCodes);
        }
        List<SummaryComCodeListVO> esttPdfPtrnCodes = comCodeRepo.searchComCodeList("ESS01"); // 공통관련코드(견적 상품 유형)
        if (esttPdfPtrnCodes.size() > 0) {
            estimationCodeVO.setEsttPdfPtrnCodes(esttPdfPtrnCodes);
        }
        List<SummaryComCodeListVO> comPdfUtCodes = comCodeRepo.searchComCodeList("COC02"); // 공통관련코드(공통 상품 단위) // 개 등
        if (comPdfUtCodes.size() > 0) {
            estimationCodeVO.setComPdfUtCodes(comPdfUtCodes);
        }
        List<SummaryComCodeListVO> comPrcUtCodes = comCodeRepo.searchComCodeList("COC03"); // 공통관련코드(공통 가격 단위) // 원 등
        if (comPrcUtCodes.size() > 0) {
            estimationCodeVO.setComPrcUtCodes(comPrcUtCodes);
        }

        return estimationCodeVO;
    }

    /**
     * 마이페이지 > 견적요청 > 목록(보낸견적/받은견적)
     * @param requestSearchEstimationVO
     * @return
     * @throws Exception
     */
    public PagingVO<SummaryEstimationInfoVO> searchEstimationList(RequestSearchEstimationVO requestSearchEstimationVO) throws Exception {
        /*
         * 목록 조회
         * 견적관련 문의정보ID 등도 전달한다.
         */

        // 로그인 정보
        String loginUserId = null;
        String loginUtlinsttId = null;
        AbstractMap.SimpleImmutableEntry<String, String> auth = authInSvc();
        if (auth.getKey() != null && auth.getValue() != null) {
            loginUserId = auth.getKey();
            loginUtlinsttId = auth.getValue();
        } else {
            // TODO 비로그인시 처리하기
            //            return ResponseData.builder()
            //                    .code(HttpStatus.BAD_REQUEST.value())
            //                    .message(StatusCode.COM0005.getMessage()) // COM0005(잘못된 접근입니다.)
            //                    .build();
        }
        requestSearchEstimationVO.setLoginUsisId(loginUtlinsttId);
        requestSearchEstimationVO.setLoginUserId(loginUserId);
        requestSearchEstimationVO.setDpmpUsisId(requestSearchEstimationVO.getLoginUsisId()); // 발신자 이용기관 ID
        requestSearchEstimationVO.setRcvrUsisId(requestSearchEstimationVO.getLoginUsisId()); // 수신자 이용기관 ID

        // 공통 처리

        // DB 처리(견적 정보)
        requestSearchEstimationVO.setFilePtrnId(ComCode.GDS05001.getCode()); // 상품이미지(GDS05001)
        List<SummaryEstimationInfoVO> result = estimationRepo.searchEstimationInfoList(requestSearchEstimationVO);

        // DB 처리(공통코드)
        List<SummaryComCodeListVO> pcsnSttsCodes = comCodeRepo.searchComCodeList("ESS02"); // 공통관련코드(처리 상태)
        if (pcsnSttsCodes.size() > 0) {
            for (SummaryEstimationInfoVO vo : result) {
                vo.setPcsnSttsCodes(pcsnSttsCodes);
            }
        }
        List<SummaryComCodeListVO> dvryPtrnCodes = comCodeRepo.searchComCodeList("GDS02"); // 공통관련코드(배송 유형)
        if (dvryPtrnCodes.size() > 0) {
            for (SummaryEstimationInfoVO vo : result) {
                vo.setDvryPtrnCodes(dvryPtrnCodes);
            }
        }

        // 이미지 URL 셋팅
        if (result.size() > 0) {
            result.forEach(x -> {
                x.setImgUrl(fileUtil.setImageUrl(x.getImgFileId()));
            });
        }

        return new PagingVO<>(requestSearchEstimationVO, result);
    }

    /**
     * 마이페이지 > 견적요청 > 목록(보낸견적/받은견적)
     * @param requestSearchEstimationVO
     * @return
     * @throws Exception
     */
    public EstimationCntVO searchEstimationListCnt(RequestSearchEstimationVO requestSearchEstimationVO) throws Exception {
        /*
         * 목록 조회
         * 견적관련 문의정보ID 등도 전달한다.
         */

        // 로그인 정보
        String loginUserId = null;
        String loginUtlinsttId = null;
        AbstractMap.SimpleImmutableEntry<String, String> auth = authInSvc();
        if (auth.getKey() != null && auth.getValue() != null) {
            loginUserId = auth.getKey();
            loginUtlinsttId = auth.getValue();
        } else {
            // TODO 비로그인시 처리하기
            //            return ResponseData.builder()
            //                    .code(HttpStatus.BAD_REQUEST.value())
            //                    .message(StatusCode.COM0005.getMessage()) // COM0005(잘못된 접근입니다.)
            //                    .build();
        }
        requestSearchEstimationVO.setLoginUsisId(loginUtlinsttId);
        requestSearchEstimationVO.setLoginUserId(loginUserId);
        requestSearchEstimationVO.setDpmpUsisId(requestSearchEstimationVO.getLoginUsisId()); // 발신자 이용기관 ID
        requestSearchEstimationVO.setRcvrUsisId(requestSearchEstimationVO.getLoginUsisId()); // 수신자 이용기관 ID

        // 공통 처리

        // DB 처리(견적 건수)
        EstimationCntVO estimationCntVO = estimationRepo.searchEstimationSumCnt(requestSearchEstimationVO);

        // DB 처리(공통코드)
        List<SummaryComCodeListVO> pcsnSttsCodes = comCodeRepo.searchComCodeList("ESS02"); // 공통관련코드(처리 상태)
        if (pcsnSttsCodes.size() > 0) {
            estimationCntVO.setPcsnSttsCodes(pcsnSttsCodes);
        }
        List<SummaryComCodeListVO> dvryPtrnCodes = comCodeRepo.searchComCodeList("GDS02"); // 공통관련코드(배송 유형)
        if (dvryPtrnCodes.size() > 0) {
            estimationCntVO.setDvryPtrnCodes(dvryPtrnCodes);
        }

        return estimationCntVO;
    }

    /**
     * 마이페이지 > 문의 > 견적상세
     * @param requestSearchEstimationVO
     * @return
     * @throws Exception
     */
    public SummaryEstimationInfoVO searchEstimation(RequestSearchEstimationVO requestSearchEstimationVO) throws Exception {
        /*
         * 상세 조회
         * 견적관련 문의정보ID 등도 전달한다.
         * 견적관련 공통코드도 전달한다.
         */

        // 로그인 정보
        String loginUserId = null;
        String loginUtlinsttId = null;
        AbstractMap.SimpleImmutableEntry<String, String> auth = authInSvc();
        if (auth.getKey() != null && auth.getValue() != null) {
            loginUserId = auth.getKey();
            loginUtlinsttId = auth.getValue();
        } else {
            // TODO 비로그인시 처리하기
            //            return ResponseData.builder()
            //                    .code(HttpStatus.BAD_REQUEST.value())
            //                    .message(StatusCode.COM0005.getMessage()) // COM0005(잘못된 접근입니다.)
            //                    .build();
        }
        requestSearchEstimationVO.setLoginUsisId(loginUtlinsttId);
        requestSearchEstimationVO.setLoginUserId(loginUserId);

        // 공통 처리

        // DB 처리(견적 정보)
        SummaryEstimationInfoVO result = estimationRepo.searchEstimationInfo(requestSearchEstimationVO);
        if (result != null) {
            // DB 처리(견적 상품 정보)
            requestSearchEstimationVO.setFilePtrnId(ComCode.GDS05001.getCode()); // 상품이미지(GDS05001)
            List<EstimationProductVO> estimationProductVOList = estimationProductRepo.searchEstimationProduct(requestSearchEstimationVO);
            if (estimationProductVOList.size() > 0) {
                result.setItems(estimationProductVOList);
            }
        } else {
            result = new SummaryEstimationInfoVO();
        }

        // DB 처리(공통코드)
        List<SummaryComCodeListVO> pcsnSttsCodes = comCodeRepo.searchComCodeList("ESS02"); // 공통관련코드(처리 상태)
        if (pcsnSttsCodes.size() > 0) {
            result.setPcsnSttsCodes(pcsnSttsCodes);
        }
        List<SummaryComCodeListVO> dvryPtrnCodes = comCodeRepo.searchComCodeList("GDS02"); // 공통관련코드(배송 유형)
        if (dvryPtrnCodes.size() > 0) {
            result.setDvryPtrnCodes(dvryPtrnCodes);
        }
        List<SummaryComCodeListVO> esttPdfPtrnCodes = comCodeRepo.searchComCodeList("ESS01"); // 공통관련코드(견적 상품 유형)
        if (esttPdfPtrnCodes.size() > 0) {
            result.setEsttPdfPtrnCodes(esttPdfPtrnCodes);
        }

        // 이미지 URL 셋팅
        if (result != null && result.getItems() != null && result.getItems().size() > 0) {
            result.getItems().forEach(x -> {
                x.setImgUrl(fileUtil.setImageUrl(x.getImgFileId()));
            });
        }
        result.setRgslImgFileUrl(fileUtil.setImageUrl(result.getRgslImgFileId()));

        return result;
    }

    /**
     * 마이페이지 > 문의 > 추가할 상품 검색
     * @param requestSearchEstimationVO
     * @return
     * @throws Exception
     */
    public PagingVO<EstimationProductVO> searchProductAdd(RequestSearchEstimationVO requestSearchEstimationVO) throws Exception {
        /*
         * 상품 목록 전달.
         * 견적은 상품의 판매중 조건 제외.
         */

        // 로그인 정보
        String loginUserId = null;
        String loginUtlinsttId = null;
        AbstractMap.SimpleImmutableEntry<String, String> auth = authInSvc();
        if (auth.getKey() != null && auth.getValue() != null) {
            loginUserId = auth.getKey();
            loginUtlinsttId = auth.getValue();
        } else {
            // TODO 비로그인시 처리하기
            //            return ResponseData.builder()
            //                    .code(HttpStatus.BAD_REQUEST.value())
            //                    .message(StatusCode.COM0005.getMessage()) // COM0005(잘못된 접근입니다.)
            //                    .build();
        }
        requestSearchEstimationVO.setLoginUsisId(loginUtlinsttId);
        requestSearchEstimationVO.setLoginUserId(loginUserId);

        // 공통 처리

        // DB 처리(상품 정보)
        // 견적은 판매중 조건 제외
        requestSearchEstimationVO.setPdfPgrsYn("Y"); // 진열함(Y)
//        requestSearchEstimationVO.setPdfSttsId(ComCode.GDS00001.getCode()); // 판매중(GDS00001)
//        AND TBMPIM.PDF_STTS_ID = #{pdfSttsId}
        requestSearchEstimationVO.setFilePtrnId(ComCode.GDS05001.getCode()); // 상품이미지(GDS05001)
        List<EstimationProductVO> result = estimationProductRepo.searchProductAdd(requestSearchEstimationVO);

        // 이미지 URL 셋팅
        if (result.size() > 0) {
            result.forEach(x -> {
                x.setImgUrl(fileUtil.setImageUrl(x.getImgFileId()));
            });
        }

        return new PagingVO<>(requestSearchEstimationVO, result);
    }

    public List<EstimationDeliveryVO> requestEsmCompany(RequestSearchEstimationVO requestSearchEstimationVO, List<EstimationProductVO> reqProductVOList, MyPageUserVO myPageUserVO, EstimationService.DeliveryListServiceKind deliveryListServiceKind) {
        /*
         * 배송비 산정 작업.
         * 업체만큼 돈다.
         * 상품만큼 돈다.
         * 받은 견적 결과만큼 합계 돈다.
         */
        // DB 처리(배송 업체)
        List<EstimationDeliveryVO> dvryEntps = null;
//        switch (deliveryListServiceKind) {
//            case OrderReqService_ProductDeliveryList: // 배송정보입력시
//            case OrderReqService_ProductDeliveryAmt: // 운임체크시
//                dvryEntps = estimationDeliveryRepo.searchDvryEntp(requestSearchEstimationVO); //
//                break;
//            case EstimationService: // 견적발송시
//            case None: // 운송의뢰시
//                dvryEntps = estimationDeliveryRepo.searchDvryEntp(requestSearchEstimationVO);
//                break;
//        }
        dvryEntps = estimationDeliveryRepo.searchDvryEntp(requestSearchEstimationVO);
        for (EstimationDeliveryVO vo : dvryEntps) {

            DeliveryEntpKind entpKind = DeliveryEntpKind.Chunil; // 현재는 천일화물 밖에 없긴 하다.
            switch (entpKind) {
                case Chunil:
                    String dateStr = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

                    RequestCheckDeliveryCostVO chunilApiVO = new RequestCheckDeliveryCostVO();
                    chunilApiVO.setSAUPNO(bizrnoFormat(myPageUserVO.getBizrno())); // 사업자등록번호
                    chunilApiVO.setESTDAT(dateStr); // 견적일자
                    chunilApiVO.setBALZIP(requestSearchEstimationVO.getRlontfZpcd()); // 발송자 우편번호
                    chunilApiVO.setBALADD(requestSearchEstimationVO.getRlontfAdr()); // 발송자 주소
                    chunilApiVO.setBALADS(requestSearchEstimationVO.getRlontfDtad()); // 발송자 건물관리번호(도로명 주소인 경우)
                    chunilApiVO.setDOCZIP(requestSearchEstimationVO.getRcarZpcd()); // 수신자 우편번호
                    chunilApiVO.setDOCADD(requestSearchEstimationVO.getRcarAdr()); // 수신자 주소
                    chunilApiVO.setDOCADS(requestSearchEstimationVO.getRcarDtlAdr()); // 수신자 건물관리번호(도로명 주소인 경우)
                    chunilApiVO.setRsCount(String.valueOf(reqProductVOList.size()));

                    List<DeliveryProductVO> rsVOList = new ArrayList<>();
                    Integer seq = 1;
                    for (EstimationProductVO reqProductVO : reqProductVOList) {
                        DeliveryProductVO rsVO = new DeliveryProductVO();
                        rsVO.setSEQNUM(String.valueOf(seq++)); // 순번

                        /*
                         * 수량 계산
                         * 주문 제품 수 / 최대상품수(1box당) 나머지는 올림처리
                         */
                        double gdsCntDouble = reqProductVO.getOrdnQty();
                        double maxGdsCntDouble = Integer.valueOf(reqProductVO.getMxmmGdsCnt() == null || reqProductVO.getMxmmGdsCnt().equals("") ? "1" : reqProductVO.getMxmmGdsCnt());
                        Integer resultGdsCnt = (int) Math.ceil(gdsCntDouble / maxGdsCntDouble);
                        String gdsCnt = String.valueOf(resultGdsCnt);
                        rsVO.setITMCNT(gdsCnt); // 수량

                        rsVO.setWIDTHH(reqProductVO.getPrdtBrdh()); // 부피_가로
                        rsVO.setLENGTT(reqProductVO.getPrdtVrtc()); // 부피_세로
                        rsVO.setHEIGHT(reqProductVO.getPrdtAhgd()); // 부피_높이
                        rsVO.setWEIGHT(reqProductVO.getPrdtWgt()); // 중량
                        rsVO.setITMAMT(String.valueOf(reqProductVO.getDchGdsPrc())); // 내품가격
                        rsVOList.add(rsVO);
                    }

                    chunilApiVO.setRs(rsVOList);
                    Map<String, Object> resultMap = null;
                    try {
                        // feign 처리(운임체크)
                        resultMap = deliveryService.checkDeliveryCost(chunilApiVO);
                        logger.debug("resultMap: " + resultMap);
                    } catch (BizException bx) {
                        logger.error("Business Exception ({}) : {}", bx.getErrorCode(), bx.getErrorMsg());
                    } catch (Exception ex) {
                        logger.error("ex: " + ex.getMessage());
                    }
                    /*
                     * 실패유형01 : {flag=false, msg=DB처리오류 : Index: 0, Size: 0}
                     * 실패유형02 : {rs=[{SEQNUM=1, TRNNYN=N, TRNTXT=수량오류, ESTAMT=0, BASAMT=0, ADDAMT=0, SHIAMT=0, SCHDAT=}], rsCount=1, flag=true, msg=}
                     * 실패유형03 : {rs=[{SEQNUM=1, TRNNYN=N, TRNTXT=물품가액제한, ESTAMT=0, BASAMT=0, ADDAMT=0, SHIAMT=0, SCHDAT=}, {SEQNUM=2, TRNNYN=Y, TRNTXT=, ESTAMT=50000, BASAMT=50000, ADDAMT=0, SHIAMT=0, SCHDAT=2022-07-30}, {SEQNUM=3, TRNNYN=Y, TRNTXT=, ESTAMT=10000, BASAMT=10000, ADDAMT=0, SHIAMT=0, SCHDAT=2022-07-30}], rsCount=3, flag=true, msg=}
                     * 성공시 : {rs=[{SEQNUM=1, TRNNYN=Y, TRNTXT=, ESTAMT=50000, BASAMT=50000, ADDAMT=0, SHIAMT=0, SCHDAT=2022-07-22}, {SEQNUM=2, TRNNYN=Y, TRNTXT=, ESTAMT=50000, BASAMT=50000, ADDAMT=0, SHIAMT=0, SCHDAT=2022-07-22}, {SEQNUM=3, TRNNYN=Y, TRNTXT=, ESTAMT=10000, BASAMT=10000, ADDAMT=0, SHIAMT=0, SCHDAT=2022-07-22}], rsCount=3, flag=true, msg=}
                     */

                    String errTxt = "";
                    Integer estSum = 0;
                    List<EstimationEsmVO> estimationEsmVOList = new ArrayList<>();
                    if (resultMap == null) {
                        vo.setApiResultYn("N"); // 화물서비스 api 결과 여부
                        vo.setApiResultTxt(StatusCode.ESTIMATION0015.getMessage());
                    } else {
                        List<Map<String, Object>> rsList = (List<Map<String, Object>>) resultMap.get("rs");
                        if (rsList == null) {
                            vo.setApiResultYn("N"); // 화물서비스 api 결과 여부
                            vo.setApiResultTxt(resultMap.get("msg") != null ? resultMap.get("msg").toString() : StatusCode.ESTIMATION0015.getMessage());
                        } else {
                            /*
                             * 운임체크 성공시 유입
                             */

                            boolean errOccured = false;
                            Integer i = 0;
                            for (Map<String, Object> rs : rsList) {
                                Integer estAmt = Integer.valueOf(rs.get("ESTAMT").toString());
                                estSum += estAmt;
                                if (rs.get("TRNNYN").toString().equals("N")) {
                                    errOccured = true;
                                    errTxt = rs.get("TRNTXT").toString();
                                }

                                // 상품별 배송비
                                EstimationEsmVO estimationEsmVO = new EstimationEsmVO();
                                estimationEsmVO.setSeqNum(rs.get("SEQNUM").toString());
                                estimationEsmVO.setPdfInfoId(reqProductVOList.get(i).getGearPdfInfoId());
                                estimationEsmVO.setDvrynone(estAmt);
                                estimationEsmVOList.add(estimationEsmVO);
                                i++;
                            }
                            if (!errOccured) {
                                vo.setApiResultYn("Y"); // 화물서비스 api 결과 여부
                            } else {
                                vo.setApiResultYn("N"); // 화물서비스 api 결과 여부
                            }
                            vo.setApiResultTxt(errTxt);
                        }
                    }
                    vo.setDvrynone(estSum); // 배송비
                    vo.setComPrcUtId("COC03001"); // 공통 가격 단위 ID // 원(COC03001) // TODO COC03001 을 ComCode 로 빼기?
                    vo.setEstimationEsmVOList(estimationEsmVOList);

                    break;
            }

        }

        return dvryEntps;
    }

    /**
     * 마이페이지 > 문의 > 화물서비스 선택
     * @param requestSearchEstimationVO
     * @return
     * @throws Exception
     */
    public ResponseData searchDeliveryList(RequestSearchEstimationVO requestSearchEstimationVO, DeliveryListServiceKind deliveryListServiceKind) throws Exception {
        /*
         * 프런트의 화물서비스 선택 요청에 대해, 각 운송업체의 api 를 호출하고, 배송비정보를 받아온다.
         * 운송업체 api 를 호출하기 위해, 출고지 / 수령지 / 상품규격 / 상품개수 등의 정보를 프런트에서 받아야 한다.
         * 여러개 운송업체가 있을 수 있으므로, 운송업체 목록으로 프런트에 전달하고 있다.
         */

        // 로그인 정보
        String loginUserId = null;
        String loginUtlinsttId = null;
        AbstractMap.SimpleImmutableEntry<String, String> auth = authInSvc();
        if (auth.getKey() != null && auth.getValue() != null) {
            loginUserId = auth.getKey();
            loginUtlinsttId = auth.getValue();
        } else {
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(StatusCode.COM0005.getMessage()) // COM0005(잘못된 접근입니다.)
                    .build();
        }
        requestSearchEstimationVO.setLoginUsisId(loginUtlinsttId);
        requestSearchEstimationVO.setLoginUserId(loginUserId);

        // validation(입력값)
        switch (deliveryListServiceKind) {
            case EstimationService: // 견적 서비스 // 견적 발송 시
            case OrderReqService_ProductDeliveryAmt: // 주문 서비스(운임체크(화물서비스)) // 주문 전
            case OrderReqService_ProductDeliveryList: // 주문 서비스(배송정보입력 > 화물서비스) // 주문 후 // 직접배송, 무료배송에서 화물서비스로 변경하는 케이스
                String validateRes = validateReqValForSave(requestSearchEstimationVO, EstimationApiKind.EstimationApiDeliveryList);
                if (!validateRes.equals("")) {
                    return ResponseData.builder()
                            .code(HttpStatus.BAD_REQUEST.value())
                            .message(validateRes)
                            .build();
                }
                break;
            case MyProductService_SingleProductDeliveryList: // 상품 서비스 // 상품 등록 시
                break;
        }

        // 공통 처리

        // 값 처리(초기화)
        List<SummaryEstimationInfoVO> result = new ArrayList<>();
        SummaryEstimationInfoVO summaryEstimationInfoVO = new SummaryEstimationInfoVO();

        // 값 처리(보내시는 분)
        MyPageUserVO myPageUserVO = userService.searchMyPageUserInfo();
        if (myPageUserVO != null) {
            summaryEstimationInfoVO.setBplcNm(myPageUserVO.getBplcNm()); // 이용기관 명
            summaryEstimationInfoVO.setReprsntTelno(myPageUserVO.getReprsntTelno()); // 이용기관 대표 전화번호
        } else {
//            summaryEstimationInfoVO.setBplcNm("가나다전자");
//            summaryEstimationInfoVO.setReprsntTelno("080-1111-2222");
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(StatusCode.LOGIN_NOT_USER_INFO.getMessage()) // 사용자 정보가 존재하지 않습니다
                    .build();
        }

        // 값 처리(받은 값 돌려줌)
        summaryEstimationInfoVO.setDvryPtrnId(requestSearchEstimationVO.getDvryPtrnId()); // 배송 유형
        summaryEstimationInfoVO.setRcarZpcd(requestSearchEstimationVO.getRcarZpcd()); // 수령지 우편번호
        summaryEstimationInfoVO.setRcarAdr(requestSearchEstimationVO.getRcarAdr()); // 수령지 주소
        summaryEstimationInfoVO.setRcarDtlAdr(requestSearchEstimationVO.getRcarDtlAdr()); // 수령지 상세주소
        summaryEstimationInfoVO.setRcarNm(requestSearchEstimationVO.getRcarNm()); // 수령인
        summaryEstimationInfoVO.setRcarCnplone(requestSearchEstimationVO.getRcarCnplone()); // 수령인 연락처1
        summaryEstimationInfoVO.setRlontfZpcd(requestSearchEstimationVO.getRlontfZpcd()); // 출고 우편번호
        summaryEstimationInfoVO.setRlontfAdr(requestSearchEstimationVO.getRlontfAdr()); // 출고 주소
        summaryEstimationInfoVO.setRlontfDtad(requestSearchEstimationVO.getRlontfDtad()); // 출고 상세주소

        List<EstimationProductVO> reqProductVOList = requestSearchEstimationVO.getItems();
        switch (deliveryListServiceKind) {
            case EstimationService: // 견적 서비스 // 견적 발송 시
            case OrderReqService_ProductDeliveryAmt: // 주문 서비스(운임체크(화물서비스)) // 주문 전
            case OrderReqService_ProductDeliveryList: // 주문 서비스(배송정보입력 > 화물서비스) // 주문 후 // 직접배송, 무료배송에서 화물서비스로 변경하는 케이스
                // 값 처리(상품)
                for (EstimationProductVO vo : reqProductVOList) {
                    if (!validateReqValForPdf(vo)) { // 제품 규격, 무게, 내품가액 등이 없으면 조회해서 set 해준다
                        if (!vo.getEsttPdfPtrnId().equals(ComCode.ESS01002.getCode())
                                && vo.getGearPdfInfoId() != null && !vo.getGearPdfInfoId().equals("")) { // 직접입력(ESS01002)아니고, 상품 정보 ID 가 있는 경우
                            RequestSearchEstimationVO validateReqVO = new RequestSearchEstimationVO();
                            validateReqVO.setLoginUsisId(requestSearchEstimationVO.getLoginUsisId());
                            validateReqVO.setPdfPgrsYn("Y");
                            validateReqVO.setFilePtrnId(ComCode.GDS05001.getCode()); // 상품이미지(GDS05001)
                            validateReqVO.setPdfInfoId(vo.getGearPdfInfoId()); // 제품 규격, 무게, 내품가액 등이 없는 상품 정보 ID
//                            List<EstimationProductVO> validateResVOList = estimationProductRepo.searchProductAdd(validateReqVO); // 상품 추가와 같은 쿼리를 쓰고 있다
                            List<EstimationProductVO> validateResVOList = estimationProductRepo.searchProductForDeliveryAmt(validateReqVO);
                            if (validateResVOList.size() == 1) {
                                EstimationProductVO validateResVO = validateResVOList.get(0);
                                vo.setPrdtBrdh(validateResVO.getPrdtBrdh());
                                vo.setPrdtVrtc(validateResVO.getPrdtVrtc());
                                vo.setPrdtAhgd(validateResVO.getPrdtAhgd());
                                vo.setPrdtWgt(validateResVO.getPrdtWgt());
                                vo.setDchGdsPrc(validateResVO.getDchGdsPrc()); // 내품가액
                                vo.setMxmmGdsCnt(validateResVO.getMxmmGdsCnt()); // 최대상품수
                                vo.setTms2ClsfNm(validateResVO.getTms2ClsfNm());
                                vo.setTms3ClsfNm(validateResVO.getTms3ClsfNm());
                                vo.setTms4ClsfNm(validateResVO.getTms4ClsfNm());
                                vo.setTms5ClsfNm(validateResVO.getTms5ClsfNm());
                                vo.setPdfCtgyId(validateResVO.getPdfCtgyId());
                                vo.setCtgyData(validateResVO.getCtgyData());
                            } else {
                                return ResponseData.builder()
                                        .code(HttpStatus.BAD_REQUEST.value())
                                        .message(StatusCode.ESTIMATION0008.getMessage())
                                        .build();
                            }
                        }
                    }
                }
                summaryEstimationInfoVO.setItems(reqProductVOList); // 상품 목록
                break;
            case MyProductService_SingleProductDeliveryList: // 상품 서비스 // 상품 등록 시
                // 값 처리(상품)
                for (EstimationProductVO vo : reqProductVOList) {
                    for (FileInfoVO fileVO : vo.getProductFileList()) {
                        fileVO.setImgFileId(fileVO.getFileId());
                    }

                    // 이미지 URL 셋팅
                    if (vo.getProductFileList().size() > 0) {
                        vo.getProductFileList().forEach(x -> {
                            x.setImgUrl(fileUtil.setImageUrl(x.getImgFileId()));
                        });
                    }
                }
                summaryEstimationInfoVO.setItems(reqProductVOList); // 상품 목록
                break;
        }

        List<EstimationDeliveryVO> dvryEntps = requestEsmCompany(requestSearchEstimationVO, reqProductVOList, myPageUserVO, deliveryListServiceKind);

        // 값 처리(배송 업체 목록)
        summaryEstimationInfoVO.setDvryEntps(dvryEntps); // 배송 업체 목록

        // DB 처리(공통코드)
        List<SummaryComCodeListVO> prdtpcknUtCodes = comCodeRepo.searchComCodeList("DIS00"); // 공통관련코드(제품포장 단위)
        if (prdtpcknUtCodes.size() > 0) {
            summaryEstimationInfoVO.setPrdtpcknUtCodes(prdtpcknUtCodes);
        }

        result.add(summaryEstimationInfoVO);

        return ResponseData.builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(new PagingVO<>(requestSearchEstimationVO, result))
                .build();
    }

    private String bizrnoFormat(String bizrno) {
        String result = "";
        if (bizrno != null && bizrno.length() == 10) {
            result = bizrno.substring(0, 3) + "-" + bizrno.substring(3, 5) + "-" + bizrno.substring(5, 10);
        }
        return result;
    }

    /**
     * 입력값 검증
     * @param estimationProductVO
     * @return
     */
    private boolean validateReqValForPdf(EstimationProductVO estimationProductVO) {
        /*
         * 제품 규격, 무게, 내품가액 등이 없으면 false 리턴
         */
        if (estimationProductVO.getPrdtBrdh() == null || estimationProductVO.getPrdtBrdh().equals("")) {
            return false;
        }
        if (estimationProductVO.getPrdtVrtc() == null || estimationProductVO.getPrdtVrtc().equals("")) {
            return false;
        }
        if (estimationProductVO.getPrdtAhgd() == null || estimationProductVO.getPrdtAhgd().equals("")) {
            return false;
        }
        if (estimationProductVO.getPrdtWgt() == null || estimationProductVO.getPrdtWgt().equals("")) {
            return false;
        }
        if (estimationProductVO.getDchGdsPrc() == null) {
            return false;
        }
        if (estimationProductVO.getMxmmGdsCnt() == null || estimationProductVO.getMxmmGdsCnt().equals("")) {
            return false;
        }
        if (estimationProductVO.getCtgyData() == null || estimationProductVO.getCtgyData().equals("")) {
            return false;
        }
        if (estimationProductVO.getTms2ClsfNm() == null || estimationProductVO.getTms2ClsfNm().equals("")) {
            return false;
        }
        if (estimationProductVO.getTms3ClsfNm() == null || estimationProductVO.getTms3ClsfNm().equals("")) {
            return false;
        }
        if (estimationProductVO.getTms4ClsfNm() == null || estimationProductVO.getTms4ClsfNm().equals("")) {
            return false;
        }
        if (estimationProductVO.getTms5ClsfNm() == null || estimationProductVO.getTms5ClsfNm().equals("")) {
            return false;
        }

        return true;
    }

    /**
     * 입력값 검증
     * @param requestSearchEstimationVO
     * @return
     */
    private String validateReqValForSave(RequestSearchEstimationVO requestSearchEstimationVO, EstimationApiKind estimationApiKind) {
        /*
         * 견적발송 api 와 화물서비스 선택 api 요청에 대해, 입력값을 검증한다.
         * 배송유형에 맞게 입력값이 왔는지(주소가 있는지 등) 등을 검증한다.
         * 상품에 대해 주문 수량 검증, 상품명 검증 등.
         */

        // validation(동일 업체)
        switch (estimationApiKind) {
            case EstimationApiSave:
                if (requestSearchEstimationVO.getDpmpUsisId().equals(requestSearchEstimationVO.getRcvrUsisId())) {
                    return StatusCode.ESTIMATION0014.getMessage(); // 본인에게 견적발송할 수 없습니다.
                }
                if (!requestSearchEstimationVO.getDpmpUsisId().equals(requestSearchEstimationVO.getItems().get(0).getSelrUsisId())) {
                    return StatusCode.ESTIMATION0017.getMessage(); // 타인 상품을 견적발송할 수 없습니다.
                }
                break;
            case EstimationApiDeliveryList:
                break;
        }

        // validation(배송 유형)
        /*
         * 화물서비스(GDS02001) : 출고 주소, 수령지 주소, 배송비
         * 직접배송(GDS02002) : 배송비
         * 무료배송(GDS02003)
         * 구매자수령(GDS02004) : 수령지 주소
         */
        if (requestSearchEstimationVO.getDvryPtrnId() == null || requestSearchEstimationVO.getDvryPtrnId().equals("")) {
            return StatusCode.ESTIMATION0012.getMessage();
        }
        if (requestSearchEstimationVO.getDvryPtrnId().equals(ComCode.GDS02001.getCode())) {
            /*
             * 화물서비스(GDS02001)
             */
            if (requestSearchEstimationVO.getRlontfZpcd() == null || requestSearchEstimationVO.getRlontfZpcd().equals("")
                    || requestSearchEstimationVO.getRlontfAdr() == null || requestSearchEstimationVO.getRlontfAdr().equals("")
                    || requestSearchEstimationVO.getRcarZpcd() == null || requestSearchEstimationVO.getRcarZpcd().equals("")
                    || requestSearchEstimationVO.getRcarAdr() == null || requestSearchEstimationVO.getRcarAdr().equals("")) { // 출고 주소, 수령지 주소 검증
                return StatusCode.ESTIMATION0002.getMessage();
            }
            if (requestSearchEstimationVO.getDvrynone() == null || requestSearchEstimationVO.getDvrynone() <= 0) { // 배송비 검증
                if (estimationApiKind == EstimationApiKind.EstimationApiSave) { // 견적발송(saveEstimation) api 인 경우
                    return StatusCode.ESTIMATION0003.getMessage();
                }
            }
            if (requestSearchEstimationVO.getEntpInfoId() == null || requestSearchEstimationVO.getEntpInfoId().equals("")) { // 운송업체 검증
                if (estimationApiKind == EstimationApiKind.EstimationApiSave) { // 견적발송(saveEstimation) api 인 경우
                    return StatusCode.ESTIMATION0004.getMessage();
                }
            }
        }
        if (requestSearchEstimationVO.getDvryPtrnId().equals(ComCode.GDS02002.getCode())) {
            /*
             * 직접배송(GDS02002)
             */
            if (requestSearchEstimationVO.getDvrynone() == null || requestSearchEstimationVO.getDvrynone() <= 0) { // 배송비 검증
                return StatusCode.ESTIMATION0003.getMessage();
            }
        }
        if (requestSearchEstimationVO.getDvryPtrnId().equals(ComCode.GDS02003.getCode())) {
            /*
             * 무료배송(GDS02003)
             */
        }
        if (requestSearchEstimationVO.getDvryPtrnId().equals(ComCode.GDS02004.getCode())) {
            /*
             * 구매자수령(GDS02004)
             */
            if (requestSearchEstimationVO.getRcarZpcd() == null || requestSearchEstimationVO.getRcarZpcd().equals("")
                    || requestSearchEstimationVO.getRcarAdr() == null || requestSearchEstimationVO.getRcarAdr().equals("")) { // 수령지 검증
                return StatusCode.ESTIMATION0002.getMessage();
            }
        }

        // validation(인감)
        if (requestSearchEstimationVO.getRgslImgFileId() == null || requestSearchEstimationVO.getRgslImgFileId().equals("")) {
            if (estimationApiKind == EstimationApiKind.EstimationApiSave) { // 견적발송(saveEstimation) api 인 경우
                return StatusCode.ESTIMATION0016.getMessage();
            }
        }

        // validation(상품)
        if (requestSearchEstimationVO.getItems().size() <= 0) { // 상품 목록 사이즈 검증
            return StatusCode.ESTIMATION0005.getMessage();
        }
        for (EstimationProductVO vo : requestSearchEstimationVO.getItems()) {
            if (vo.getOrdnQty() == null || vo.getOrdnQty() <= 0) { // 주문 수량 검증
                return StatusCode.ESTIMATION0006.getMessage();
            }
            if (vo.getPdfNm() == null || vo.getPdfNm().equals("")) { // 상품명 검증
                return StatusCode.ESTIMATION0007.getMessage();
            }
            if (vo.getEsttPdfPtrnId().equals(ComCode.ESS01001.getCode())) { // 상품 정보 ID 검증 // 상품(ESS01001)
                if (vo.getGearPdfInfoId() == null || vo.getGearPdfInfoId().equals("")) {
                    return StatusCode.ESTIMATION0008.getMessage();
                }
            }
            if (vo.getEsttPdfPtrnId().equals(ComCode.ESS01002.getCode())) { // 상품 정보 ID 검증 // 직접입력(ESS01002)
                if (vo.getGearPdfInfoId() != null && !vo.getGearPdfInfoId().equals("")) {
                    return StatusCode.ESTIMATION0009.getMessage();
                }
            }
        }

        return "";
    }

    /**
     * 입력값 검증
     * @param requestSearchEstimationVO
     * @return
     */
    private String validateReqValForCancel(RequestSearchEstimationVO requestSearchEstimationVO) {
        /*
         * 견적취소에 대해, 입력값을 검증한다.
         */

        if (requestSearchEstimationVO.getEsttInfoId() == null || requestSearchEstimationVO.getEsttInfoId().equals("")) {
            return StatusCode.ESTIMATION0013.getMessage();
        }

        SummaryEstimationInfoVO summaryEstimationInfoVO = estimationRepo.searchEstimationForCancel(requestSearchEstimationVO);
        if (summaryEstimationInfoVO.getPcsnSttsId().equals(ComCode.ESS02001.getCode())) {
            // 견적 발송 상태
        } else {
            // 견적 결제 / 견적 취소 상태
            return StatusCode.ESTIMATION0010.getMessage();
        }

        return "";
    }

}
