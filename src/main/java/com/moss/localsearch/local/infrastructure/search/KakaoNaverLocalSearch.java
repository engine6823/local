package com.moss.localsearch.local.infrastructure.search;

import com.moss.localsearch.shared.util.Assert;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * 키워드에 해당하는 장소 검색
 * - 기본적으로 카카오 결과를 기준 순서로 사용합니다.
 * - 카카오 결과 네이버 결과 모두 존재해서 상위로 정렬
 * - 둘 중 하나에만 존재하는 경우, 카카오 결과를 우선 배치 후 네이버 결과 배치
 */
@Slf4j
public class KakaoNaverLocalSearch implements LocalSearch {
    // 카카오 지역 검색
    private LocalSearch kakaoLocalSearch;
    // 네이버 지역 검색
    private LocalSearch naverLocalSearch;

    public KakaoNaverLocalSearch(LocalSearch kakaoLocalSearch, LocalSearch naverLocalSearch) {
        Assert.notNull(kakaoLocalSearch, "kakaoLocalSearch can not be null");
        Assert.notNull(naverLocalSearch, "naverLocalSearch can not be null");
        this.kakaoLocalSearch = kakaoLocalSearch;
        this.naverLocalSearch = naverLocalSearch;
    }

    @Override
    public List<Local> search(String keyword, Integer count) {
        // 카카오 조회
        List<Local> kakaos = null;
        try {
            kakaos = kakaoLocalSearch.search(keyword, count);
        } catch (Exception e) {
            log.error("fail to get kakao local API", e);
            kakaos = new ArrayList<>();
        }

        // 네이버 조회
        List<Local> navers = null;
        try {
            navers = naverLocalSearch.search(keyword, count);
        } catch (Exception e) {
            log.error("fail to get naver local API", e);
            navers = new ArrayList<>();
        }
        
        // 결과 계산
        List<Local> result = new ArrayList<>();
        List<Local> onlyKakao = new ArrayList<>();
        List<Local> onlyNaver = new ArrayList<>();

        // 두곳 모두 존재 또는 카카오만 존재 확인
        for (var kakao : kakaos) {
            boolean isAdded = false;
            for (var naver : navers) {
                if (isSame(kakao, naver)) {
                    result.add(kakao);
                    isAdded = true;
                    break;
                }
            }

            if (!isAdded) {
                onlyKakao.add(kakao);
            }
        }

        // 네이버만 존재 확인
        for (var naver : navers) {
            boolean sameExist = false;
            for (var kakao : result) {
                if (isSame(kakao, naver)) {
                    sameExist = true;
                    break;
                }
            }

            if (!sameExist) {
                onlyNaver.add(naver);
            }
        }

        // kakao만 존재
        result.addAll(onlyKakao);

        // naver만 존재
        result.addAll(onlyNaver);

        if (result.size() <= count) {
            return result;
        }
        return result.subList(0, count);
    }



    /**
     * 동일 장소임을 비교
     * 1. 카카오의 시.도(첫번째) 항목이 네이버의 시작과 같음을 확인
     * 2. 카카오의 두번쨰 항목 부터 주소가 네이버의 주소에 포함여부 확인
     * @param kakao 카카오 장소
     * @param naver 네이버 장소
     * @return 동일 여부
     */
    private boolean isSame(Local kakao, Local naver) {
        int indexOfSpace = kakao.getAddress().indexOf(' ');
        String firstPart = kakao.getAddress().substring(0, indexOfSpace);
        if (!naver.getAddress().startsWith(firstPart)) {
            return false;
        }

        String lastPart = kakao.getAddress().substring(indexOfSpace + 1);
        if (!naver.getAddress().contains(lastPart)) {
            return  false;
        }
        return true;
    }
}
