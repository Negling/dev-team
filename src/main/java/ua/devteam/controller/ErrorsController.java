package ua.devteam.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorsController {

    @RequestMapping("/error-page-403")
    public String accessDenied() {
        return "/errors/403";
    }

    @RequestMapping("/error-page-500")
    public String internalServerError() {
        return "/errors/500";
    }

    @RequestMapping("/error-page-404")
    public String notFound() {
        return "/errors/404";
    }
}
