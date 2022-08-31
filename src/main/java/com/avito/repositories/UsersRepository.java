package com.avito.repositories;

import com.avito.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends CrudRepository<User, String> {
    Optional<User> findByUsername(String username);
    Optional<User> findById(String id);
}
