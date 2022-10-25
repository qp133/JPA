package com.example.usercoursetopictest;

import com.example.usercoursetopictest.entity.Course;
import com.example.usercoursetopictest.entity.Topic;
import com.example.usercoursetopictest.entity.User;
import com.example.usercoursetopictest.repository.CourseRepository;
import com.example.usercoursetopictest.repository.TopicRepository;
import com.example.usercoursetopictest.repository.UserRepository;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SpringBootTest
class UserCourseTopicTestApplicationTests {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Test
    void save_user() {
        User user1 = User.builder()
                .name("Nguyễn Văn A")
                .email("a@gmail.com")
                .phone("123456789")
                .build();

        User user2 = User.builder()
                .name("Trần Ngọc B")
                .email("b@gmail.com")
                .phone("456789123")
                .build();

        User user3 = User.builder()
                .name("Lê Thị C")
                .email("c@gmail.com")
                .phone("987654321")
                .build();

        userRepository.saveAll(List.of(user1, user2, user3));
    }

    @Test
    void save_course(){
        Faker faker = new Faker();

        List<User> list = userRepository.findAll();
        List<Topic> topicList = topicRepository.findAll();

        Random rd = new Random();

        for (int i = 0; i < 10; i++) {
            User rdUser = list.get(rd.nextInt(list.size()));

            List<Topic> myList = new ArrayList<>();
            for (int j = 0; j < 3; j++) {
                Topic rdTopic = topicList.get(rd.nextInt(topicList.size()));
                if(!myList.contains(rdTopic)){
                    myList.add(rdTopic);
                }
            }
            Course course = Course.builder()
                    .name(faker.name().fullName())
                    .description(faker.lorem().sentence(20))
                    .type(rd.nextInt(2) == 0 ? "Online" : "Onlab")
                    .user(rdUser)
                    .topics(myList)
                    .build();
            courseRepository.save(course);
        }

    }

    @Test
    void save_topic() {
        Topic topic1 = Topic.builder()
                .name("Frontend")
                .build();

        Topic topic2 = Topic.builder()
                .name("Backend")
                .build();

        Topic topic3 = Topic.builder()
                .name("Full Stack")
                .build();

        topicRepository.saveAll(List.of(topic1, topic2, topic3));
    }
}
