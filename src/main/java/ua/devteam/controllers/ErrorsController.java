package ua.devteam.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorsController {

    @GetMapping("/error-page-403")
    public String accessDenied() {
        return "/errors/403";
    }

    @GetMapping("/error-page-500")
    public String internalServerError() {
        return "/errors/500";
    }

    @GetMapping("/error-page-404")
    public String notFound() {
        return "/errors/404";
    }
}
