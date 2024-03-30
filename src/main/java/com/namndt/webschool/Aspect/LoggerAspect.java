package com.namndt.webschool.Aspect;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
public class LoggerAspect {

    @Before("execution(* com.namndt.webschool..*.*(..))")
    public void beforeExecute(JoinPoint joinPoint) {
        log.info(joinPoint.getSignature().toString() + "method execution start");
    }

    @AfterReturning("execution(* com.namndt.webschool..*.*(..))")
    public void afterExecute(JoinPoint joinPoint){
        log.info(joinPoint.getSignature().toString() + "method execution end");
    }

    @AfterThrowing(value = "execution(* com.namndt.webschool..*.*(..))", throwing = "ex")
    public void haveError(JoinPoint joinPoint, Exception ex){
        log.error(joinPoint.getSignature() + "An exception happened due to: " + ex.getMessage());
    }
}
