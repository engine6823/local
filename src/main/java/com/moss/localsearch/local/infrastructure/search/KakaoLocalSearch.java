package com.moss.localsearch.local.infrastructure.search;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.moss.localsearch.local.infrastructure.search.kakao.KakaoLocalSearchQuery;
import com.moss.localsearch.local.infrastructure.search.kakao.KakaoLocalSearchResult;
import com.moss.localsearch.shared.util.Assert;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// https://dapi.kakao.com/v2/local/search/keyword.json?y=37.514322572335935&x=127.06283102249932&radius=20000"

/**
 * 카카오 로컬 API
 * @see <a href="https://developers.kakao.com/docs/latest/ko/local/dev-guide">카카오 로컬 API</a>
 */
public class KakaoLocalSearch implements LocalSearch {
    // 페이지당 최대 조회 개수
    private final int MAX_SIZE = 45;
    
    // API 호출용 RestTemplate
    private RestTemplate restTemplate;

    // API 서버 주소
    private String server;

    public KakaoLocalSearch(String server, String restApiKey) {
        Assert.hasText(server, "server can not be blank");
        Assert.hasText(restApiKey, "restApiKey can not be blank");
        this.server = server;

        restTemplate = new RestTemplate();
        ObjectMapper objectMapper = new ObjectMapper();
        // model에 @JsonIgnorePropert(ignoreUnkonw=true)를 제거하기 위함
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // null값은 결과에 포함되지 않도록 함
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        // snake_case
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);

        // message converter
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        messageConverters.add(new MappingJackson2HttpMessageConverter(objectMapper));
        messageConverters.add(new StringHttpMessageConverter(StandardCharsets.UTF_8));
        restTemplate.setMessageConverters(messageConverters);

        // Authorization Header
        var key = "KakaoAK " + restApiKey;
        restTemplate.getInterceptors().add((request, body, execution) -> {
            request.getHeaders().set("Authorization", key);
            return execution.execute(request, body);
        });
    }

    /**
     * 좌표계 변환하기
     * x, y 값과 입력 및 출력 좌표계를 지정해 변환된 좌표 값을 구해, 서로 다른 좌표계간 데이터 호환이 가능하도록 합니다.
     * @param x X 좌표값, 경위도인 경우 longitude(경도)
     * @param y Y 좌표값, 경위도인 경우 latitude(위도)
     * @param inputCoord , y 값의 좌표계 지원 좌표계: WGS84, WCONGNAMUL, CONGNAMUL, WTM, TM, KTM, UTM, BESSEL, WKTM, WUTM (기본값: WGS84)
     * @param outputCoord 변환할 좌표계 지원 좌표계:WGS84, WCONGNAMUL, CONGNAMUL, WTM, TM, KTM, UTM, BESSEL, WKTM, WUTM (기본값: WGS84)
     * @return
     */
    public Map geocoding(double x, double y, String inputCoord, String outputCoord) {
        var result = restTemplate.getForObject(server + "/v2/local/geo/transcoord.json?x={x}&y={y}&input_coord={inputCoord}&output_coord={outputCoord}",
                Map.class, x, y, inputCoord, outputCoord);
        return result;

    }

    /**
     * 키워드로 장소 검색하기
     * 질의어에 매칭된 장소 검색 결과를 지정된 정렬 기준에 따라 제공합니다. 현재 위치 좌표, 반경 제한, 정렬 옵션, 페이징 등의 기능을 통해 원하는 결과를 요청 할 수 있습니다.
     *
     * 앱 REST API 키를 헤더에 담아 GET으로 요청합니다. 원하는 검색어와 함께 결과 형식 파라미터의 값을 선택적으로 추가할 수 있습니다.
     *
     * 응답은 JSON과 XML 형식을 지원합니다. 요청 시 URL의 ${FORMAT} 부분에 원하는 응답 형식을 지정할 수 있습니다. 별도로 포맷을 지정하지 않은 경우 응답은 JSON 형식으로 반환됩니다.
     *
     * 요청 성공 시 응답의 장소 정보는 이름, 주소, 좌표, 카테고리 등의 기본 정보와 다양한 부가정보, 카카오 맵의 장소 상세 페이지로 연결되는 URL을 제공합니다.
     * @see <a href="https://developers.kakao.com/docs/latest/ko/local/dev-guide#search-by-keyword">키워드로 장소 검색하기</a>
     * @param query
     * @return
     */
    public KakaoLocalSearchResult search(KakaoLocalSearchQuery query) {
        if (query.getSize() != null && query.getSize() > MAX_SIZE) {
            query.setSize(MAX_SIZE);
        }
         var result = restTemplate.getForObject(server + "/v2/local/search/keyword.json?query={query}&page={page}&size={size}",
                 KakaoLocalSearchResult.class, query.getQuery(), query.getPage(), query.getSize());
        return result;
    }

    /**
     * {@inheritDoc}
     * 최대 개수 10
     */
    @Override
    public List<Local> search(String keyword, Integer count) {
        return search(KakaoLocalSearchQuery.builder().query(keyword).page(1).size(count).build())
                .getDocuments().stream()
                .map(document -> Local.of(document.getPlaceName(), document.getAddressName(), document.getRoadAddressName()))
                .collect(Collectors.toList());
    }
}
