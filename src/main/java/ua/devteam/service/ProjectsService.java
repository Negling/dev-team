package ua.devteam.service;


import ua.devteam.entity.tasks.TaskDeveloper;

import java.math.BigDecimal;
import java.util.List;

public interface ProjectsService {

    void registerProjectDetails(Long projectId, BigDecimal projectPrice, List<TaskDeveloper> projectTaskDetails);

    /*List<Project> getCompleteProjectsByManagerId(Long managerId);*/


}
