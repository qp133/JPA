package com.example.demo;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.github.javafaker.Faker;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;


@SpringBootTest
//Sử dụng DataJpaTest nếu máy có cấu hình yếu
//    @DataJpaTest
//    @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DemoApplicationTests {
    @Autowired
    private UserRepository userRepository;

    @Test
    void save_user() {
        Faker faker = new Faker();
        for(int i = 0; i < 50; i++) {
            User user = User.builder()
                    .name(faker.name().fullName())
                    .email(faker.internet().emailAddress())
                    .age(faker.number().numberBetween(20,70))
                    .build();
            userRepository.save(user);
        }
    }

    @Test
    void get_all_user() {
        List<User> user = userRepository.findAll();
        System.out.println(user.size());
    }

    @Test
    void get_user_by_id() {
        Optional<User> userOptional = userRepository.findById(2);
        userOptional.ifPresent(System.out::println);
    }

    @Test
    void delete_user_by_id() {
        userRepository.deleteById(1);
    }

    @Test
    void test_getByNameContainsIgnoreCase() {
        List<User> users = userRepository.getByNameContainsIgnoreCase("Grimes");
        users.forEach(System.out::println);

        Assertions.assertThat(users).isNotNull();
        Assertions.assertThat(users.size()).isEqualTo(1);
    }

    @Test
    void test_updateUserName() {
        userRepository.updateUserName(2, "Quang");
    }

    @Test
    void test_updateUserName_Other() {
        User user = userRepository.getUserById(3);
        user.setName("Nguyễn Văn A");
        userRepository.save(user);
    }

    @Test
    void test_getByOrderByAgeAsc() {
        List<User> users = userRepository.getByOrderByAgeAsc();
        users.forEach(System.out::println);
    }

    @Test
    void test_getListUserOrderByNameAsc() {
        List<User> users = userRepository.getListUserOrderByNameAsc();
        users.forEach(System.out::println);
    }

    @Test
    void test_getAllSort() {
        List<User> users = userRepository.findAll(Sort.by("age").ascending());
        users.forEach(System.out::println);
    }

    @Test
    void test_pagination(){
        Page<User> page = userRepository.findAll(PageRequest.of(0,10));
        System.out.println(page.getContent());
    }
}
