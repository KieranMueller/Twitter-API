package com.cooksys.group01.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cooksys.group01.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
