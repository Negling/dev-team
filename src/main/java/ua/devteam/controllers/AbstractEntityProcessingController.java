package ua.devteam.controllers;


import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * Abstract class that provides to its subclasses possibility to generate {@link ResponseEntity}
 * with messages List and status based on bindingsResult content.
 */
abstract class AbstractEntityProcessingController {
    private MessageSource messageSource;

    public AbstractEntityProcessingController(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * Generates ResponseEntity filled with list of localized messages and status based on bindingResult content.
     */
    ResponseEntity<List<String>> generateResponse(List<String> responseMsg, Errors bindingResult, Locale locale) {
        if (!bindingResult.hasErrors()) {
            return getDefaultSuccessResponse(responseMsg, locale);
        } else {
            responseMsg.addAll(bindingResult.getAllErrors().stream()
                    .map(error -> messageSource.getMessage(error.getCode(), error.getArguments(), locale))
                    .collect(Collectors.toList()));

            return getDefaultErrorResponse(responseMsg, locale);
        }
    }

    /**
     * Returns ResponseEntity filled with default msg that signals about invalid request data.
     */
    ResponseEntity<List<String>> getDefaultErrorResponse(List<String> responseMsg, Locale locale) {
        responseMsg.add(0, messageSource.getMessage("general.error", null, locale));
        responseMsg.add(1, messageSource.getMessage("validationErrors.requestHasErrors", null, locale));

        return new ResponseEntity<>(responseMsg, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    /**
     * Returns ResponseEntity filled with default msg that signals about successful entity processing.
     */
    ResponseEntity<List<String>> getDefaultSuccessResponse(List<String> responseMsg, Locale locale) {
        responseMsg.add(0, messageSource.getMessage("general.success", null, locale));
        responseMsg.add(1, messageSource.getMessage("validationErrors.requestIsOk", null, locale));

        return new ResponseEntity<>(responseMsg, HttpStatus.OK);
    }
}
