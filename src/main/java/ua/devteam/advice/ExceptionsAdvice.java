package ua.devteam.advice;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import ua.devteam.controllers.exceptions.AjaxMethodInternalException;

@Aspect
public class ExceptionsAdvice {

    @AfterThrowing(pointcut = "@within(org.springframework.web.bind.annotation.RestController)", throwing = "ex")
    public void afterThrowingExceptionInAjaxMethod(Exception ex) {
        throw new AjaxMethodInternalException(ex);
    }
}
