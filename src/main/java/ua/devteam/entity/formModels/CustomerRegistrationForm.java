package ua.devteam.entity.formModels;

import ua.devteam.entity.enums.Role;
import ua.devteam.entity.users.Customer;

import java.io.Serializable;

import static ua.devteam.entity.enums.Role.Customer;

public class CustomerRegistrationForm extends AbstractRegistrationForm<Customer> implements Serializable{
    private Role role = Customer;

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

        return role == that.role;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
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
