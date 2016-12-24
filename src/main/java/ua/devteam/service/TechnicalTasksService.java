package ua.devteam.service;

import ua.devteam.entity.projects.TechnicalTask;
import ua.devteam.entity.enums.Status;

import java.util.List;

public interface TechnicalTasksService {

    Long registerTechnicalTask(TechnicalTask task);

    List<TechnicalTask> getTasksByManagerId(Long managerId, Status status);

    List<TechnicalTask> getAllTechnicalTasks();
}
