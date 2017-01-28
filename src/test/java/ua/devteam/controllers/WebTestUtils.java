package ua.devteam.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import ua.devteam.entity.enums.Role;
import ua.devteam.entity.users.User;

import java.security.Principal;

/**
 * Utils class to help with controllers integration testing.
 */
abstract class WebTestUtils {
    private final static ObjectMapper jsonMapper = new ObjectMapper();

    /**
     * Returns configured instance of {@link InternalResourceViewResolver} to resolve project jsp view structure.
     *
     * @return {@link ViewResolver} instance, configured to resolve project views structure
     */
    static ViewResolver getDefaultViewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");

        return viewResolver;
    }

    /**
     * Returns configured instance of {@link ResourceBundleMessageSource} to resolve project l10n messages structure.
     *
     * @return {@link MessageSource} instance
     */
    static MessageSource getDefaultMessageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("l10n/localization");
        messageSource.setDefaultEncoding("UTF-8");

        return messageSource;
    }

    /**
     * Returns {@link UsernamePasswordAuthenticationToken} instance, with requested params
     * to configure security environment of incoming request.
     *
     * @param id   principal id param
     * @param role principal role param
     * @return {@link Principal} implementation
     */
    static Principal getUserWithIdAndRole(Long id, Role role) {
        User user = new User();
        user.setId(id);
        user.setRole(role);

        return new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
    }

    /**
     * Converts object to JSON format string.
     *
     * @param object object to be converted
     * @return JSON string
     * @throws JsonProcessingException exception
     */
    static String getObjectAsJson(Object object) throws JsonProcessingException {
        return jsonMapper.writeValueAsString(object);
    }
}
