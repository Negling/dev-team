package ua.devteam.dao;


import ua.devteam.entity.users.User;

public interface UsersDAO {

    User getUser(String email);

    boolean persists(String email);

    boolean persistsByPhone(String phoneNumber);
}
