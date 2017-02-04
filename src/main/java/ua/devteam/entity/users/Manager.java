package ua.devteam.entity.users;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ua.devteam.entity.AbstractBuilder;
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
            instance.setTotalProjectsServed(totalProjectsServed);
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
