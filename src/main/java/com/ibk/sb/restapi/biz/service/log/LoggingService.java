package com.ibk.sb.restapi.biz.service.log;

//import com.github.pagehelper.PageHelper;
//import com.github.pagehelper.PageInfo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class LoggingService {

//    private final UserMenuAccessLogRepo userMenuAccessLogRepo;
//
//    private final BatchLogRepo batchLogRepo;
//
//
//    public void startBatch(String schedulerName, String methodName, BatchStatus status) {
//
//        BatchLogVO batchLogVO = new BatchLogVO();
//        batchLogVO.setBatchExecScheduler(schedulerName);
//        batchLogVO.setBatchExecMethod(methodName);
//        batchLogVO.setBatchStatus(status.name());
//        batchLogRepo.insertBatchLog(batchLogVO);
//    }
//
//
//    public void endBatch(String schedulerName, String methodName, BatchStatus status) {
//
//        BatchLogVO batchLogVO = new BatchLogVO();
//        batchLogVO.setBatchExecScheduler(schedulerName);
//        batchLogVO.setBatchExecMethod(methodName);
//        batchLogVO.setBatchStatus(status.name());
//        batchLogRepo.insertBatchLog(batchLogVO);
//    }
//
//
//    /**
//     * 배치 로그 검색
//     * @param batchLogSeq
//     * @return
//     */
//    public BatchLogVO searchBatchLog(int batchLogSeq) throws Exception {
//
//        BatchLogVO batchLogVO = batchLogRepo.selectBatchLog(batchLogSeq);
//
//        if(batchLogVO == null) {
//            throw new Exception("해달 로그가 존재하지 않음");
//        }
//
//        return batchLogVO;
//    }
//
//
//    /**
//     * 배치 로그 리스트 조건 검색
//     * @param requestBatchLogSearchVO
//     * @return
//     */
//    public PageInfo searchBatchLogList(RequestBatchLogSearchVO requestBatchLogSearchVO) {
//
//        //PageHelper set
//        PageHelper.startPage(requestBatchLogSearchVO.getPageOfInt(), requestBatchLogSearchVO.getRecordsOfInt());
//
//        List<BatchLogVO> batchLogVOList = batchLogRepo.selectBatchLogList(requestBatchLogSearchVO.getBatchStatus());
//
//        return new PageInfo<>(batchLogVOList);
//    }
//
//    /**
//     * 배치 로그 갱신
//     * @param batchLogVO
//     */
//    public void updateBatchLog(BatchLogVO batchLogVO) throws Exception {
//
//        // 해당 배치 로그가 존재하지 않을 경우, 에러
//        if(batchLogRepo.selectBatchLog(batchLogVO.getBatchLogSeq()) == null) {
//            throw new Exception("해당 배치 로그가 존재하지 않음");
//        }
//
//        // update
//        batchLogRepo.updateBatchLog(batchLogVO);
//    }
//
//
//    /**
//     * 메뉴에 접근한 유저 로그 저장
//     * @param userMenuAccessLogVO
//     * @return
//     */
//    public void saveUserMenuAccessLog(UserMenuAccessLogVO userMenuAccessLogVO) throws Exception{
//
//        //로그인한 유저 정보 획득
//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//        // HttpServletRequest 획득
//        HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
//
//        userMenuAccessLogVO.setUserId(user.getUsername());
//        userMenuAccessLogVO.setBrowserType(HttpServletRequestUtil.getBrowserinfo(req));
//        userMenuAccessLogVO.setIpAddress(HttpServletRequestUtil.getClientIP(req));
//        userMenuAccessLogVO.setCreateUserId(user.getUsername());
//
////        userMenuAccessLogRepo.insertUserMenuAccessLog(userMenuAccessLogVO);
//    }
}