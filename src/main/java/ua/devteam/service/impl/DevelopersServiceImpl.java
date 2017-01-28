package ua.devteam.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ua.devteam.dao.DeveloperDAO;
import ua.devteam.entity.enums.DeveloperRank;
import ua.devteam.entity.enums.DeveloperSpecialization;
import ua.devteam.entity.enums.DeveloperStatus;
import ua.devteam.entity.users.Developer;
import ua.devteam.exceptions.InvalidObjectStateException;
import ua.devteam.service.DevelopersService;

import java.util.List;

import static ua.devteam.entity.enums.DeveloperStatus.*;

/**
 * Provides service operations to {@link Developer developer}.
 */
@Service
@Transactional(isolation = Isolation.READ_COMMITTED)
public class DevelopersServiceImpl implements DevelopersService {

    private DeveloperDAO developerDAO;

    @Autowired
    public DevelopersServiceImpl(DeveloperDAO developerDAO) {
        this.developerDAO = developerDAO;
    }

    /**
     * Returns developer instance which id matches to requested.
     *
     * @return developer instance
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true)
    public Developer getById(Long developerId) {
        return developerDAO.getById(developerId);
    }

    /**
     * Returns list of developers, which params match to requested, and status is "AVAILABLE".
     * If no results found - empty list will be returned.
     *
     * @return List of developers that match to params, or empty list
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true)
    public List<Developer> getAvailableDevelopers(DeveloperSpecialization specialization, DeveloperRank rank, String lastName) {
        if (lastName == null || lastName.isEmpty()) {
            return developerDAO.getAvailableByParams(specialization, rank);
        } else {
            return developerDAO.getAvailableByParams(specialization, rank, lastName);
        }
    }

    /**
     * If requested developer status is "AVAILABLE" - changes it to "LOCKED", otherwise throws exception.
     */
    @Override
    public void lockDeveloper(Long developerId) {
        if (developerDAO.getById(developerId).getStatus().equals(AVAILABLE)) {
            updateDeveloperStatus(developerId, LOCKED);
        } else {
            throw new InvalidObjectStateException("errorPage.alreadyLocked", null);
        }
    }

    /**
     * If requested developer status is "LOCKED" - changes it to "AVAILABLE", otherwise throws exception.
     */
    @Override
    public void unlockDeveloper(Long developerId) {
        if (developerDAO.getById(developerId).getStatus().equals(LOCKED)) {
            updateDeveloperStatus(developerId, AVAILABLE);
        } else {
            throw new InvalidObjectStateException("errorPage.notLocked", null);
        }
    }

    /**
     * If requested developer status is "HIRED" - changes it to "AVAILABLE", otherwise throws exception.
     */
    @Override
    public void releaseDeveloper(Long developerId) {
        if (developerDAO.getById(developerId).getStatus().equals(HIRED)) {
            updateDeveloperStatus(developerId, AVAILABLE);
        } else {
            throw new InvalidObjectStateException("errorPage.notHired", null);
        }
    }

    /**
     * Updates status of all devs, that bind to tasks to specified project to "AVAILABLE".
     */
    @Override
    public void removeDevelopersFromProject(Long projectId) {
        developerDAO.updateStatusByProject(AVAILABLE, projectId);
    }

    /**
     * Updates status of all devs, that bind to tasks to specified project to "HIRED".
     */
    @Override
    public void approveDevelopersOnProject(Long projectId) {
        developerDAO.updateStatusByProject(HIRED, projectId);
    }

    /**
     * Updates status of specified developer.
     */
    private void updateDeveloperStatus(Long developerId, DeveloperStatus developerStatus) {
        Developer dev = developerDAO.getById(developerId);
        dev.setStatus(developerStatus);

        developerDAO.update(dev, dev);
    }
}
