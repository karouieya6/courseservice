package com.example.courseservice.repository;

import com.example.courseservice.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
    // Custom queries can be added here if needed
}
