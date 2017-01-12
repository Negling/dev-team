package ua.devteam.controllers;


import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

abstract class AbstractEntityProcessingController {
    private ResourceBundleMessageSource messageSource;

    public AbstractEntityProcessingController(ResourceBundleMessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public ResourceBundleMessageSource getMessageSource() {
        return messageSource;
    }

    public void setMessageSource(ResourceBundleMessageSource messageSource) {
        this.messageSource = messageSource;
    }

    ResponseEntity<List<String>> generateDefaultResponse(List<String> responseMsg, Errors bindingResult, Locale locale) {
        if (!bindingResult.hasErrors()) {

            responseMsg.add(messageSource.getMessage("general.success", null, locale));
            responseMsg.add(messageSource.getMessage("validationErrors.requestIsOk", null, locale));

            return new ResponseEntity<>(responseMsg, HttpStatus.OK);
        } else {
            responseMsg.add(messageSource.getMessage("general.error", null, locale));
            responseMsg.add(messageSource.getMessage("validationErrors.requestHasErrors", null, locale));
            responseMsg.addAll(bindingResult.getAllErrors().stream()
                    .map(error -> messageSource.getMessage(error.getCode(), error.getArguments(), locale))
                    .collect(Collectors.toList()));

            return new ResponseEntity<>(responseMsg, HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    ResponseEntity<List<String>> getDefaultErrorResponse(List<String> responseMsg, Locale locale) {
        responseMsg.add(messageSource.getMessage("general.error", null, locale));
        responseMsg.add(messageSource.getMessage("validationErrors.requestHasErrors", null, locale));

        return new ResponseEntity<>(responseMsg, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    ResponseEntity<List<String>> getDefaultSuccessResponse(List<String> responseMsg, Locale locale) {
        responseMsg.add(messageSource.getMessage("general.success", null, locale));
        responseMsg.add(messageSource.getMessage("validationErrors.requestIsOk", null, locale));

        return new ResponseEntity<>(responseMsg, HttpStatus.OK);
    }
}
