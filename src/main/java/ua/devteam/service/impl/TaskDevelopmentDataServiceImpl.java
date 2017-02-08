package ua.devteam.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ua.devteam.dao.TaskDevelopmentDataDAO;
import ua.devteam.entity.tasks.TaskDevelopmentData;
import ua.devteam.entity.users.Developer;
import ua.devteam.service.DevelopersService;
import ua.devteam.service.ProjectTasksService;
import ua.devteam.service.TaskDevelopmentDataService;

import java.util.List;

import static ua.devteam.entity.enums.Status.*;

/**
 * Provides service operations to {@link TaskDevelopmentData taskDevelopmentData}.
 */
@Service
@Transactional(isolation = Isolation.READ_COMMITTED)
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class TaskDevelopmentDataServiceImpl implements TaskDevelopmentDataService {

    private TaskDevelopmentDataDAO taskDevelopmentDataDAO;
    private DevelopersService developersService;
    private ProjectTasksService projectTasksService;

    /**
     * Creates instance of taskDevelopmentData and delegates to developers service subsequent operations.
     * Cuz of reason, that all available developers can be accessed by multiple managers at the same time, we can face situation,
     * when two or more managers will try to lock single developer on multiple tasks. To prevent this, transaction level
     * must be "SERIALIZABLE", this level simply locks tables and prohibits parallel transactions to affected data.
     *
     * @return TaskDevelopmentData instance
     */
    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public TaskDevelopmentData bindDeveloper(Long developerId, Long taskId) {
        Developer dev = developersService.getById(developerId);

        taskDevelopmentDataDAO.createDefault(
                new TaskDevelopmentData.Builder(taskId, developerId, dev.getSpecialization(), dev.getRank()).build());
        developersService.lockDeveloper(developerId);

        return taskDevelopmentDataDAO.getByTaskAndDeveloper(taskId, developerId);
    }

    /**
     * Deletes instance of taskDevelopmentData and delegates to developers service subsequent operations.
     */
    @Override
    public void unbindDeveloper(Long developerId) {
        developersService.unlockDeveloper(developerId);
        taskDevelopmentDataDAO.delete(
                taskDevelopmentDataDAO.getByDeveloperAndStatus(developerId, NEW).stream().findAny().orElse(null));
    }

    /**
     * Updates task development data status to complete, and updates hours spent on task value.
     * Than unlocks developers and checks status of project task.
     */
    @Override
    public void complete(Long taskId, Long developerId, Integer hoursSpent) {
        TaskDevelopmentData data = taskDevelopmentDataDAO.getByTaskAndDeveloper(taskId, developerId);
        data.setHoursSpent(hoursSpent);
        data.setStatus(COMPLETE);

        developersService.releaseDeveloper(developerId);
        taskDevelopmentDataDAO.update(data, data);
        projectTasksService.checkStatus(taskId);
    }

    /**
     * Deletes all task development data mapped to specific project and updates assigned developers status to "AVAILABLE".
     */
    @Override
    public void dropByProject(Long projectId) {
        developersService.removeDevelopersFromProject(projectId);
        taskDevelopmentDataDAO.deleteAllByProject(projectId);
    }

    /**
     * Updates all assigned to project developers status to "HIRED", and updates all assigned to project task development data to "RUNNING".
     */
    @Override
    public void runByProject(Long projectId) {
        developersService.approveDevelopersOnProject(projectId);
        taskDevelopmentDataDAO.setStatusByProject(RUNNING, projectId);
    }

    /**
     * Updates all assigned to project task development data status to "PENDING".
     */
    @Override
    public void confirmByProject(Long projectId) {
        taskDevelopmentDataDAO.setStatusByProject(PENDING, projectId);
    }

    /**
     * Returns task development data with status "RUNNING" and specified developer ID.
     *
     * @return task development data
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true)
    public TaskDevelopmentData getActive(Long developerId) {
        return taskDevelopmentDataDAO.getByDeveloperAndStatus(developerId, RUNNING).stream().findAny().orElse(null);
    }

    /**
     * Returns list of task development data with status "COMPLETE" and specified developer ID.
     *
     * @return list of task development data, or empty list if no results found
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true)
    public List<TaskDevelopmentData> getComplete(Long developerId) {
        return taskDevelopmentDataDAO.getByDeveloperAndStatus(developerId, COMPLETE);
    }
}
