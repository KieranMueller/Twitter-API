package com.cooksys.group01.repositories;

import com.cooksys.group01.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByCredentialsUsernameAndDeletedFalse(String username);

    Optional<User> findByCredentialsUsernameAndDeletedTrue(String username);
}
