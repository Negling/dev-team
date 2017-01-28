package ua.devteam.controllers;

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

/**
 * This controller process customer registration procedure.
 */
@Controller
@SessionAttributes("customerRegistrationForm")
public class RegistrationController {

    private CustomersService customersService;

    @Autowired
    public RegistrationController(CustomersService customersService) {
        this.customersService = customersService;
    }

    /**
     * Returns registration page.
     */
    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    /**
     * Validates registration form, and if validation successful - delegates customer registration to service,
     * otherwise redirects back with binding errors.
     */
    @PostMapping("/registration")
    public String registerCustomer(@Valid @ModelAttribute CustomerRegistrationForm customerRegistrationForm,
                                   BindingResult bindingResult, SessionStatus sessionStatus,
                                   RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.customerRegistrationForm",
                    bindingResult);
            System.out.println(bindingResult);
            return "redirect:/registration";
        }
        // save customer
        customersService.registerCustomer(customerRegistrationForm.getEntity());
        sessionStatus.setComplete();

        return "redirect:/login?registered";
    }

    @ModelAttribute("customerRegistrationForm")
    public CustomerRegistrationForm getCustomerRegistrationForm() {
        return new CustomerRegistrationForm();
    }
}
