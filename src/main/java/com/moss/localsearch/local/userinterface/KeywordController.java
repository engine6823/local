package com.moss.localsearch.local.userinterface;

import com.moss.localsearch.local.dto.KeywordDto;
import com.moss.localsearch.local.service.KeywordService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 키워드 Controller
 */
@RestController
@RequestMapping("local/v1/keywords")
public class KeywordController {
    private final KeywordService service;

    public KeywordController(KeywordService service) {
        this.service = service;
    }

    /**
     * 키워드 목록 조회
     */
    @GetMapping
    public List<KeywordDto> getKeywordDtos() {
        return service.getKeywordDtos();
    }
}
