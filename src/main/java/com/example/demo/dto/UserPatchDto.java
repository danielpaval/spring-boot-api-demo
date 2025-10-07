package com.example.demo.dto;

import com.example.common.validator.NotBlankIfPresent;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.openapitools.jackson.nullable.JsonNullable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "UserPatchDto")
public class UserPatchDto {

    @NotBlankIfPresent(message = "Name must not be blank if present")
    private JsonNullable<String> name = JsonNullable.undefined();

}
