package ua.devteam.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ResourceBundle;

/**
 * This controller returns static error pages.
 */
@Controller
@AllArgsConstructor
public class ErrorsController {
    private ResourceBundle pagesProperties;

    /**
     * Returns access denied (403 error code) page.
     */
    @GetMapping("${error.403}")
    public String accessDenied() {
        return pagesProperties.getString("error.403");
    }

    /**
     * Returns not found (404 error code) page.
     */
    @GetMapping("${error.404}")
    public String notFound() {
        return pagesProperties.getString("error.404");
    }

    /**
     * Returns internal server error (500 error code) page.
     */
    @GetMapping("${error.500}")
    public String internalServerError() {
        return pagesProperties.getString("error.500");
    }
}
