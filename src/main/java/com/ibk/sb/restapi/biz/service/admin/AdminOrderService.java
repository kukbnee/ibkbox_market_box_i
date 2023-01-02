package com.ibk.sb.restapi.biz.service.admin;

import com.ibk.sb.restapi.app.common.constant.ComCode;
import com.ibk.sb.restapi.app.common.util.FileUtil;
import com.ibk.sb.restapi.app.common.vo.PagingVO;
import com.ibk.sb.restapi.biz.service.admin.repo.AdminOrderRepo;
import com.ibk.sb.restapi.biz.service.admin.vo.AdminOrderVO;
import com.ibk.sb.restapi.biz.service.admin.vo.AdminSellerVO;
import com.ibk.sb.restapi.biz.service.admin.vo.RequestOrderSearchVO;
import com.ibk.sb.restapi.biz.service.common.repo.ComCodeRepo;
import com.ibk.sb.restapi.biz.service.common.vo.SummaryComCodeListVO;
import com.ibk.sb.restapi.biz.service.estimation.repo.EstimationProductRepo;
import com.ibk.sb.restapi.biz.service.estimation.repo.EstimationRepo;
import com.ibk.sb.restapi.biz.service.estimation.vo.EstimationProductVO;
import com.ibk.sb.restapi.biz.service.estimation.vo.RequestSearchEstimationVO;
import com.ibk.sb.restapi.biz.service.estimation.vo.SummaryEstimationInfoVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminOrderService {

    private final AdminOrderRepo repo;
    private final EstimationRepo estimationRepo;
    private final EstimationProductRepo estimationProductRepo;
    private final ComCodeRepo comCodeRepo;
    private final FileUtil fileUtil;

    /**
     * 주문 목록 조회
     * @param requestOrderSearchVO
     * @return
     * @throws Exception
     */
    public PagingVO<AdminOrderVO> searchOrderList(RequestOrderSearchVO requestOrderSearchVO) throws Exception {

        List<AdminOrderVO> list = repo.searchOrderList(requestOrderSearchVO);

        if(list.size() > 0){
            list.forEach(x -> {
                x.setProducts(repo.searchOrderProductList(x.getOrdnInfoId(), requestOrderSearchVO.getOrdnSttsId()));
            });
        }

        return new PagingVO<>(requestOrderSearchVO, list);
    }

    /**
     * 운영자포털 > 주문관리 > 견적상세
     * @param esttInfoId
     * @return
     * @throws Exception
     */
    public SummaryEstimationInfoVO searchEstimation(String esttInfoId) throws Exception {

        RequestSearchEstimationVO requestSearchEstimationVO = new RequestSearchEstimationVO();
        requestSearchEstimationVO.setEsttInfoId(esttInfoId);

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
}
