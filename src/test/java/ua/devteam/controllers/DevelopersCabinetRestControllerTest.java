package ua.devteam.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.test.web.servlet.MockMvc;
import ua.devteam.entity.enums.Role;
import ua.devteam.entity.users.User;
import ua.devteam.service.TaskDevelopmentDataService;

import java.util.Locale;
import java.util.ResourceBundle;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ua.devteam.controllers.WebTestUtils.*;

@RunWith(JUnit4.class)
public class DevelopersCabinetRestControllerTest {

    private TaskDevelopmentDataService taskDevelopmentDataService = mock(TaskDevelopmentDataService.class);
    private DevelopersCabinetRestController controller = new DevelopersCabinetRestController(getDefaultMessageSource(),
            taskDevelopmentDataService, ResourceBundle.getBundle("properties/validation"));
    private User developer = getUserWithIdAndRole(1L, Role.DEVELOPER);

    // controller mock object
    private MockMvc mockMvc = getConfiguredWithPlaceholdersStandaloneMockMvcBuilder(controller)
            .setCustomArgumentResolvers(getUserArgumentResolverWith(developer))
            .setViewResolvers(getDefaultViewResolver()).build();

    @Test
    public void completeTaskInvalidHoursSpentTest() throws Throwable {
        mockMvc.perform(patch("/development/task/{taskId}", 1L)
                .locale(Locale.ENGLISH)
                .contentType("application/json")
                .content(getObjectAsJson(999999999)))
                // checks
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void completeTaskSuccessTest() throws Throwable {
        mockMvc.perform(patch("/development/task/{taskId}", 1L)
                .locale(Locale.ENGLISH)
                .contentType("application/json")
                .content(getObjectAsJson(10)))
                // checks
                .andExpect(status().isOk());

        verify(taskDevelopmentDataService, only()).complete(1L, 1L, 10);
    }
}
