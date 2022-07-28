package com.moss.localsearch.error;

import com.moss.localsearch.shared.error.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 에러가 발생시 반환되는 기본모델<br />
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ErrorResult<T> {
    /**
     * 에러코드
     */
    private ErrorCode<?> error;

    /**
     * 메시지
     */
    private String text;

    /**
     * 데이터
     */
    private T data;
}
