package ua.devteam.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ua.devteam.entity.enums.Role;
import ua.devteam.service.TaskDevelopmentDataService;

import java.security.Principal;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ua.devteam.controllers.WebTestUtils.*;

@RunWith(JUnit4.class)
public class DevelopersCabinetRestControllerTest {

    private TaskDevelopmentDataService taskDevelopmentDataService = mock(TaskDevelopmentDataService.class);
    private DevelopersCabinetRestController controller = new DevelopersCabinetRestController(getDefaultMessageSource(),
            taskDevelopmentDataService, ResourceBundle.getBundle("properties/validation"));
    private Principal developer = getUserWithIdAndRole(1L, Role.DEVELOPER);

    // controller mock object
    private MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller)
            .setViewResolvers(getDefaultViewResolver()).build();

    @Test
    public void completeTaskInvalidHoursSpentTest() throws Throwable {
        Map<String, String> requestBody = new HashMap<String, String>() {{
            put("id", "1");
            put("hoursSpent", "999999999");
        }};

        mockMvc.perform(put("/development/completeTask")
                .locale(Locale.ENGLISH)
                .principal(developer)
                .contentType("application/json")
                .content(getObjectAsJson(requestBody)))
                // checks
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void completeTaskSuccessTest() throws Throwable {
        Map<String, String> requestBody = new HashMap<String, String>() {{
            put("id", "1");
            put("hoursSpent", "10");
        }};

        mockMvc.perform(put("/development/completeTask")
                .locale(Locale.ENGLISH)
                .principal(developer)
                .contentType("application/json")
                .content(getObjectAsJson(requestBody)))
                // checks
                .andExpect(status().isOk());

        verify(taskDevelopmentDataService, only()).complete(1L, 1L, 10);
    }
}
