package com.moss.localsearch.shared.error;

/**
 * 에러 코드
 */
public interface ErrorCode<E extends Enum<E>> {

    /**
     * code 값을 반환한다.
     */
    int getCode();

    /**
     * code의 name을 반환한다.
     */
    String name();

    /**
     * 에러가 발생하고, 이를 http response 로 반환할 때 사용할 statusCode를 반환한다.
     */
    int httpStatus();

    /**
     * ErrorCode의 Enum값을 반환한다.
     * 실제로는 동일한 객체이나 ErrorCode를 사용하는 곳에서 cast 편의를 줄이기 위해서 추가선언
     */
    E getEnum();
}