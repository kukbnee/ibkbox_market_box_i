package com.ibk.sb.restapi.biz.service.admin;

import com.ibk.sb.restapi.app.common.constant.ComCode;
import com.ibk.sb.restapi.app.common.vo.PagingVO;
import com.ibk.sb.restapi.biz.service.admin.repo.AdminSellerRepo;
import com.ibk.sb.restapi.biz.service.admin.vo.AdminProductRequestVO;
import com.ibk.sb.restapi.biz.service.admin.vo.AdminSellerTypeVO;
import com.ibk.sb.restapi.biz.service.admin.vo.AdminSellerVO;
import com.ibk.sb.restapi.biz.service.admin.vo.RequestSellerSearchVO;
import com.ibk.sb.restapi.biz.service.mainbox.MainBoxService;
import com.ibk.sb.restapi.biz.service.mainbox.vo.MainCompanyVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminSellerService {

    private final AdminSellerRepo repo;
    private final MainBoxService mainBoxService;

    /**
     * 판매자 관리 목록 조회
     * @param params
     * @return
     * @throws Exception
     */
    public PagingVO<AdminSellerVO> searchSellerList(RequestSellerSearchVO params) throws Exception {
        List<AdminSellerVO> list = repo.selectSellerList(params);
        if(list != null) {
            for (AdminSellerVO seller : list) {
                MainCompanyVO mainCompany = mainBoxService.searchMainCompany(seller.getSelrUsisId());
                if(mainCompany != null) {
                    seller.setRprsntvNm(mainCompany.getRprsntvNm());
                }
            }
        } else {
            list = new ArrayList<>();
        }
        return new PagingVO<>(params, list);
    }


    /**
     * 판매자 상세 정보 조회
     * @param selrUsisId
     * @return
     */
    public AdminSellerVO searchSellerDeatil(String selrUsisId) {
        return repo.selectSellerDeatil(selrUsisId);
    }

    /**
     * 판매자 자격 박탈
     * @param params
     * @return
     */
    public boolean updateRoleOff(AdminSellerVO params) {
        return repo.updateSellerStatus(ComCode.SELLER_ROLEOFF.getCode(), params.getSelrUsisId()) > 0 ? true : false;
    }

    /**
     * 판매자 자격 박탈 해제
     * @param params
     * @return
     */
    public boolean updateRoleOffCancel(AdminSellerVO params) {
        return repo.updateSellerStatus(ComCode.SELLER_APPROVED.getCode(), params.getSelrUsisId()) > 0 ? true : false;
    }

    /**
     * 판매자 유형 목록
     * @return
     */
    public List<AdminSellerTypeVO> searchSellerTypeList() {
        List<AdminSellerTypeVO> list = new ArrayList<>();
        AdminSellerTypeVO vo = new AdminSellerTypeVO();
        vo.setMmbrsttsId(ComCode.REGULAR_MEMBER.getCode());
        vo.setMmbrsttsNm(ComCode.REGULAR_MEMBER.getName());
        list.add(vo);
        vo = new AdminSellerTypeVO();
        vo.setMmbrsttsId(ComCode.ASSOCIATE_MEMBER.getCode());
        vo.setMmbrsttsNm(ComCode.ASSOCIATE_MEMBER.getName());
        list.add(vo);
        vo = new AdminSellerTypeVO();
        vo.setMmbrsttsId(ComCode.AGENCY_MEMBER.getCode());
        vo.setMmbrsttsNm(ComCode.AGENCY_MEMBER.getName());
        list.add(vo);
        return list;
    }

    /**
     * 판매사 등록 상품 판매중지
     * @param params
     * @return
     */
    public boolean updateSellerRegProductCancel(AdminProductRequestVO params) {
        return repo.updateProductStatus(params.getPdfInfoId(), ComCode.STOP_SALES_BY_MANAGER.getCode()) > 0 ? true : false;
    }

    /**
     * 판매사 등록 상품 판매중지상태 취소
     * @param params
     * @return
     */
    public boolean updateSellerRegProductRecovery(AdminProductRequestVO params) {
        return repo.updateProductStatus(params.getPdfInfoId(), ComCode.SELLING_OK.getCode()) > 0 ? true : false;
    }

    /**
     * 에이전시 상품 판매중지
     * @param params
     * @return
     */
    public boolean updateAgencyProductCancel(AdminProductRequestVO params) {
        return repo.updateProductStatus(params.getPdfInfoId(), ComCode.STOP_SALES_BY_MANAGER.getCode()) > 0 ? true : false;
    }

    /**
     * 에이전시 상품 판매중지상태 취소
     * @param params
     * @return
     */
    public boolean updateAgencyProductRecovery(AdminProductRequestVO params) {
        return repo.updateProductStatus(params.getPdfInfoId(), ComCode.SELLING_OK.getCode()) > 0 ? true : false;
    }

}
