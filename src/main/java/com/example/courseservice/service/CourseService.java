package com.example.courseservice.service;

import com.example.courseservice.CourseSpecification;
import com.example.courseservice.dto.CourseCreateRequest;
import com.example.courseservice.dto.CourseResponse;
import com.example.courseservice.dto.CourseUpdateRequest;
import com.example.courseservice.model.Category;
import com.example.courseservice.model.Course;
import com.example.courseservice.repository.CategoryRepository;
import com.example.courseservice.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CategoryRepository categoryRepository;

    private final CourseRepository courseRepository;

    public CourseResponse createCourse(CourseCreateRequest request) {
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Course course = new Course();
        course.setTitle(request.getTitle());
        course.setDescription(request.getDescription());
        course.setInstructorId(request.getInstructorId());
        course.setCategory(category);

        Course saved = courseRepository.save(course);
        return mapToResponse(saved); // Make sure this method exists
    }
    private CourseResponse mapToResponse(Course course) {
        CourseResponse response = new CourseResponse();
        response.setId(course.getId());
        response.setTitle(course.getTitle());
        response.setDescription(course.getDescription());
        response.setInstructorId(course.getInstructorId());

        // 🛠 Check for null before accessing category
        if (course.getCategory() != null) {
            response.setCategoryId(course.getCategory().getId());
            response.setCategoryName(course.getCategory().getName());
        } else {
            response.setCategoryId(null);
            response.setCategoryName("Unknown"); // or leave it null
        }

        return response;
    }




    public List<CourseResponse> getAllCourses() {
        return courseRepository.findAll().stream()
                .map(this::mapToResponse)
                .toList();
    }


    public CourseResponse getCourseById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        return mapToResponse(course);
    }


    public CourseResponse updateCourse(Long id, CourseUpdateRequest request) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        if (request.getTitle() != null) course.setTitle(request.getTitle());
        if (request.getDescription() != null) course.setDescription(request.getDescription());
        if (request.getInstructorId() != null) course.setInstructorId(request.getInstructorId());

        if (request.getCategoryId() != null) {
            Category category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            course.setCategory(category);
        }

        Course saved = courseRepository.save(course);
        return mapToResponse(saved); // ✅ Make sure this helper exists
    }


    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }

    /**
     * ✅ Get Courses by Category ID
     */
    public List<CourseResponse> getCoursesByCategory(Long categoryId) {
        List<Course> courses = courseRepository.findByCategoryId(categoryId);
        return courses.stream()
                .map(this::mapToResponse)
                .toList();
    }
    public Page<CourseResponse> searchCourses(String keyword, Long categoryId, Long instructorId, Pageable pageable) {
        Specification<Course> spec = CourseSpecification.filterCourses(keyword, categoryId, instructorId);
        Page<Course> page = courseRepository.findAll(spec, pageable);
        return page.map(this::mapToResponse);
    }


}
