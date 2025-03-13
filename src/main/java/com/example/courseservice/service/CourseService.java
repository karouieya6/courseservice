package com.example.courseservice.service;

import com.example.courseservice.model.Course;
import com.example.courseservice.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    /**
     * ✅ Create a New Course
     */
    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

    /**
     * ✅ Get All Courses
     */
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    /**
     * ✅ Get a Specific Course by ID
     */
    public Optional<Course> getCourseById(Long id) {
        return courseRepository.findById(id);
    }

    /**
     * ✅ Update a Course
     */
    public Course updateCourse(Long id, Course courseDetails) { // Added 'id' as a parameter
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found!"));

        course.setTitle(courseDetails.getTitle());
        course.setDescription(courseDetails.getDescription());
        course.setInstructorId(courseDetails.getInstructorId());
        course.setCategory(courseDetails.getCategory());
        course.setUpdatedAt(LocalDateTime.now()); // Ensure timestamp updates

        return courseRepository.save(course);
    }

    /**
     * ✅ Delete a Course
     */
    public void deleteCourse(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        courseRepository.delete(course);
    }
}
