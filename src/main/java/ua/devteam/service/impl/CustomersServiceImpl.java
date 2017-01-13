package ua.devteam.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.devteam.dao.CustomerDAO;
import ua.devteam.entity.users.Customer;
import ua.devteam.service.CustomersService;

@Service("customersService")
public class CustomersServiceImpl implements CustomersService {

    private CustomerDAO customerDAO;
    private PasswordEncoder encoder;

    @Autowired
    public CustomersServiceImpl(CustomerDAO customerDAO, PasswordEncoder encoder) {
        this.customerDAO = customerDAO;
        this.encoder = encoder;
    }

    @Override
    public Customer getById(Long customerId) {
        return customerDAO.getById(customerId);
    }

    @Override
    public long registerCustomer(Customer customer) {
        customer.setPassword(encoder.encode(customer.getPassword()));

        return customerDAO.create(customer);
    }
}
