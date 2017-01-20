package ua.devteam.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import ua.devteam.entity.enums.Role;
import ua.devteam.entity.projects.Check;
import ua.devteam.entity.projects.Project;
import ua.devteam.entity.projects.TechnicalTask;
import ua.devteam.entity.tasks.Operation;
import ua.devteam.entity.tasks.ProjectTask;
import ua.devteam.entity.tasks.RequestForDevelopers;
import ua.devteam.entity.tasks.TaskDevelopmentData;
import ua.devteam.entity.users.User;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;

import static ua.devteam.entity.enums.DeveloperRank.Junior;
import static ua.devteam.entity.enums.DeveloperSpecialization.Backend;

class WebTestUtils {
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

    static TechnicalTask getValidTechnicalTask(Long customerId) {
        String length_10 = "1234567890";
        String length_30 = "123456789012345678901234567890";

        return new TechnicalTask(null, length_10, length_30, customerId, null, null, new ArrayList<Operation>() {{
            add(new Operation(null, null, length_10, length_30, new ArrayList<RequestForDevelopers>() {{
                add(new RequestForDevelopers(null, Backend, Junior, 1));
            }}));
        }});
    }

    static Project getValidProject() {
        Project project = new Project();

        // not actually a good solution, but for temporary is ok
        project.setTasks(new ArrayList<ProjectTask>() {{
            add(new ProjectTask(null, null, null, null, null, null, null,
                    new ArrayList<TaskDevelopmentData>() {{
                        add(new TaskDevelopmentData(null, null, Backend, Junior));
                    }},
                    new ArrayList<RequestForDevelopers>() {{
                        add(new RequestForDevelopers(null, Backend, Junior, 1));
                    }}));
        }});

        return project;
    }

    static Check getValidCheck() {
        return new Check((long) 1, null, new BigDecimal("10.00"), new BigDecimal("10.00"), new BigDecimal("4.00"), null);
    }

    static Check getInvalidCheck() {
        return new Check((long) 1, null, new BigDecimal("10.00"), new BigDecimal("10.00"), new BigDecimal("5.00"), null);
    }
}
