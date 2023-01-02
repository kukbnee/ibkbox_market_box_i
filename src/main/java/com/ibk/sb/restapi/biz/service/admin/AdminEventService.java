package com.ibk.sb.restapi.biz.service.admin;

import com.ibk.sb.restapi.app.common.constant.ComCode;
import com.ibk.sb.restapi.app.common.constant.StatusCode;
import com.ibk.sb.restapi.app.common.util.BizException;
import com.ibk.sb.restapi.app.common.util.FileUtil;
import com.ibk.sb.restapi.app.common.vo.PagingVO;
import com.ibk.sb.restapi.biz.service.admin.repo.AdminEventRepo;
import com.ibk.sb.restapi.biz.service.admin.vo.AdminEventProductVO;
import com.ibk.sb.restapi.biz.service.admin.vo.AdminEventVO;
import com.ibk.sb.restapi.biz.service.admin.vo.RequestEventSearchProductVO;
import com.ibk.sb.restapi.biz.service.event.EventService;
import com.ibk.sb.restapi.biz.service.event.repo.EventRepo;
import com.ibk.sb.restapi.biz.service.event.vo.EventProductVO;
import com.ibk.sb.restapi.biz.service.event.vo.RequestSearchEventVO;
import com.ibk.sb.restapi.biz.service.event.vo.SummaryEventVO;
import com.ibk.sb.restapi.biz.service.file.repo.FileRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class AdminEventService {

    private final EventService service;
    private final EventRepo repo;
    private final AdminEventRepo adminEventRepo;
    private final FileRepo fileRepo;

    /**
     * 이벤트 목록 특정상태 조회
     * @param searchParams
     * @return
     * @throws Exception
     */
    public PagingVO<SummaryEventVO> searchEventList(RequestSearchEventVO searchParams) throws Exception {
        return service.searchEventPageList(searchParams);
    }

    /**
     * 이벤트 상세 정보 조회
     */
    public SummaryEventVO searchEventDetail(String evntInfId) throws Exception {
        SummaryEventVO detail = service.searchEventDetailInfo(evntInfId);
        // 파일 상세 정보 조히
        if(detail != null && StringUtils.hasLength(detail.getFileId())) {
            detail.setImgFileInfo(fileRepo.selectFileInfo(detail.getFileId()));
            detail.setImgUrl(FileUtil.setImageUrl(detail.getImgFileId()));
        }
        return detail;
    }

    /**
     * 이벤트 정보 저장
     * @param params
     * @return
     */
    public boolean saveEventInfo(AdminEventVO params) {
        int result = 0;
        if(!StringUtils.hasLength(params.getEvntInfId())) {
            String evntInfId = UUID.randomUUID().toString();
            params.setEvntInfId(evntInfId);

            // 모집 마감일이 시작일 보다 큰 경우는 준비중으로 설정 : 모집 마감일 > 이벤트 시작일
            if(params.getEnlsCldyTs().toLocalDateTime().isAfter(params.getEvntStdyTs().toLocalDateTime())){
                params.setPgstId(ComCode.ETS00002.getCode()); //준비중
            }else{
                params.setPgstId(ComCode.ETS00001.getCode()); //진행중
            }

            result = repo.insertEventInfo(params);
        } else {
            result = repo.updateEventInfo(params);
        }
        return result > 0 ? true : false;
    }

    /**
     * 이벤트 정보 삭제
     * @param params
     * @return
     */
    public boolean deleteEventInfo(AdminEventVO params) {
        return repo.deleteEventInfo(params) > 0 ? true : false;
    }

    /**
     * 이벤트 신청 상품 승인 목록 조회
     * @param params
     * @return
     */
    public PagingVO<EventProductVO> searchEventProductList(RequestEventSearchProductVO params) {
        params.setPcsnsttsId(ComCode.ETS01002.getCode());         /* 이벤트 신정 상태 ID */
        params.setFilePtrnId(ComCode.GDS05001.getCode());
        params.setPdfSttsId(ComCode.SELLING_OK.getCode());        /* 상품상태 ID */
        List<EventProductVO> productList = adminEventRepo.selectAdminEventProductList(params);
        productList.forEach(detail -> {
            if(detail != null && StringUtils.hasLength(detail.getImgFileId())) {
                detail.setImgFileInfo(fileRepo.selectFileInfo(detail.getImgFileId()));
                detail.setImgUrl(FileUtil.setImageUrl(detail.getImgFileId()));
            }
        });
        return new PagingVO<>(params, productList);
    }

    /**
     * 이벤트 신청 상품 요청 목록 조회
     * @param params
     * @return
     */
    public PagingVO<EventProductVO> searchEventRequestProductList(RequestEventSearchProductVO params) {
        params.setPcsnsttsId(ComCode.ETS01001.getCode());         /* 이벤트 신정 상태 ID */
        params.setFilePtrnId(ComCode.GDS05001.getCode());
        params.setPdfSttsId(ComCode.SELLING_OK.getCode());        /* 상품상태 ID */
        List<EventProductVO> productList = adminEventRepo.selectAdminEventProductList(params);
        productList.forEach(detail -> {
            if(detail != null && StringUtils.hasLength(detail.getImgFileId())) {
                detail.setImgFileInfo(fileRepo.selectFileInfo(detail.getImgFileId()));
                detail.setImgUrl(FileUtil.setImageUrl(detail.getImgFileId()));
            }
        });
        return new PagingVO<>(params, productList);
    }

    /**
     * 이벤트 상품추가 상품 검색
     * @param params
     * @return
     */
    public PagingVO<EventProductVO> searchEventEveryProductList(RequestEventSearchProductVO params) {
        params.setEveryEventProductYn(true);
        params.setFilePtrnId(ComCode.GDS05001.getCode());
        params.setPdfSttsId(ComCode.SELLING_OK.getCode());        /* 상품상태 ID */
        List<EventProductVO> productList = adminEventRepo.selectAdminEventAllProductList(params);
        productList.forEach(detail -> {
            if(detail != null && StringUtils.hasLength(detail.getImgFileId())) {
                detail.setImgFileInfo(fileRepo.selectFileInfo(detail.getImgFileId()));
                detail.setImgUrl(FileUtil.setImageUrl(detail.getImgFileId()));
            }
        });
        return new PagingVO<>(params, productList);
    }

    /**
     * 이벤트 상품 추가 (승인)
     */
    public boolean approveEventProduct(List<AdminEventProductVO> list) {
        AtomicInteger errCnt = new AtomicInteger();
        list.forEach(vo -> {
            vo.setPcsnsttsId(ComCode.ETS01002.getCode());
            if(adminEventRepo.updateEventProductApproved(vo) < 1) {
                if(errCnt.get() < 0 || errCnt.get() > Integer.MAX_VALUE - 1) {
                    throw new BizException(StatusCode.MNB0002);
                }
                errCnt.getAndIncrement();
            }
        });
        if(errCnt.get() > 0) throw new BizException(StatusCode.MNB0002);
        return true;
    }
    public boolean addEventProduct(List<AdminEventProductVO> list) {
        AtomicInteger errCnt = new AtomicInteger();
        list.forEach(vo -> {
            vo.setPcsnsttsId(ComCode.ETS01002.getCode());
            vo.setRcipptrnId(ComCode.ETS02002.getCode());
            if(adminEventRepo.insertEventProductApproved(vo) < 1) {
                if(errCnt.get() < 0 || errCnt.get() > Integer.MAX_VALUE - 1) {
                    throw new BizException(StatusCode.MNB0002);
                }
                errCnt.getAndIncrement();
            }
        });
        if(errCnt.get() > 0) throw new BizException(StatusCode.MNB0002);
        return true;
    }

    /**
     * 이벤트 상품 추가 취소 (승인 취소)
     */
    public boolean cancelAddEventProduct(List<AdminEventProductVO> list) {

        //  todo 승인요청인지 아닌지 구분해서 update or delete 처리할 것 확인

        AtomicInteger errCnt = new AtomicInteger();
        list.forEach(vo -> {
            AdminEventProductVO result = adminEventRepo.selectEventProductApproved(vo);
            if(result.getRcipptrnId().equals(ComCode.ETS02002.getCode())) { //관리자 직접접수 인경우는 정보 삭제
                adminEventRepo.deleteEventProductApproved(vo);
            }else{
                vo.setPcsnsttsId(ComCode.ETS01001.getCode());
                if(adminEventRepo.updateEventProductApproved(vo) < 1) {
                    if(errCnt.get() < 0 || errCnt.get() > Integer.MAX_VALUE - 1) {
                        throw new BizException(StatusCode.MNB0002);
                    }
                    errCnt.getAndIncrement();
                }
            }
        });
        if(errCnt.get() > 0) throw new BizException(StatusCode.MNB0002);
        return true;
    }

    /**
     * 이벤트 목록 순번 저장
     */
    public boolean saveEventProductSort(List<AdminEventProductVO> list) {
        AtomicInteger errCnt = new AtomicInteger();
        list.forEach(vo -> {
            if (adminEventRepo.updateEventProductSort(vo) < 1) {
                if(errCnt.get() < 0 || errCnt.get() > Integer.MAX_VALUE - 1) {
                    throw new BizException(StatusCode.MNB0002);
                }
                errCnt.getAndIncrement();
            }
        });
        if (errCnt.get() > 0) throw new BizException(StatusCode.MNB0002);
        return true;
    }

}
