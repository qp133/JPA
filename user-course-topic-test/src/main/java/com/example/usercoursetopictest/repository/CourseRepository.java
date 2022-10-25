package com.example.usercoursetopictest.repository;

import com.example.usercoursetopictest.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Integer> {
    List<Course> findByType(String type);

    List<Course> findByNameContains(String name);

    List<Course> findByTopics_Id(Integer id);
}