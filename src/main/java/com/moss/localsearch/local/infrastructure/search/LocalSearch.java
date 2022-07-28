package com.moss.localsearch.local.infrastructure.search;

import java.util.List;

/**
 * 지역 검색
 */
public interface LocalSearch {
    /**
     * 키워드에 대한 지역 검색
     * @param keyword 키워드
     * @param count 검색 수
     * @return 지역 검색 결과
     */
    List<Local> search(String keyword, Integer count);
}
