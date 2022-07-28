package com.moss.localsearch.local.userinterface;

import com.moss.localsearch.local.dto.LocalSearchResult;
import com.moss.localsearch.local.service.LocalService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 장소 Controller
 */
@RestController
@RequestMapping("local/v1/locals")
public class LocalController {
    private final LocalService service;

    public LocalController(LocalService service) {
        this.service = service;
    }

    /**
     * 장소 검색
     * @param keyword 키워드
     * @return 장소 검색 결과
     */
    @GetMapping("search")
    public LocalSearchResult search(@RequestParam String keyword) {
        return service.search(keyword);
    }
}
