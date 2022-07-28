package com.moss.localsearch.local.infrastructure.search;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

public class KakaoNaverLocalSearchTest {

    @Mock
    LocalSearch kakaoLocalSearch;
    @Mock
    LocalSearch naverLocalSearch;

    List<Local> kakaos;
    List<Local> navers;

    @BeforeEach
    public void beforeEach() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void geocoding() {
        // 네이버(KTM) -> 카카오(WGS84)
        //

        KakaoLocalSearch search = new KakaoLocalSearch("https://dapi.kakao.com", "1a3e3a7cdd1377bb20830ff9acc2e617");
        var result = search.geocoding(303671, 551215, "KTM", "WGS84");
        System.out.println(result);
        // https://windyroom.tistory.com/m/entry/%EB%84%A4%EC%9D%B4%EB%B2%84-%EC%A2%8C%ED%91%9C%EB%A5%BC-%EC%B9%B4%EC%B9%B4%EC%98%A4-%EC%A2%8C%ED%91%9C%EB%A1%9C-%EB%B3%80%EA%B2%BD-%EC%A2%8C%ED%91%9C%EA%B3%84-%EB%B3%80%EA%B2%BD
        // 이 때 input_coords는 KTM, output_coord는 WGS84를 사용하면 됩니다!
        // 소수 4자리 까지 같으면 같은 장소로 판단 가능할 것으로 보여짐
        
    }
}
