package ua.devteam.controllers;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.NestedServletException;
import ua.devteam.entity.projects.Project;
import ua.devteam.entity.projects.TechnicalTask;
import ua.devteam.entity.tasks.TaskDevelopmentData;
import ua.devteam.entity.users.Developer;
import ua.devteam.entity.users.User;
import ua.devteam.service.DevelopersService;
import ua.devteam.service.ProjectsService;
import ua.devteam.service.TaskDevelopmentDataService;
import ua.devteam.service.TechnicalTasksService;

import java.util.ArrayList;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ua.devteam.controllers.WebTestUtils.*;
import static ua.devteam.entity.enums.Role.CUSTOMER;

@RunWith(JUnit4.class)
public class StaticContentControllerTest {

    // id for tests
    private long testId = 1;

    // service mocks
    private TechnicalTasksService technicalTasksService = mock(TechnicalTasksService.class);
    private ProjectsService projectsService = mock(ProjectsService.class);
    private TaskDevelopmentDataService taskDevelopmentDataService = mock(TaskDevelopmentDataService.class);
    private DevelopersService developersService = mock(DevelopersService.class);

    // principal for tests
    private User customer = getUserWithIdAndRole(testId, CUSTOMER);

    // controller to test
    private StaticContentController controller = new StaticContentController(technicalTasksService, projectsService,
            developersService, taskDevelopmentDataService, getPagesBundle());

    // controller mock object
    private MockMvc mockMvc = getConfiguredWithPlaceholdersStandaloneMockMvcBuilder(controller)
            .setCustomArgumentResolvers(getUserArgumentResolverWith(customer))
            .setViewResolvers(getDefaultViewResolver()).build();

    // mocks setup
    {
        TechnicalTask tTask = new TechnicalTask();
        Project project = new Project();

        tTask.setCustomerId(testId);
        project.setCustomerId(testId);

        // default mocks behavior
        when(technicalTasksService.getById(testId, true)).thenReturn(tTask);
        when(projectsService.getById(testId, true)).thenReturn(project);
        when(developersService.getById(testId)).thenReturn(new Developer());
        when(taskDevelopmentDataService.getComplete(testId)).thenReturn(new ArrayList<>());
        when(taskDevelopmentDataService.getActive(testId)).thenReturn(new TaskDevelopmentData());
    }

    @Test
    public void getTechnicalTaskTest() throws Exception {
        mockMvc.perform(get("/technicalTask").param("id", String.valueOf(testId)))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("technicalTask"))
                .andExpect(view().name("technicalTask"));
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void getNonexistentTechnicalTaskTest() throws Throwable {
        when(technicalTasksService.getById(testId, true)).thenThrow(new EmptyResultDataAccessException(1));

        try {
            mockMvc.perform(get("/technicalTask").param("id", String.valueOf(testId)));
        } catch (NestedServletException nse) {
            throw nse.getCause();
        }
    }

    @Test
    public void getProjectTest() throws Exception {
        mockMvc.perform(get("/project").param("id", String.valueOf(testId)))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("project"))
                .andExpect(view().name("project"));
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void getNonexistentProjectTest() throws Throwable {
        when(projectsService.getById(testId, true)).thenThrow(new EmptyResultDataAccessException(1));

        try {
            mockMvc.perform(get("/project").param("id", String.valueOf(testId)));
        } catch (NestedServletException nse) {
            throw nse.getCause();
        }
    }

    @Test
    public void getDeveloperTest() throws Exception {
        mockMvc.perform(get("/developer").param("id", String.valueOf(testId)))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("developer", "developersBackground", "currentTask"))
                .andExpect(view().name("developer"));
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void getNonexistentDeveloperTest() throws Throwable {
        when(developersService.getById(testId)).thenThrow(new EmptyResultDataAccessException(1));

        try {
            mockMvc.perform(get("/developer").param("id", String.valueOf(testId)));
        } catch (NestedServletException nse) {
            throw nse.getCause();
        }
    }

    @Test
    public void getMainTest() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("main"));
    }

    @Test
    public void getLoginTest() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    public void getLoginRegisteredTest() throws Exception {
        mockMvc.perform(get("/login").param("registered", ""))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("registered"))
                .andExpect(view().name("login"));
    }

    @Test
    public void getLoginErrorTest() throws Exception {
        mockMvc.perform(get("/login").param("error", ""))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("error"))
                .andExpect(view().name("login"));
    }

    @Test
    public void getLoginLogoutTest() throws Exception {
        mockMvc.perform(get("/login").param("logout", ""))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("logout"))
                .andExpect(view().name("login"));
    }
}
