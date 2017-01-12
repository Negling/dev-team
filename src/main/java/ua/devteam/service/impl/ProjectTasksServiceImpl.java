package ua.devteam.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ua.devteam.dao.OperationDAO;
import ua.devteam.dao.ProjectTaskDAO;
import ua.devteam.dao.RequestsForDevelopersDAO;
import ua.devteam.dao.TaskDevelopmentDataDAO;
import ua.devteam.entity.tasks.ProjectTask;
import ua.devteam.service.ProjectTasksService;

import java.util.List;

@Service("projectTasksService")
public class ProjectTasksServiceImpl implements ProjectTasksService {

    private ProjectTaskDAO projectTaskDAO;
    private TaskDevelopmentDataDAO taskDevelopmentDataDAO;
    private OperationDAO operationDAO;
    private RequestsForDevelopersDAO requestsForDevelopersDAO;

    @Autowired
    public ProjectTasksServiceImpl(ProjectTaskDAO projectTaskDAO, TaskDevelopmentDataDAO taskDevelopmentDataDAO,
                                   OperationDAO operationDAO, RequestsForDevelopersDAO requestsForDevelopersDAO) {
        this.projectTaskDAO = projectTaskDAO;
        this.taskDevelopmentDataDAO = taskDevelopmentDataDAO;
        this.operationDAO = operationDAO;
        this.requestsForDevelopersDAO = requestsForDevelopersDAO;
    }

    @Override
    public void registerFromTechnicalTask(Long technicalTaskId, Long projectId) {
        operationDAO.getByTechnicalTask(technicalTaskId).forEach(operation ->
                projectTaskDAO.create(new ProjectTask(projectId, operation)));
    }

    @Override
    public void checkStatus(Long taskId) {
        projectTaskDAO.checkStatus(taskId);
    }

    @Override
    public List<ProjectTask> getAllByProject(Long projectId, boolean loadNested) {
        List<ProjectTask> projectTasks = projectTaskDAO.getByProject(projectId);

        if (loadNested) {
            projectTasks.forEach(projectTask -> {
                projectTask.setRequestsForDevelopers(requestsForDevelopersDAO.getByOperation(projectTask.getOperationId()));

                try {
                    projectTask.setTasksDevelopmentData(taskDevelopmentDataDAO.getAllByTask(projectTask.getId()));
                } catch (EmptyResultDataAccessException ex) {
                    projectTask.setTasksDevelopmentData(null);
                }
            });
        }

        return projectTasks;
    }
}
