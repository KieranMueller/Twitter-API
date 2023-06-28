package com.cooksys.group01.repositories;

import com.cooksys.group01.entities.User;
import com.cooksys.group01.entities.embeddable.Credentials;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByCredentialsUsernameAndCredentialsPasswordAndDeletedFalse(String username, String password);

    Optional<User> findByCredentialsUsernameAndDeletedFalse(String username);

    Optional<User> findByCredentialsUsernameAndDeletedTrue(String username);

    Optional<User> findOneByCredentials(Credentials credentials);

    Optional<User> findByCredentialsUsername(String username);

    List<User> findAllByDeletedFalse();
}
