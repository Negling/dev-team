package ua.devteam.service;


import ua.devteam.entity.projects.Project;
import ua.devteam.entity.projects.TechnicalTask;

import java.util.List;

/**
 * Provides service operations to {@link Project project}.
 */
public interface ProjectsService {

    /**
     * Creates and records Project instance from specified Technical task, and delegates to project tasks service subsequent operations.
     *
     * @return generated ID
     */
    long createProject(TechnicalTask technicalTask, Long managerId);

    /**
     * Updates project, project tasks and task developers data status to "PENDING".
     */
    void confirmProject(Long projectId);

    void runProject(Long projectId);

    /**
     * Updates projects status to "DECLINED", endDate to current time, and delegates to task developers service subsequent operations.
     */
    void decline(Long projectId, String managerCommentary);


    /**
     * Updates project, project tasks and task developers data status to "RUNNING".
     */
    void cancel(Long projectId);

    /**
     * Returns project instance which id match to requested.
     *
     * @param loadNested if false, loads only project data, otherwise loads projectsTasks data too.
     * @return projects instance
     */
    Project getById(Long projectId, boolean loadNested);

    /**
     * Returns list of projects, which manager ID match to requested and status is "NEW" or "PENDING", or empty list if no results found.
     *
     * @param loadNested if false, loads only project data, otherwise loads projectsTasks data too.
     * @return list of projects or empty list
     */
    List<Project> getNewByManager(Long managerId, boolean loadNested);

    /**
     * Returns list of projects, which manager ID match to requested and status is "RUNNING" or "PENDING", or empty list if no results found.
     *
     * @param loadNested if false, loads only project data, otherwise loads projectsTasks data too.
     * @return list of projects or empty list
     */
    List<Project> getRunningByManager(Long managerId, boolean loadNested);

    /**
     * Returns list of projects, which manager ID match to requested and status is "COMPLETE", "CANCELED" or "DECLINED",
     * or empty list if no results found.
     *
     * @param loadNested if false, loads only project data, otherwise loads projectsTasks data too.
     * @return list of projects or empty list
     */
    List<Project> getCompleteByManager(Long managerId, boolean loadNested);

    /**
     * Returns list of projects, which customer ID match to requested and status is "RUNNING" or "PENDING", or empty list if no results found.
     *
     * @param loadNested if false, loads only project data, otherwise loads projectsTasks data too.
     * @return list of projects or empty list
     */
    List<Project> getRunningByCustomer(Long customerId, boolean loadNested);

    /**
     * Returns list of projects, which customer ID match to requested and status is "COMPLETE", "CANCELED" or "DECLINED",
     * or empty list if no results found.
     *
     * @param loadNested if false, loads only project data, otherwise loads projectsTasks data too.
     * @return list of projects or empty list
     */
    List<Project> getCompleteByCustomer(Long customerId, boolean loadNested);
}
