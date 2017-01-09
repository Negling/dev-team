package ua.devteam.dao;


import ua.devteam.entity.enums.CheckStatus;
import ua.devteam.entity.users.Check;

import java.util.List;

public interface CheckDAO extends GenericDAO<Check> {

    void create(Check entity);

    Check getByProject(Long projectId);

    List<Check> getAllByCustomer(Long customerId);

    List<Check> getNewByCustomer(Long customerId);

    List<Check> getCompleteByCustomer(Long customerId);
}
