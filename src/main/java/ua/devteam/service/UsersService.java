package ua.devteam.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ua.devteam.entity.users.User;

/**
 * Provides service operations to {@link User User}.
 */
public interface UsersService extends UserDetailsService {

    /**
     * Returns true if there is no registered user with such email.
     */
    boolean isEmailAvailable(String email);

    /**
     * Returns true if there is no registered user with such phone number.
     */
    boolean isPhoneAvailable(String phoneNumber);
}
