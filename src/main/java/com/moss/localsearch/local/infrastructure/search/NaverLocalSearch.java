package com.moss.localsearch.local.infrastructure.search;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moss.localsearch.local.infrastructure.search.naver.NaverLocalSearchQuery;
import com.moss.localsearch.local.infrastructure.search.naver.NaverLocalSearchResult;
import com.moss.localsearch.shared.util.Assert;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 네이버 검색 API
 * @see <a href="https://developers.naver.com/docs/serviceapi/search/local/local.md">네이버 지역 API</a>
 */
public class NaverLocalSearch implements LocalSearch {
    // 페이지당 최대 조회 개수
    private static final int MAX_DISPLAY = 5;
    
    // Api 호출용 RestTemplate
    private RestTemplate restTemplate;

    // API 서버 주소
    private String server;

    public NaverLocalSearch(String server, String clientId, String clientSecret) {
        Assert.hasText(server, "server can not be blank");
        Assert.hasText(clientId, "clientId can not be blank");
        Assert.hasText(clientSecret, "clientSecret can not be blank");
        this.server = server;

        restTemplate = new RestTemplate();
        ObjectMapper objectMapper = new ObjectMapper();
        // model에 @JsonIgnorePropert(ignoreUnkonw=true)를 제거하기 위함
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // null값은 결과에 포함되지 않도록 함
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        // message converter
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        messageConverters.add(new MappingJackson2HttpMessageConverter(objectMapper));
        messageConverters.add(new StringHttpMessageConverter(StandardCharsets.UTF_8));
        restTemplate.setMessageConverters(messageConverters);

        // Authoriazation Header
        restTemplate.getInterceptors().add((request, body, execution) -> {
            request.getHeaders().set("X-Naver-Client-Id", clientId);
            request.getHeaders().set("X-Naver-Client-Secret", clientSecret);
            return execution.execute(request, body);
        });
    }


    /**
     * 지역 API
     * 네이버 지역 서비스에 등록된 각 지역별 업체 및 상호 검색 결과를 출력해주는 REST API입니다.
     * 비로그인 오픈 API이므로 GET으로 호출할 때 HTTP Header에 애플리케이션 등록 시 발급받은 Client ID와 Client Secret 값을 같이 전송해 주시면 활용 가능합니다.
     * @see <a href="https://developers.naver.com/docs/serviceapi/search/local/local.md">네이버 지역 API</a>
     * @param query
     * @return
     */
    public NaverLocalSearchResult search(NaverLocalSearchQuery query) {
        var result = restTemplate.getForObject(server + "/v1/search/local.json?query={query}&display={display}",
                NaverLocalSearchResult.class, query.getQuery(), query.getDisplay());
        return result;
    }

    /**
     * {@inheritDoc}
     * 검색 최대 개수: 5
     * Title의 강조(<b></b>) 제거
     */
    @Override
    public List<Local> search(String keyword, Integer count) {
        if (count > MAX_DISPLAY) {
            count = MAX_DISPLAY;
        }
        return search(NaverLocalSearchQuery.builder().query(keyword).display(count).build())
                .getItems().stream()
                .map(item -> Local.of(item.getTitle().replaceAll("[(<b>)(</b>)]", ""), item.getAddress(), item.getRoadAddress()))
                .collect(Collectors.toList());
    }
}
