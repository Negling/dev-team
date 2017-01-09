package ua.devteam.service;

import ua.devteam.entity.projects.TechnicalTask;
import ua.devteam.entity.enums.Status;

import java.util.List;

public interface TechnicalTasksService {

    Long registerTechnicalTask(TechnicalTask task);

    List<TechnicalTask> getAllUnassigned();

    List<TechnicalTask> getAllTechnicalTasks();

    void accept(Long technicalTaskId, Long managerId);

    void decline(Long technicalTaskId, String managerCommentary);
}
