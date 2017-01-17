package ua.devteam.dao;

import ua.devteam.entity.tasks.Operation;

import java.util.List;

/**
 * Simple interface to access {@link Operation} records.
 * {@link ua.devteam.dao.jdbc.JDBCOperationDAO} - implementation.
 */
public interface OperationDAO extends GenericDAO<Operation>, Identified<Operation> {

    /**
     * Returns list of checks which assigned to specified technical task.
     *
     * @param technicalTaskId query param
     * @return List of operations, or empty list if no results found.
     */
    List<Operation> getByTechnicalTask(Long technicalTaskId);
}
