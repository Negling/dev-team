package ua.devteam.dao;


import ua.devteam.entity.users.Check;

import java.util.List;

public interface CheckDAO extends GenericDAO<Check> {

    void create(Check entity);

    Check getByProject(Long projectId);

    List<Check> getByCustomer(Long customerId);
}
