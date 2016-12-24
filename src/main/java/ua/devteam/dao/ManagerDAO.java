package ua.devteam.dao;

import ua.devteam.entity.users.Manager;

public interface ManagerDAO extends GenericDAO<Manager>, Identified<Manager> {

    Manager getByEmail(String email);
}
