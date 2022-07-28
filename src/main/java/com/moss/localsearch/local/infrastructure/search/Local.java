package com.moss.localsearch.local.infrastructure.search;

import lombok.Value;

/**
 * 장소
 */
@Value(staticConstructor = "of")
public class Local {
    // 장소명
    private String title;

    // 지번 주소
    private String address;

    // 도로명 주소
    private String roadAddress;
}
