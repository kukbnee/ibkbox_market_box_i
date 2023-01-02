package com.ibk.sb.restapi.biz.service.admin;

import com.ibk.sb.restapi.app.common.constant.AlarmCode;
import com.ibk.sb.restapi.app.common.constant.ComCode;
import com.ibk.sb.restapi.app.common.vo.CustomUser;
import com.ibk.sb.restapi.app.common.vo.PagingVO;
import com.ibk.sb.restapi.biz.service.admin.repo.AdminAgencyRepo;
import com.ibk.sb.restapi.biz.service.admin.vo.AdminAgencyVO;
import com.ibk.sb.restapi.biz.service.admin.vo.RequestAgencySearchVO;
import com.ibk.sb.restapi.biz.service.alarm.AlarmService;
import com.ibk.sb.restapi.biz.service.alarm.vo.RequestAlarmVO;
import com.ibk.sb.restapi.biz.service.mainbox.MainBoxService;
import com.ibk.sb.restapi.biz.service.user.UserService;
import com.ibk.sb.restapi.biz.service.user.vo.CompanyVO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminAgencyService {

    private final AdminAgencyRepo repo;
    private final MainBoxService mainBoxService;
    private final UserService userService;
    // 알림 서비스
    private final AlarmService alarmService;

    /**
     * 에이전시 등록 요청 목록 조회
     * @param params
     * @return
     * @throws Exception
     */
    public PagingVO<AdminAgencyVO> searchAgencyAuthRequestList(RequestAgencySearchVO params) throws Exception {
        List<AdminAgencyVO> list = repo.selectAgencyAuthList(params);
        if(list != null) {
            for (AdminAgencyVO agency : list) {
                agency.setRprsntvNm(mainBoxService.searchMainCompany(agency.getSelrUsisId()).getRprsntvNm());
                agency.setRgsnUserName(mainBoxService.searchMainUser(agency.getRgsnUserId(), agency.getSelrUsisId()).getUserNm());
            }
        } else {
            list = new ArrayList<>();
        }
        return new PagingVO<>(params, list);
    }

    /**
     * 에이전시 등록 승인 처리 (정회원 -> 에이전시 / 요청 -> 승인)
     * @param params
     * @return
     */
    public boolean requestApprove(AdminAgencyVO params) throws Exception {

        // 로그인 정보
        String loginUserId = null;
        String loginUtlinsttId = null;
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof CustomUser) {
            CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            loginUserId = user.getLoginUserId();
            loginUtlinsttId = user.getUtlinsttId();
        }

        repo.updateAgencyAuth(ComCode.APPROVED.getCode(), params.getAgenReqId(), loginUserId);
        repo.updateSellerType(ComCode.AGENCY_MEMBER.getCode(), params.getSelrUsisId(), loginUserId);

        AdminAgencyVO history = new AdminAgencyVO();
        history.setLoginUserId(loginUserId);
        history.setAgenReqId(params.getAgenReqId());
        history.setPcsnsttsId(ComCode.APPROVED.getCode());
        history.setPcsnCon("");
        repo.addAgencyInfMyHistory(history);

        /*
         * 알림 서비스
         */
        // 알림 요청 인스턴스 생성
        RequestAlarmVO requestAlarmVO = alarmService.getAlarmRequest(AlarmCode.AlarmCodeEnum.ESTIMATE_PAY, null, null);
        // 알림 발송
        alarmService.sendMarketAlarm(requestAlarmVO, params.getSelrUsisId());

        return true;
    }

    /**
     * 에이전시 등록 반려 처리
     * @param params
     * @return
     */
    public boolean requestReject(AdminAgencyVO params) {
        // 로그인 정보
        String loginUserId = null;
        String loginUtlinsttId = null;
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof CustomUser) {
            CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            loginUserId = user.getLoginUserId();
            loginUtlinsttId = user.getUtlinsttId();
        }

        AdminAgencyVO history = new AdminAgencyVO();
        history.setLoginUserId(loginUserId);
        history.setAgenReqId(params.getAgenReqId());
        history.setPcsnsttsId(ComCode.REJECT.getCode());
        history.setPcsnCon("");
        repo.addAgencyInfMyHistory(history);

        return repo.updateAgencyAuth(ComCode.REJECT.getCode(), params.getAgenReqId(), loginUserId) > 0 ? true : false;
    }


    /**
     * 에이전시 등록 권한 해제 (에이전시 -> 정회원 / 승인 -> 권한해제)
     * @param params
     * @return
     */
    public boolean requestApprovalCancel(AdminAgencyVO params) {
        // 로그인 정보
        String loginUserId = null;
        String loginUtlinsttId = null;
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof CustomUser) {
            CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            loginUserId = user.getLoginUserId();
            loginUtlinsttId = user.getUtlinsttId();
        }

        repo.updateAgencyAuth(ComCode.APPROVE_CANCEL.getCode(), params.getAgenReqId(), loginUserId);
        repo.updateSellerType(ComCode.REGULAR_MEMBER.getCode(), params.getSelrUsisId(), loginUserId);

        AdminAgencyVO history = new AdminAgencyVO();
        history.setLoginUserId(loginUserId);
        history.setAgenReqId(params.getAgenReqId());
        history.setPcsnsttsId(ComCode.APPROVE_CANCEL.getCode());
        history.setPcsnCon("");
        repo.addAgencyInfMyHistory(history);

        return true;
    }

    /**
     * 에이전시 등록 반려 취소 (반려 -> 요청)
     * @param params
     * @return
     */
    public boolean requestRejectCancel(AdminAgencyVO params) {

        // 로그인 정보
        String loginUserId = null;
        String loginUtlinsttId = null;
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof CustomUser) {
            CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            loginUserId = user.getLoginUserId();
            loginUtlinsttId = user.getUtlinsttId();
        }

        AdminAgencyVO history = new AdminAgencyVO();
        history.setLoginUserId(loginUserId);
        history.setAgenReqId(params.getAgenReqId());
        history.setPcsnsttsId(ComCode.REQUEST.getCode());
        history.setPcsnCon("");
        repo.addAgencyInfMyHistory(history);

        return repo.updateAgencyAuth(ComCode.REQUEST.getCode(), params.getAgenReqId(), loginUserId) > 0 ? true : false;
    }

    /**
     * 에이전시 등록 해제 취소 (정회원 -> 에이전시 / 권한 해제 -> 승인)
     * @param params
     * @return
     */
    public boolean requestReverseCancel(AdminAgencyVO params) {
        // 로그인 정보
        String loginUserId = null;
        String loginUtlinsttId = null;
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof CustomUser) {
            CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            loginUserId = user.getLoginUserId();
            loginUtlinsttId = user.getUtlinsttId();
        }

        repo.updateAgencyAuth(ComCode.APPROVED.getCode(), params.getAgenReqId(), loginUserId);
        repo.updateSellerType(ComCode.AGENCY_MEMBER.getCode(), params.getSelrUsisId(), loginUserId);

        AdminAgencyVO history = new AdminAgencyVO();
        history.setLoginUserId(loginUserId);
        history.setAgenReqId(params.getAgenReqId());
        history.setPcsnsttsId(ComCode.APPROVED.getCode());
        history.setPcsnCon("");
        repo.addAgencyInfMyHistory(history);

        return true;
    }


//    public List<AdminAuthStatusVO> searchAuthStatusList() {
//        List<AdminAuthStatusVO> list = new ArrayList<>();
//        AdminAuthStatusVO vo = new AdminAuthStatusVO();
//        vo.setPcsnsttsId(ComCode.REGULAR_MEMBER.getCode());
//        vo.setPcsnsttsNm(ComCode.REGULAR_MEMBER.getName());
//        list.add(vo);
//        vo = new AdminAuthStatusVO();
//        vo.setPcsnsttsId(ComCode.ASSOCIATE_MEMBER.getCode());
//        vo.setPcsnsttsNm(ComCode.ASSOCIATE_MEMBER.getName());
//        list.add(vo);
//        vo = new AdminAuthStatusVO();
//        vo.setPcsnsttsId(ComCode.AGENCY_MEMBER.getCode());
//        vo.setPcsnsttsNm(ComCode.AGENCY_MEMBER.getName());
//        list.add(vo);
//        return list;
//    }
}
