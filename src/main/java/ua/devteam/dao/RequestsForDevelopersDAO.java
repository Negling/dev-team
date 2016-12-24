package ua.devteam.dao;

import ua.devteam.entity.tasks.RequestForDevelopers;

import java.util.List;

public interface RequestsForDevelopersDAO extends GenericDAO<RequestForDevelopers> {

    void create(RequestForDevelopers entity);

    List<RequestForDevelopers> getByOperation(Long taskId);
}
