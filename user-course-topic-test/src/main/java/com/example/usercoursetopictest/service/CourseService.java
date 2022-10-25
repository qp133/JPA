package com.example.usercoursetopictest.service;

import com.example.usercoursetopictest.entity.Course;
import com.example.usercoursetopictest.entity.Topic;
import com.example.usercoursetopictest.exception.BadRequestException;
import com.example.usercoursetopictest.exception.NotFoundException;
import com.example.usercoursetopictest.repository.CourseRepository;
import com.example.usercoursetopictest.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private TopicRepository topicRepository;

    public List<Course> getCourses() {
        List<Course> courses = courseRepository.findAll();
        return courses;
    }

    public List<Course> getCoursesByType(String type) {
        List<Course> courses = courseRepository.findByType(type);
        if (courses.isEmpty()) {
            throw new NotFoundException("Not found course with type = " + type);
        }
        return courses;
    }

    public List<Course> getCoursesByNameAndTopic(String name, String topic) {
        if (topic == null && name != null) {
            List<Course> courses=courseRepository.findByNameContains(name);
            if (courses.isEmpty()) {
                throw new NotFoundException("Not found course with name: " + name);
            }
            return courses;
        }
        if (name == null && topic != null) {
            List<Topic> topics = topicRepository.findByNameContains(topic);
            List<Course> courses=new ArrayList<>();
            if (topics.isEmpty()){
                throw new NotFoundException("Not found topic: " + topic);
            }
            for (Topic t :topics){
                courses.addAll(courseRepository.findByTopics_Id(t.getId()));
            }
            if (courses.isEmpty()){
                throw new BadRequestException("Not found course with topic: "+topic);
            }
            return courses;
        }
        List<Course> courses = courseRepository.findByNameContains(name);
        List<Topic> topic1 = topicRepository.findByNameContains(topic);
        List<Course> rs = new ArrayList<>();
        for (Course course : courses) {
            if (course.getTopics().contains(topic1)) ;
            rs.add(course);
        }
        if (rs.isEmpty()){
            throw new NotFoundException("Not found course with name = '"+name+"' and topic = '"+topic+"'");
        }
        return rs;
    }

    public List<Course> getCoursesById(Integer id) {
        Optional<Course> coursesOptional = courseRepository.findById(id);
        if (coursesOptional.isPresent()) {
            return coursesOptional.stream().toList();
        }
        throw new NotFoundException("Not found course with id = " + id);
    }


}
