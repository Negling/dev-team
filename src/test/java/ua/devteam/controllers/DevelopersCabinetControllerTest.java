package ua.devteam.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.test.web.servlet.MockMvc;
import ua.devteam.entity.enums.Role;
import ua.devteam.entity.tasks.TaskDevelopmentData;
import ua.devteam.service.TaskDevelopmentDataService;

import java.util.ResourceBundle;

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
    private DevelopersCabinetController controller = new DevelopersCabinetController(taskDevelopmentDataService, getViewNamingsBundle());
    private ResourceBundle names = getViewNamingsBundle();
    private ResourceBundle mappings = getMappingBundle();

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
        mockMvc.perform(get(mappings.getString("developer.page")))
                .andExpect(status().isOk())
                .andExpect(view().name(names.getString("developer.page")));
    }

    @Test
    public void getActiveTaskFragmentTest() throws Exception {
        mockMvc.perform(get(mappings.getString("developer.activeTask")))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists(names.getString("model.activeTaskData")))
                .andExpect(view().name(names.getString("developer.activeTask")));
    }

    @Test
    public void getCompleteTasksFragmentTest() throws Exception {
        mockMvc.perform(get(mappings.getString("developer.tasksHistory")))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists(names.getString("model.completeTasks")))
                .andExpect(view().name(names.getString("developer.tasksHistory")));
    }
}
