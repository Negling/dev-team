package ua.devteam.service;


import ua.devteam.entity.enums.Status;

public interface TasksService {

    void updateTaskStatus(Long taskId, Status status);

    void updateDeveloperTaskStatus(Long taskId, Long developerId, Status status);

    void updateDeveloperTaskHoursSpent(Long taskId, Long developerId, Long hours);
}
