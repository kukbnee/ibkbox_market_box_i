package com.ibk.sb.restapi.biz.service.category;

import com.ibk.sb.restapi.app.common.vo.ResponseData;
import com.ibk.sb.restapi.biz.service.category.repo.CategoryRepo;
import com.ibk.sb.restapi.biz.service.category.vo.CategoryVO;
import com.ibk.sb.restapi.biz.service.category.vo.ResponseCategoryVO;
import com.ibk.sb.restapi.biz.service.category.vo.SummaryCategoryVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepo categoryRepo;

    /**
     * 상품 카테고리 리스트 조회
     * @return
     */
    public List<ResponseCategoryVO> searchCategoryList(CategoryVO requsetCategoryVO) throws Exception {

        // 카테고리 리스트 조회
        List<ResponseCategoryVO> categoryVOList = categoryRepo.selectCategoryList(requsetCategoryVO);

        // 3차 코드가 있고, 4차 코드가 없는 경우
        // 3차 분류와 4차 분류를 트리 관계로 return
        if(StringUtils.hasLength(requsetCategoryVO.getTms3ClsfCd()) && !StringUtils.hasLength(requsetCategoryVO.getTms4ClsfCd())) {
            categoryVOList.forEach(x -> {
                requsetCategoryVO.setTms3ClsfCd(x.getCtgyId());
                x.setItems(categoryRepo.selectCategoryList(requsetCategoryVO));
            });
        }

        return categoryVOList;
    }

    /**
     * 카테고리 전체 목록
     * @return
     */
    public ResponseData searchCategoryDepthList(String parentCode) throws Exception {

        SummaryCategoryVO summaryCategoryVO = new SummaryCategoryVO();

        summaryCategoryVO.setDepthOne(categoryRepo.selectCategoryDepthList(1, parentCode));
        summaryCategoryVO.setDepthTwo(categoryRepo.selectCategoryDepthList(2, parentCode));
        summaryCategoryVO.setDepthThr(categoryRepo.selectCategoryDepthList(3, parentCode));
        summaryCategoryVO.setDepthFor(categoryRepo.selectCategoryDepthList(4, parentCode));
        summaryCategoryVO.setDepthFiv(categoryRepo.selectCategoryDepthList(5, parentCode));

        return ResponseData.builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(summaryCategoryVO)
                .build();
    }

//    /**
//     * Tree메뉴 Children 작성
//     * @param root
//     * @param allList
//     * @return
//     */
//    private List<MenuInfoWorkGroupVO> setChildren(MenuInfoWorkGroupVO root, List<MenuInfoWorkGroupVO> allList) {
//        List<MenuInfoWorkGroupVO> children =
//                allList.stream()
//                        .filter(x-> x.getParentMenuId().equals(root.getMenuId()))
//                        .map((x) -> {
//                            x.setNodes(setChildren(x, allList));
//                            return x;
//                        }).collect(Collectors.toList());
//        return children;
//    };

}
