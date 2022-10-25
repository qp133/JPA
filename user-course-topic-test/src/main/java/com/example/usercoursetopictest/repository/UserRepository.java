package com.example.usercoursetopictest.repository;

import com.example.usercoursetopictest.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}