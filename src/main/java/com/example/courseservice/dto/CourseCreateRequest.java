package com.example.courseservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseCreateRequest {
    private String title;
    private String description;
    private Long instructorId;
    private Long categoryId;
}
