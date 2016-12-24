package ua.devteam.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.devteam.dao.CustomerDAO;
import ua.devteam.entity.users.Customer;
import ua.devteam.service.CustomersService;

@Service("customersService")
public class CustomersServiceImpl implements CustomersService {

    private CustomerDAO customerDAO;

    @Autowired
    public CustomersServiceImpl(CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }

    @Override
    public long registerCustomer(Customer customer) {
            return customerDAO.create(customer);
    }
}
