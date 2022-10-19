package com.example.todolistbackend.repository;

import com.example.todolistbackend.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Integer> {
    List<Todo> getTodosByStatus(Boolean status);

    @Modifying
    @Query("update Todo t set t.title = ?1, t.status = ?2 where t.id = ?3")
    void updateTodo(String title, Boolean status, Integer id);
}