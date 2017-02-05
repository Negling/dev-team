package ua.devteam.entity.users;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ua.devteam.entity.AbstractBuilder;
import ua.devteam.entity.Operation;
import ua.devteam.entity.enums.Role;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Main class that represents all-tier users of application. Mostly used on security domain to recognize
 * general information about current user, such as authority, username, password, etc.
 */
@Data
public class User implements UserDetails, Serializable {

    /* Unique long identification value */
    protected Long id;
    /* User first name in roman or cyrillic alphabet chars */
    protected String firstName;
    /* User last name in roman or cyrillic alphabet chars */
    protected String lastName;
    /* User email. In this domain area used as username */
    protected String email;
    /* User phone number. Unique value */
    protected String phoneNumber;
    /* User password. Can contain any type chars */
    protected String password;
    /* User role that defines access level */
    protected Role role;
    /* Set of that contain only one value that equal to role field value */
    private final Set<GrantedAuthority> authorities;

    public User() {
        authorities = new HashSet<>();
    }

    public void setRole(Role role) {
        this.role = role;
        authorities.clear();
        authorities.add(role);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public static class Builder extends AbstractBuilder<User> {
        public Builder() {
            super(new User());
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
