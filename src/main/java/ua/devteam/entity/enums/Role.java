package ua.devteam.entity.enums;

import org.springframework.security.core.GrantedAuthority;

/**
 * Represent granted to users authority. Used in Security area.
 */
public enum Role implements GrantedAuthority {
    CUSTOMER, DEVELOPER, MANAGER, ULTRAMANAGER, ADMIN;

    @Override
    public String getAuthority() {
        return this.toString();
    }
}
