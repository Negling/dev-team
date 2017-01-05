package ua.devteam.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.devteam.entity.enums.Role;

@Controller
public class BaseController {

    @RequestMapping("/toCabinet")
    @PreAuthorize("isAuthenticated()")
    public String resolveCabinetView(Authentication auth) {
        Role role = (Role) auth.getAuthorities().stream().findAny().get();

        return "redirect:/".concat(role.getDefaultViewName());
    }



    @RequestMapping("/")
    public String main() {
        return "main";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "logout", required = false) String logout,
                        @RequestParam(value = "registered", required = false) String registered,
                        Model model) {
        if (error != null) {
            model.addAttribute("error", "error");
        }
        if (logout != null) {
            model.addAttribute("logout", "logout");
        }
        if (registered != null) {
            model.addAttribute("registered", "registered");
        }

        return "login";
    }

}
