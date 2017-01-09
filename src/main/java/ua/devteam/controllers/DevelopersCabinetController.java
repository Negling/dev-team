package ua.devteam.controllers;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/development")
public class DevelopersCabinetController {

    @RequestMapping
    @PreAuthorize("hasAuthority('Developer')")
    public String cabinet() {
        return "development";
    }
}
