package com.estsoft.mysite.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Component			// Bean 생성
@Aspect				// Proxy Bean 생성 
public class MeasureDaoExecutionTimeAspect {
	
	@Around("execution(* *..dao.*.*(..)) || execution(* *..service.*.*(..)) || execution(* *..controller.*.*(..))")
	public Object around(ProceedingJoinPoint pjp) throws Throwable{
		
		String taskName = pjp.getTarget().getClass()+":."+pjp.getSignature().getName();
		
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		
		Object obj = pjp.proceed();
		
		stopWatch.stop();
		System.out.println("[Execution Time]["+taskName+"]:"+stopWatch.getTotalTimeMillis()+"millis");
		
		return obj;
	}
	
	
}
