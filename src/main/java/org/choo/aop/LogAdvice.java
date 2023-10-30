package org.choo.aop;

import lombok.extern.log4j.Log4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Log4j
@Component
public class LogAdvice {
    @Before("execution(* org.choo.service.SampleService*.*(..))")
    public void logBefore() {
        log.info("------------------------");
    }

    @AfterThrowing(pointcut = "execution(* org.choo.service.SampleService*.*(..))", throwing = "exception")
    public void logException(Exception exception) {
        log.info("Exection.....!!!!");
        log.info("execution: " + exception);
    }

    @Around("execution(* org.choo.service.SampleService*.*(..))")
    public Object logTime(ProceedingJoinPoint pjp) {
        long start = System.currentTimeMillis();
        log.info("Target: " + pjp.getTarget());
        log.info("Param: " + Arrays.toString(pjp.getArgs()));

        Object result = null;
        try {
            result = pjp.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
        }

        long end = System.currentTimeMillis();
        log.info("Time: " + (end - start));

        return result;
    }
}
