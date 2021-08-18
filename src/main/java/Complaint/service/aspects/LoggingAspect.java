package Complaint.service.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
class LoggingAspect {

    private Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Around("@annotation(Complaint.utilities.Logged)")
    public String logMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        /**
         * Perform action before method invocation
         */

        String output = (String) joinPoint.proceed();

        logger.info("method " + joinPoint.getSignature() + "was called with input : " + joinPoint.getArgs()[0] + " and outputs {} " + output);

        /**
         * perform action after method invocation
         */
        return output;
    }

    @Before("execution(* Complaint.service.PerformCommandService.*(..))")
    public void logMethods(JoinPoint joinPoint) throws Throwable {
        /**
         * Perform action after method invocation
         */
        logger.info(" Before method called " + joinPoint.getSignature());
    }
}
