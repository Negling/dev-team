package ua.devteam.advice;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.devteam.exceptions.AjaxMethodInternalException;
import ua.devteam.exceptions.LocalizedException;
import ua.devteam.exceptions.ResourceNotFoundException;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;

/**
 * This aspect provides specific exception dispatching and forming logic.
 */
@Aspect
public class ExceptionsAdvice {

    /**
     * When an exception occurs in RestController method, this advice catches and translates it
     * to appropriate {@link AjaxMethodInternalException} exception, which can be properly processed
     * by {@link org.springframework.web.bind.annotation.ExceptionHandler}.
     *
     * @param ex occurred exception
     */
    @AfterThrowing(pointcut = "ua.devteam.advice.SystemArchitecture.inRestControllers()", throwing = "ex")
    public void afterThrowingExceptionInAjaxMethod(Exception ex) {

        if (ex instanceof LocalizedException) {
            LocalizedException lex = (LocalizedException) ex;

            throw new AjaxMethodInternalException(lex, lex.getLocalizedErrorCode(), lex.getErrorParams());
        } else {
            throw new AjaxMethodInternalException(ex, "errorPage.tryAgainLater", null);
        }
    }

    /**
     * When GET request trying to obtain resource that not exist - {@link EmptyResultDataAccessException} occurs.
     * This advice forms appropriate {@link ResourceNotFoundException}, which can be properly processed by
     * {@link org.springframework.web.bind.annotation.ExceptionHandler}.
     *
     * @param jp method
     * @param ex occurred exception in case if resource is not found by DAO object
     * @throws NoSuchMethodException ex
     */
    @AfterThrowing(pointcut = "ua.devteam.advice.SystemArchitecture.inControllerGETMethods() " +
            "&& !ua.devteam.advice.SystemArchitecture.inRestControllers()", throwing = "ex")
    public void afterThrowingExceptionInAjaxMethod(JoinPoint jp, EmptyResultDataAccessException ex) throws NoSuchMethodException {
        StringBuilder builder = new StringBuilder();
        Method method = ((MethodSignature) jp.getSignature()).getMethod();
        Parameter[] params = method.getParameters();

        // requestMapping value
        String mapping = ((GetMapping) Arrays.stream(method.getDeclaredAnnotations())
                .filter(ann -> ann instanceof GetMapping).findAny().get()).value()[0];


        // add query params
        builder.append(mapping).append("?");
        for (int i = 0; i < params.length; i++) {

            // if param has annotation RequestParam - add it to result URL
            if (params[i].getAnnotation(RequestParam.class) != null) {
                builder.append(((MethodSignature) jp.getSignature()).getParameterNames()[i]).
                        append("=").append(jp.getArgs()[i]).append("&");
            }
        }

        throw new ResourceNotFoundException(builder.toString().substring(0, builder.length() - 1), ex);
    }
}
