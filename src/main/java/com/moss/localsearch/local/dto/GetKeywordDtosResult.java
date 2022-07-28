package com.moss.localsearch.local.dto;

import lombok.Data;

import java.util.List;

/**
 * 키워드 Dto 목록 조회 결과
 */
@Data
public class GetKeywordDtosResult {
    // 결과 총 수
    private Integer count;
    
    // 키워드 목록
    private List<KeywordDto> list;
}
