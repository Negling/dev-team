package ua.devteam.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * This controller returns static error pages.
 */
@Controller
public class ErrorsController {

    /**
     * Returns access denied (403 error code) page.
     */
    @GetMapping("/error-page-403")
    public String accessDenied() {
        return "/errors/403";
    }

    /**
     * Returns internal server error (500 error code) page.
     */
    @GetMapping("/error-page-500")
    public String internalServerError() {
        return "/errors/500";
    }

    /**
     * Returns not found (404 error code) page.
     */
    @GetMapping("/error-page-404")
    public String notFound() {
        return "/errors/404";
    }
}
