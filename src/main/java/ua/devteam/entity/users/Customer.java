package ua.devteam.entity.users;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ua.devteam.entity.AbstractBuilder;
import ua.devteam.entity.enums.Role;
import ua.devteam.entity.projects.Check;

import java.io.Serializable;
import java.util.List;

/**
 * User subclass. {@link User}
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@ToString(callSuper = true)
public class Customer extends User implements Serializable {

    /* User checks history */
    private List<Check> checks;

    public static class Builder extends AbstractBuilder<Customer> {
        public Builder() {
            super(new Customer());
        }

        public Builder setChecks(List<Check> checks) {
            instance.setChecks(checks);
            return this;
        }

        public Builder setEmail(String email) {
            instance.setEmail(email);
            return this;
        }

        public Builder setFirstName(String firstName) {
            instance.setFirstName(firstName);
            return this;
        }

        public Builder setId(Long id) {
            instance.setId(id);
            return this;
        }

        public Builder setLastName(String lastName) {
            instance.setLastName(lastName);
            return this;
        }

        public Builder setPassword(String password) {
            instance.setPassword(password);
            return this;
        }

        public Builder setPhoneNumber(String phoneNumber) {
            instance.setPhoneNumber(phoneNumber);
            return this;
        }

        public Builder setRole(Role role) {
            instance.setRole(role);
            return this;
        }
    }
}
