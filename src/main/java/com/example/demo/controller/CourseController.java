package com.example.demo.controller;

import com.example.demo.dto.CourseCriteria;
import com.example.demo.dto.CourseDto;
import com.example.demo.dto.CoursePatchDto;
import com.example.demo.generated.api.CoursesApi;
import com.example.demo.generated.dto.CoursePageDto;
import com.example.demo.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.history.Revision;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CourseController implements CoursesApi {

    private final CourseService courseService;

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CourseDto> saveCourse(CourseDto courseDto) {
        CourseDto createdCourse = courseService.save(courseDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCourse);
    }

    @Override
    public ResponseEntity<CourseDto> findCourseById(Long id) {
        return courseService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<CoursePageDto> findCoursesByCriteria(CourseCriteria criteria, Pageable pageable) {
        Page<CourseDto> courses = courseService.findByCriteria(criteria, pageable);
        return ResponseEntity.ok(
                CoursePageDto.builder()
                        .items(courses.getContent())
                        .number(courses.getNumber())
                        .size(courses.getSize())
                        .count(courses.getTotalElements())
                        .build()
        );
    }

    @Override
    public ResponseEntity<CourseDto> updateCourse(Long id, CourseDto courseDto) {
        return ResponseEntity.ok(courseService.update(id, courseDto));
    }

    @Override
    public ResponseEntity<CourseDto> patchCourse(Long id, CoursePatchDto coursePatchDto) {
        return ResponseEntity.ok(courseService.patch(id, coursePatchDto));
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteCourse(Long id) {
        courseService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Audit endpoints
    @GetMapping("/api/courses/{id}/revisions")
    public ResponseEntity<Page<Revision<Integer, CourseDto>>> getCourseRevisions(
            @PathVariable Long id, Pageable pageable) {
        return courseService.findCourseRevisions(id, pageable)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/api/courses/{id}/revisions/{revisionNumber}")
    public ResponseEntity<Revision<Integer, CourseDto>> getCourseRevision(
            @PathVariable Long id, @PathVariable Integer revisionNumber) {
        return courseService.findCourseRevision(id, revisionNumber)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/api/courses/{id}/revisions/latest")
    public ResponseEntity<Revision<Integer, CourseDto>> getLatestCourseRevision(@PathVariable Long id) {
        return courseService.findLatestCourseRevision(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
