package ua.devteam.service;


import ua.devteam.entity.tasks.TaskDevelopmentData;

import java.util.List;

public interface TaskDevelopmentDataService {

    void dropByProject(Long projectId);

    void runByProject(Long projectId);

    void complete(Long taskId, Long developerId, Integer hoursSpent);

    void unbindDeveloper(Long developerId);

    void confirmByProject(Long projectId);

    TaskDevelopmentData bindDeveloper(Long developerId, Long taskId);

    TaskDevelopmentData getActive(Long developerId);

    List<TaskDevelopmentData> getComplete(Long developerId);

    List<TaskDevelopmentData> getAllByDeveloper(Long developerId);
}
