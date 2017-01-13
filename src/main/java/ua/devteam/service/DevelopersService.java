package ua.devteam.service;

import ua.devteam.entity.enums.DeveloperRank;
import ua.devteam.entity.enums.DeveloperSpecialization;
import ua.devteam.entity.users.Developer;

import java.util.List;

public interface DevelopersService {

    void lockDeveloper(Long developerId);

    void unlockDeveloper(Long developerId);

    List<Developer> getAvailableDevelopers(DeveloperSpecialization specialization, DeveloperRank rank, String lastName);

    void removeDevelopersFromProject(Long projectId);

    void approveDevelopersOnProject(Long projectId);

    Developer getById(Long developerId);
}
