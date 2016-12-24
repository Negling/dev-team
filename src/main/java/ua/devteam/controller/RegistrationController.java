package ua.devteam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.devteam.validation.formModels.CustomerRegistrationForm;

import javax.validation.Valid;

@Controller
@SessionAttributes("customerRegistrationForm")
public class RegistrationController {

    private Validator customerRegistrationFormValidator;

    @Autowired
    public void setCustomerRegistrationFormValidator(Validator customerRegistrationFormValidator) {
        this.customerRegistrationFormValidator = customerRegistrationFormValidator;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration() {
        System.out.println("show registration page");
        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registerCustomer(@Valid @ModelAttribute CustomerRegistrationForm customerRegistrationForm,
                                   BindingResult bindingResult, SessionStatus sessionStatus,
                                   RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.customerRegistrationForm",
                    bindingResult);
            System.out.println("notValid!");
            return "redirect:/registration";
        }
        System.out.println(customerRegistrationForm);
        // save customer
        sessionStatus.setComplete();
        return "redirect:/login?registered";
    }

    @ModelAttribute("customerRegistrationForm")
    public CustomerRegistrationForm getCustomerRegistrationForm() {
        System.out.println("adding form");
        return new CustomerRegistrationForm();
    }

    @InitBinder("customerRegistrationForm")
    public void dataBinding(WebDataBinder binder) {
        binder.addValidators(customerRegistrationFormValidator);
    }
}
