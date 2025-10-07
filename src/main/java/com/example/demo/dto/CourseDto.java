package com.example.demo.dto;

import com.example.common.dto.AbstractCommonDto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseDto extends AbstractCommonDto<Long> {

    @Size(min=1, max=50)
    private String name;

    @NotNull
    private String categoryId;

    @NotNull
    private LocalDate startDate;

    private Long teacherId;

    private Long createdById;

    private Long updatedById;

}
