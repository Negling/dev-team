package ua.devteam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.devteam.entity.projects.TechnicalTask;

import javax.validation.Valid;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/parlor")
public class CustomersParlorController {

    private ResourceBundleMessageSource messageSource;

    @Autowired
    public void setMessageSource(ResourceBundleMessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @RequestMapping
    public String parlor() {
        return "customers-parlor";
    }

    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<List<String>> createProject(@Valid @RequestBody TechnicalTask technicalTask,
                                                      BindingResult bindingResult, Locale locale) {
        List<String> responseMsg = new LinkedList<>();

        if (bindingResult.hasErrors()) {
            /*responseMsg.add();*/
            responseMsg.addAll(bindingResult.getFieldErrors().stream()
                    .map(error -> messageSource.getMessage(error.getCode(), error.getArguments(), locale))
                    .collect(Collectors.toList()));

            return new ResponseEntity<>(responseMsg, HttpStatus.BAD_REQUEST);
        } else {
            responseMsg.add(messageSource.getMessage("general.success", null, locale));
            responseMsg.add(messageSource.getMessage("customersParlor.projectSubmitted",
                    new Object[]{technicalTask.getName()}, locale));

            return new ResponseEntity<>(responseMsg, HttpStatus.OK);
        }
    }
}
