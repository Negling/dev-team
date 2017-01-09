package ua.devteam.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.devteam.dao.OperationDAO;
import ua.devteam.dao.RequestsForDevelopersDAO;
import ua.devteam.entity.tasks.Operation;
import ua.devteam.service.OperationsService;

import java.util.List;

@Service("operationsService")
public class OperationsServiceImpl implements OperationsService {

    private OperationDAO operationDAO;
    private RequestsForDevelopersDAO requestsForDevelopersDAO;

    @Autowired
    public OperationsServiceImpl(OperationDAO operationDAO, RequestsForDevelopersDAO requestsForDevelopersDAO) {
        this.operationDAO = operationDAO;
        this.requestsForDevelopersDAO = requestsForDevelopersDAO;
    }

    @Override
    public void registerOperation(Operation operation) {
        operation.setDeepId(operationDAO.create(operation));
        operation.getRequestsForDevelopers().forEach(requestForDevelopers ->
                requestsForDevelopersDAO.create(requestForDevelopers));
    }

    @Override
    public void registerOperations(List<Operation> operations) {
        operations.forEach(this::registerOperation);
    }

    @Override
    public List<Operation> getByTechnicalTask(Long technicalTaskId) {
        List<Operation> operations = operationDAO.getByTechnicalTask(technicalTaskId);
        operations.forEach(operation ->
                operation.setRequestsForDevelopers(requestsForDevelopersDAO.getByOperation(operation.getId())));

        return operations;
    }
}
