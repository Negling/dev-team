package ua.devteam.dao;


import ua.devteam.entity.enums.DeveloperRank;
import ua.devteam.entity.enums.DeveloperSpecialization;
import ua.devteam.entity.users.Developer;

import java.util.List;

public interface DeveloperDAO extends GenericDAO<Developer>, Identified<Developer> {

    Developer getById(Long id);

    Developer getByEmail(String email);

    List<Developer> getByParams(DeveloperSpecialization specialization, DeveloperRank rank);

    List<Developer> getByParams(DeveloperSpecialization specialization, DeveloperRank rank, String lastName);
}
