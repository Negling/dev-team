package ua.devteam.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import ua.devteam.entity.enums.Role;
import ua.devteam.entity.users.User;

import java.util.ResourceBundle;

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


    static ResourceBundle getPagesBundle() {
        return ResourceBundle.getBundle("properties/view_naming");
    }

    static ResourceBundle getMappingBundle() {
        return ResourceBundle.getBundle("properties/mapping");
    }

    static StandaloneMockMvcBuilder getConfiguredWithPlaceholdersStandaloneMockMvcBuilder(Object... controllers) {
        StandaloneMockMvcBuilder builder = MockMvcBuilders.standaloneSetup(controllers);
        ResourceBundle mappings = getMappingBundle();

        mappings.keySet().forEach(key -> builder.addPlaceholderValue(key, mappings.getString(key)));

        return builder;
    }

    /**
     * Returns {@link User} instance, with requested params to configure security environment of incoming request.
     *
     * @param id   user id param
     * @param role user role param
     * @return {@link User} implementation
     */
    static User getUserWithIdAndRole(Long id, Role role) {
        User user = new User();
        user.setId(id);
        user.setRole(role);

        return user;
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

    static HandlerMethodArgumentResolver getUserArgumentResolverWith(User user) {
        return new UserArgumentResolver(user);
    }

    @AllArgsConstructor
    private static class UserArgumentResolver implements HandlerMethodArgumentResolver {
        private User user;

        @Override
        public boolean supportsParameter(MethodParameter parameter) {
            return parameter.hasParameterAnnotation(AuthenticationPrincipal.class);
        }

        @Override
        public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                      NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
            return user;
        }
    }


}
