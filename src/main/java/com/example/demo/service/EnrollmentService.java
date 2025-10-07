package com.example.demo.service;

import com.example.common.service.CommonService;
import com.example.demo.entity.Enrollment;
import com.example.demo.entity.EnrollmentId;
import com.example.demo.generated.dto.EnrollmentDto;

public interface EnrollmentService extends CommonService<EnrollmentId, Enrollment, EnrollmentDto, Void> {
}
