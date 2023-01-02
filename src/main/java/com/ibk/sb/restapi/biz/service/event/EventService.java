package com.ibk.sb.restapi.biz.service.event;

import com.ibk.sb.restapi.app.common.constant.ComCode;
import com.ibk.sb.restapi.app.common.util.FileUtil;
import com.ibk.sb.restapi.app.common.vo.CustomUser;
import com.ibk.sb.restapi.app.common.vo.PagingVO;
import com.ibk.sb.restapi.app.common.vo.ResponseData;
import com.ibk.sb.restapi.biz.service.event.repo.EventProductRepo;
import com.ibk.sb.restapi.biz.service.event.repo.EventRepo;
import com.ibk.sb.restapi.biz.service.event.vo.*;
import com.ibk.sb.restapi.biz.service.event.vo.mypage.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class EventService {

    private final EventRepo eventRepo;

    private final EventProductRepo eventProductRepo;

    private final FileUtil fileUtil;

    /**
     * 메인 화면 > 이벤르 리스트 조회
     * @param requestSearchEventVO
     * @return
     * @throws Exception
     */
    public PagingVO<SummaryEventVO> searchEventMainList(RequestSearchEventVO requestSearchEventVO) throws Exception {

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


        // 이벤트 목록
        requestSearchEventVO.setPgstId("ETS00001");     //진행중
        requestSearchEventVO.setEvntUsed("Y");          //사용
        requestSearchEventVO.setEvntSort("DESC");       //정렬
        requestSearchEventVO.setMainEventFlg("Y");      //메인화면 이벤트 플래그

        requestSearchEventVO.setFilePtrnId(ComCode.GDS05001.getCode());     //상품이미지
        requestSearchEventVO.setLoginUsisId(loginUtlinsttId);
        requestSearchEventVO.setLoginUserId(loginUserId);
        requestSearchEventVO.setPcsnsttsId(ComCode.ETS01002.getCode());     //선정
        requestSearchEventVO.setPdfPgrsYn("Y");                             //진열중
        requestSearchEventVO.setPdfSttsId(ComCode.SELLING_OK.getCode());    //판매중

        // 이벤트 리스트 조회
        List<SummaryEventVO> eventList = eventRepo.selectEventList(requestSearchEventVO);

        // 이벤트의 참여상품 검색
        eventList.forEach(x-> {

            // 이벤트 이미지 셋팅
            x.setImgUrl(fileUtil.setImageUrl(x.getImgFileId()));

            // 상품검색
            requestSearchEventVO.setRecord(3);
            requestSearchEventVO.setEvntInfId(x.getEvntInfId());

            // 이벤트 상품 리스트 조회
            List<SummaryEventProductVO> productList = eventProductRepo.selectEventProductList(requestSearchEventVO);

            // 이벤트 상품 리스트 > 이미지 URL 셋팅
            productList.forEach(y -> {
                // 이벤트 상품 이미지 URL 셋팅
                y.setImgUrl(fileUtil.setImageUrl(y.getImgFileId()));
            });

            x.setItems(productList);
        });

        return new PagingVO<>(requestSearchEventVO, eventList);
    }

    /**
     * 이벤트 페이지 > 특정상태 목록
     * @return
     * @throws Exception
     */
    public PagingVO<SummaryEventVO> searchEventPageList(RequestSearchEventVO requestSearchEventVO) throws Exception {

        requestSearchEventVO.setEvntUsed("Y");      //사용
        requestSearchEventVO.setEvntSort("DESC");   //정렬

        List<SummaryEventVO> result = eventRepo.selectEventList(requestSearchEventVO);

        // 이미지 URL 셋팅
        if(result.size() > 0) {
            result.forEach(x -> {
                x.setImgUrl(fileUtil.setImageUrl(x.getImgFileId()));
            });
        }

        // 이벤트 리스트 조회
        return new PagingVO<>(requestSearchEventVO, result);
    }

    /**
     * 이벤트 상세 조회
     * @return
     * @throws Exception
     */
    public SummaryEventVO searchEventDetail(RequestSearchEventVO requestSearchEventVO) throws Exception {

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

        // TODO 로그인 정보 필요 : 로그인 사용자의 위시리스트 추가 여부를 체크함
        requestSearchEventVO.setLoginUsisId(utlinsttId);
        requestSearchEventVO.setLoginUserId(loginUserId);

        //
//        requestSearchEventVO.setPgstId("ETS00001"); //진행중
        requestSearchEventVO.setPgstId("ETS00002"); //준비중(모집중)
        requestSearchEventVO.setEvntUsed("Y"); //사용

        SummaryEventVO eventInfo = eventRepo.selectEventDetail(requestSearchEventVO);

        // 이벤트 상품 이미지 URL 셋팅
        eventInfo.setImgUrl(fileUtil.setImageUrl(eventInfo.getImgFileId()));

        // 진행중인경우 승인된 상품 목록 검색
//        if(eventInfo.getPgstId().equals(ComCode.ETS00001.getCode())){
        if(!eventInfo.getPgstId().equals(ComCode.ETS00002.getCode())){

            // 상품검색
            requestSearchEventVO.setPcsnsttsId(ComCode.ETS01002.getCode()); //선정
            requestSearchEventVO.setPdfPgrsYn("Y"); //진열중
            requestSearchEventVO.setPdfSttsId(ComCode.SELLING_OK.getCode()); //판매중
            requestSearchEventVO.setFilePtrnId(ComCode.GDS05001.getCode()); //상품이미지

            // 이벤트 상품 리스트 조회
            List<SummaryEventProductVO> productList = eventProductRepo.selectEventProductList(requestSearchEventVO);

            // 이미지 URL 셋팅
            if(productList.size() > 0) {
                productList.forEach(x -> {
                    x.setImgUrl(fileUtil.setImageUrl(x.getImgFileId()));
                });
            }

            eventInfo.setItems(productList);
        }

        //
        return eventInfo;
    }

    public SummaryEventVO searchEventDetailInfo(String evntInfId) throws Exception {
        return eventRepo.selectEventDetailInfo(evntInfId);
    }

    /**
     * 참여가능 이벤트 체크
     * @return
     * @throws Exception
     */
    public ResponseData checkEventPartici(RequestSearchEventVO requestSearchEventVO) throws Exception {

        requestSearchEventVO.setPgstId("ETS00001"); //진행중
        requestSearchEventVO.setEvntUsed("Y"); //사용

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

        // TODO 로그인 정보 필요 : 이벤트 참여 가능여부 확인
        requestSearchEventVO.setLoginUsisId(utlinsttId);
        requestSearchEventVO.setLoginUserId(loginUserId);

        String checkState = eventProductRepo.selectEventCheck(requestSearchEventVO);

        if(checkState == null){

            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data("N")
                    .build();

        }else{

            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(checkState)
                    .build();

        }

    }

    /**
     * 이벤트페이지 > 참여 > 참여가능 상품검색
     * @return
     * @throws Exception
     */
    public PagingVO<EventProductVO> searchEventProduct(RequestSearchEventVO requestSearchEventVO) throws Exception {

        requestSearchEventVO.setPdfPgrsYn("Y"); //진열중
        requestSearchEventVO.setPdfSttsId(ComCode.SELLING_OK.getCode()); //판매중
        requestSearchEventVO.setFilePtrnId(ComCode.GDS05001.getCode()); //상품이미지

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

        // TODO 로그인 정보 필요 : 이벤트 참여 가능여부 확인
        requestSearchEventVO.setLoginUsisId(utlinsttId);
        requestSearchEventVO.setLoginUserId(loginUserId);

        List<EventProductVO> result = eventProductRepo.selectEventSelrProductList(requestSearchEventVO);

        // 이미지 URL 셋팅
        if(result.size() > 0) {
            result.forEach(x -> {
                x.setImgUrl(fileUtil.setImageUrl(x.getImgFileId()));
            });
        }

        return new PagingVO<>(requestSearchEventVO, result);
    }

    /**
     * 이벤트 상태별 Total 값
     * @return
     * @throws Exception
     */
    public SummaryEventTotalVO searchEventStateTotal(){

        SummaryEventTotalVO result = eventRepo.searchEventStateTotal();
        result.setEventTotalCnt(result.getEventIngCnt()+result.getEventReadyCnt()+result.getEventEndCnt());

        return result;
    }

    /**
     * 판매자 이벤트 참여
     * @return
     * @throws Exception
     */
    public ResponseData selrSaveEvent(SummaryEventProductSelrVO summaryEventProductSelrVO) throws Exception {

        summaryEventProductSelrVO.setPcsnsttsId(ComCode.ETS01001.getCode()); //접수

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

        // TODO 로그인 정보 필요 : 판매자 이벤트 참여 상품 조회 및 이벤트 참여
        summaryEventProductSelrVO.setSelrUsisId(utlinsttId);
        summaryEventProductSelrVO.setSelrUserId(loginUserId);

        Integer result = eventProductRepo.selrSaveEvent(summaryEventProductSelrVO);

        eventProductRepo.selrSaveProductSort(summaryEventProductSelrVO);

        return ResponseData.builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(result)
                .build();
    }

    /**
     * 판매자 이벤트 참여취소
     * @return
     * @throws Exception
     */
    public ResponseData selrCancelEvent(SummaryEventProductSelrVO summaryEventProductSelrVO) throws Exception {

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

        // TODO 로그인 정보 필요 : 판매자 이벤트 참여취소
        summaryEventProductSelrVO.setSelrUsisId(utlinsttId);
        summaryEventProductSelrVO.setSelrUserId(loginUserId);

        Integer result = eventProductRepo.selrCancelEvent(summaryEventProductSelrVO);

        return ResponseData.builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(result)
                .build();
    }

    /**
     * 마이페이지 > 이벤트목록 조회
     * @param requestSearchEventMyVO
     * @return
     * @throws Exception
     */
    public PagingVO<SummaryEventMyParticiInfoVO> searchEventParticiList(RequestSearchEventMyVO requestSearchEventMyVO) throws Exception {

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

        // TODO 로그인 정보 필요 : 로그인 사용자의 회사의 이벤트 참여정보 검색
        requestSearchEventMyVO.setLoginUsisId(utlinsttId);
        requestSearchEventMyVO.setLoginUserId(loginUserId);

        // 참여한 이벤트 (선정/미선정/접수)정보목록
        List<SummaryEventMyParticiVO> eventList = eventRepo.selectEventMyParticiList(requestSearchEventMyVO);

        // 이미지 URL 셋팅
        if(eventList.size() > 0) {
            eventList.forEach(x -> {
                x.setImgUrl(fileUtil.setImageUrl(x.getImgFileId()));
            });
        }

        // 1. 검색된 이벤트코드를 in절로 사용 > 상태조건에 따른 이벤트정보 검색
        List<String> evntids = new ArrayList<String>();
        eventList.forEach(y -> {
            evntids.add(y.getEvntInfId());
        });

        //검색할 이벤트 IDs
        if(evntids.size() == 0) {
            evntids.add("");
        }
        requestSearchEventMyVO.setEvntIds(evntids);

        //이벤트 정보 검색
        List<SummaryEventMyParticiInfoVO> results = eventRepo.selectEventMyParticiInfoList(requestSearchEventMyVO);

        //
        results.forEach(x -> {

            x.setImgUrl(fileUtil.setImageUrl(x.getImgFileId()));

            //response data에 상태업데이트
            eventList.forEach(y -> {
                if(y.getEvntInfId().equals(x.getEvntInfId())){

                    //이벤트가 진행 중일때
                    if(x.getPgstId().equals(ComCode.ETS00001.getCode()) || x.getPgstId().equals(ComCode.ETS00003.getCode())){
                        //이벤트에 대한 상품이 선정이 한개라도 있는 경우 >> 선정
                        if(y.getStateCode().equals(ComCode.ETS01002.getCode())){
                            x.setStateCode(ComCode.ETS01002.getCode());
                        //선정상품이 없다면 접수상태도 미선정으로 고정
                        }else{
                            x.setStateCode(ComCode.ETS01003.getCode());
                        }
                    }

                    //이벤트가 준비중 일때
                    if(x.getPgstId().equals(ComCode.ETS00002.getCode())){
                        //이벤트에 대한 상품이 선정이 한개라도 있는 경우 >> 선정
                        if(y.getStateCode().equals(ComCode.ETS01002.getCode())){
                            x.setStateCode(ComCode.ETS01002.getCode());
                        //선정상품이 없다면 접수상태도 미선정으로 고정
                        }else{
                            //접수상태로 고정
                            x.setStateCode(ComCode.ETS01001.getCode());
                        }
                    }
                }
            });
        });

        return new PagingVO<>(requestSearchEventMyVO, results);
    }

    /**
     * 마이페이지 > 이벤르 페이지 신청현황
     * @return
     * @throws Exception
     */
    public ResponseData searchEventMyDetail(RequestSearchEventMyVO requestSearchEventMyVO) throws Exception {

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

        // TODO 로그인 정보 필요 : 마이페이지 이벤트 신청현황
        requestSearchEventMyVO.setLoginUsisId(utlinsttId);
        requestSearchEventMyVO.setLoginUserId(loginUserId);

        SummaryEventVO result = eventRepo.searchEventMyDetail(requestSearchEventMyVO);

        return ResponseData.builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(result)
                .build();
    }

    /**
     * 마이페이지 > 이벤르 페이지 신청현황 상품목록
     * @return
     * @throws Exception
     */
    public PagingVO<EventProductVO> searchEventMyProduct(RequestSearchEventMyVO requestSearchEventMyVO) throws Exception {

        requestSearchEventMyVO.setPdfPgrsYn("Y"); //진열중
        requestSearchEventMyVO.setPdfSttsId(ComCode.SELLING_OK.getCode()); //판매중
        requestSearchEventMyVO.setFilePtrnId(ComCode.GDS05001.getCode()); //상품이미지

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

        // TODO 로그인 정보 필요 : 마이페이지 이벤트 신청현황 > 참여목록
        requestSearchEventMyVO.setLoginUsisId(utlinsttId);
        requestSearchEventMyVO.setLoginUserId(loginUserId);

        List<EventProductVO> result = eventProductRepo.searchEventMyProduct(requestSearchEventMyVO);

        // 이미지 URL 셋팅
        if(result.size() > 0) {
            result.forEach(x -> {
                x.setImgUrl(fileUtil.setImageUrl(x.getImgFileId()));
            });
        }

        return new PagingVO<>(requestSearchEventMyVO, result);
    }

}
