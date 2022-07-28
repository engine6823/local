package com.moss.localsearch.local.eventhandler;

import com.moss.localsearch.local.domain.event.LocalSearched;
import com.moss.localsearch.local.service.KeywordService;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 장소 이벤트 핸들러
 */
@Component
public class LocalEventHandler {
    private final KeywordService keywordService;

    public LocalEventHandler(KeywordService keywordService) {
        this.keywordService = keywordService;
    }

    /**
     * 장소 검색 이벤트 처리
     */
    @Async
    @EventListener
    public void localSearched(LocalSearched event) {
        keywordService.countUp(event.getKeyword());
     }

}
