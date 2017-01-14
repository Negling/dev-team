package ua.devteam.dao;

import ua.devteam.entity.enums.Status;
import ua.devteam.entity.tasks.TaskDevelopmentData;

import java.util.List;

public interface TaskDevelopmentDataDAO extends GenericDAO<TaskDevelopmentData> {

    void create(TaskDevelopmentData entity);

    void createDefault(TaskDevelopmentData entity);

    void setStatusByProject(Status status, Long projectId);

    void deleteAllByProject(Long projectId);

    TaskDevelopmentData getByTaskAndDeveloper(Long taskId, Long developerId);

    List<TaskDevelopmentData> getAllByTask(Long taskId);

    List<TaskDevelopmentData> getAllByDeveloper(Long developerId);

    List<TaskDevelopmentData> getByDeveloperAndStatus(Long developerId, Status status);
}
