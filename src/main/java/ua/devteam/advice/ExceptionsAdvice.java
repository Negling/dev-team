package ua.devteam.advice;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.devteam.exceptions.AjaxMethodInternalException;
import ua.devteam.exceptions.LocalizedException;
import ua.devteam.exceptions.ResourceNotFoundException;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;

@Aspect
public class ExceptionsAdvice {

    @AfterThrowing(pointcut = "ua.devteam.advice.SystemArchitecture.inRestControllers()", throwing = "ex")
    public void afterThrowingExceptionInAjaxMethod(Exception ex) {

        if (ex instanceof LocalizedException) {
            LocalizedException lEx = (LocalizedException) ex;

            throw new AjaxMethodInternalException(lEx, lEx.getLocalizedErrorCode(), lEx.getErrorParams());
        } else {
            throw new AjaxMethodInternalException(ex, "errorPage.tryAgainLater", null);
        }
    }

    @AfterThrowing(pointcut = "ua.devteam.advice.SystemArchitecture.inControllerMethods() " +
            "&& !ua.devteam.advice.SystemArchitecture.inRestControllers()", throwing = "ex")
    public void afterThrowingExceptionInAjaxMethod(JoinPoint jp, EmptyResultDataAccessException ex) throws NoSuchMethodException {
        StringBuilder builder = new StringBuilder();
        Method method = ((MethodSignature) jp.getSignature()).getMethod();
        Parameter[] params = method.getParameters();

        // requestMapping value
        String mapping = ((RequestMapping) Arrays.stream(method.getDeclaredAnnotations())
                .filter(ann -> ann instanceof RequestMapping).findAny().get()).value()[0];


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
