package ua.devteam.entity.users;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ua.devteam.entity.AbstractBuilder;
import ua.devteam.entity.Operation;
import ua.devteam.entity.enums.Role;

import java.io.Serializable;

/**
 * User subclass. As additional field contains counter of total served projects.
 * {@link User}
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@ToString(callSuper = true)
public class Manager extends User implements Serializable {

    /* Total amount of projects that this manager served */
    private Long totalProjectsServed;

    public static class Builder extends AbstractBuilder<Manager> {
        public Builder() {
            super(new Manager());
        }

        public Builder setTotalProjectsServed(Long totalProjectsServed) {
            return perform(() -> getConstruction().setTotalProjectsServed(totalProjectsServed));
        }

        public Builder setEmail(String email) {
            return perform(() -> getConstruction().setEmail(email));
        }

        public Builder setFirstName(String firstName) {
            return perform(() -> getConstruction().setFirstName(firstName));
        }

        public Builder setId(Long id) {
            return perform(() -> getConstruction().setId(id));
        }

        public Builder setLastName(String lastName) {
            return perform(() -> getConstruction().setLastName(lastName));
        }

        public Builder setPassword(String password) {
            return perform(() -> getConstruction().setPassword(password));
        }

        public Builder setPhoneNumber(String phoneNumber) {
            return perform(() -> getConstruction().setPhoneNumber(phoneNumber));
        }

        public Builder setRole(Role role) {
            return perform(() -> getConstruction().setRole(role));
        }

        @Override
        protected Builder perform(Operation operation) {
            operation.perform();
            return this;
        }
    }
}
