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
@RequestMapping("/parlor")
public class CustomersParlorController {

    private ResourceBundleMessageSource messageSource;
    private TechnicalTasksService technicalTasksService;
    private Validator technicalTaskValidator;

    @Autowired
    public void setMessageSource(ResourceBundleMessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Autowired
    public void setTechnicalTasksService(TechnicalTasksService technicalTasksService) {
        this.technicalTasksService = technicalTasksService;
    }

    @Autowired
    public void setTechnicalTaskValidator(Validator technicalTaskValidator) {
        this.technicalTaskValidator = technicalTaskValidator;
    }


    @RequestMapping
    @PreAuthorize("hasAuthority('Customer')")
    public String parlor() {
        return "customers-parlor";
    }

    @ResponseBody
    @PreAuthorize("hasAuthority('Customer')")
    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public ResponseEntity<List<String>> createProject(@Valid @RequestBody TechnicalTask technicalTask, Authentication auth,
                                                      BindingResult bindingResult, Locale locale) {
        List<String> responseMsg = new LinkedList<>();

        if (bindingResult.hasErrors()) {
            /*responseMsg.add();*/
            responseMsg.addAll(bindingResult.getFieldErrors().stream()
                    .map(error -> messageSource.getMessage(error.getCode(), error.getArguments(), locale))
                    .collect(Collectors.toList()));

            return new ResponseEntity<>(responseMsg, HttpStatus.BAD_REQUEST);
        } else {

            technicalTask.setCustomerId(((User) auth.getPrincipal()).getId());
            technicalTask.setStatus(Status.New);
            technicalTasksService.registerTechnicalTask(technicalTask);

            responseMsg.add(messageSource.getMessage("general.success", null, locale));
            responseMsg.add(messageSource.getMessage("customersParlor.projectSubmitted",
                    new Object[]{technicalTask.getName()}, locale));

            return new ResponseEntity<>(responseMsg, HttpStatus.OK);
        }
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
