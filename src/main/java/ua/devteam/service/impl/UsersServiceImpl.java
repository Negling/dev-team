package ua.devteam.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ua.devteam.dao.UsersDAO;
import ua.devteam.entity.users.User;
import ua.devteam.service.UsersService;

/**
 * Provides service operations to {@link User User}.
 */
@Service
@Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true)
public class UsersServiceImpl implements UsersService {

    private UsersDAO usersDAO;

    @Autowired
    public UsersServiceImpl(UsersDAO usersDAO) {
        this.usersDAO = usersDAO;
    }

    /**
     * Returns user with email that match requested.
     *
     * @param email as username param
     * @return User instance
     * @throws UsernameNotFoundException if no user found
     */
    @Override
    public User loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            return usersDAO.getUser(email);
        } catch (EmptyResultDataAccessException ex) {
            throw new UsernameNotFoundException("User not found!");
        }
    }

    /**
     * Returns true if there is no registered user with such phone number.
     */
    @Override
    public boolean isPhoneAvailable(String phoneNumber) {
        return !usersDAO.persistsByPhone(phoneNumber);
    }

    /**
     * Returns true if there is no registered user with such email.
     */
    @Override
    public boolean isEmailAvailable(String email) {
        return !usersDAO.persistsByEmail(email);
    }
}
