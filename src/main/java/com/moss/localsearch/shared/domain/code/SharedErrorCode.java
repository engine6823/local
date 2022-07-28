package com.moss.localsearch.shared.domain.code;

import com.moss.localsearch.shared.error.ErrorCode;

import javax.servlet.http.HttpServletResponse;

/**
 * 공용 ErrorCode
 */
public enum SharedErrorCode implements ErrorCode<SharedErrorCode> {
    // 00000 ~ 00999 공통 에러 코드
    Unknown(0, HttpServletResponse.SC_INTERNAL_SERVER_ERROR),  // 정의되지 않은 에러

    // 200 ~ 299: Success
    Success(200, HttpServletResponse.SC_OK),  // 성공

    // 400 ~ 499: Client Error
    InvalidParam(400, HttpServletResponse.SC_BAD_REQUEST),  // 입력값 잘못됨
    Unauthenticated(401, HttpServletResponse.SC_UNAUTHORIZED),  // 로그인 필요
    Unauthorized(403, HttpServletResponse.SC_FORBIDDEN),  // 권한 없음
    PageNotFound(404, HttpServletResponse.SC_NOT_FOUND),  // 페이지 없음
    Conflict(409, HttpServletResponse.SC_CONFLICT),  // 상태 충돌

    // 500 ~ : Server Error
    SystemError(500, HttpServletResponse.SC_INTERNAL_SERVER_ERROR),  // 시스템 오류
    SystemRuntimeError(501, HttpServletResponse.SC_INTERNAL_SERVER_ERROR),  // 시스템 런타임 오류
    ;

    SharedErrorCode(int code) {
        this(code, HttpServletResponse.SC_CONFLICT);
    }

    SharedErrorCode(int code, int httpStatus) {
        this.code = code;
        this.httpStatus = httpStatus;
    }

    int code;
    int httpStatus;

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public int httpStatus() {
        return httpStatus;
    }

    @Override
    public SharedErrorCode getEnum() {
        return this;
    }
}