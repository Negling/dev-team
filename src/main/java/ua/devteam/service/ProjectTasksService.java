package ua.devteam.service;


import ua.devteam.entity.tasks.ProjectTask;

import java.util.List;

/**
 * Provides service operations to {@link ProjectTask projectTask}.
 */
public interface ProjectTasksService {

    /**
     * Creates and maps projectTasks to project from existing operations.
     */
    void registerFromTechnicalTask(Long technicalTaskId, Long projectId);

    /**
     * Updates status of all tasks that mapped to specified project to "CANCELED".
     */
    void cancelByProject(Long projectId);

    /**
     * Checks statuses of all task developers that bind to this task. If count of mapped task developers is equal
     * to mapped task developers which have "COMPLETE" status - than status of task changes to "COMPLETE" and
     * Project status is going to be checked in same way.
     *
     * @param taskId to check
     */
    void checkStatus(Long taskId);

    /**
     * Updates status of all tasks that mapped to specified project to "RUNNING".
     */
    void runByProject(Long projectId);

    /**
     * Updates status of all tasks that mapped to specified project to "PENDING".
     */
    void confirmByProject(Long projectId);

    /**
     * Returns list of project tasks mapped to specified project, or empty list if no results found.
     *
     * @param loadNested if false, loads only project task data, otherwise loads task development and requests for developers data too.
     * @return list of projectTasks or empty list
     */
    List<ProjectTask> getAllByProject(Long projectId, boolean loadNested);
}
