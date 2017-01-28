package ua.devteam.service;

import ua.devteam.entity.projects.TechnicalTask;

import java.util.List;

/**
 * Provides service operations to {@link TechnicalTask technicalTask}.
 */
public interface TechnicalTasksService {

    /**
     * Registers technical task instance and its operations in storage.
     */
    Long registerTechnicalTask(TechnicalTask task);

    /**
     * Updates status of technical task to "PENDING" and creates instance of project based on this Technical Task.
     */
    void accept(Long technicalTaskId, Long managerId);

    /**
     * Updates status of technical task to "DECLINED".
     */
    void decline(Long technicalTaskId, String managerCommentary);

    /**
     * Returns Technical Task instance which id match to requested.
     *
     * @param loadNested if false, loads only technical task data, otherwise loads operations data too.
     * @return technical task
     */
    TechnicalTask getById(Long technicalTaskId, boolean loadNested);

    /**
     * Returns list of technical tasks with status "NEW".
     *
     * @param loadNested if false, loads only technical task data, otherwise loads operations data too.
     * @return list of technical tasks, or empty list if no results found
     */
    List<TechnicalTask> getAllUnassigned(boolean loadNested);

    /**
     * Returns list of all technical tasks which customer ID matches to requested.
     *
     * @param loadNested if false, loads only technical task data, otherwise loads operations data too.
     * @return list of technical tasks, or empty list if no results found
     */
    List<TechnicalTask> getAllByCustomer(Long customerId, boolean loadNested);
}
