package com.moss.localsearch.local.service;

import com.moss.localsearch.config.AppProperties;
import com.moss.localsearch.local.domain.event.LocalSearched;
import com.moss.localsearch.local.dto.LocalSearchResult;
import com.moss.localsearch.local.infrastructure.search.LocalSearch;
import com.moss.localsearch.shared.event.PublishEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;

/**
 * 장소 Service
 */
@Slf4j
@Validated
@Service
public class LocalService {
    // 장소 검색
    private final LocalSearch localSearch;

    // 검색 수
    private Integer searchCount;

    public LocalService(AppProperties appProperties, LocalSearch localSearch) {
        this.localSearch = localSearch;
        this.searchCount = appProperties.getSearchCount();
    }

    /**
     * 키워드에 해당하는 장소 검색
     * - 기본적으로 카카오 결과를 기준 순서로 사용합니다.
     * - 카카오 결과 네이버 결과 모두 존재해서 상위로 정렬
     * - 둘 중 하나에만 존재하는 경우, 카카오 결과를 우선 배치 후 네이버 결과 배치
     * @param keyword 키워드
     * @return 장소 검색 결과
     */
    @PublishEvent(type = LocalSearched.class, args = {"keyword"})
    @Cacheable(value = "localSearch", key = "#keyword")
    public LocalSearchResult search(@NotEmpty String keyword) {
        var locals = localSearch.search(keyword, searchCount);
        return LocalSearchResult.builder()
                .keyword(keyword)
                .total(locals.size())
                .locals(locals)
                .build();
    }

    /**
     * 매일 새벽 2시 장소 검색 캐시 삭제
     */
    @Scheduled(cron = "0 0 2 * * *")
    @CacheEvict(value = "localSearch")
    public void cacheEvictSearch() {
        log.info("localSearch cache is removed.");
    }
}
