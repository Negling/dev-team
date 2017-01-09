package ua.devteam.service;


import ua.devteam.entity.tasks.TaskDeveloper;

public interface TaskDevelopersService {

    void dropByProject(Long projectId);

    void runByProject(Long projectId);

    TaskDeveloper bindDeveloper(Long developerId, Long taskId);

    void unbindDeveloper(Long developerId);
}
