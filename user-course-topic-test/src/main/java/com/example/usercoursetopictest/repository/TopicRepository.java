package com.example.usercoursetopictest.repository;

import com.example.usercoursetopictest.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TopicRepository extends JpaRepository<Topic, Integer> {
    List<Topic> findByNameContains(String name);
}