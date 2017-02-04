package ua.devteam.service.impl;

import lombok.AllArgsConstructor;
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

import static ua.devteam.entity.enums.Status.*;

/**
 * Provides service operations to {@link ProjectTask projectTask}.
 */
@Service
@Transactional(isolation = Isolation.READ_COMMITTED)
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class ProjectTasksServiceImpl implements ProjectTasksService {

    private ProjectTaskDAO projectTaskDAO;
    private TaskDevelopmentDataDAO taskDevelopmentDataDAO;
    private OperationDAO operationDAO;
    private RequestsForDevelopersDAO requestsForDevelopersDAO;

    /**
     * Creates and maps projectTasks to project from existing operations.
     */
    @Override
    public void registerFromTechnicalTask(Long technicalTaskId, Long projectId) {
        operationDAO.getByTechnicalTask(technicalTaskId)
                .forEach(operation -> projectTaskDAO.create(
                        new ProjectTask.Builder(
                                projectId,
                                operation.getId(),
                                operation.getName(),
                                operation.getDescription()).
                                build()
                        )
                );
    }

    /**
     * Updates status of all tasks that mapped to specified project to "RUNNING".
     */
    @Override
    public void runByProject(Long projectId) {
        projectTaskDAO.setStatusByProject(RUNNING, projectId);
    }

    /**
     * Updates status of all tasks that mapped to specified project to "PENDING".
     */
    @Override
    public void confirmByProject(Long projectId) {
        projectTaskDAO.setStatusByProject(PENDING, projectId);
    }

    /**
     * Updates status of all tasks that mapped to specified project to "CANCELED".
     */
    @Override
    public void cancelByProject(Long projectId) {
        projectTaskDAO.setStatusByProject(CANCELED, projectId);
    }

    /**
     * Checks statuses of all task developers that bind to this task. If count of mapped task developers is equal
     * to mapped task developers which have "COMPLETE" status - than status of task changes to "COMPLETE" and
     * Project status is going to be checked in same way.
     *
     * @param taskId to check
     */
    @Override
    public void checkStatus(Long taskId) {
        projectTaskDAO.checkStatus(taskId);
    }

    /**
     * Returns list of project tasks mapped to specified project, or empty list if no results found.
     *
     * @param loadNested if false, loads only project task data, otherwise loads task development and requests for developers data too.
     * @return list of projectTasks or empty list
     */
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
