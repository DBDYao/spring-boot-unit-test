package com.example.spring_unit_test.controller;

import com.example.spring_unit_test.model.Course;
import com.example.spring_unit_test.model.StudentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
public class StudentController {

    @Autowired
    private StudentDao studentDao;

    @GetMapping("/students/{studentID}/courses/{courseID}")
    public Course retrieveDetailsForCourse(@PathVariable String studentId, @PathVariable String courseId){
        return studentDao.retrieveCourse(studentId, courseId);
    }

    @PostMapping("/students/{studentId}/couses")
    public ResponseEntity<Void> registerStudentForCourse(@PathVariable String studentId, @RequestBody Course newCourse){

        Course  course = studentDao.addCourse(studentId, newCourse);

        if(course == null){
            return ResponseEntity.noContent().build();
        }

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(course.getId()).toUri();

        return ResponseEntity.created(location).build();
    }
}
