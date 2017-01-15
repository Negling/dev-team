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

@Service("developersService")
@Transactional(isolation = Isolation.READ_COMMITTED)
public class DevelopersServiceImpl implements DevelopersService {

    private DeveloperDAO developerDAO;

    @Autowired
    public DevelopersServiceImpl(DeveloperDAO developerDAO) {
        this.developerDAO = developerDAO;
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void lockDeveloper(Long developerId) {
        if (developerDAO.getById(developerId).getStatus().equals(Available)) {
            updateDeveloperStatus(developerId, Locked);
        } else {
            throw new InvalidObjectStateException("errorPage.alreadyLocked", null);
        }
    }

    @Override
    public void unlockDeveloper(Long developerId) {
        updateDeveloperStatus(developerId, Available);
    }

    @Override
    public void removeDevelopersFromProject(Long projectId) {
        developerDAO.updateStatusByProject(Available, projectId);
    }

    @Override
    public void approveDevelopersOnProject(Long projectId) {
        developerDAO.updateStatusByProject(Hired, projectId);
    }

    @Override
    @Transactional(readOnly = true)
    public Developer getById(Long developerId) {
        return developerDAO.getById(developerId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Developer> getAvailableDevelopers(DeveloperSpecialization specialization, DeveloperRank rank, String lastName) {
        if (lastName == null || lastName.isEmpty()) {
            return developerDAO.getAvailableByParams(specialization, rank);
        } else {
            return developerDAO.getAvailableByParams(specialization, rank, lastName);
        }
    }

    private void updateDeveloperStatus(Long developerId, DeveloperStatus developerStatus) {
        Developer dev = developerDAO.getById(developerId);
        dev.setStatus(developerStatus);

        developerDAO.update(dev, dev);
    }
}
