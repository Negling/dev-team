package ua.devteam.advice;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;


/**
 * An aspect that provides logging logic by methods advices.
 */
@Aspect
public class LoggingAdvice {
    private final Logger globalLogger = LogManager.getLogger("GlobalLogger");

    /**
     * Trace logging in web layer.
     *
     * @param jp method
     */
    @Before("ua.devteam.advice.SystemArchitecture.inWebLayer()")
    public void webTrace(JoinPoint jp) {
        if (globalLogger.isTraceEnabled()) {
            globalLogger.trace(formTraceMsg(jp));
        }
    }

    /**
     * Trace logging in service layer.
     *
     * @param jp method
     */
    @Before("ua.devteam.advice.SystemArchitecture.inServiceLayer()")
    public void servicesTrace(JoinPoint jp) {
        if (globalLogger.isTraceEnabled()) {
            globalLogger.trace(formTraceMsg(jp));
        }
    }

    /**
     * Trace logging in DAO layer.
     *
     * @param jp method
     */
    @Before("ua.devteam.advice.SystemArchitecture.inDataAccessLayer()")
    public void dataAccessTrace(JoinPoint jp) {
        if (globalLogger.isTraceEnabled()) {
            globalLogger.trace(formTraceMsg(jp));
        }
    }

    /**
     * Formats method data to appropriate trace logging msg.
     *
     * @param jp method
     * @return msg string
     */
    private String formTraceMsg(JoinPoint jp) {
        return "Executing method: " + jp.getSignature();
    }
}
