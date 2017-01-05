package ua.devteam.service;


import ua.devteam.entity.projects.Project;
import ua.devteam.entity.tasks.TaskDeveloper;
import ua.devteam.entity.users.Check;

import java.math.BigDecimal;
import java.util.List;

public interface ProjectsService {

   long createProject(Long technicalTaskId, Long managerId);

   List<Project> getNewByManager(Long managerId);

    void confirmProject(Long projectId, Check projectCheck);

    void decline(Long projectId);
}
