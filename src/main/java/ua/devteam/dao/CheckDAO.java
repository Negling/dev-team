package ua.devteam.dao;


import ua.devteam.entity.users.Check;

import java.util.List;

/**
 * Simple interface to access {@link Check} records.
 * {@link ua.devteam.dao.jdbc.JDBCCheckDAO} - implementation.
 */
public interface CheckDAO extends GenericDAO<Check> {

    /**
     * Records check entity into repository.
     *
     * @param check check to be recorded.
     */
    void create(Check check);

    /**
     * Returns check entity according to project id.
     *
     * @param projectId query param.
     * @return {@link Check} entity.
     * @throws org.springframework.dao.EmptyResultDataAccessException - if no results found.
     */
    Check getByProject(Long projectId);

    /**
     * Returns list of checks which assigned to specified customer id.
     *
     * @param customerId query param
     * @return List of checks, or empty list if no results found.
     */
    List<Check> getAllByCustomer(Long customerId);

    /**
     * Returns list of checks which assigned to specified customer id and have status equal to 'Awaiting'.
     *
     * @param customerId query param.
     * @return List of checks, or empty list if no results found.
     */
    List<Check> getNewByCustomer(Long customerId);

    /**
     * Returns list of checks which assigned to specified customer id and have status not equal to 'Awaiting'.
     *
     * @param customerId query param
     * @return List of checks, or empty list if no results found.
     */
    List<Check> getCompleteByCustomer(Long customerId);
}
