package ua.devteam.advice;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.devteam.controllers.exceptions.AjaxMethodInternalException;

@Aspect
public class ExceptionsAdvice {

    @AfterThrowing(pointcut = "ua.devteam.advice.SystemArchitecture.inWebLayer() && @annotation(responseBody)", throwing = "ex")
    public void afterThrowingExceptionInAjaxMethod(ResponseBody responseBody, Exception ex) {
        throw new AjaxMethodInternalException(ex);
    }
}
