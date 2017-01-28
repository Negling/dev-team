package ua.devteam.service;

import ua.devteam.entity.users.Customer;

/**
 * Provides service operations to {@link Customer customer}.
 */
public interface CustomersService {

    /**
     * Registers customer entity, and returns its ID.
     *
     * @param customer to be registered
     * @return generated customer ID
     */
    long registerCustomer(Customer customer);

    /**
     * Returns customer instance which id match to requested.
     *
     * @param customerId query param
     * @return Customer instance
     */
    Customer getById(Long customerId);
}
