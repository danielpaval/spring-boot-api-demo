package com.example.demo.controller;

import com.example.demo.dto.CourseDto;
import com.example.demo.generated.dto.UserDto;
import com.example.demo.service.CourseService;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class CourseGraphController {

    private final CourseService courseService;

    private final UserService userService;

    @QueryMapping
    public CourseDto courseById(@Argument Long id) {
        return courseService.getById(id);
    }

    @SchemaMapping(typeName = "Course", field = "createdBy")
    public UserDto createdBy(CourseDto courseDto) {
        return userService.getById(courseDto.getCreatedById());
    }

    @SchemaMapping(typeName = "Course", field = "updatedBy")
    public UserDto updatedBy(CourseDto courseDto) {
        return userService.getById(courseDto.getUpdatedById());
    }

}
