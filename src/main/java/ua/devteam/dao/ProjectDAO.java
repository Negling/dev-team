package ua.devteam.dao;

import ua.devteam.entity.enums.Status;
import ua.devteam.entity.projects.Project;

import java.util.List;

/**
 * Simple interface to access {@link Project} records.
 * {@link ua.devteam.dao.jdbc.JDBCProjectDAO} - implementation.
 */
public interface ProjectDAO extends GenericDAO<Project>, Identified<Project> {

    /**
     * Updates status of project with specified ID, and bounded to it technical task status.
     *
     * @param projectId query param
     * @param status    new status
     */
    void updateStatus(Long projectId, Status status);

    /**
     * Returns list of projects assigned to concrete manager.
     *
     * @param managerId query param
     * @return List of projects, or empty list if no results found.
     */
    List<Project> getAllByManager(Long managerId);

    /**
     * Returns list of projects which assigned to concrete manager and have specified status.
     *
     * @param managerId query param
     * @param status    query param
     * @return List of projects, or empty list if no results found.
     */
    List<Project> getByManagerAndStatus(Long managerId, Status status);

    /**
     * Returns list of projects which assigned to concrete manager and have status not in 'New', 'Running' or 'Pending'.
     *
     * @param managerId query param
     * @return List of projects, or empty list if no results found.
     */
    List<Project> getCompleteByManager(Long managerId);

    /**
     * Returns list of projects which assigned to concrete manager and have status in 'New', 'Running' or 'Pending'.
     *
     * @param managerId query param
     * @return List of projects, or empty list if no results found.
     */
    List<Project> getRunningByManager(Long managerId);

    /**
     * Returns list of projects which assigned to concrete customer and have status not in 'New', 'Running' or 'Pending'.
     *
     * @param customerId query param
     * @return List of projects, or empty list if no results found.
     */
    List<Project> getCompleteByCustomer(Long customerId);

    /**
     * Returns list of projects which assigned to concrete customer and have status in 'New', 'Running' or 'Pending'.
     *
     * @param customerId query param
     * @return List of projects, or empty list if no results found.
     */
    List<Project> getRunningByCustomer(Long customerId);
}
