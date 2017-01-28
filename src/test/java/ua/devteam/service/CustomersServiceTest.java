package ua.devteam.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.security.crypto.password.PasswordEncoder;
import ua.devteam.dao.CustomerDAO;
import ua.devteam.entity.users.Customer;
import ua.devteam.service.impl.CustomersServiceImpl;

import static org.mockito.Mockito.*;
import static ua.devteam.EntityUtils.getValidCustomer;

@RunWith(JUnit4.class)
public class CustomersServiceTest {
    private CustomerDAO customerDAO = mock(CustomerDAO.class);
    private PasswordEncoder encoder = mock(PasswordEncoder.class);
    private CustomersService service = new CustomersServiceImpl(customerDAO, encoder);

    @Test
    public void registerCustomerTest() {
        Customer customer = getValidCustomer();

        service.registerCustomer(customer);

        verify(encoder, times(1)).encode(any());
        verify(customerDAO, times(1)).create(customer);
    }
}
