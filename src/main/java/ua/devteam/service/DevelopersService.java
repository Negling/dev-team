package ua.devteam.service;

import ua.devteam.entity.enums.DeveloperRank;
import ua.devteam.entity.enums.DeveloperSpecialization;
import ua.devteam.entity.users.Developer;

import java.util.List;

/**
 * Provides service operations to {@link Developer developer}.
 */
public interface DevelopersService {

    /**
     * If requested developer status is "AVAILABLE" - changes it to "LOCKED", otherwise throws exception.
     */
    void lockDeveloper(Long developerId);

    /**
     * If requested developer status is "LOCKED" - changes it to "AVAILABLE", otherwise throws exception.
     */
    void unlockDeveloper(Long developerId);

    /**
     * If requested developer status is "HIRED" - changes it to "AVAILABLE", otherwise throws exception.
     */
    void releaseDeveloper(Long developerId);

    /**
     * Updates status of all devs, that bind to tasks to specified project to "AVAILABLE".
     */
    void removeDevelopersFromProject(Long projectId);

    /**
     * Updates status of all devs, that bind to tasks to specified project to "HIRED".
     */
    void approveDevelopersOnProject(Long projectId);

    /**
     * Returns developer instance which id matches to requested.
     *
     * @return developer instance
     */
    Developer getById(Long developerId);

    /**
     * Returns list of developers, which params match to requested, and status is "AVAILABLE".
     * If no results found - empty list will be returned.
     *
     * @return List of developers that match to params, or empty list
     */
    List<Developer> getAvailableDevelopers(DeveloperSpecialization specialization, DeveloperRank rank, String lastName);
}
