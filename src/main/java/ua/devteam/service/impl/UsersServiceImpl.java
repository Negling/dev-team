package ua.devteam.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.devteam.dao.UsersDAO;
import ua.devteam.service.UsersService;

@Service("usersService")
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
    public boolean isAvailable(String email) {
        return !usersDAO.persists(email);
    }
}