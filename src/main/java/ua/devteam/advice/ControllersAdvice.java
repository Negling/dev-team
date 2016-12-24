package ua.devteam.advice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ui.Model;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.util.UriUtils;

import java.io.UnsupportedEncodingException;
import java.util.List;

@ControllerAdvice
public class ControllersAdvice {

    @ExceptionHandler(Exception.class)
    public String handleInternalError(Exception ex) {
        System.out.println(ex);
        return "forward:/error-page-500";
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public String handleNoHandlerFound(NoHandlerFoundException ex, Model model)
            throws UnsupportedEncodingException {
        model.addAttribute("requestURL", UriUtils.decode(ex.getRequestURL(), "UTF-8"));

        return "forward:/error-page-404";
    }
}
