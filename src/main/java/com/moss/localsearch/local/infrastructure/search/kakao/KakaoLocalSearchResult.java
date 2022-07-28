package com.moss.localsearch.local.infrastructure.search.kakao;

import lombok.Data;

import java.util.List;

@Data
public class KakaoLocalSearchResult {
    /**
     * 메타 정보
     */
    private Meta meta;

    /**
     * 문서 목록
     */
    private List<Document> documents;
}
