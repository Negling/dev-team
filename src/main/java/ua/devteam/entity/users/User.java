package ua.devteam.entity.users;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
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

    public User(Long id, String firstName, String lastName, String email, String phoneNumber, String password, Role role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.role = role;
        this.authorities = new HashSet<GrantedAuthority>() {{
            add(role);
        }};
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
}
