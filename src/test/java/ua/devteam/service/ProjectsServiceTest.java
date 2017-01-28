package ua.devteam.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import ua.devteam.dao.ProjectDAO;
import ua.devteam.entity.projects.Project;
import ua.devteam.entity.projects.TechnicalTask;
import ua.devteam.service.impl.ProjectsServiceImpl;

import java.util.ArrayList;

import static org.mockito.Mockito.*;
import static ua.devteam.entity.enums.Status.*;

@RunWith(JUnit4.class)
public class ProjectsServiceTest {

    private final long ID_FOR_TEST = 1;
    private ProjectDAO projectDAO = mock(ProjectDAO.class);
    private ProjectTasksService projectTasksService = mock(ProjectTasksService.class);
    private TaskDevelopmentDataService taskDevelopersService = mock(TaskDevelopmentDataService.class);
    private ProjectsService service = new ProjectsServiceImpl(projectDAO, projectTasksService, taskDevelopersService);

    @Test
    public void createProjectTest() {
        when(projectDAO.create(any())).thenReturn(ID_FOR_TEST);

        service.createProject(new TechnicalTask(), ID_FOR_TEST);

        verify(projectDAO, only()).create(any());
        verify(projectTasksService, only()).registerFromTechnicalTask(any(), eq(ID_FOR_TEST));
    }

    @Test
    public void confirmProjectTest() {
        service.confirmProject(ID_FOR_TEST);

        verify(projectDAO, only()).updateStatus(ID_FOR_TEST, PENDING);
        verify(taskDevelopersService, only()).confirmByProject(ID_FOR_TEST);
        verify(projectTasksService, only()).confirmByProject(ID_FOR_TEST);
    }

    @Test
    public void runProjectTest() {
        service.runProject(ID_FOR_TEST);

        verify(projectDAO, only()).updateStatus(ID_FOR_TEST, RUNNING);
        verify(taskDevelopersService, only()).runByProject(ID_FOR_TEST);
        verify(projectTasksService, only()).runByProject(ID_FOR_TEST);
    }

    @Test
    public void declineProjectTest() {
        Project project = mock(Project.class);
        when(projectDAO.getById(ID_FOR_TEST)).thenReturn(project);

        service.decline(ID_FOR_TEST, "test");

        verify(project, times(1)).setEndDate(any());
        verify(project, times(1)).setManagerCommentary("test");
        verify(projectDAO, times(1)).getById(ID_FOR_TEST);
        verify(projectDAO, times(1)).updateStatus(ID_FOR_TEST, DECLINED);
        verify(taskDevelopersService, times(1)).dropByProject(ID_FOR_TEST);
    }

    @Test
    public void cancelProjectTest() {
        Project project = mock(Project.class);
        when(projectDAO.getById(ID_FOR_TEST)).thenReturn(project);

        service.cancel(ID_FOR_TEST);

        verify(project, times(1)).setEndDate(any());
        verify(projectDAO, times(1)).getById(ID_FOR_TEST);
        verify(projectDAO, times(1)).updateStatus(ID_FOR_TEST, CANCELED);
        verify(taskDevelopersService, times(1)).dropByProject(ID_FOR_TEST);
    }

    @Test
    public void getByIdNoNestedTest() {
        service.getById(ID_FOR_TEST, false);

        verify(projectDAO, only()).getById(ID_FOR_TEST);
        verify(projectTasksService, never()).getAllByProject(any(), eq(true));
    }

    @Test
    public void getByIdTest() {
        when(projectDAO.getById(ID_FOR_TEST)).thenReturn(new Project());

        service.getById(ID_FOR_TEST, true);

        verify(projectDAO, only()).getById(ID_FOR_TEST);
        verify(projectTasksService, atLeastOnce()).getAllByProject(any(), eq(true));
    }

    @Test
    public void getNewByManagerNoNestedTest() {
        service.getNewByManager(ID_FOR_TEST, false);

        verify(projectDAO, only()).getByManagerAndStatus(ID_FOR_TEST, NEW);
        verify(projectTasksService, never()).getAllByProject(any(), eq(true));
    }

    @Test
    public void getNewByManagerTest() {
        when(projectDAO.getByManagerAndStatus(ID_FOR_TEST, NEW)).thenReturn(new ArrayList<Project>() {{
            add(new Project());
        }});

        service.getNewByManager(ID_FOR_TEST, true);

        verify(projectDAO, only()).getByManagerAndStatus(ID_FOR_TEST, NEW);
        verify(projectTasksService, atLeastOnce()).getAllByProject(any(), eq(true));
    }

    @Test
    public void getCompleteByManagerNoNestedTest() {
        service.getCompleteByManager(ID_FOR_TEST, false);

        verify(projectDAO, only()).getCompleteByManager(ID_FOR_TEST);
        verify(projectTasksService, never()).getAllByProject(any(), eq(true));
    }

    @Test
    public void getCompleteByManagerTest() {
        when(projectDAO.getCompleteByManager(ID_FOR_TEST)).thenReturn(new ArrayList<Project>() {{
            add(new Project());
        }});

        service.getCompleteByManager(ID_FOR_TEST, true);

        verify(projectDAO, only()).getCompleteByManager(ID_FOR_TEST);
        verify(projectTasksService, atLeastOnce()).getAllByProject(any(), eq(true));
    }

    @Test
    public void getRunningByCustomerNoNestedTest() {
        service.getRunningByCustomer(ID_FOR_TEST, false);

        verify(projectDAO, only()).getRunningByCustomer(ID_FOR_TEST);
        verify(projectTasksService, never()).getAllByProject(any(), eq(true));
    }

    @Test
    public void getRunningByCustomerTest() {
        when(projectDAO.getRunningByCustomer(ID_FOR_TEST)).thenReturn(new ArrayList<Project>() {{
            add(new Project());
        }});

        service.getRunningByCustomer(ID_FOR_TEST, true);

        verify(projectDAO, only()).getRunningByCustomer(ID_FOR_TEST);
        verify(projectTasksService, atLeastOnce()).getAllByProject(any(), eq(true));
    }

    @Test
    public void getCompleteByCustomerNoNestedTest() {
        service.getCompleteByCustomer(ID_FOR_TEST, false);

        verify(projectDAO, only()).getCompleteByCustomer(ID_FOR_TEST);
        verify(projectTasksService, never()).getAllByProject(any(), eq(true));
    }

    @Test
    public void getCompleteByCustomerTest() {
        when(projectDAO.getCompleteByCustomer(ID_FOR_TEST)).thenReturn(new ArrayList<Project>() {{
            add(new Project());
        }});

        service.getCompleteByCustomer(ID_FOR_TEST, true);

        verify(projectDAO, only()).getCompleteByCustomer(ID_FOR_TEST);
        verify(projectTasksService, atLeastOnce()).getAllByProject(any(), eq(true));
    }
}
