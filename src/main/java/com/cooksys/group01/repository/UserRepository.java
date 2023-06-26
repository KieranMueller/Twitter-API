package com.cooksys.group01.repository;

import com.cooksys.group01.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
