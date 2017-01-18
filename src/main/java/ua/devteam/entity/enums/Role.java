package ua.devteam.entity.enums;

import org.springframework.security.core.GrantedAuthority;

/**
 * Represent granted to users authority. Used in Security area.
 * Role id equals to role_id mapping in repository.
 */
public enum Role implements GrantedAuthority {
    Customer(1), Developer(2), Manager(3), Ultramanager(4), Admin(5);

    private int id;

    Role(int id) {
        this.id = id;
    }

    @Override
    public String getAuthority() {
        return this.toString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
