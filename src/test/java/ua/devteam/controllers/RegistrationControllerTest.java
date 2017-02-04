package ua.devteam.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.Validator;
import ua.devteam.entity.forms.CustomerRegistrationForm;
import ua.devteam.service.CustomersService;
import ua.devteam.service.impl.UsersServiceImpl;
import ua.devteam.validation.formValidators.CustomerRegistrationFormValidator;

import java.util.ResourceBundle;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ua.devteam.controllers.WebTestUtils.*;

@RunWith(JUnit4.class)
public class RegistrationControllerTest {

    private CustomersService customersService = mock(CustomersService.class);
    private RegistrationController controller = new RegistrationController(customersService, getPagesBundle());
    private MockMvc mockMvc;

    // setup
    {
        UsersServiceImpl usersService = mock(UsersServiceImpl.class);
        Validator validator = new CustomerRegistrationFormValidator(usersService, ResourceBundle.getBundle("properties/validation"));

        mockMvc = getConfiguredWithPlaceholdersStandaloneMockMvcBuilder(controller)
                .setViewResolvers(getDefaultViewResolver())
                .setValidator(validator).build();

        // default mocks behavior
        when(usersService.isEmailAvailable(anyString())).thenReturn(true);
        when(usersService.isPhoneAvailable(anyString())).thenReturn(true);
    }

    @Test
    public void getRegistrationPageTest() throws Exception {
        mockMvc.perform(get("/registration"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("customerRegistrationForm"))
                .andExpect(view().name("registration"));
    }

    @Test
    public void postRegistrationFormFailTest() throws Exception {
        // invalid form
        CustomerRegistrationForm form = new CustomerRegistrationForm();

        mockMvc.perform(post("/registration").sessionAttr("customerRegistrationForm", form))
                .andExpect(status().isFound())
                .andExpect(flash().attributeExists("org.springframework.validation.BindingResult.customerRegistrationForm"))
                .andExpect(redirectedUrl("/registration"));
    }

    @Test
    public void postRegistrationFormSuccessTest() throws Exception {
        // valid customer
        CustomerRegistrationForm form = new CustomerRegistrationForm("Joe", "Sea", "teat@mail.com", "teat@mail.com",
                "+380965433192", "+380965433192", "password", "password");

        mockMvc.perform(post("/registration").sessionAttr("customerRegistrationForm", form))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/login?registered"));

        verify(customersService, only()).registerCustomer(form.getEntity());
    }
}
