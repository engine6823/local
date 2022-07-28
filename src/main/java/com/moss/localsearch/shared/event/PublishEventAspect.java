package com.moss.localsearch.shared.event;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.ApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * method 반환 값을 이벤트 생성자 인자로 사용하여 Event를 생성 후,
 * 이벤트를 Publish 하는 이벤트 Publish Aspect
 *
 */
@Slf4j
@Order(value = PublishEventAspect.AopOrder)
@Aspect
public class PublishEventAspect {
    /**
     * 가장 먼저 적용되도록 지정
     */
    public static final int AopOrder = Ordered.HIGHEST_PRECEDENCE;

    final ApplicationContext context;
    final Executor executor = Executors.newCachedThreadPool();

    public PublishEventAspect(ApplicationContext context) {
        this.context = context;
    }

    @Around("@annotation(PublishEvent)")
    public Object publicEvent(ProceedingJoinPoint joinPoint) throws Throwable {
        Object proceed = joinPoint.proceed();

        executor.execute(() -> {
            try {
                MethodSignature signature = (MethodSignature) joinPoint.getSignature();
                Method method = signature.getMethod();

                PublishEvent publishEvent = method.getAnnotation(PublishEvent.class);
                Class<?> proceedClass = proceed.getClass();

                var eventType = publishEvent.type();
                var args = publishEvent.args();
                Object[] eventArgs = new Object[args.length];
                Class<?>[] eventArgTypes = new Class<?>[args.length];

                for (int i = 0; i < args.length; i++) {
                    var field = proceedClass.getDeclaredField(args[i]);
                    var canAccess = field.canAccess(proceed);
                    if (!canAccess) {
                        field.setAccessible(true);
                    }
                    var value = field.get(proceed);
                    if (!canAccess) {
                        field.setAccessible(false);
                    }
                    eventArgs[i] = value;
                    eventArgTypes[i] = value.getClass();
                }

                var constructor = eventType.getDeclaredConstructor(eventArgTypes);
                if (null != constructor) {
                    context.publishEvent(constructor.newInstance(eventArgs));
                } else if (null != eventType.getDeclaredConstructor()) {
                    context.publishEvent(constructor.newInstance());
                } else {
                    log.warn("fail to publish event. eventType doesn't have proper constructor.");
                }
            } catch (Exception e) {
                log.error("fail to publish event. " + e.getMessage(), e);
            }
        });
        return proceed;
    }
}
