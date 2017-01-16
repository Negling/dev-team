package ua.devteam.dao;

import ua.devteam.entity.enums.Status;
import ua.devteam.entity.projects.Project;

import java.util.List;

public interface ProjectDAO extends GenericDAO<Project>, Identified<Project> {

    void updateStatus(Long projectId, Status status);

    Project getById(Long projectId);

    List<Project> getAllByManager(Long managerId);

    List<Project> getByManagerAndStatus(Long managerId, Status status);

    List<Project> getCompleteByManager(Long managerId);

    List<Project> getRunningByManager(Long managerId);

    List<Project> getCompleteByCustomer(Long customerId);

    List<Project> getRunningByCustomer(Long customerId);
}
