package com.example.eshop.annotation.logging;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @author Asadbek
 */

@Component
@Aspect
@Slf4j
public class LoggingAspect {

    @Pointcut("@annotation(Loggable)")
    public void executeLogging() {}

    @Around("executeLogging()")
    public Object logMethodCall(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object returnValue = joinPoint.proceed();
        long totalTime = System.currentTimeMillis() - startTime;

        StringBuilder message = new StringBuilder("Method: ");
        message.append(joinPoint.getSignature().getName());

        message.append(" totalTime: ").append(totalTime).append(" ms");

        Object[] args = joinPoint.getArgs();

        if (args != null && args.length > 0) {
            message.append(" args=[ ");
            for (int i = 0; i < args.length - 1; i++)
                message.append(args[i]).append(" , ");
            message.append(args[args.length - 1]);
            message.append("]");
        }
        log.info(message.toString());
        return returnValue;
    }
}
