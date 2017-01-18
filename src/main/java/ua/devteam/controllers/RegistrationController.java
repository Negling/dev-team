package ua.devteam.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.devteam.entity.forms.CustomerRegistrationForm;
import ua.devteam.service.CustomersService;

import javax.validation.Valid;

@Controller
@SessionAttributes("customerRegistrationForm")
public class RegistrationController {

    private CustomersService customersService;

    @Autowired
    public RegistrationController(CustomersService customersService) {
        this.customersService = customersService;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration() {
        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registerCustomer(@Valid @ModelAttribute CustomerRegistrationForm customerRegistrationForm,
                                   BindingResult bindingResult, SessionStatus sessionStatus,
                                   RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.customerRegistrationForm",
                    bindingResult);
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
