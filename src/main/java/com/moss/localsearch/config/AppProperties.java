package com.moss.localsearch.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "app")
public class AppProperties {
    /**
     * 검색 개수 (기본값 : 10)
     */
    private Integer searchCount = 10;

    /**
     * 카카오 API 설정
     */
    private KakaoProperties kakao = new KakaoProperties();

    /**
     * 네이버 API 설정
     */
    private NaverProperties naver = new NaverProperties();

    @Data
    public static class KakaoProperties {
        /**
         * API 서버 주소
         */
        private String server;

        /**
         * REST API 키
         */
        private String restApiKey;
    }
    @Data
    public static class NaverProperties {
        /**
         * API 서버 주소
         */
        private String server;

        /**
         * API Client Id
         */
        private String clientId;

        /**
         * API Client Secret
         */
        private String clientSecret;
    }
}
