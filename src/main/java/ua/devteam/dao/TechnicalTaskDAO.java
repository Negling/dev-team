package ua.devteam.dao;

import ua.devteam.entity.projects.TechnicalTask;

import java.util.List;

public interface TechnicalTaskDAO extends GenericDAO<TechnicalTask>, Identified<TechnicalTask> {

    TechnicalTask getById(Long id);

    List<TechnicalTask> getAll();

    List<TechnicalTask> getByManager(Long managerId);

}
