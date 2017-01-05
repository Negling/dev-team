package ua.devteam.dao;

import ua.devteam.entity.tasks.Operation;

import java.util.List;

public interface OperationDAO extends GenericDAO<Operation>, Identified<Operation> {

    Operation getById(Long id);

    List<Operation> getByTechnicalTask(Long technicalTaskId);
}
