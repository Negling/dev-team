package ua.devteam.dao;

import ua.devteam.entity.enums.Status;
import ua.devteam.entity.tasks.ProjectTask;

import java.util.List;

/**
 * Simple interface to access {@link ProjectTask} records.
 * {@link ua.devteam.dao.jdbc.JDBCProjectTaskDAO} - implementation.
 */
public interface ProjectTaskDAO extends GenericDAO<ProjectTask>, Identified<ProjectTask> {

    /**
     * Checks count of all assigned to this task {@link ua.devteam.entity.tasks.TaskDevelopmentData} statuses.
     * If count of complete data matches to assigned data count - updates task status to 'Complete'.
     * Than if previous condition is true - checks count of project complete tasks to count of assigned tasks -
     * if they are equal - updates project status to 'Complete'.
     *
     * @param taskId query param
     */
    void checkStatus(Long taskId);

    /**
     * Updates all project tasks assigned to concrete project to new status.
     *
     * @param status    query param
     * @param projectId query param
     */
    void setStatusByProject(Status status, Long projectId);

    /**
     * Returns list of project tasks assigned to concrete project.
     *
     * @param projectId query param
     * @return List of project tasks, or empty list if no results found.
     */
    List<ProjectTask> getByProject(Long projectId);
}
