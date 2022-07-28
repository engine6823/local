package com.moss.localsearch.shared.event;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PublishEvent {
    /**
     * 이벤트 타입
     */
    Class<?> type();

    /**
     * 반환 값이 Object인 경우 생성자에 적용할 속성명 배열
     */
    String[] args() default {};
}
