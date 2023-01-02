package com.ibk.sb.restapi.biz.service.test;

import com.ibk.sb.restapi.biz.service.test.vo.ApiTestVO;
import com.ibk.sb.restapi.biz.service.test.vo.TestMainFundInvestSwiperVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class ApiTestService {
    public ApiTestVO getTest(){
        String id = "Invest";
        String name = "Iiunex";
        ApiTestVO apiTestVO = new ApiTestVO();
        apiTestVO.setId(id);
        apiTestVO.setName(name);
        return apiTestVO;
    }

    public ArrayList<TestMainFundInvestSwiperVO> getMainFuncInvestSwiper(){
        String[] imgList = {"/images/tmp/img_logo_b01.png", "/images/tmp/img_logo_b02.png", "/images/tmp/img_logo_b03.png"};
        String[] introList = {"IBK투자증권은 중소기업의 성장을 선도해 온 IBK기업은행이 중소업금융 리딩뱅크로서 확고한 지위를 확보",
                "17~18일 이틀 동안 올겨울 최강 한파가 온다. 아침 기온이 영하 15도 안팎까지 떨어지는 가운데 낮에도 영하권을 기록하는 곳이 많겠다. ",
                "수천 년에 걸쳐 그 모습을 드러내지 않고 살아온 불멸의 히어로들이 ‘어벤져스: 엔드게임’ 이후 인류의 가장 오래된 적 ‘데비안츠’"};
        String[] nameList = {"창업투자사","날씨","이터널스"};
        String[] titleList = { "고객과 함께하는 IBK성공투자",  "내일부터 올 겨울 최강 한파 온다..호남 등엔 최대 15cm 눈",  "이터널스’ 2022년 1월 12일 디즈니+ 스트리밍 확정"};
        String[] urlList = {"/main", "/main", "/main"};

        ArrayList<TestMainFundInvestSwiperVO> arrayList = new ArrayList<TestMainFundInvestSwiperVO>();
        for(int i = 0; i < imgList.length; i++){
            String img = imgList[i];
            String intro = introList[i];
            String name = nameList[i];
            String title = titleList[i];
            String url = urlList[i];
            TestMainFundInvestSwiperVO vo = new TestMainFundInvestSwiperVO();
            vo.setImg(img);
            vo.setIntro(intro);
            vo.setName(name);
            vo.setTitle(title);
            vo.setUrl(url);
            arrayList.add(vo);
        }
        return arrayList;
    }
}
