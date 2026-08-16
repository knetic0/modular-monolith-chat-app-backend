package com.mehmetsolak.mini_chat_app.common.logging;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(1)
@Slf4j
public class LoggingAspect {

    @Around("execution(public * com.mehmetsolak.mini_chat_app..application..*(..))")
    public Object logAroundServiceMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().toShortString();
        long startTime = System.currentTimeMillis();

        log.info(">>> [PROXY] Cagri baslatiliyor: {}", methodName);

        Object result;
        try {
            result = joinPoint.proceed();
        } catch (Throwable ex) {
            long duration = System.currentTimeMillis() - startTime;
            log.error(">>> [PROXY] Hata ile sonuclandi: {} ({} ms) - {}", methodName, duration, ex.getMessage());
            throw ex;
        }

        long duration = System.currentTimeMillis() - startTime;
        log.info(">>> [PROXY] Cagri tamamlandi: {} ({} ms)", methodName, duration);
        return result;
    }

}
