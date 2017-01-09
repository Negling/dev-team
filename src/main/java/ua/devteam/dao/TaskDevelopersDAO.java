package ua.devteam.dao;

import ua.devteam.entity.enums.Status;
import ua.devteam.entity.tasks.TaskDeveloper;

import java.util.List;

public interface TaskDevelopersDAO extends GenericDAO<TaskDeveloper> {

    void create(TaskDeveloper entity);

    void createDefault(TaskDeveloper entity);

    void setStatusByProject(Status status, Long projectId);

    void deleteAllByProject(Long projectId);

    TaskDeveloper getByTaskAndDeveloper(Long taskId, Long developerId);

    List<TaskDeveloper> getAllByTask(Long taskId);

    List<TaskDeveloper> getAllByProject(Long projectId);

    TaskDeveloper getByDeveloperAndStatus(Long developerId, Status status);
}
