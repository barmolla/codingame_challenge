package com.codingame.challenge.utils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogMeasureTime {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Around("@annotation(com.codingame.challenge.utils.MeasureTime)")
	public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
	    long start = System.currentTimeMillis();

	    Object proceed = joinPoint.proceed();

	    long executionTime = System.currentTimeMillis() - start;

	    logger.info(joinPoint.getSignature() + " executed in " + executionTime + "ms");

	    return proceed;
	}
}