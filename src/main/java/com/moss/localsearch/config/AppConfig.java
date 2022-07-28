package com.moss.localsearch.config;

import com.moss.localsearch.local.infrastructure.search.KakaoLocalSearch;
import com.moss.localsearch.local.infrastructure.search.KakaoNaverLocalSearch;
import com.moss.localsearch.local.infrastructure.search.LocalSearch;
import com.moss.localsearch.local.infrastructure.search.NaverLocalSearch;
import com.moss.localsearch.shared.event.PublishEventAspect;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

@EnableScheduling
@EnableAsync
@EnableCaching(order = AppConfig.CacheableOrder)
@EnableConfigurationProperties({AppProperties.class})
@Configuration
public class AppConfig implements AsyncConfigurer {
    /**
     * {@literal @}Cacheable Order, {@literal @}Transaction 보다 우선 적용
     */
    public static final int CacheableOrder = Ordered.LOWEST_PRECEDENCE + 1;

    private final AppProperties appProperties;

    public AppConfig(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(8);
        executor.setMaxPoolSize(8);
        executor.setThreadNamePrefix("Executor-");
        executor.initialize();
        return executor;
    }

    /**
     * 메모리 Cache Manager
     */
    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        List<Cache> caches = new ArrayList<>();
        caches.add(new ConcurrentMapCache("localSearch"));
        caches.add(new ConcurrentMapCache("keyword"));
        cacheManager.setCaches(caches);
        return cacheManager;
    }

    /**
     * 이벤트 Publish Aspeoct
     */
    @Bean
    public PublishEventAspect publishEventAspect(ApplicationContext context) {
        return new PublishEventAspect(context);
    }

    /**
     * 장소 검색
     */
    @Bean
    public LocalSearch kakaoNaverLocalSearch() {
        return new KakaoNaverLocalSearch(
                new KakaoLocalSearch(
                        appProperties.getKakao().getServer(),
                        appProperties.getKakao().getRestApiKey()),
                new NaverLocalSearch(
                        appProperties.getNaver().getServer(),
                        appProperties.getNaver().getClientId(),
                        appProperties.getNaver().getClientSecret()));
    }
}
