package ua.devteam.dao;

import ua.devteam.entity.projects.TechnicalTask;

import java.util.List;

/**
 * Simple interface to access {@link TechnicalTask} records.
 * {@link ua.devteam.dao.jdbc.JDBCTechnicalTaskDAO} - implementation.
 */
public interface TechnicalTaskDAO extends GenericDAO<TechnicalTask>, Identified<TechnicalTask> {

    /**
     * Returns list of technical tasks assigned to concrete customer.
     *
     * @param customerId query param
     * @return List of technical tasks, or empty list if no results found.
     */
    List<TechnicalTask> getAllByCustomer(Long customerId);

    /**
     * Returns list of all incoming technical tasks with status 'NEW' and no managers assigned to it.
     *
     * @return List of technical tasks, or empty list if no results found.
     */
    List<TechnicalTask> getAllNew();

    /**
     * Returns list of all technical tasks.
     *
     * @return List of technical tasks, or empty list if no results found.
     */
    List<TechnicalTask> getAll();
}
