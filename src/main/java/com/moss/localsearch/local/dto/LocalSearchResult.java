package com.moss.localsearch.local.dto;

import com.moss.localsearch.local.infrastructure.search.Local;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 장소 검색 결과
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LocalSearchResult {
    // 키워드
    private String keyword;

    // 총수
    private Integer total;

    // 장소 목록
    private List<Local> locals;
}
