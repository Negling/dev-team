package ua.devteam.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.test.web.servlet.MockMvc;
import ua.devteam.entity.enums.Role;
import ua.devteam.entity.tasks.TaskDevelopmentData;
import ua.devteam.service.TaskDevelopmentDataService;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ua.devteam.controllers.WebTestUtils.*;

@RunWith(JUnit4.class)
public class DevelopersCabinetControllerTest {

    // service mocks
    private TaskDevelopmentDataService taskDevelopmentDataService = mock(TaskDevelopmentDataService.class);
    // controller to test
    private DevelopersCabinetController controller = new DevelopersCabinetController(taskDevelopmentDataService, getPagesBundle());

    // controller mock object
    private MockMvc mockMvc;

    // setup
    {
        long testId = 1;

        // default mocks behavior
        when(taskDevelopmentDataService.getActive(testId)).thenReturn(new TaskDevelopmentData());

        mockMvc = getConfiguredWithPlaceholdersStandaloneMockMvcBuilder(controller)
                .setCustomArgumentResolvers(getUserArgumentResolverWith(getUserWithIdAndRole(testId, Role.DEVELOPER)))
                .setViewResolvers(getDefaultViewResolver()).build();
    }

    @Test
    public void getCabinetTest() throws Exception {
        mockMvc.perform(get("/development"))
                .andExpect(status().isOk())
                .andExpect(view().name("development"));
    }

    @Test
    public void getActiveTaskFragmentTest() throws Exception {
        mockMvc.perform(get("/development/fragments/development_active_task"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("activeTaskData"))
                .andExpect(view().name("/fragments/development/development_active_task"));
    }

    @Test
    public void getCompleteTasksFragmentTest() throws Exception {
        mockMvc.perform(get("/development/fragments/development_tasks_history"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("completeTasks"))
                .andExpect(view().name("/fragments/development/development_tasks_history"));
    }
}
