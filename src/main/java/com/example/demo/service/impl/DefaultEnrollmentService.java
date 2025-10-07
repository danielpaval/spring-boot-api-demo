package com.example.demo.service.impl;

import com.example.common.service.AbstractCommonService;
import com.example.demo.entity.Enrollment;
import com.example.demo.entity.EnrollmentId;
import com.example.demo.generated.dto.EnrollmentDto;
import com.example.demo.mapper.EnrollmentMapper;
import com.example.demo.repository.EnrollmentRepository;
import com.example.demo.service.EnrollmentService;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DefaultEnrollmentService extends AbstractCommonService<EnrollmentId, Enrollment, EnrollmentDto, Void> implements EnrollmentService {

    public DefaultEnrollmentService(EnrollmentRepository enrollmentRepository, EnrollmentMapper enrollmentMapper, Validator validator) {
        super(enrollmentRepository, enrollmentMapper, validator);
    }

}
