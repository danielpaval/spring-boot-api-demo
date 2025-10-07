package com.example.demo.dto;

import com.example.common.validator.NotBlankIfPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.openapitools.jackson.nullable.JsonNullable;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoursePatchDto {

    @NotBlankIfPresent(message = "Name must not be blank if present")
    private JsonNullable<String> name = JsonNullable.undefined();

    private JsonNullable<String> categoryId = JsonNullable.undefined();

    private JsonNullable<LocalDate> startDate = JsonNullable.undefined();

    private JsonNullable<Long> teacherId = JsonNullable.undefined();

}
