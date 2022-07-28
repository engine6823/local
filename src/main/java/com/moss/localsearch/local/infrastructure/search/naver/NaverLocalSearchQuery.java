package com.moss.localsearch.local.infrastructure.search.naver;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NaverLocalSearchQuery {
    /**
     * 검색을 원하는 문자열로서 UTF-8로 인코딩한다.
     * @required O
     */
    private String query;

    /**
     * 1(기본값), 5(최대)	검색 결과 출력 건수 지정
     * @required X
     */
    private Integer display;

    /**
     * 1(기본값), 1(최대)	검색 시작 위치로 1만 가능
     * @required X
     */
    private Integer start;

    /**
     * random (기본값), comment
     * 정렬 옵션: random(유사도순), comment(카페/블로그 리뷰 개수 순)
     */
    private String sort;
}
