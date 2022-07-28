package com.moss.localsearch.shared.util;

/**
 * Assert 오류시 발생하는 Exception
 */
public class AssertException extends RuntimeException {

    public AssertException(String message, Throwable cause) {
        super(message, cause);
    }

    public AssertException(String message) {
        this(message, null);
    }
}
