package com.example.demo.repository;

import com.example.common.repository.CommonRepository;
import com.example.demo.entity.Course;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CourseRepository extends CommonRepository<Long, Course>, RevisionRepository<Course, Long, Integer> {

    // Find courses by name (case-insensitive)
    List<Course> findByNameContainingIgnoreCase(String name);

    // Find courses starting after a specific date
    List<Course> findByStartDateAfter(LocalDate date);

    // Find courses starting between two dates
    List<Course> findByStartDateBetween(LocalDate startDate, LocalDate endDate);

    // Custom query to find upcoming courses (starting from today onwards)
    @Query("SELECT c FROM Course c WHERE c.startDate >= CURRENT_DATE ORDER BY c.startDate ASC")
    List<Course> findUpcomingCourses();
}
