package ua.devteam.dao;

import ua.devteam.entity.users.Customer;

public interface CustomerDAO extends GenericDAO<Customer>, Identified<Customer> {

    Customer getById(Long id);

    Customer getByEmail(String email);
}