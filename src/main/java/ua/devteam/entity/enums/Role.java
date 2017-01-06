package ua.devteam.entity.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    Customer(1, "cabinet"), Developer(2, "development"), Manager(3, "manage"), Ultramanager(4, "manage"), Admin(5, "manage");

    private int id;
    private String defaultViewName;

    Role(int id, String defaultViewName) {
        this.id = id;
        this.defaultViewName = defaultViewName;
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

    public String getDefaultViewName() {
        return defaultViewName;
    }

    public void setDefaultViewName(String defaultViewName) {
        this.defaultViewName = defaultViewName;
    }
}
