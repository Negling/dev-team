package ua.devteam.service;


import ua.devteam.entity.tasks.ProjectTask;

import java.util.List;

public interface ProjectTasksService {

    void registerFromTechnicalTask(Long technicalTaskId, Long projectId);

    List<ProjectTask> getAllByProject(Long projectId, boolean loadNested);
}
