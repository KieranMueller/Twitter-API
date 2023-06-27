package com.cooksys.group01.repositories;

import com.cooksys.group01.entities.User;
import com.cooksys.group01.entities.embeddable.Credentials;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByCredentialsUsernameAndDeletedFalse(String username);

    Optional<User> findByCredentialsUsernameAndDeletedTrue(String username);

    Optional<User> findOneByCredentials(Credentials credentials);

    Optional<User> findByCredentialsUsername(String username);
}
