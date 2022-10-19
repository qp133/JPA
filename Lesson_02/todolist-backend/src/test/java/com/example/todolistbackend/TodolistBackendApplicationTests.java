package com.example.todolistbackend;

import com.example.todolistbackend.entity.Todo;
import com.example.todolistbackend.repository.TodoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class TodolistBackendApplicationTests {

    @Autowired
    private TodoRepository todoRepository;

    @Test
    void save_todos() {
        Todo todo1 = Todo.builder().title("Làm bài tập").build();
        Todo todo2 = Todo.builder().title("ĐI chơi").status(true).build();
        Todo todo3 = Todo.builder().title("Đá bóng").build();

        todoRepository.saveAll(List.of(todo1, todo2, todo3));
    }

}
