package ua.devteam.entity.forms;

import ua.devteam.entity.enums.Role;
import ua.devteam.entity.users.Customer;

import java.io.Serializable;

import static ua.devteam.entity.enums.Role.Customer;

/**
 * Form used to map all customer data for following registration.
 */
public class CustomerRegistrationForm extends AbstractRegistrationForm<Customer> implements Serializable {
    private String confirmedEmail;
    private String confirmedPhoneNumber;
    private Role role = Customer;

    public String getConfirmedEmail() {
        return confirmedEmail;
    }

    public String getConfirmedPhoneNumber() {
        return confirmedPhoneNumber;
    }

    public void setConfirmedPhoneNumber(String confirmedPhoneNumber) {
        this.confirmedPhoneNumber = confirmedPhoneNumber;
    }

    public void setConfirmedEmail(String confirmedEmail) {
        this.confirmedEmail = confirmedEmail;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public Customer getEntity() {
        return new Customer(getFirstName(), getLastName(), getEmail(), getPhoneNumber(), getPassword(), role);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CustomerRegistrationForm)) return false;
        if (!super.equals(o)) return false;

        CustomerRegistrationForm that = (CustomerRegistrationForm) o;

        if (confirmedEmail != null ? !confirmedEmail.equals(that.confirmedEmail) : that.confirmedEmail != null)
            return false;
        if (confirmedPhoneNumber != null ? !confirmedPhoneNumber.equals(that.confirmedPhoneNumber) : that.confirmedPhoneNumber != null)
            return false;

        return role == that.role;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (confirmedEmail != null ? confirmedEmail.hashCode() : 0);
        result = 31 * result + (confirmedPhoneNumber != null ? confirmedPhoneNumber.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);

        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CustomerRegistrationForm{");
        sb.append("firstName='").append(getFirstName()).append('\'');
        sb.append(", lastName='").append(getLastName()).append('\'');
        sb.append(", email='").append(getEmail()).append('\'');
        sb.append(", confirmedEmail='").append(getConfirmedEmail()).append('\'');
        sb.append(", phoneNumber='").append(getPhoneNumber()).append('\'');
        sb.append(", confirmedPhoneNumber='").append(getConfirmedPhoneNumber()).append('\'');
        sb.append(", password='").append(getPassword()).append('\'');
        sb.append(", confirmedPassword='").append(getConfirmedPassword()).append('\'');
        sb.append(", role=").append(role);
        sb.append('}');
        return sb.toString();
    }
}
