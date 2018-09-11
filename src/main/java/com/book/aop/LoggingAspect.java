package com.book.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class LoggingAspect {
	@Before("execution(* com.book.dto.*.get* ())")
	public void before() {
		log.info("Before");
	}
}
