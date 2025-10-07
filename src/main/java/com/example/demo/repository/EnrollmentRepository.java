package com.example.demo.repository;

import com.example.common.repository.CommonRepository;
import com.example.demo.entity.Enrollment;
import com.example.demo.entity.EnrollmentId;
import org.springframework.stereotype.Repository;

@Repository
public interface EnrollmentRepository extends CommonRepository<EnrollmentId, Enrollment> {
}
