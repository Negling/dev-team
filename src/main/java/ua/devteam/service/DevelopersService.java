package ua.devteam.service;

import ua.devteam.entity.users.Developer;
import ua.devteam.entity.enums.DeveloperRank;
import ua.devteam.entity.enums.DeveloperSpecialization;

import java.util.List;

public interface DevelopersService {

    List<Developer> getAvailableDevelopers(DeveloperSpecialization specialization, DeveloperRank rank, String lastName);
}
