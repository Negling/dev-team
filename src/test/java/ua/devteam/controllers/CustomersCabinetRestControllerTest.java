package ua.devteam.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.Validator;
import ua.devteam.entity.enums.Role;
import ua.devteam.entity.projects.TechnicalTask;
import ua.devteam.service.ChecksService;
import ua.devteam.service.TechnicalTasksService;
import ua.devteam.validation.entityValidators.OperationValidator;
import ua.devteam.validation.entityValidators.RequestForDevelopersValidator;
import ua.devteam.validation.entityValidators.TechnicalTaskValidator;

import java.security.Principal;
import java.util.Locale;
import java.util.ResourceBundle;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ua.devteam.EntityUtils.getValidTechnicalTask;
import static ua.devteam.controllers.WebTestUtils.*;

@RunWith(JUnit4.class)
public class CustomersCabinetRestControllerTest {
    private ChecksService checksService = mock(ChecksService.class);
    private TechnicalTasksService technicalTasksService = mock(TechnicalTasksService.class);
    private CustomersCabinetRestController controller =
            new CustomersCabinetRestController(getDefaultMessageSource(), checksService, technicalTasksService);
    private Principal customer = getUserWithIdAndRole(1L, Role.CUSTOMER);
    private MockMvc mockMvc;

    // setup
    {
        ResourceBundle validationProperties = ResourceBundle.getBundle("properties/validation");
        Validator validator = new TechnicalTaskValidator(
                new OperationValidator(
                        new RequestForDevelopersValidator(validationProperties),
                        validationProperties),
                validationProperties);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setViewResolvers(getDefaultViewResolver()).setValidator(validator).build();
    }

    @Test
    public void confirmCheckTest() throws Exception {
        mockMvc.perform(put("/cabinet/confirmCheck").contentType("application/json").content(getObjectAsJson(1)))
                .andExpect(status().isOk());

        verify(checksService, only()).accept(1L);
    }

    @Test
    public void declineCheckTest() throws Exception {
        mockMvc.perform(put("/cabinet/declineCheck").contentType("application/json").content(getObjectAsJson(1)))
                .andExpect(status().isOk());

        verify(checksService, only()).decline(1L);
    }

    @Test
    public void registerInvalidTechnicalTaskTest() throws Exception {
        TechnicalTask requestBody = new TechnicalTask();

        mockMvc.perform(post("/cabinet/submit")
                .locale(Locale.ENGLISH)
                .principal(customer)
                .contentType("application/json")
                .content(getObjectAsJson(requestBody)))
                // checks
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void registerTechnicalTaskSuccessTest() throws Exception {
        TechnicalTask requestBody = getValidTechnicalTask(1L);

        mockMvc.perform(post("/cabinet/submit")
                .locale(Locale.ENGLISH)
                .principal(customer)
                .contentType("application/json")
                .content(getObjectAsJson(requestBody)))
                // checks
                .andExpect(status().isOk());

        verify(technicalTasksService, only()).registerTechnicalTask(requestBody);
    }
}
