package com.moss.localsearch.local.service;

import com.moss.localsearch.local.domain.model.Keyword;
import com.moss.localsearch.local.domain.repository.KeywordRepository;
import com.moss.localsearch.local.dto.KeywordDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Keyword 서비스
 */
@Slf4j
@Validated
@Service
public class KeywordService {
    private final KeywordRepository repository;

    public KeywordService(KeywordRepository repository) {
        this.repository = repository;
    }

    /**
     * 키워드 목록 조회 - 횟수 내림차순, 최대 10개
     * @return 키워드 목록
     */
    @Cacheable(value = "keyword")
    @Transactional(readOnly = true)
    public List<KeywordDto> getKeywordDtos() {
        return repository.findTop10ByOrderByCountDesc()
                .stream().map(model -> KeywordDto.of(model.getKeyword(), model.getCount()))
                .collect(Collectors.toList());
    }

    /**
     * 키워드 검색 횟수 추가
     * @param keyword 키워드
     */
    @Transactional
    public void countUp(@NotEmpty String keyword) {
        repository.findByIdForUpdate(keyword).ifPresentOrElse(
                searchCount -> searchCount.add(1),
                () -> repository.save(new Keyword(keyword, 1)));
    }

    /**
     * 5분마다 키워드 목록 조회 캐쉬 삭제
     */
    @Scheduled(cron = "0 */5 * * * *")
    @CacheEvict(value = "keyword")
    public void cacheEvictKeyword() {
        log.info("keyword cache is removed");
    }
}
