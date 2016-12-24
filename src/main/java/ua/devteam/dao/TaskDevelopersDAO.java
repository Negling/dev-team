package ua.devteam.dao;

import ua.devteam.entity.tasks.TaskDeveloper;

import java.util.List;

public interface TaskDevelopersDAO extends GenericDAO<TaskDeveloper> {

    void create(TaskDeveloper entity);

    void createDefault(TaskDeveloper entity);

    TaskDeveloper getByTaskAndDeveloper(Long taskId, Long developerId);

    List<TaskDeveloper> getAllByTask(Long taskId);

}
