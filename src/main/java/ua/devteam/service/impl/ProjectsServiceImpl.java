package ua.devteam.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ua.devteam.dao.ProjectDAO;
import ua.devteam.entity.projects.Project;
import ua.devteam.entity.projects.TechnicalTask;
import ua.devteam.service.ProjectTasksService;
import ua.devteam.service.ProjectsService;
import ua.devteam.service.TaskDevelopmentDataService;

import java.util.Date;
import java.util.List;

import static ua.devteam.entity.enums.Status.*;

@Service("projectsService")
@Transactional(isolation = Isolation.READ_COMMITTED)
public class ProjectsServiceImpl implements ProjectsService {

    private ProjectDAO projectDAO;
    private ProjectTasksService projectTasksService;
    private TaskDevelopmentDataService taskDevelopersService;

    @Autowired
    public ProjectsServiceImpl(ProjectDAO projectDAO, ProjectTasksService projectTasksService,
                               TaskDevelopmentDataService taskDevelopersService) {
        this.projectDAO = projectDAO;
        this.projectTasksService = projectTasksService;
        this.taskDevelopersService = taskDevelopersService;
    }

    @Override
    public long createProject(TechnicalTask technicalTask, Long managerId) {
        long projectId = projectDAO.create(new Project(managerId, technicalTask));

        projectTasksService.registerFromTechnicalTask(technicalTask.getId(), projectId);

        return projectId;
    }

    @Override
    public void confirmProject(Long projectId) {
        projectTasksService.confirmByProject(projectId);
        taskDevelopersService.confirmByProject(projectId);
        projectDAO.updateStatus(projectId, Pending);
    }

    @Override
    public void runProject(Long projectId) {
        projectTasksService.runByProject(projectId);
        taskDevelopersService.runByProject(projectId);
        projectDAO.updateStatus(projectId, Running);
    }

    @Override
    public void decline(Long projectId, String managerCommentary) {
        Project project = projectDAO.getById(projectId);
        project.setEndDate(new Date());

        if (managerCommentary != null) {
            project.setManagerCommentary(managerCommentary.isEmpty() ? null : managerCommentary);
        }

        taskDevelopersService.dropByProject(projectId);
        projectDAO.updateStatus(projectId, Declined);
        projectDAO.update(project, project);
    }

    @Override
    public void cancel(Long projectId) {
        Project project = projectDAO.getById(projectId);
        project.setEndDate(new Date());

        taskDevelopersService.dropByProject(projectId);
        projectDAO.updateStatus(projectId, Canceled);
        projectDAO.update(project, project);
    }

    @Override
    @Transactional(readOnly = true)
    public Project getById(Long projectId, boolean loadNested) {
        Project project = projectDAO.getById(projectId);

        if (loadNested) {
            project.setTasks(projectTasksService.getAllByProject(project.getId(), true));
        }

        return project;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Project> getNewByManager(Long managerId, boolean loadNested) {
        List<Project> projects = projectDAO.getByManagerAndStatus(managerId, New);

        if (loadNested) {
            projects.forEach(project -> project.setTasks(projectTasksService.getAllByProject(project.getId(), true)));
        }

        return projects;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Project> getRunningByManager(Long managerId, boolean loadNested) {
        List<Project> projects = projectDAO.getRunningByManager(managerId);

        if (loadNested) {
            projects.forEach(project -> project.setTasks(projectTasksService.getAllByProject(project.getId(), true)));
        }

        return projects;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Project> getCompleteByManager(Long managerId, boolean loadNested) {
        List<Project> projects = projectDAO.getCompleteByManager(managerId);

        if (loadNested) {
            projects.forEach(project -> project.setTasks(projectTasksService.getAllByProject(project.getId(), true)));
        }

        return projects;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Project> getRunningByCustomer(Long customerId, boolean loadNested) {
        List<Project> projects = projectDAO.getRunningByCustomer(customerId);

        if (loadNested) {
            projects.forEach(project -> project.setTasks(projectTasksService.getAllByProject(project.getId(), true)));
        }

        return projects;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Project> getCompleteByCustomer(Long customerId, boolean loadNested) {
        List<Project> projects = projectDAO.getCompleteByCustomer(customerId);

        if (loadNested) {
            projects.forEach(project -> project.setTasks(projectTasksService.getAllByProject(project.getId(), true)));
        }

        return projects;
    }
}
