package com.moss.localsearch.local.infrastructure.search.naver;

import lombok.Data;

import java.util.List;

@Data
public class NaverLocalSearchResult {

    /**
     * 검색 결과를 생성한 시간이다.
     * 예) Sun, 24 Jul 2022 03:42:09 +0900"
     */
    private String lastBuildDate;

    /**
     * 검색 결과 문서의 총 개수를 의미한다.
     */
    private Integer total;

    /**
     * 	검색 결과 문서 중, 문서의 시작점을 의미한다.
     */
    private Integer start;

    /**
     * 검색된 검색 결과의 개수이다.
     */
    private Integer display;

    /**
     * 개별 검색 결과이며 title, link, description, address, mapx, mapy를 포함한다.
     */
    private List<Item> items;
}
