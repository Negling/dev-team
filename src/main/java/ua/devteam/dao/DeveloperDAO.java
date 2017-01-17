package ua.devteam.dao;


import ua.devteam.entity.enums.DeveloperRank;
import ua.devteam.entity.enums.DeveloperSpecialization;
import ua.devteam.entity.enums.DeveloperStatus;
import ua.devteam.entity.users.Developer;

import java.util.List;

/**
 * Simple interface to access {@link Developer} records.
 * {@link ua.devteam.dao.jdbc.JDBCDeveloperDAO} - implementation.
 */
public interface DeveloperDAO extends GenericDAO<Developer>, Identified<Developer> {

    /**
     * Returns developer entity which match to specified email.
     *
     * @param email query param
     * @return {@link Developer} entity
     * @throws org.springframework.dao.EmptyResultDataAccessException - if there no entity with such ID.
     */
    Developer getByEmail(String email);

    /**
     * Updates status of all developers assigned to specified project.
     *
     * @param status    new status
     * @param projectId unique project id
     */
    void updateStatusByProject(DeveloperStatus status, Long projectId);


    /**
     * Returns list of developers which match to query params and has 'Available' status.
     *
     * @param specialization developer specialization
     * @param rank           developer rank
     * @return List of developers, or empty list if no results found.
     */
    List<Developer> getAvailableByParams(DeveloperSpecialization specialization, DeveloperRank rank);

    /**
     * Returns list of developers which match to query params and has 'Available' status.
     *
     * @param specialization developer specialization
     * @param rank           developer rank
     * @param lastName       developer's last name to match. Case insensitive.
     * @return List of developers, or empty list if no results found.
     */
    List<Developer> getAvailableByParams(DeveloperSpecialization specialization, DeveloperRank rank, String lastName);

    /**
     * Returns list of developers which match to query params.
     *
     * @param specialization developer specialization
     * @param rank           developer rank
     * @return List of developers, or empty list if no results found.
     */
    List<Developer> getByParams(DeveloperSpecialization specialization, DeveloperRank rank);

    /**
     * Returns list of developers which match to query params.
     *
     * @param specialization developer specialization
     * @param rank           developer rank
     * @param lastName       developer's last name to match. Case insensitive.
     * @return List of developers, or empty list if no results found.
     */
    List<Developer> getByParams(DeveloperSpecialization specialization, DeveloperRank rank, String lastName);

}
