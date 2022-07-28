package com.moss.localsearch.shared.util;

/**
 *
 */
public class Assert {

    /**
     * object가 null이 아님을 체크.
     * null인 경우 AssertionException 발생
     * @param obj 체크할 오브젝트
     * @param message 오류 메시지
     */
    public static void notNull(Object obj, String message) {
        if (null == obj) {
            throw new AssertException(message);
        }
    }

    /**
     * 문자열에 문자가 포함 되어있음으로 체크.
     * null, empty(""), whitespace("  ") 인 경우 AssertionException 발생
     * @param str 체크할 문자열
     * @param message  오류 메시지
     */
    public static void hasText(String str, String message) {
        if (null == str || str.trim().length() == 0) {
            throw new AssertException(message);
        }
    }

    /**
     * true 여부 확인
     * true가 아닌 겨우 AssertionException 발생
     * @param bool 확인할 불리언
     * @param message true가 아닌 경우 에러 메시지
     */
    public static void isTrue(Boolean bool, String message) {
        if (null == bool || bool != true) {
            throw new AssertException(message);
        }
    }
}
