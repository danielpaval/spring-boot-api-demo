package com.example.demo.service;

import com.example.common.service.CommonService;
import com.example.demo.dto.CourseCriteria;
import com.example.demo.dto.CourseDto;
import com.example.demo.dto.CoursePatchDto;
import com.example.demo.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.history.Revision;

import java.util.Optional;

public interface CourseService extends CommonService<Long, Course, CourseDto, CoursePatchDto> {

    Page<CourseDto> findByCriteria(CourseCriteria criteria, Pageable pageable);

    // Audit methods
    Optional<Page<Revision<Integer, CourseDto>>> findCourseRevisions(Long id, Pageable pageable);
    
    Optional<Revision<Integer, CourseDto>> findCourseRevision(Long id, Integer revisionNumber);
    
    Optional<Revision<Integer, CourseDto>> findLatestCourseRevision(Long id);
}
