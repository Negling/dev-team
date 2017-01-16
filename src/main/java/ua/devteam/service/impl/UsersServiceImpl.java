package ua.devteam.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ua.devteam.dao.UsersDAO;
import ua.devteam.service.UsersService;

@Service("usersService")
@Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true)
public class UsersServiceImpl implements UserDetailsService, UsersService {

    private UsersDAO usersDAO;

    @Autowired
    public UsersServiceImpl(UsersDAO usersDAO) {
        this.usersDAO = usersDAO;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            return usersDAO.getUser(email);
        } catch (EmptyResultDataAccessException ex) {
            throw new UsernameNotFoundException("User not found!");
        }
    }

    @Override
    public boolean isPhoneAvailable(String phoneNumber) {
        return !usersDAO.persistsByPhone(phoneNumber);
    }

    @Override
    public boolean isEmailAvailable(String email) {
        return !usersDAO.persistsByEmail(email);
    }
}
