package com.example.usercoursetopictest.controller;

import com.example.usercoursetopictest.entity.Course;
import com.example.usercoursetopictest.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @GetMapping("/courses")
    public List<Course> getCourses() {
        return courseService.getCourses();
    }

    @GetMapping("/courses/online")
    public List<Course> getOnlineCourses() {
        return courseService.getCoursesByType("online");
    }

    @GetMapping("/courses/onlab")
    public List<Course> getOnlabCourses() {
        return courseService.getCoursesByType("onlab");
    }

    @GetMapping("/courses/search")
    public List<Course> getCoursesByNameAndTopic(@RequestParam(required = false) String name
                                                ,@RequestParam(required = false) String topic){
        return courseService.getCoursesByNameAndTopic(name,topic);
    }

    @GetMapping("/courses/{id}")
    public ResponseEntity<?> getCoursesById(@PathVariable Integer id) {
        return ResponseEntity.ok(courseService.getCoursesById(id));
    }
}
