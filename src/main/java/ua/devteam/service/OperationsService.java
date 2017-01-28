package ua.devteam.service;


import ua.devteam.entity.tasks.Operation;

import java.util.List;

/**
 * Provides service operations to {@link Operation operation}.
 */
public interface OperationsService {

    /**
     * Creates Operation and RequestForDevelopers entity in storage.
     */
    void registerOperation(Operation operation);

    /**
     * Creates Operations and RequestForDevelopers entities in storage.
     */
    void registerOperations(List<Operation> operations);

    /**
     * @param loadNested if false, loads only operations data, otherwise loads requests for developers data too.
     * @return list of operations or empty list if no results found
     */
    List<Operation> getByTechnicalTask(Long technicalTaskId, boolean loadNested);
}
