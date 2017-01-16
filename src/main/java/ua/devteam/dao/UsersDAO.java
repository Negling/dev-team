package ua.devteam.dao;


import ua.devteam.entity.users.User;

public interface UsersDAO {

    User getUser(String email);

    boolean persistsByEmail(String email);

    boolean persistsByPhone(String phoneNumber);
}
