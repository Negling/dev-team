package ua.devteam.dao;

import ua.devteam.entity.enums.Status;
import ua.devteam.entity.tasks.TaskDevelopmentData;

import java.util.List;

/**
 * Simple interface to access {@link TaskDevelopmentData} records.
 * {@link ua.devteam.dao.jdbc.JDBCTaskDevelopmentDataDAO} - implementation.
 */
public interface TaskDevelopmentDataDAO extends GenericDAO<TaskDevelopmentData> {

    /**
     * Records task development data entity into repository.
     *
     * @param taskDevelopmentData to be recorded
     */
    void create(TaskDevelopmentData taskDevelopmentData);

    /**
     * Records only taskId, developerId, specialization and rank field values.
     *
     * @param taskDevelopmentData to be recorded
     */
    void createDefault(TaskDevelopmentData taskDevelopmentData);

    /**
     * Updates all task development data status assigned to concrete project.
     *
     * @param status    query param
     * @param projectId query param
     */
    void setStatusByProject(Status status, Long projectId);

    /**
     * Deletes all task development data status assigned to concrete project.
     *
     * @param projectId query param
     */
    void deleteAllByProject(Long projectId);

    /**
     * Returns entity specified by developer and task id's.
     *
     * @param taskId      query param
     * @param developerId query param
     * @return {@link TaskDevelopmentData} entity
     * @throws org.springframework.dao.EmptyResultDataAccessException - if there no entity with such ID
     */
    TaskDevelopmentData getByTaskAndDeveloper(Long taskId, Long developerId);

    /**
     * Returns list of task development data assigned to specified task.
     *
     * @param taskId query param
     * @return List of task development data, or empty list if no results found.
     */
    List<TaskDevelopmentData> getAllByTask(Long taskId);

    /**
     * Returns list of task development data assigned to specified developer.
     *
     * @param developerId query param
     * @return List of task development data, or empty list if no results found.
     */
    List<TaskDevelopmentData> getAllByDeveloper(Long developerId);

    /**
     * Returns list of task development data assigned to specified developer and have specified status in the moment.
     *
     * @param developerId query param
     * @param status      query param
     * @return List of task development data, or empty list if no results found.
     */
    List<TaskDevelopmentData> getByDeveloperAndStatus(Long developerId, Status status);
}
