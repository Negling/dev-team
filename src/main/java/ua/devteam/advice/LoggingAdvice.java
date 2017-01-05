package ua.devteam.advice;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class LoggingAdvice {

    private final Logger globalLogger = LogManager.getLogger("GlobalLogger");


    @Before("ua.devteam.advice.SystemArchitecture.inWebLayer()")
    public void webTrace(JoinPoint jp) {
        if (globalLogger.isTraceEnabled()) {
            globalLogger.trace(formTraceMsg(jp));
        }
    }

    @Before("ua.devteam.advice.SystemArchitecture.inServiceLayer()")
    public void servicesTrace(JoinPoint jp) {
        if (globalLogger.isTraceEnabled()) {
            globalLogger.trace(formTraceMsg(jp));
        }
    }

    @Before("ua.devteam.advice.SystemArchitecture.inDataAccessLayer()")
    public void dataAccessTrace(JoinPoint jp) {
        if (globalLogger.isTraceEnabled()) {
            globalLogger.trace(formTraceMsg(jp));
        }
    }

    private String formTraceMsg(JoinPoint jp) {
        return "Executing method: " + jp.getSignature();
    }
}
