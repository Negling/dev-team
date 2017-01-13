package ua.devteam.dao;

import ua.devteam.entity.enums.Status;
import ua.devteam.entity.tasks.ProjectTask;

import java.util.List;

public interface ProjectTaskDAO extends GenericDAO<ProjectTask>, Identified<ProjectTask> {

    void checkStatus(Long taskId);

    ProjectTask getById(Long id);

    List<ProjectTask> getByProject(Long projectId);

    void setStatusByProject(Status status, Long projectId);
}
