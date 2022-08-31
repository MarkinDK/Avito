package com.avito.services;

import com.avito.exceptions.ElementAlreadyExists;
import com.avito.model.User;
import com.avito.repositories.UsersRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Optional;

@Service
public class UsersService {
    private final UsersRepository usersRepository;

    public UsersService(UsersRepository repository) {
        this.usersRepository = repository;
    }

    public User saveUser(User user) {
        user.initializeId();
        user.initializeCreatedAt();
        try {
            return usersRepository.save(user);
        } catch (DataIntegrityViolationException exception) {
            if (exception
                    .getMostSpecificCause()
                    .getClass()
                    .getName()
                    .equals("org.postgresql.util.PSQLException") &&
                    ((SQLException) exception.getMostSpecificCause())
                            .getSQLState()
                            .equals("23505")) {
                throw new ElementAlreadyExists("Пользователь " + user.getUsername() + " уже существует.");
            }
            throw exception;
        }
    }

    public Optional<User> getUser(String username) {
        return usersRepository.findByUsername(username);
    }

    public Iterable<User> getAllUsers() {
        return usersRepository.findAll();
    }
}