package ua.devteam.dao;

import ua.devteam.entity.tasks.ProjectTask;

import java.util.List;

public interface ProjectTaskDAO extends GenericDAO<ProjectTask>, Identified<ProjectTask> {

    ProjectTask getById(Long id);

    List<ProjectTask> getByProject(Long projectId);
}