package com.vishal.transactions.aop;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;

@Aspect
public class LoggingAspect {

	private final Environment env;

	public LoggingAspect(Environment env) {
		this.env = env;
	}

	@Pointcut("within(@org.springframework.stereotype.Repository *)"
			+ " || within(@org.springframework.stereotype.Service *)"
			+ " || within(@org.springframework.web.bind.annotation.RestController *)")
	public void springBeanPointcut() {

	}

	@Pointcut("within(com.vishal.transactions.repository..*)" + " || within(com.vishal.transactions.service..*)"
			+ " || within(com.vishal.transactions.web.rest..*)")
	public void applicationPackagePointcut() {

	}

	private Logger logger(JoinPoint joinPoint) {
		return LoggerFactory.getLogger(joinPoint.getSignature().getDeclaringTypeName());
	}

	@AfterThrowing(pointcut = "applicationPackagePointcut() && springBeanPointcut()", throwing = "e")
	public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {

		if (env.matchesProfiles("dev")) {
			logger(joinPoint).error("Exception in {}() with cause = \'{}\' and exception = \'{}\'",
					joinPoint.getSignature().getName(), e.getCause() != null ? e.getCause() : "NULL", e.getMessage(),
					e);
		} else {
			logger(joinPoint).error("Exception in {}() with cause = {}", joinPoint.getSignature().getName(),
					e.getCause() != null ? e.getCause() : "NULL");
		}
	}

	@Around("applicationPackagePointcut() && springBeanPointcut()")
	public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
		Logger log = logger(joinPoint);
		if (log.isDebugEnabled()) {
			log.debug("Enter: {}() with argument[s] = {}", joinPoint.getSignature().getName(),
					Arrays.toString(joinPoint.getArgs()));
		}
		try {
			Object result = joinPoint.proceed();
			if (log.isDebugEnabled()) {
				log.debug("Exit: {}() with result = {}", joinPoint.getSignature().getName(), result);
			}
			return result;
		} catch (IllegalArgumentException e) {
			log.error("Illegal argument: {} in {}()", Arrays.toString(joinPoint.getArgs()),
					joinPoint.getSignature().getName());
			throw e;
		}
	}
}