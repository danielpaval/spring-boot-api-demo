package com.example.demo.service.criteria;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LegacyCourseCriteria {

    @Builder.Default
    private List<Long> ids = new ArrayList<>();

    private String name;

    private Integer createdById;

    private Integer updatedById;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime startDate;

    public void setId(Long id) {
        this.ids = List.of(id);
    }

}
