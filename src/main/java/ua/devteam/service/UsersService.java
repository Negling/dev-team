package ua.devteam.service;

public interface UsersService {

    boolean isEmailAvailable(String email);

    boolean isPhoneAvailable(String phoneNumber);
}
