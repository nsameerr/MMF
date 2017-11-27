package com.gtl.mmf;

import static com.gtl.mmf.service.util.IConstants.ONE;
import static com.gtl.mmf.service.util.IConstants.ZERO;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.aspectj.lang.JoinPoint;

public class LogingAspect {
	private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf. LogingAspect");	
	private long start;
	private long elapsedTime;
	public void logBefore(JoinPoint joinPoint){
		start = System.currentTimeMillis();
		StringBuffer logMessage = new StringBuffer();
		logMessage.append("Before invoking method -- ").
		append(joinPoint.getTarget().getClass().getName()).append(" -> ")
		.append(joinPoint.getSignature().getName()).append(" -> ");
		Object []  args = joinPoint.getArgs();
		for (int i = ZERO; i < args.length; i++) {
			logMessage.append(args[i]).append(",");
		}
		if(args.length > ZERO){
			logMessage.deleteCharAt(logMessage.length() - ONE);
		}
		//LOGGER.info(logMessage.toString());
		LOGGER.log(Level.INFO, logMessage.toString());
	}
	
	public void logAfter(JoinPoint joinPoint){
		elapsedTime = System.currentTimeMillis() - start;
		StringBuffer logMessage = new StringBuffer();
		logMessage.append("After invocation of method -- ").
		append(joinPoint.getTarget().getClass().getName()).append(" -> ")
		.append(joinPoint.getSignature().getName()).append(" -> ")
		.append(" execution time -> ").append(elapsedTime).append(" milliseconds");
		//LOGGER.info(logMessage.toString());
		LOGGER.log(Level.INFO, logMessage.toString());
	}
}
