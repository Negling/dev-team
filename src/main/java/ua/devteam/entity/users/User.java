package ua.devteam.entity.users;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ua.devteam.entity.AbstractBuilder;
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
