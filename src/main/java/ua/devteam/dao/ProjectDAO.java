package ua.devteam.dao;

import ua.devteam.entity.enums.Status;
import ua.devteam.entity.projects.Project;

import java.util.List;

public interface ProjectDAO extends GenericDAO<Project>, Identified<Project> {

    Project getById(Long projectId);

    List<Project> getAllByManager(Long managerId);

    List<Project> getByManagerAndStatus(Long managerId, Status status);

    List<Project> getCompleteByManager(Long managerId);
}
