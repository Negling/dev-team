package ua.devteam.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ua.devteam.dao.OperationDAO;
import ua.devteam.dao.RequestsForDevelopersDAO;
import ua.devteam.entity.tasks.Operation;
import ua.devteam.service.OperationsService;

import java.util.List;

/**
 * Provides service operations to {@link Operation operation}.
 */
@Service
@Transactional(isolation = Isolation.READ_COMMITTED)
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class OperationsServiceImpl implements OperationsService {

    private OperationDAO operationDAO;
    private RequestsForDevelopersDAO requestsForDevelopersDAO;

    /**
     * Creates Operation and RequestForDevelopers entity in storage.
     */
    @Override
    public void registerOperation(Operation operation) {
        operation.setDeepId(operationDAO.create(operation));
        operation.getRequestsForDevelopers().forEach(requestForDevelopers ->
                requestsForDevelopersDAO.create(requestForDevelopers));
    }

    /**
     * Creates Operations and RequestForDevelopers entities in storage.
     */
    @Override
    public void registerOperations(List<Operation> operations) {
        operations.forEach(this::registerOperation);
    }

    /**
     * @param loadNested if false, loads only operations data, otherwise loads requests for developers data too.
     * @return list of operations or empty list if no results found
     */
    @Override
    public List<Operation> getByTechnicalTask(Long technicalTaskId, boolean loadNested) {
        List<Operation> operations = operationDAO.getByTechnicalTask(technicalTaskId);

        if (loadNested) {
            operations.forEach(operation ->
                    operation.setRequestsForDevelopers(requestsForDevelopersDAO.getByOperation(operation.getId())));
        }

        return operations;
    }
}
