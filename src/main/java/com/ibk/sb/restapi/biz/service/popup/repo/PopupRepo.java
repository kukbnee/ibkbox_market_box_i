package com.ibk.sb.restapi.biz.service.popup.repo;

import com.ibk.sb.restapi.biz.service.admin.vo.RequestSearchVO;
import com.ibk.sb.restapi.biz.service.popup.vo.PopupVO;
import com.ibk.sb.restapi.biz.service.popup.vo.RequestPopupSearchVO;
import com.ibk.sb.restapi.biz.service.popup.vo.SummaryPopupVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PopupRepo {

    List<SummaryPopupVO> selectPopupList();

    List<PopupVO> selectPopupPaging(RequestPopupSearchVO params);

    PopupVO selectPopupInfo(@Param("popupInfId") String popupInfId);

    int insertPopupInfo(@Param("params") PopupVO params);

    int updatePopupInfo(@Param("params") PopupVO params);

    int deletePopupInfo(@Param("params") PopupVO params);

}
