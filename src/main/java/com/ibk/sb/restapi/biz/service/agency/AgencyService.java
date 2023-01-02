package com.ibk.sb.restapi.biz.service.agency;

import com.ibk.sb.restapi.app.common.constant.AlarmCode;
import com.ibk.sb.restapi.app.common.constant.ComCode;
import com.ibk.sb.restapi.app.common.constant.StatusCode;
import com.ibk.sb.restapi.app.common.util.FileUtil;
import com.ibk.sb.restapi.app.common.vo.CustomUser;
import com.ibk.sb.restapi.app.common.vo.PagingVO;
import com.ibk.sb.restapi.app.common.vo.ResponseData;
import com.ibk.sb.restapi.biz.service.agency.repo.AgencyProductCopyRepo;
import com.ibk.sb.restapi.biz.service.agency.repo.AgencyRepo;
import com.ibk.sb.restapi.biz.service.agency.vo.*;
import com.ibk.sb.restapi.biz.service.alarm.AlarmService;
import com.ibk.sb.restapi.biz.service.alarm.vo.RequestAlarmVO;
import com.ibk.sb.restapi.biz.service.product.single.repo.SingleProductRepo;
import com.ibk.sb.restapi.biz.service.product.single.vo.SingleProductVO;
import com.ibk.sb.restapi.biz.service.user.UserService;
import com.ibk.sb.restapi.biz.service.user.vo.CompanyVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class AgencyService {

    private final AgencyRepo agencyRepo;
    private final AgencyProductCopyRepo agencyProductCopyRepo;
    private final FileUtil fileUtil;
    private final AlarmService alarmService;
    private final SingleProductRepo singleProductRepo;

    /**
     * 상품상세 > 에이전시 요청
     * @return
     * @throws Exception
     */
    public ResponseData requestProductDetailAgency(RequestSearchAgencyVO requestSearchAgencyVO) throws Exception {

        requestSearchAgencyVO.setPdfSttsId(ComCode.SELLING_OK.getCode()); //판매중
        requestSearchAgencyVO.setPdfPgrsYn("Y"); //진열중

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
        requestSearchAgencyVO.setLoginUsisId(loginUtlinsttId);
        requestSearchAgencyVO.setLoginUserId(loginUserId);

        // 에이전시 요청가능 상품여부 확인
        if(agencyRepo.checkAgencyInfProduct(requestSearchAgencyVO).size() == 1){

            // 에이전시 승인이력 여부 확인
            if(agencyRepo.checkAgencyInfApplyProduct(requestSearchAgencyVO).size() == 0){

                // 요청정보
                requestSearchAgencyVO.setAgenInfId(UUID.randomUUID().toString()); //신규 UUID
                requestSearchAgencyVO.setPcsnCon(StatusCode.AGENCY1000.getMessage()); //메시지
                requestSearchAgencyVO.setPcsnsttsId(ComCode.REQUEST.getCode()); //요청

                requestSearchAgencyVO.setRgsnUserId(loginUserId); //요청자 아이디

                // 요청 등록
                agencyRepo.addAgencyInfProduct(requestSearchAgencyVO);

                //이력등록
                agencyRepo.addAgencyInfProductHistory(requestSearchAgencyVO);

                /*
                 * 알림 서비스
                 */
                //상품 정보 검색
                SingleProductVO singleProductVO = singleProductRepo.selectMySingleProduct(requestSearchAgencyVO.getPdfId());
                // 알림 요청 인스턴스 생성
                RequestAlarmVO requestAlarmVO = alarmService.getAlarmRequest(AlarmCode.AlarmCodeEnum.AGENCY_REQUEST, null, new Object[]{singleProductVO.getPdfNm()});
                // 알림 발송
                alarmService.sendMarketAlarm(requestAlarmVO, requestSearchAgencyVO.getRecUsisId());
            }else{
                return ResponseData.builder()
                        .code(HttpStatus.BAD_REQUEST.value())
                        .message(HttpStatus.OK.getReasonPhrase())
                        .data(StatusCode.AGENCY0004.getMessage())
                        .build();
            }
        }else{
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(StatusCode.AGENCY0001.getMessage())
                    .build();
        }

        return ResponseData.builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(StatusCode.AGENCY0002.getMessage())
                .build();
    }

    /**
    * 마이페이지 > 에이전시 > 관리자 승인 요청
    */
    public ResponseData applyMyAgency(){

        RequestSearchAgencyVO requestSearchAgencyVO = new RequestSearchAgencyVO();

        requestSearchAgencyVO.setMmbrsttsId(ComCode.SELLER_APPROVED.getCode()); //승인
        requestSearchAgencyVO.setMmbrtypeId(ComCode.REGULAR_MEMBER.getCode()); //정회원
        requestSearchAgencyVO.setMmbrUseYn("Y"); //사용여부

        // 로그인 체크
        String loginUserId = null;
        String loginUtlinsttId = null;

//         로그인 정보가 CustomUser.class 라면 jwt 로그인 조회
//         로그인 상태가 아니라면 String 타입으로 떨이짐
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof CustomUser) {
            CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            loginUserId = user.getLoginUserId();
            loginUtlinsttId = user.getUtlinsttId();
        }

        requestSearchAgencyVO.setLoginUsisId(loginUtlinsttId);
        requestSearchAgencyVO.setLoginUserId(loginUserId);

        // 정회원 여부 확인
        if(agencyRepo.checkMyUserTypeAgency(requestSearchAgencyVO).size() == 1){
            // 에이전시 요청 여부 확인
            if(agencyRepo.checkMyUserAgency(requestSearchAgencyVO).size() == 0){

                // 요청이력번호 생성
                requestSearchAgencyVO.setAgenReqId(UUID.randomUUID().toString()); //신규ID
                requestSearchAgencyVO.setPcsnCon(StatusCode.AGENCY0005.getMessage()); //메시지
                requestSearchAgencyVO.setPcsnsttsId(ComCode.REQUEST.getCode()); //요청

                // 에이전시 요청 등록
                agencyRepo.applyMyAgency(requestSearchAgencyVO);

                // 에이전시 요청 이력 등록
                agencyRepo.addMyAgencyHistory(requestSearchAgencyVO);

            }else{
                return ResponseData.builder()
                        .code(HttpStatus.BAD_REQUEST.value())
                        .message(HttpStatus.OK.getReasonPhrase())
                        .data(StatusCode.AGENCY0004.getMessage())
                        .build();
            }
        }else{
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(StatusCode.AGENCY0003.getMessage())
                    .build();
        }

        return ResponseData.builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .build();
    }

    /**
     * 마이페이지 > 에이전시 > 관리자 승인 요청취소
     */
    public ResponseData applyMyAgencyCancel(RequestSearchAgencyVO requestSearchAgencyVO){

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

        requestSearchAgencyVO.setLoginUsisId(loginUtlinsttId);
        requestSearchAgencyVO.setLoginUserId(loginUserId);

        // 요청 삭제
        if(agencyRepo.applyMyAgencyCancel(requestSearchAgencyVO).equals(1)){

            // 요청 이력 삭제
            agencyRepo.addMyAgencyHistoryCancel(requestSearchAgencyVO);

        }else{
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(StatusCode.AGENCY0006.getMessage())
                    .build();
        }

        return ResponseData.builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .build();
    }

    /**
     * 마이페이지 > 에이전시 > 관리자 승인 요청 상세
     */
    public ResponseData applyAgencyMyDetail(){

        RequestSearchAgencyVO requestSearchAgencyVO = new RequestSearchAgencyVO();

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

        requestSearchAgencyVO.setLoginUsisId(loginUtlinsttId);
        requestSearchAgencyVO.setLoginUserId(loginUserId);

        return ResponseData.builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(agencyRepo.applyAgencyMyDetail(requestSearchAgencyVO))
                .build();
    }

    /**
     * 마이페이지 > 에이전시 상태별 Total
     */
    public ResponseData searchAgenInfMyStateTotal(){

        // Login Vo
        RequestSearchAgencyVO requestSearchAgencyVO = new RequestSearchAgencyVO();

        requestSearchAgencyVO.setPdfPgrsYn("Y"); //진열중
        requestSearchAgencyVO.setPdfSttsId(ComCode.SELLING_OK.getCode()); //판매중
        requestSearchAgencyVO.setFilePtrnId(ComCode.GDS05001.getCode()); //상품이미지

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

        requestSearchAgencyVO.setLoginUsisId(loginUtlinsttId);
        requestSearchAgencyVO.setLoginUserId(loginUserId);

        // Totoal Vo
        SummaryAgencyTotalVO summaryAgencyTotalVO = new SummaryAgencyTotalVO();

        // 에이전시 보낸요청 Total
        requestSearchAgencyVO.setAgenSearchType("sen");
        summaryAgencyTotalVO.setSenAgenTotal(agencyRepo.searchAgenInfMyStateTotal(requestSearchAgencyVO));

        // 에이전시 받은요청 Total
        requestSearchAgencyVO.setAgenSearchType("rec");
        summaryAgencyTotalVO.setRecAgenTotal(agencyRepo.searchAgenInfMyStateTotal(requestSearchAgencyVO));

        return ResponseData.builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(summaryAgencyTotalVO)
                .build();
    }

    /**
     * 마이페이지 > 에이전시 보낸/받은 목록
     */
    public PagingVO<SummaryAgencyVO> searchAgencyInfMyList(RequestSearchAgencyVO requestSearchAgencyVO){

        requestSearchAgencyVO.setPdfPgrsYn("Y"); //진열중
        requestSearchAgencyVO.setPdfSttsId(ComCode.SELLING_OK.getCode()); //판매중
        requestSearchAgencyVO.setFilePtrnId(ComCode.GDS05001.getCode()); //상품이미지

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

        requestSearchAgencyVO.setLoginUsisId(loginUtlinsttId);
        requestSearchAgencyVO.setLoginUserId(loginUserId);

        List<SummaryAgencyVO> result = agencyRepo.searchAgencyInfMyList(requestSearchAgencyVO);

        // 에이전시 상품 이미지 URL 셋팅
        if (result.size() > 0) {
            result.forEach(x -> {
                x.setImgUrl(fileUtil.setImageUrl(x.getImgFileId()));
            });

        }

        return new PagingVO<>(requestSearchAgencyVO, result);
    }

    /**
     * 마이페이지 > 보낸요청 > 대기상태 > 취소
     * agenReqId
     * @param requestSearchAgencyVO
     * @return
     */
    public ResponseData updateAgencyInfMyCancel(RequestSearchAgencyVO requestSearchAgencyVO){

        //
        requestSearchAgencyVO.setPcsnsttsId(ComCode.CANCEL.getCode()); //취소
        requestSearchAgencyVO.setPcsnCon(StatusCode.AGENCY1001.getMessage()); //요청 취소 메시지

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

        // 에이전시 상태에 변경에 대한 사용자 정보 agenReqId 아이디 하나에서 상태를 변경할 수 있는 계정은 구매자, 판매자이기에 처리하는 대상의 정보를 가집니다.
        requestSearchAgencyVO.setLoginUsisId(loginUtlinsttId);
        requestSearchAgencyVO.setLoginUserId(loginUserId);

        //에이전시 요청 취소
        if(agencyRepo.updateAgencyInfMyCancel(requestSearchAgencyVO).equals(1)){

            // 에이전시 요청 이력 등록
            agencyRepo.addAgencyInfMyHistory(requestSearchAgencyVO);

        }else{
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(StatusCode.AGENCY0007.getMessage())
                    .build();
        }

        return ResponseData.builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .build();
    }

    /**
     * 마이페이지 > 받은요청 > 대기상태 > 반려
     * agenReqId
     * @param requestSearchAgencyVO
     * @return
     */
    public ResponseData updateAgencyInfMyReject(RequestSearchAgencyVO requestSearchAgencyVO) throws Exception {

        //
        requestSearchAgencyVO.setPcsnsttsId(ComCode.REJECT.getCode()); //반려

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

        // 에이전시 상태에 변경에 대한 사용자 정보 agenReqId 아이디 하나에서 상태를 변경할 수 있는 계정은 구매자, 판매자이기에 처리하는 대상의 정보를 가집니다.
        requestSearchAgencyVO.setLoginUsisId(loginUtlinsttId);
        requestSearchAgencyVO.setLoginUserId(loginUserId);

        //에이전시 요청 반려
        if(agencyRepo.updateAgencyInfMyReject(requestSearchAgencyVO).equals(1)){

            /*
             * 알림 서비스
             */
            // 에이전시 접수 정보 조회
            SummaryAgencyVO searchVo = agencyRepo.selectAgencyInfo(requestSearchAgencyVO.getAgenInfId());
            //상품 정보 검색
            SingleProductVO singleProductVO = singleProductRepo.selectMySingleProduct(searchVo.getPdfInfoId());
            // 알림 요청 인스턴스 생성
            RequestAlarmVO requestAlarmVO = alarmService.getAlarmRequest(AlarmCode.AlarmCodeEnum.AGENCY_REQUEST_CANCEL, null, new Object[]{singleProductVO.getPdfNm()});
            // 알림 발송
            alarmService.sendMarketAlarm(requestAlarmVO, requestSearchAgencyVO.getSenUsisId());
            // 에이전시 요청 이력 등록
            agencyRepo.addAgencyInfMyHistory(requestSearchAgencyVO);

        }else{
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(StatusCode.AGENCY0008.getMessage())
                    .build();
        }

        return ResponseData.builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .build();
    }

    /**
     * 마이페이지 > 보낸요청 > 반려사유, 마이페이지 > 받은요청 > 반려사유
     * agenReqId
     * @param requestSearchAgencyVO
     * @return
     */
    public ResponseData searchAgencyInfMyReason(RequestSearchAgencyVO requestSearchAgencyVO){

        requestSearchAgencyVO.setPcsnsttsId(ComCode.REJECT.getCode()); //반려상태

        return ResponseData.builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(agencyRepo.searchAgencyInfMyReason(requestSearchAgencyVO))
                .build();

    }

    /**
     * 마이페이지 > 받은요청 > 대기상태 > 승인
     * 승인조건 : 요청상태이면서, 받은사람이 본인 회사인경우
     * 승인시 : 요청을 보낸 회사/사용자ID 기준으로 요청한 상품의 정보를 복사 해줌
     * agenInfId
     * @param requestSearchAgencyVO
     * @return
     */
    public ResponseData updateAgencyInfMyApproval(RequestSearchAgencyVO requestSearchAgencyVO) throws Exception {

        //
        requestSearchAgencyVO.setPcsnsttsId(ComCode.REQUEST.getCode()); //요청상태

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

        // 에이전시 승인시 요청을 받은 회사인지 비교
        requestSearchAgencyVO.setLoginUsisId(loginUtlinsttId);
        requestSearchAgencyVO.setLoginUserId(loginUserId);

        SummaryAgencyVO info = agencyRepo.updateAgencyInfMyApprovalInfo(requestSearchAgencyVO);

        // 에이전시 승인가능여부 확인(요청받은 회사가 본인회사이면서 요청상태인경우)
        if(info != null){

            String newPdfId = UUID.randomUUID().toString();
            requestSearchAgencyVO.setPdfId(info.getPdfInfoId());        // 요청상품ID
            requestSearchAgencyVO.setNewPdfId(newPdfId);                // 신규상품ID
            requestSearchAgencyVO.setSenUsisId(info.getSenUsisId());    // 요청자 이용기관ID
            requestSearchAgencyVO.setSenUserId(info.getSenUserId());    // 요청자 사용자ID
            requestSearchAgencyVO.setRecUsisId(info.getRecUsisId());    // 수신 이용기관ID
            requestSearchAgencyVO.setRecUserId(info.getRecUserId());    // 수신 판매자ID

            // STEP1. 상품기본정보, 진열안함, 판매중지 상태로 신규 등록
            agencyProductCopyRepo.productCopyPdfInfM(requestSearchAgencyVO);

            // STEP2. 상품판매정보, 판매가 0원, 할인가 0원
            agencyProductCopyRepo.productCopyPdfSalL(requestSearchAgencyVO);

            // STEP3. 상품키워드정보
            agencyProductCopyRepo.productCopyPdfKwrR(requestSearchAgencyVO);

            // STEP4. 상품특허정보
            agencyProductCopyRepo.productCopyPdfPatR(requestSearchAgencyVO);

            // STEP5. 상품제품영상정보 파일목록
            List<AgencyProductFileVO> pdfVidRfiles = agencyProductCopyRepo.productCopyPdfVidRFiles(requestSearchAgencyVO);
            pdfVidRfiles.forEach(fileInfo -> {

                //신규파일정보 ID
                String newFileId = UUID.randomUUID().toString();

                // 신규파일정보 등록
                fileInfo.setNewFileId(newFileId); //신규파일ID
                fileInfo.setRgsnUserId(info.getSenUserId()); //요청자 사용자ID
                // TODO 실제 파일을 복사하는 로직 필요
                /*
                fileInfo.setFilePath(...);
                */
                agencyProductCopyRepo.productCopyFile(fileInfo);

                // 신규제품영상정보 등록
                requestSearchAgencyVO.setFileId(fileInfo.getFileId());
                requestSearchAgencyVO.setNewFileId(newFileId);
                agencyProductCopyRepo.productCopyPdfVidR(requestSearchAgencyVO);

            });

            // STEP6. 상품이미지파일정보 파일목록
            List<AgencyProductFileVO> pdfFieRfiles = agencyProductCopyRepo.productCopyPdfFieRFiles(requestSearchAgencyVO);
            pdfFieRfiles.forEach(fileInfo -> {

                //신규파일정보 ID
                String newFileId = UUID.randomUUID().toString();

                // 신규파일정보 등록
                fileInfo.setNewFileId(newFileId); //신규파일ID
                fileInfo.setRgsnUserId(info.getSenUserId()); //요청자 사용자ID
                // TODO 실제 파일을 복사하는 로직 필요
                /*
                fileInfo.setFilePath(...);
                */
                agencyProductCopyRepo.productCopyFile(fileInfo);

                // 상품이미지파일정보 등록
                requestSearchAgencyVO.setFileId(fileInfo.getFileId());
                requestSearchAgencyVO.setNewFileId(newFileId);
                agencyProductCopyRepo.productCopyPdfFieR(requestSearchAgencyVO);

            });

            // STEP7. 상품화물서비스기본정보
            List<AgencyProductFileVO> pdfDvryMfiles = agencyProductCopyRepo.productCopyPdfDvryMFiles(requestSearchAgencyVO);
            pdfDvryMfiles.forEach(fileInfo -> {

                //신규파일정보 ID
                String newFileId = UUID.randomUUID().toString();

                // 신규파일정보 등록
                fileInfo.setNewFileId(newFileId); //신규파일ID
                fileInfo.setRgsnUserId(info.getSenUserId()); //요청자 사용자ID
                // TODO 실제 파일을 복사하는 로직 필요
                /*
                fileInfo.setFilePath(...);
                */
                agencyProductCopyRepo.productCopyFile(fileInfo);

                // 상품화물서비스기본정보 등록
                requestSearchAgencyVO.setFileId(fileInfo.getFileId());
                requestSearchAgencyVO.setNewFileId(newFileId);
                agencyProductCopyRepo.productCopyPdfDvryM(requestSearchAgencyVO);

            });
            if (pdfDvryMfiles.size() == 0) { // 파일ID 가 없더라도 복사되게 한다
                requestSearchAgencyVO.setFileId("");
                requestSearchAgencyVO.setNewFileId("");
                agencyProductCopyRepo.productCopyPdfDvryM(requestSearchAgencyVO);
            }

            // STEP8. 상품화물서비스견적정보
            agencyProductCopyRepo.productCopyDvryEsttM(requestSearchAgencyVO);

            // STEP9. 상품반품/교환정보, 요청자의 최신 반품교환정보 있는지 확인
            AgencyProductRetrunVO agencyProductRetrunVO = agencyProductCopyRepo.searchProductCopyPdfRtinL(requestSearchAgencyVO);

            // 등록한 최신 반품교환정보가 없는 경우 공백으로 등록
            if(agencyProductRetrunVO == null){
                AgencyProductRetrunVO defaultData = new AgencyProductRetrunVO();

                defaultData.setRtgdInrcTrm("");
                defaultData.setRtgdExp("");
                defaultData.setRtgdInrcPrcd("");
                defaultData.setRtgdInrcDsln("");

                agencyProductRetrunVO = defaultData;
            }

            agencyProductRetrunVO.setPdfInfoId(newPdfId); //상품ID
            agencyProductRetrunVO.setRgsnUserId(info.getSenUserId()); //이용자ID

            // STEP9. 상품반품/교환정보 등록
            agencyProductCopyRepo.addProductCopyPdfRtinL(agencyProductRetrunVO);

            // STEP10. 상품배송정보
            agencyProductCopyRepo.productCopyPdfDelL(requestSearchAgencyVO);

            // STEP11. 상품지역별배송정보
            agencyProductCopyRepo.productCopyPearDvryM(requestSearchAgencyVO);

            // STEP12. 상품수량별배송정보
            agencyProductCopyRepo.productCopyQtyDvryM(requestSearchAgencyVO);

            // STEP13. 상품복사완료후 요청상태 변경
            requestSearchAgencyVO.setPcsnsttsId(ComCode.APPROVED.getCode()); //승인
            requestSearchAgencyVO.setPcsnCon(StatusCode.AGENCY0010.getMessage()); //승인완료메시지
            agencyRepo.updateAgencyInfMyApproval(requestSearchAgencyVO);

            // STEP14. 이력등록
            agencyRepo.addAgencyInfMyHistory(requestSearchAgencyVO);

            /*
             * 알림 서비스
             */
            //상품 정보 검색
            SingleProductVO singleProductVO = singleProductRepo.selectMySingleProduct(requestSearchAgencyVO.getPdfId());
            // 알림 요청 인스턴스 생성
            RequestAlarmVO requestAlarmVO = alarmService.getAlarmRequest(AlarmCode.AlarmCodeEnum.AGENCY_REQUEST_APPROVAL, null, new Object[]{singleProductVO.getPdfNm()});
            // 알림 발송
            alarmService.sendMarketAlarm(requestAlarmVO, requestSearchAgencyVO.getSenUsisId());

        }else{
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(StatusCode.AGENCY0009.getMessage())
                    .build();
        }

        return ResponseData.builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .build();
    }

    /**
     * 마이페이지 > 보낸요청 > 승인상태 > 취소, 마이페이지 > 받은요청 > 승인상태 > 취소
     * agenReqId
     * @param requestSearchAgencyVO
     * @return
     */
    public ResponseData updateAgencyInfMyApprovalCancel(RequestSearchAgencyVO requestSearchAgencyVO){

        //
        requestSearchAgencyVO.setPcsnsttsId(ComCode.APPROVED.getCode()); //승인상태

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

        // 에이전시 승인시 요청받은 회사정보인지 비교
        requestSearchAgencyVO.setLoginUsisId(loginUtlinsttId);
        requestSearchAgencyVO.setLoginUserId(loginUserId);

        SummaryAgencyVO info = agencyRepo.updateAgencyInfMyApprovalInfo(requestSearchAgencyVO);

        // 승인상태에서만 승인취소
        if(info != null){

            requestSearchAgencyVO.setPcsnsttsId(ComCode.APPROVE_CANCEL.getCode()); //승인취소
            requestSearchAgencyVO.setPcsnCon(StatusCode.AGENCY0012.getMessage()); //승인취소 메시지

            // 요청 정보 상태 승인취소로 변경
            agencyRepo.updateAgencyInfMyApprovalState(requestSearchAgencyVO);

            // 승인 상품 아이디 상태변경
            requestSearchAgencyVO.setPdfId(info.getAthzPdfInfoId()); //승인상품ID
            requestSearchAgencyVO.setPdfSttsId(ComCode.CANCEL_AGENCY_PRODUCT.getCode()); //에이전시 상품취소
            requestSearchAgencyVO.setPdfPgrsYn("N"); //진열상태
            agencyRepo.updateAgencyPdfInfMyApprovalState(requestSearchAgencyVO);

            // 이력등록
            agencyRepo.addAgencyInfMyHistory(requestSearchAgencyVO);

        }else{
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(StatusCode.AGENCY0011.getMessage())
                    .build();
        }

        return ResponseData.builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .build();
    }

    /**
     * 마이페이지 > 받은요청 > 승인취소 > 취소해제
     * agenReqId
     * @param requestSearchAgencyVO
     * @return
     */
    public ResponseData updateAgencyInfMyApprovalRecovery(RequestSearchAgencyVO requestSearchAgencyVO){

        //
        requestSearchAgencyVO.setPcsnsttsId(ComCode.APPROVE_CANCEL.getCode()); //승인취소상태

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

        // 에이전시 승인시 요청받은 회사정보인지 비교
        requestSearchAgencyVO.setLoginUsisId(loginUtlinsttId);
        requestSearchAgencyVO.setLoginUserId(loginUserId);

        SummaryAgencyVO info = agencyRepo.updateAgencyInfMyApprovalInfo(requestSearchAgencyVO);

        // 승인취소인경우만 승인으로 변경
        if(info != null){

            requestSearchAgencyVO.setPcsnsttsId(ComCode.APPROVED.getCode()); //승인상태
            requestSearchAgencyVO.setPcsnCon(StatusCode.AGENCY0014.getMessage()); //승인상태로 복구

            // 승인취소에서 승인으로 변경
            agencyRepo.updateAgencyInfMyApprovalState(requestSearchAgencyVO);

            // 승인 상품 아이디 상태변경
            requestSearchAgencyVO.setPdfId(info.getAthzPdfInfoId()); //승인상품ID
            requestSearchAgencyVO.setPdfSttsId(ComCode.SELLING_OK.getCode()); //판매중
            requestSearchAgencyVO.setPdfPgrsYn("N"); //진열안함 유지
            agencyRepo.updateAgencyPdfInfMyApprovalState(requestSearchAgencyVO);

            // 이력등록
            agencyRepo.addAgencyInfMyHistory(requestSearchAgencyVO);

        }else{
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(StatusCode.AGENCY0013.getMessage())
                    .build();
        }

        return ResponseData.builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .build();
    }

}
