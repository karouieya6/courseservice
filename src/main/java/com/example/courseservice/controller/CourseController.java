package com.example.courseservice.controller;

import com.example.courseservice.model.Course;
import com.example.courseservice.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;
    private static final Logger log = LoggerFactory.getLogger(CourseController.class);
    /**
     * ✅ Create a New Course (Only for INSTRUCTORS)
     */
    @PostMapping("/create")
    public ResponseEntity<?> createCourse(@RequestBody Course course) {
        try {
            // Directly set the instructorId in the course object
            if (course.getInstructorId() == 0) {
                return ResponseEntity.badRequest().body("Instructor ID is required.");
            }

            Course savedCourse = courseService.createCourse(course);
            return ResponseEntity.ok(savedCourse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating course: " + e.getMessage());
        }
    }




    /**
     * ✅ Get All Courses (Public) - Without ID
     */
    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getAllCourses() {
        List<Map<String, Object>> courses = courseService.getAllCourses()
                .stream()
                .map(course -> Map.of(
                        "title", (Object) course.getTitle(),  // Explicitly casting to Object
                        "description", (Object) course.getDescription(),
                        "category", (Object) course.getCategory(),
                        "instructorId", (Object) course.getInstructorId()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(courses);
    }



    /**
     * ✅ Get a Course by ID (Public) - Without ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getCourseById(@PathVariable Long id) {
        Optional<Course> courseOpt = courseService.getCourseById(id);
        if (courseOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Course course = courseOpt.get();
        return ResponseEntity.ok(Map.of(
                "title", course.getTitle(),
                "description", course.getDescription(),
                "category", course.getCategory(),
                "instructorId", course.getInstructorId()
        ));
    }

    /**
     * ✅ Update a Course (Only for INSTRUCTORS)
     */

    @PreAuthorize("hasRole('INSTRUCTOR')")
    @PutMapping("/{id}")
    public ResponseEntity<Course> updateCourse(@PathVariable Long id, @RequestBody Course course) {
        Course updatedCourse = courseService.updateCourse(id, course);
        return ResponseEntity.ok(updatedCourse);
    }



    /**
     * ✅ Delete a Course (Only for INSTRUCTORS)
     */

    @PreAuthorize("hasRole('INSTRUCTOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }

}
