package ua.devteam.service;


import ua.devteam.entity.users.Check;

import java.util.List;

public interface ChecksService {

    void accept(Long projectId);

    void decline(Long projectId);

    void registerCheck(Check check);

    List<Check> getNewByCustomer(Long customerId);

    List<Check> getCompleteByCustomer(Long customerId);
}
