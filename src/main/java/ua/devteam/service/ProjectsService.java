package ua.devteam.service;


import ua.devteam.entity.projects.Project;
import ua.devteam.entity.projects.TechnicalTask;

import java.util.List;

public interface ProjectsService {

    List<Project> getNewByManager(Long managerId);

    long createProject(TechnicalTask technicalTask, Long managerId);

    void confirmProject(Long projectId);

    void runProject(Long projectId);

    void decline(Long projectId, String managerCommentary);

    void cancel(Long projectId);

    Project getById(Long projectId);
}
