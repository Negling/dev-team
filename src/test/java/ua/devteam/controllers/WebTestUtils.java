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

abstract class WebTestUtils {
    private final static ObjectMapper jsonMapper = new ObjectMapper();

    static ViewResolver getDefaultViewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");

        return viewResolver;
    }

    static MessageSource getDefaultMessageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("l10n/localization");
        messageSource.setDefaultEncoding("UTF-8");

        System.out.println();

        return messageSource;
    }

    static Principal getUserWithIdAndRole(Long id, Role role) {
        User user = new User();
        user.setId(id);
        user.setRole(role);

        return new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
    }

    static String getObjectAsJson(Object object) throws JsonProcessingException {
        return jsonMapper.writeValueAsString(object);
    }
}
