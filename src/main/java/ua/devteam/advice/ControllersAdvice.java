package ua.devteam.advice;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.ui.Model;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.util.UriUtils;
import ua.devteam.exceptions.AjaxMethodInternalException;
import ua.devteam.exceptions.InvalidObjectStateException;
import ua.devteam.exceptions.ResourceNotFoundException;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Locale;

@ControllerAdvice
public class ControllersAdvice {

    private final static Logger logger = LogManager.getLogger("ExceptionsLogger");
    private MessageSource messageSource;
    private Validator compositeValidator;


    public ControllersAdvice(MessageSource messageSource, Validator compositeValidator) {
        this.messageSource = messageSource;
        this.compositeValidator = compositeValidator;
    }

    @ExceptionHandler(Exception.class)
    public String handleInternalError(Exception ex) {
        logException(ex);

        return "forward:/error-page-500";
    }

    @ExceptionHandler(AjaxMethodInternalException.class)
    public ResponseEntity<String> handleAjaxMethodException(AjaxMethodInternalException ex, Locale locale) {
        HttpHeaders headers = new HttpHeaders();
        HttpStatus status;

        headers.add("Content-type", "text/html;charset=UTF-8");

        logException(ex.getCause());

        if (ex.getCause() instanceof InvalidObjectStateException) {
            status = HttpStatus.CONFLICT;
        } else {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<>(messageSource.getMessage(ex.getLocalizedErrorCode(), ex.getErrorParams(), locale), headers, status);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public String handleAccessDeniedException() {
        if (logger.isTraceEnabled()) {
            logger.trace("Access Denied! Forwarding on 403 error page.");
        }

        return "forward:/error-page-403";
    }

    @ExceptionHandler({NoHandlerFoundException.class, MethodArgumentTypeMismatchException.class, ResourceNotFoundException.class})
    public String handleNoHandlerFound(Exception ex, Model model) throws UnsupportedEncodingException {
        String requestedURL;

        if (ex instanceof NoHandlerFoundException) {
            requestedURL = UriUtils.decode(((NoHandlerFoundException) ex).getRequestURL(), "UTF-8");
        } else if (ex instanceof ResourceNotFoundException) {
            requestedURL = ((ResourceNotFoundException) ex).getRequestedURL();
        } else {
            MethodArgumentTypeMismatchException ex1 = (MethodArgumentTypeMismatchException) ex;
            RequestMapping requestMapping = (RequestMapping) Arrays.stream(ex1.getParameter().getMethodAnnotations()).
                    filter(annotation -> (annotation instanceof RequestMapping)).findAny().get();

            requestedURL = requestMapping.value()[0].concat("?").concat(ex1.getName()).concat("=")
                    .concat(ex1.getValue().toString());
        }

        model.addAttribute("requestURL", requestedURL);

        if (logger.isTraceEnabled()) {
            logger.trace("Forwarding on 404 error page, requested URL is: " + requestedURL);
        }

        return "forward:/error-page-404";
    }

    @InitBinder({"check", "customerRegistrationForm", "technicalTask"})
    public void dataBinding(WebDataBinder binder) {
        binder.addValidators(compositeValidator);
    }

    private void logException(Throwable ex) {
        if (ex instanceof NullPointerException) {
            logger.warn(formExceptionMessage(ex));
        } else if (ex instanceof DataAccessException) {
            logger.error(formExceptionMessage(ex));
        } else if (!(ex instanceof InvalidObjectStateException)) {
            logger.fatal(formExceptionMessage(ex));
        }
    }

    private String formExceptionMessage(Throwable ex) {
        StringBuilder builder = new StringBuilder();

        StackTraceElement stacktrace = ex.getStackTrace()[0];

        builder.append("An exception has occurred in : ").append(stacktrace.getClassName());
        builder.append(".").append(stacktrace.getMethodName()).append("()");
        builder.append(", line: ").append(stacktrace.getLineNumber()).append(". \n");
        builder.append("Exception class: ").append(ex.getClass()).append("\n");
        builder.append("Message: ").append(ex.getMessage());


        return builder.toString();
    }
}
