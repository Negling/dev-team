package ua.devteam.service;


import ua.devteam.entity.tasks.Operation;

import java.util.List;

public interface OperationsService {

    void registerOperation(Operation operation);

    void registerOperations(List<Operation> operations);

    List<Operation> getByTechnicalTask(Long technicalTaskId, boolean loadNested);
}
