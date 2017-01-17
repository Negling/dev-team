package ua.devteam.dao;

import ua.devteam.entity.tasks.RequestForDevelopers;

import java.util.List;

/**
 * Simple interface to access {@link RequestForDevelopers} records.
 * {@link ua.devteam.dao.jdbc.JDBCRequestsForDevelopersDAO} - implementation.
 */
public interface RequestsForDevelopersDAO extends GenericDAO<RequestForDevelopers> {

    /**
     * Records request for developers entity into repository.
     *
     * @param requestForDevelopers to be recorded
     */
    void create(RequestForDevelopers requestForDevelopers);

    /**
     * Returns list of request for developers assigned to concrete operation.
     *
     * @param operationId query param
     * @return List of requests for developers, or empty list if no results found.
     */
    List<RequestForDevelopers> getByOperation(Long operationId);
}
