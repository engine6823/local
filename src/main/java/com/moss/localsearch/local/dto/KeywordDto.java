package com.moss.localsearch.local.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 키워드 Dto
 */
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Data
public class KeywordDto {
    // 키워드
    private String keyword;

    // 검색 수
    private Integer count;
}
