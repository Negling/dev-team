package ua.devteam.entity.forms;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ua.devteam.entity.enums.Role;
import ua.devteam.entity.users.Customer;

import java.io.Serializable;

import static ua.devteam.entity.enums.Role.CUSTOMER;

/**
 * Form used to map all customer data for following registration.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@ToString(callSuper = true)
public class CustomerRegistrationForm extends AbstractUserForm<Customer> implements Serializable {
    private final Role role = CUSTOMER;
    private String confirmedEmail;
    private String confirmedPhoneNumber;

    public CustomerRegistrationForm(String firstName, String lastName, String email, String confirmedEmail,
                                    String phoneNumber, String confirmedPhoneNumber, String password, String confirmedPassword) {

        super(firstName, lastName, email, phoneNumber, password, confirmedPassword);
        this.confirmedEmail = confirmedEmail;
        this.confirmedPhoneNumber = confirmedPhoneNumber;
    }

    @Override
    public Customer getEntity() {
        return new
                Customer.Builder()
                .setFirstName(getFirstName())
                .setLastName(getLastName())
                .setEmail(getEmail())
                .setPhoneNumber(getPhoneNumber())
                .setPassword(getPassword())
                .setRole(role)
                .build();
    }
}
