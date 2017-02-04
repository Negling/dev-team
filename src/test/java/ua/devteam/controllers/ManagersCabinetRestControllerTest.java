package ua.devteam.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.Validator;
import ua.devteam.entity.enums.Role;
import ua.devteam.entity.projects.Project;
import ua.devteam.entity.tasks.TaskDevelopmentData;
import ua.devteam.service.*;
import ua.devteam.validation.entityValidators.CheckValidator;
import ua.devteam.validation.entityValidators.ProjectTaskValidator;
import ua.devteam.validation.entityValidators.ProjectValidator;

import java.util.ArrayList;
import java.util.Locale;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ua.devteam.EntityUtils.*;
import static ua.devteam.controllers.WebTestUtils.*;
import static ua.devteam.entity.enums.DeveloperRank.JUNIOR;
import static ua.devteam.entity.enums.DeveloperSpecialization.BACKEND;

@RunWith(JUnit4.class)
public class ManagersCabinetRestControllerTest {

    // service mocks
    private TechnicalTasksService technicalTasksService = mock(TechnicalTasksService.class);
    private ProjectsService projectsService = mock(ProjectsService.class);
    private ChecksService checksService = mock(ChecksService.class);
    private DevelopersService developersService = mock(DevelopersService.class);
    private TaskDevelopmentDataService taskDevelopmentDataService = mock(TaskDevelopmentDataService.class);

    // necessary data
    private Validator projectValidator = new ProjectValidator(new ProjectTaskValidator());

    // controller to test
    private ManagersCabinetRestController controller = new ManagersCabinetRestController(getDefaultMessageSource(),
            technicalTasksService, projectsService, checksService, taskDevelopmentDataService, developersService, projectValidator);

    private MockMvc mockMvc;

    // setup
    {
        mockMvc = getConfiguredWithPlaceholdersStandaloneMockMvcBuilder(controller)
                .setCustomArgumentResolvers(getUserArgumentResolverWith(getUserWithIdAndRole(1L, Role.ADMIN)))
                .setViewResolvers(getDefaultViewResolver())
                .setValidator(new CheckValidator()).build();

        // default mocks behavior
        when(developersService.getAvailableDevelopers(any(), any(), any())).thenReturn(new ArrayList<>());
        when(taskDevelopmentDataService.bindDeveloper(anyLong(), anyLong())).thenReturn(new TaskDevelopmentData());
    }

    @Test
    public void getDevelopersTest() throws Exception {
        mockMvc.perform(get("/manage/developers")
                .param("specialization", BACKEND.toString())
                .param("rank", JUNIOR.toString())
                .param("lastName", "Kiral"))
                // checks
                .andExpect(status().isOk());

        verify(developersService, only()).getAvailableDevelopers(BACKEND, JUNIOR, "Kiral");
    }

    @Test
    public void bindDeveloperTest() throws Exception {
        mockMvc.perform(post("/manage/taskDevelopmentData/{developerId}", 1L)
                .contentType("application/json")
                .content(getObjectAsJson(1L)))
                // checks
                .andExpect(status().isOk());

        verify(taskDevelopmentDataService, only()).bindDeveloper(1L, 1L);
    }

    @Test
    public void unbindDeveloperTest() throws Exception {
        mockMvc.perform(delete("/manage/taskDevelopmentData/{developerId}", 1L))
                // checks
                .andExpect(status().isOk());

        verify(taskDevelopmentDataService, only()).unbindDeveloper(1L);
    }

    @Test
    public void declineTechnicalTaskTest() throws Exception {
        mockMvc.perform(patch("/manage/technicalTask/{technicalTaskId}", 1L)
                .contentType("text/plain")
                .content("test"))
                // checks
                .andExpect(status().isOk());

        verify(technicalTasksService, only()).decline(1L, "test");
    }

    @Test
    public void formTechnicalTaskAsProjectTest() throws Exception {
        mockMvc.perform(post("/manage/project/{technicalTaskId}", 1L)
                .contentType("application/json"))
                // checks
                .andExpect(status().isOk());

        verify(technicalTasksService, only()).accept(1L, 1L);
    }

    @Test
    public void declineProjectTest() throws Exception {
        mockMvc.perform(patch("/manage/project/{projectId}", 1L)
                .contentType("text/plain")
                .content("test"))
                // checks
                .andExpect(status().isOk());

        verify(projectsService, only()).decline(1L, "test");
    }

    @Test
    public void formProjectWithInvalidCheckTest() throws Exception {
        mockMvc.perform(post("/manage/check")
                .locale(Locale.ENGLISH)
                .contentType("application/json")
                .content(getObjectAsJson(getInvalidCheck())))
                // checks
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void formProjectWithInvalidProjectStateTest() throws Exception {
        when(projectsService.getById(anyLong(), eq(true))).thenReturn(new Project());

        mockMvc.perform(post("/manage/check")
                .locale(Locale.ENGLISH)
                .contentType("application/json")
                .content(getObjectAsJson(getValidCheck())))
                // checks
                .andExpect(status().isUnprocessableEntity());

        verify(projectsService, times(1)).getById(anyLong(), eq(true));
    }

    @Test
    public void formProjectSuccessTest() throws Exception {
        when(projectsService.getById(anyLong(), eq(true))).thenReturn(getValidProject());

        mockMvc.perform(post("/manage/check")
                .locale(Locale.ENGLISH)
                .contentType("application/json")
                .content(getObjectAsJson(getValidCheck())))
                // checks
                .andExpect(status().isOk());

        verify(projectsService, times(1)).getById(anyLong(), eq(true));
        verify(checksService, times(1)).registerCheck(getValidCheck());
    }
}
