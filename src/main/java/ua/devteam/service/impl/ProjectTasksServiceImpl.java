package ua.devteam.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ua.devteam.dao.OperationDAO;
import ua.devteam.dao.ProjectTaskDAO;
import ua.devteam.dao.RequestsForDevelopersDAO;
import ua.devteam.dao.TaskDevelopmentDataDAO;
import ua.devteam.entity.tasks.ProjectTask;
import ua.devteam.service.ProjectTasksService;

import java.util.List;

import static ua.devteam.entity.enums.Status.PENDING;
import static ua.devteam.entity.enums.Status.RUNNING;

@Service("projectTasksService")
@Transactional(isolation = Isolation.READ_COMMITTED)
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
    public void runByProject(Long projectId) {
        projectTaskDAO.setStatusByProject(RUNNING, projectId);
    }

    @Override
    public void confirmByProject(Long projectId) {
        projectTaskDAO.setStatusByProject(PENDING, projectId);
    }

    @Override
    public void checkStatus(Long taskId) {
        projectTaskDAO.checkStatus(taskId);
    }

    @Override
    @Transactional(readOnly = true)
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
