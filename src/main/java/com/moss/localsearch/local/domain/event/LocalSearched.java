package com.moss.localsearch.local.domain.event;

import lombok.Getter;

/**
 *  지역 검색 이벤트
 */
@Getter
public class LocalSearched {
    // 키워드
    private String keyword;

    public LocalSearched(String keyword) {
        this.keyword = keyword;
    }
}
