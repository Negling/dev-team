package ua.devteam.controllers;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.devteam.entity.forms.CustomerRegistrationForm;
import ua.devteam.service.CustomersService;

import javax.validation.Valid;
import java.util.ResourceBundle;

/**
 * This controller process customer registration procedure.
 */
@Controller
@SessionAttributes("customerRegistrationForm")
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class RegistrationController {
    private CustomersService customersService;
    private ResourceBundle pagesProperties;

    /**
     * Returns registration page.
     */
    @GetMapping("${registration.page}")
    public String registration() {
        return pagesProperties.getString("registration.page");
    }

    /**
     * Validates registration form, and if validation successful - delegates customer registration to service,
     * otherwise redirects back with binding errors.
     */
    @PostMapping("${registration.action}")
    public String registerCustomer(@Valid @ModelAttribute CustomerRegistrationForm customerRegistrationForm,
                                   BindingResult bindingResult, SessionStatus sessionStatus,
                                   RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute(pagesProperties.getString("model.customerRegistrationErrors"),
                    bindingResult);
            return pagesProperties.getString("registration.page.redirect");
        }
        // save customer
        customersService.registerCustomer(customerRegistrationForm.getEntity());
        sessionStatus.setComplete();

        return pagesProperties.getString("login.page.registered");
    }

    @ModelAttribute("customerRegistrationForm")
    public CustomerRegistrationForm getCustomerRegistrationForm() {
        return new CustomerRegistrationForm();
    }
}
