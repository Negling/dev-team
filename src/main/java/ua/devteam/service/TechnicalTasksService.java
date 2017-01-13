package ua.devteam.service;

import ua.devteam.entity.projects.TechnicalTask;

import java.util.List;

public interface TechnicalTasksService {

    Long registerTechnicalTask(TechnicalTask task);

    void accept(Long technicalTaskId, Long managerId);

    void decline(Long technicalTaskId, String managerCommentary);

    List<TechnicalTask> getAllUnassigned(boolean loadNested);

    List<TechnicalTask> getAllByCustomer(Long customerId, boolean loadNested);

    List<TechnicalTask> getAllTechnicalTasks(boolean loadNested);

    TechnicalTask getById(Long technicalTaskId, boolean loadNested);
}
