package com.example.common.dto;

import com.example.demo.dto.CourseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class CoursePageDto  {

  private Integer page;

  private Integer size;

  private Long count;

  private List<CourseDto> items;

}

