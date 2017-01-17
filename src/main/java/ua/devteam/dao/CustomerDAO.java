package ua.devteam.dao;

import ua.devteam.entity.users.Customer;

/**
 * Simple interface to access {@link Customer} records.
 * {@link ua.devteam.dao.jdbc.JDBCCustomerDAO} - implementation.
 */
public interface CustomerDAO extends GenericDAO<Customer>, Identified<Customer> {

    /**
     * Returns customer entity which match to specified email.
     *
     * @param email query param
     * @return {@link Customer} entity
     * @throws org.springframework.dao.EmptyResultDataAccessException - if there no entity with such ID.
     */
    Customer getByEmail(String email);
}