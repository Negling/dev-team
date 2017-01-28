package ua.devteam.service;


import ua.devteam.entity.tasks.TaskDevelopmentData;

import java.util.List;

/**
 * Provides service operations to {@link TaskDevelopmentData taskDevelopmentData}.
 */
public interface TaskDevelopmentDataService {

    /**
     * Deletes all task development data mapped to specific project and updates assigned developers status to "AVAILABLE".
     */
    void dropByProject(Long projectId);

    /**
     * Updates all assigned to project developers status to "HIRED", and updates all assigned to project task development data to "RUNNING".
     */
    void runByProject(Long projectId);

    /**
     * Updates task development data status to complete, and updates hours spent on task value.
     * Than unlocks developers and checks status of project task.
     */
    void complete(Long taskId, Long developerId, Integer hoursSpent);

    /**
     * Deletes instance of taskDevelopmentData and delegates to developers service subsequent operations.
     */
    void unbindDeveloper(Long developerId);

    /**
     * Updates all assigned to project task development data status to "PENDING".
     */
    void confirmByProject(Long projectId);

    /**
     * Creates instance of taskDevelopmentData and delegates to developers service subsequent operations.
     *
     * @return TaskDevelopmentData instance
     */
    TaskDevelopmentData bindDeveloper(Long developerId, Long taskId);

    /**
     * Returns task development data with status "RUNNING" and specified developer ID.
     *
     * @return task development data
     */
    TaskDevelopmentData getActive(Long developerId);

    /**
     * Returns list of task development data with status "COMPLETE" and specified developer ID.
     *
     * @return list of task development data, or empty list if no results found
     */
    List<TaskDevelopmentData> getComplete(Long developerId);
}
