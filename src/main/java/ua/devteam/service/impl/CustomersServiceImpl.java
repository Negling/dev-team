package ua.devteam.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ua.devteam.dao.CustomerDAO;
import ua.devteam.entity.users.Customer;
import ua.devteam.service.CustomersService;

/**
 * Provides service operations to {@link Customer customer}.
 */
@Service
@Transactional(isolation = Isolation.READ_COMMITTED)
public class CustomersServiceImpl implements CustomersService {

    private CustomerDAO customerDAO;
    private PasswordEncoder encoder;

    @Autowired
    public CustomersServiceImpl(CustomerDAO customerDAO, PasswordEncoder encoder) {
        this.customerDAO = customerDAO;
        this.encoder = encoder;
    }

    /**
     * Registers customer entity, and returns its ID.
     *
     * @param customer to be registered
     * @return generated customer ID
     */
    @Override
    public long registerCustomer(Customer customer) {
        customer.setPassword(encoder.encode(customer.getPassword()));

        return customerDAO.create(customer);
    }

    /**
     * Returns customer instance which id match to requested.
     *
     * @param customerId query param
     * @return Customer instance
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true)
    public Customer getById(Long customerId) {
        return customerDAO.getById(customerId);
    }
}
