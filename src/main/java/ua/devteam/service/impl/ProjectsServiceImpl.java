package ua.devteam.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.devteam.dao.*;
import ua.devteam.entity.projects.Project;
import ua.devteam.entity.projects.TechnicalTask;
import ua.devteam.service.ProjectTasksService;
import ua.devteam.service.ProjectsService;
import ua.devteam.service.TaskDevelopersService;

import java.util.List;

import static ua.devteam.entity.enums.Status.*;

@Service("projectsService")
public class ProjectsServiceImpl implements ProjectsService {

    private ProjectDAO projectDAO;
    private ProjectTasksService projectTasksService;
    private TaskDevelopersService taskDevelopersService;

    @Autowired
    public ProjectsServiceImpl(ProjectDAO projectDAO, ProjectTasksService projectTasksService,
                               TaskDevelopersService taskDevelopersService) {
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
        Project project = projectDAO.getById(projectId);

        project.setStatus(Pending);

        projectDAO.update(project, project);
    }

    @Override
    public void runProject(Long projectId) {
        Project project = projectDAO.getById(projectId);

        project.setStatus(Running);

        taskDevelopersService.runByProject(projectId);
        projectDAO.update(project, project);
    }

    @Override
    public void decline(Long projectId, String managerCommentary) {
        Project project = projectDAO.getById(projectId);

        if (managerCommentary != null) {
            project.setManagerCommentary(managerCommentary);
        }

        project.setStatus(Declined);

        taskDevelopersService.dropByProject(projectId);
        projectDAO.update(project, project);
    }

    @Override
    public Project getById(Long projectId) {
        Project project = projectDAO.getById(projectId);
        project.setTasks(projectTasksService.getAllByProject(project.getId()));

        return project;
    }

    @Override
    public List<Project> getNewByManager(Long managerId) {
        List<Project> projects = projectDAO.getAllByManagerAndStatus(managerId, New);
        projects.forEach(project -> project.setTasks(projectTasksService.getAllByProject(project.getId())));

        return projects;
    }
}
