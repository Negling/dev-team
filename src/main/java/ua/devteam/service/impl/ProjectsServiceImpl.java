package ua.devteam.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ua.devteam.dao.*;
import ua.devteam.entity.enums.CheckStatus;
import ua.devteam.entity.projects.Project;
import ua.devteam.entity.projects.TechnicalTask;
import ua.devteam.entity.tasks.ProjectTask;
import ua.devteam.entity.users.Check;
import ua.devteam.service.ProjectsService;

import java.util.List;

import static ua.devteam.entity.enums.DeveloperStatus.Available;
import static ua.devteam.entity.enums.Status.*;

@Service("projectsService")
public class ProjectsServiceImpl implements ProjectsService {

    private TechnicalTaskDAO technicalTaskDAO;
    private OperationDAO operationDAO;
    private RequestsForDevelopersDAO requestsForDevelopersDAO;
    private ProjectDAO projectDAO;
    private ProjectTaskDAO projectTaskDAO;
    private TaskDevelopersDAO taskDevelopersDAO;
    private DeveloperDAO developerDAO;
    private CheckDAO checkDAO;


    @Autowired
    public ProjectsServiceImpl(TechnicalTaskDAO technicalTaskDAO, OperationDAO operationDAO,
                               RequestsForDevelopersDAO requestsForDevelopersDAO, ProjectDAO projectDAO,
                               ProjectTaskDAO projectTaskDAO, TaskDevelopersDAO taskDevelopersDAO,
                               DeveloperDAO developerDAO, CheckDAO checkDAO) {
        this.technicalTaskDAO = technicalTaskDAO;
        this.operationDAO = operationDAO;
        this.requestsForDevelopersDAO = requestsForDevelopersDAO;
        this.projectDAO = projectDAO;
        this.projectTaskDAO = projectTaskDAO;
        this.taskDevelopersDAO = taskDevelopersDAO;
        this.developerDAO = developerDAO;
        this.checkDAO = checkDAO;
    }

    @Override
    public long createProject(Long technicalTaskId, Long managerId) {
        TechnicalTask technicalTask = technicalTaskDAO.getById(technicalTaskId);
        technicalTask.setStatus(Running);

        long projectId = projectDAO.create(new Project(managerId, technicalTask));
        operationDAO.getByTechnicalTask(technicalTaskId).forEach(operation ->
                projectTaskDAO.create(new ProjectTask(projectId, operation)));
        technicalTaskDAO.update(technicalTask, technicalTask);

        return projectId;
    }

    @Override
    public void confirmProject(Long projectId, Check projectCheck) {
        Project project = projectDAO.getById(projectId);

        project.setStatus(Pending);
        projectCheck.setStatus(CheckStatus.Awaiting);

        projectDAO.update(project, project);
        checkDAO.create(projectCheck);
    }

    @Override
    public void decline(Long projectId) {
        Project project = projectDAO.getById(projectId);

        project.setStatus(Declined);

        developerDAO.updateStatusByProject(Available, projectId);
        taskDevelopersDAO.deleteAllByProject(projectId);
        projectDAO.update(project, project);
    }

    @Override
    public List<Project> getNewByManager(Long managerId) {
        return formProject(projectDAO.getAllByManagerAndStatus(managerId, New));
    }

    private List<Project> formProject(List<Project> projects) {
        projects.forEach(project -> {
            project.setTasks(projectTaskDAO.getByProject(project.getId()));
            project.getTasks().forEach(projectTask -> {
                projectTask.setRequestsForDevelopers(requestsForDevelopersDAO.getByOperation(projectTask.getOperationId()));

                try {
                    projectTask.setTaskDevelopers(taskDevelopersDAO.getAllByTask(projectTask.getId()));
                } catch (EmptyResultDataAccessException ex) {
                    projectTask.setTaskDevelopers(null);
                }
            });
        });

        return projects;
    }
}
