package ua.devteam.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ua.devteam.entity.enums.DeveloperRank;
import ua.devteam.entity.enums.DeveloperSpecialization;
import ua.devteam.entity.enums.Status;
import ua.devteam.entity.projects.TechnicalTask;
import ua.devteam.entity.users.User;
import ua.devteam.service.TechnicalTasksService;

import javax.validation.Valid;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/cabinet")
public class CustomersCabinetController extends AbstractEntityProcessingController {

    private TechnicalTasksService technicalTasksService;
    private Validator technicalTaskValidator;

    @Autowired
    public CustomersCabinetController(ResourceBundleMessageSource messageSource, TechnicalTasksService technicalTasksService,
                                      Validator technicalTaskValidator) {
        super(messageSource);
        this.technicalTasksService = technicalTasksService;
        this.technicalTaskValidator = technicalTaskValidator;
    }


    @RequestMapping
    @PreAuthorize("hasAuthority('Customer')")
    public String parlor() {
        return "customers-cabinet";
    }

    @ResponseBody
    @PreAuthorize("hasAuthority('Customer')")
    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public ResponseEntity<List<String>> createProject(@RequestBody @Valid TechnicalTask technicalTask,
                                                      BindingResult bindingResult, Locale locale) {
        if (!bindingResult.hasErrors()) {
            technicalTasksService.registerTechnicalTask(technicalTask);
        }

        return generateDefaultResponse(new LinkedList<>(), bindingResult, locale);
    }

    @ModelAttribute
    @PreAuthorize("hasAuthority('Customer')")
    public void addAttributes(Model model) {
        model.addAttribute("specializations", DeveloperSpecialization.values());
        model.addAttribute("ranks", DeveloperRank.values());
    }

    @InitBinder("technicalTask")
    public void dataBinding(WebDataBinder binder) {
        binder.addValidators(technicalTaskValidator);
    }
}
