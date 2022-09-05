package com.org.walk.course.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CourseWalkPathDto {

    private String walkTime;

    private List<String> walkStopOver;

    private Integer totalDistance;

    private Integer totalTime;

    private List<Integer> coordinates;
}