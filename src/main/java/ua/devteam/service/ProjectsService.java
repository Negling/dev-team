package ua.devteam.service;


import ua.devteam.entity.projects.Project;
import ua.devteam.entity.projects.TechnicalTask;

import java.util.List;

public interface ProjectsService {

    long createProject(TechnicalTask technicalTask, Long managerId);

    void confirmProject(Long projectId);

    void runProject(Long projectId);

    void decline(Long projectId, String managerCommentary);

    void cancel(Long projectId);

    Project getById(Long projectId, boolean loadNested);

    List<Project> getNewByManager(Long managerId, boolean loadNested);

    List<Project> getRunningByManager(Long managerId, boolean loadNested);

    List<Project> getCompleteByManager(Long managerId, boolean loadNested);

    List<Project> getRunningByCustomer(Long customerId, boolean loadNested);

    List<Project> getCompleteByCustomer(Long customerId, boolean loadNested);
}
